package org.bdbazar.crawler;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bdbazar.App;
import org.bdbazar.Util;
import org.bdbazar.dao.JobDetailsDao;
import org.bdbazar.model.JobDetails;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class BdJobsCrawler extends WebCrawler{
	static final Logger log = Logger.getLogger(BdJobsCrawler.class);
	
	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	public static Map<String,JobDetails> jobList = new HashMap<String,JobDetails>();
	private Set<String> visitedPages = new HashSet<String>();
	
	
	private final static Pattern isVisitableUrl = Pattern.compile("^http(.*)\\/(jobsbycategory|jobdetails)(.*)cat=\\d+(.*)");
	private final static Pattern isVisitableUrlv2 = Pattern.compile("^http(.*)\\/jobdetails(.*)");
	private final static Pattern catIdPattern = Pattern.compile("(cat|cid)=(\\d*)");
	private final static Pattern jobIdPattern = Pattern.compile("id=(\\d+)");
	private final static Pattern dayLeftPattern = Pattern.compile("\\(.*\\)");
	
	private final static String catIdUnwantedStringPattern = "[a-zA-Z\\/\\(\\)\\s\\.&@#\\?\\:=]";
	private static String scriptregex = "<(script|style)[^>]*>[^<]*</(script|style)>";	
	private static String commenttregex = "<!--[^>]*-->";
	private static String formTag = "((?s)<form.*?</form>)";
	private static String anchorTag = "((?s)<a.*?</a>)";
	private static String baseUrl = "http://jobs.bdjobs.com/";
	
	private SimpleDateFormat fmt1 = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
	private SimpleDateFormat fmt2 = new SimpleDateFormat("MMMM dd, yyyy");
	private SimpleDateFormat fmt3 = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
	
	public static int jobsite_id = 1;
	
	@Autowired
	JobDetailsDao jobDetailsDao;
	
//	@Override
//	public void onBeforeExit() {
//		log.info("Total Job Link Found :- "+jobList.size());
//		for(String key : jobList.keySet()){
//			JobDetails dt = jobList.get(key);
//			log.info(dt);
//		}
//	}

	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches()
				&& !visitedPages.contains(href)
				&& (isVisitableUrl.matcher(href).matches() || isVisitableUrlv2.matcher(href).matches());
	}

	@Override
	public void visit(Page page) {
		
		String url = page.getWebURL().getURL();		
		log.info(">>>>>Parsing page :- " + url);
		
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			Document doc = Jsoup.parse(html);
			url = url.toLowerCase();
			if(isVisitableUrlv2.matcher(url.toLowerCase()).matches()){
				try{
					Pattern p = Pattern.compile("^.*(jobdetailsimg).*$");
					
					if(p.matcher(url.toLowerCase()).matches()){		
						throw new Exception("Unparseable Page");
					}		
					

					String postingDateData = doc.select(".BDJValues").first().text();
					
					String tmpPostingDate =postingDateData.substring(postingDateData.lastIndexOf(":")+1,postingDateData.length()-1).trim();;

					PrintStream ps = new PrintStream(System.out, true, "UTF-8");
					 ps.println(tmpPostingDate);
					//System.out.println(new String(tmpPostingDate.getBytes("UTF-8"),Charset.forName("UTF-8")));
					
					Element tbl = doc.select("table:eq(3)").first();
					
					Matcher m = catIdPattern.matcher(url.toLowerCase());
					String catId = null;
					String jobId = null;
					
					if (m.find()) {
						catId = m.group().replaceAll(catIdUnwantedStringPattern,"").trim();
					}
					
					if(catId == null ){
						throw new Exception("Category not found");
					}
					
					Matcher m2 = jobIdPattern.matcher(url.toLowerCase());
					if(m2.find()){
						jobId = m2.group().replaceAll("id=", "").trim();
					}
					
					JobDetails det = jobList.get("j"+jobId+"c"+catId);
									
					String data = tbl.html().replaceAll(scriptregex, "").replaceAll(commenttregex, "").replaceAll(formTag, "").replaceAll(anchorTag, "").replaceAll("<.*?>","").replaceAll("&nbsp;","").replaceAll("\\s+"," ").replaceFirst("\\?", "");
					det.setData(new String(data.getBytes("UTF-8"),Charset.forName("UTF-8")));		
					
					
					
					try{
						det.setPosting_date(fmt2.parse(tmpPostingDate));
					}catch(ParseException e1 ){
						try{
							
							det.setPosting_date(fmt2.parse(tmpPostingDate));
						}catch (ParseException e2) {
								det.setPosting_date(fmt3.parse(Util.convertBDdateToDate(tmpPostingDate)));
						}
					}
					ps.println("Parsed Date"+det.getPosting_date());
					jobDetailsDao =(JobDetailsDao) App.getBean("jobDetailsDaoImpl");
					jobDetailsDao.insert(det);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(isVisitableUrl.matcher(url.toLowerCase()).matches()){
				
					Elements tblSet1 = doc.select("table[id=tblSet1] tr:gt(0)");
					String catId = "c";
					Integer categoryId = 0;
					Long jobId = 0l;
					String categoryName = "";
					Matcher m = catIdPattern.matcher(url);
					if (m.find()) {
						catId = m.group().replaceAll(catIdUnwantedStringPattern,"").trim();
						categoryId = new Integer(m.group().replaceAll(catIdUnwantedStringPattern,""));
						categoryName = doc.select("#select > option[value="+categoryId+"]").text().trim();
					}
					
					
					for (Element row : tblSet1) {
						try {
							// get job list of the category page
							Elements tds = row.select("td:not([colspan])");
		
		
							// get details page link 
							Element link = tds.get(1).select("a").first();
							Pattern p = Pattern.compile("^.*(JobDetails|jobdetailsimg|jobdetailsbng).*$");
							
							if(!p.matcher(link.attr("href")).matches()){		
								throw new Exception("invalid posting link");
							}
							
							
							String job_title =  new String(tds.get(1).text().getBytes("UTF-8"),Charset.forName("UTF-8"));//new String(tds.get(1).text().getBytes("UTF-8"),Charset.forName("UTF-8"));
							String education = new String(tds.get(2).text().replaceFirst("\\?", "").getBytes("UTF-8"),Charset.forName("UTF-8"));
							String company = new String(tds.get(0).text().getBytes("UTF-8"),Charset.forName("UTF-8"));
							String postingLink = baseUrl+ link.attr("href").toLowerCase();

						
							Matcher m2 = jobIdPattern.matcher(postingLink);
							if(m2.find()){
								jobId = new Long(m2.group().replaceAll("id=", ""));
							}

		
								Date lastDate = fmt1.parse(tds.get(3).text().replaceAll("\\(.*\\)", ""));
								Matcher mdayLeft = dayLeftPattern.matcher(tds.get(3).text());
		
								Calendar cal = Calendar.getInstance();
								cal.setTime(lastDate);
		
								if (mdayLeft.find()) {
									String tmp = mdayLeft.group().replaceAll("[\\(\\)]", "");
									
									if (tmp.matches("today")) {
										cal.add(Calendar.DAY_OF_YEAR, -10);
									} else if (tmp.matches("tomorrow")) {
										cal.add(Calendar.DAY_OF_YEAR, -10);
									} else {								
										cal.add(Calendar.DAY_OF_YEAR, -new Integer(tmp.replaceAll("[a-zA-Z\\s]", "")));
									}
		
								}
		
								Date postingDate = cal.getTime();

							
							jobList.put("j"+jobId+"c"+catId,new JobDetails(jobId, jobsite_id, categoryId, job_title, company, postingLink, lastDate,postingDate, education,categoryName));
				
						} catch (Exception e) {
							
						}
						
					}
					
				}
			}
		visitedPages.add(url);		
	}	
	
}

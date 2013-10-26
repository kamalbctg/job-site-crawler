import java.util.Date;

import org.bdbazar.App;
import org.bdbazar.crawler.BdJobsCrawlController;
import org.bdbazar.crawler.controller.Controller;
import org.bdbazar.dao.JobDetailsDao;
import org.bdbazar.model.JobDetails;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/META-INF/app-context.xml");
//		JobDetailsDao dao = context.getBean("jobDetailsDaoImpl",JobDetailsDao.class);
//		
//		JobDetails jobDetail = new JobDetails(11l, 1, 11, "job_title", "company", "posting_link", new Date(), "education", "category");
//		jobDetail.setLast_date(new Date());
//		jobDetail.setData("dataatttataaaaaa");
//		dao.insert(jobDetail);
		
		//DbJobsCrawlController controller = context.getBean("crawlController",DbJobsCrawlController.class);
		//controller.run();

		App app = context.getBean("app",App.class);
		//app.s
	}

}

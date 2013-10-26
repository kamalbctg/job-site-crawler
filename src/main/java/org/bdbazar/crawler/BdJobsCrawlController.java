package org.bdbazar.crawler;

import java.io.File;

import org.bdbazar.crawler.controller.Controller;
import org.springframework.stereotype.Component;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Component
public class BdJobsCrawlController implements Controller {
	private CrawlConfig config;
	private String seedUrl = "http://home.bdjobs.com/";
	private int numberOfCrawlers = 7;
	
	public BdJobsCrawlController() {
		String crawlStorageFolder = System.getProperty("java.io.tmpdir")+File.separator+"bdbazar"+File.separator+"dbjobs";
		config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setPolitenessDelay(1000);
		config.setMaxDepthOfCrawling(3);
		config.setMaxPagesToFetch(-1);
		config.setResumableCrawling(false);		
	}
	
	
	public void start() throws Exception {		
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,pageFetcher);		
		CrawlController controller = new CrawlController(config, pageFetcher,robotstxtServer);
		controller.addSeed(seedUrl);
		controller.startNonBlocking(BdJobsCrawler.class, numberOfCrawlers);
	}



	public void setConfig(CrawlConfig config) {
		this.config = config;
	}



	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}



	public void setNumberOfCrawlers(int numberOfCrawlers) {
		this.numberOfCrawlers = numberOfCrawlers;
	}
}

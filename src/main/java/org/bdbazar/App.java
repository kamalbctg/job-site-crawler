package org.bdbazar;

import java.util.List;

import org.apache.log4j.Logger;
import org.bdbazar.crawler.controller.Controller;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App implements ApplicationContextAware{
	static final Logger log = Logger.getLogger(App.class);
	public static ApplicationContext context ;
	private List<Controller> controllerList;
	
	
	
	public App(List<Controller> controllerList) {		
		this.controllerList = controllerList;
	}

	public void run() {
		for(Controller ctrl : controllerList){
			try {
				ctrl.start();
				log.info("Started...");
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
	}	
	
	public static void main(String[] args) {
		ApplicationContext context  = new ClassPathXmlApplicationContext("classpath*:/META-INF/app-context.xml");		
		App app= context.getBean("app",App.class);
		app.setApplicationContext(context);
		app.run();
	}
	
	
	
	
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		context = appContext;	 
	  }
	 
	  public static ApplicationContext getApplicationContext() {
	    return context;
	  }
	  
		 
	  public static Object getBean(String name) {
	    return context.getBean(name);
	  }

}

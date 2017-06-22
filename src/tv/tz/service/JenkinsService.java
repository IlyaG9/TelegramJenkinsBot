package tv.tz.service;

import tv.tz.enums.BuildEnum;
import hudson.cli.CLI;


public class JenkinsService {
	
	private JenkinsService(){
		
	}
	
	private static JenkinsService instance=null; 
	
	public static JenkinsService getInstance(){
		if(instance==null){
			instance=new JenkinsService();
		}
		return instance;
	}
	
	public void build(BuildEnum build) throws Exception {
		String[] args={"-s","http://192.168.0.200:28080/jenkins/","build",build.getName()};
		CLI._main(args);
	}

}

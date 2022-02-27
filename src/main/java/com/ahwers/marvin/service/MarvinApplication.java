package com.ahwers.marvin.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/service")
public class MarvinApplication extends Application {

	public Set<Object> getSingletons() {
		Set<Object> set = new HashSet<>();
	
		set.add(new MarvinService());
		
		return set;
	}
	
}
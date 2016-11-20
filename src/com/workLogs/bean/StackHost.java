package com.workLogs.bean;

import java.util.ArrayList;
import java.util.List;

public class StackHost {
	public List<Host> hosts;
	
	public StackHost(){
		this.hosts=new ArrayList<Host>();
	}
	/**
	 * @param host the host to add
	 */
	public void addHost(Host host) {
		this.hosts.add(host);
	}
	/**
	 * @return the host
	 */
	public List<Host> getHosts() {
		return hosts;
	}

	/**
	 * @param host the host to set
	 */
	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}
	
}

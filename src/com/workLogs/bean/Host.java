package com.workLogs.bean;

public class Host {
	private String host;
	private Integer tiempo;
	
	public Host(){
		String host=new String();
		Integer tiempo= new Integer(0);
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the tiempo
	 */
	public Integer getTiempo() {
		return tiempo;
	}

	/**
	 * @param tiempo the tiempo to set
	 */
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}
	
}

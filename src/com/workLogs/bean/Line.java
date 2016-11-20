package com.workLogs.bean;

public class Line {
	private String origen;
	private String destino;
	private Integer tiempo;
	
	public Line(){
		String origen=new String();
		String destino=new String();
		Integer tiempo= new Integer(0);
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
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

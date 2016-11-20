package com.workLogs.workerLogs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.workLogs.bean.Host;
import com.workLogs.bean.Line;
import com.workLogs.bean.StackHost;

public class Analitics extends Thread {
	
	private String hostName;
	private List<Line> linesBean;
	private String ruta;
	private String separator;
	
	public Analitics(String hostName, List<Line> linesBean,String ruta,String separator){
		this.hostName=hostName;
		this.linesBean=linesBean;
		this.ruta=ruta;
		this.separator=separator;
		
	} 
	
	@Override
	 public void run() {
		 workHost(this.linesBean,this.hostName);
     }
	
	public boolean putData(StackHost pila,String nameFile) throws IOException {
		StringBuilder dataNew = new StringBuilder();
		for(Host item : pila.getHosts())
			dataNew.append(item.getHost() + " " + item.getTiempo() + "\n");
		BufferedWriter writer = new BufferedWriter( new FileWriter( nameFile, true));
		writer.write( dataNew.toString());
		writer.close();
		return true;
	}
	
	
	public Host workHost(List<Line> linesBean, String hostName) {
		StackHost out = new StackHost();
		StackHost in = new StackHost();
		for(Line item : linesBean){
			System.out.println(item.getDestino()+"----"+hostName);
			if(item.getOrigen().equals(hostName)){
				//Debug
				//System.out.println("entraOUT__________________");
				Host line=new Host();
				line.setHost(item.getDestino());
				line.setTiempo(item.getTiempo());
				out.addHost(line);
			}
			if(item.getDestino().equals(hostName)){
				//Debug
				//System.out.println("entraIn_____________");
				Host line=new Host();
				line.setHost(item.getOrigen());
				line.setTiempo(item.getTiempo());
				in.addHost(line);
			}
		}
		try {
			(new File(this.ruta + this.separator + "OUT" + this.separator)).mkdirs();
			putData(out, this.ruta + this.separator + "OUT" + this.separator + hostName );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			(new File(ruta + separator + "IN" + separator)).mkdirs();
			putData(in, ruta + separator + "IN" + separator + hostName );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the linesBean
	 */
	public List<Line> getLinesBean() {
		return linesBean;
	}

	/**
	 * @param linesBean the linesBean to set
	 */
	public void setLinesBean(List<Line> linesBean) {
		this.linesBean = linesBean;
	}

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	/**
	 * @return the separator
	 */
	public String getSeparator() {
		return separator;
	}


	/**
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	
}

package com.workLogs.workerLogs;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.workLogs.bean.Line;


public class Etl {
	
	public String getLastLine(String file,int lineas) {
		 RandomAccessFile fileHandler = null;
		 
		try {
	        fileHandler = new RandomAccessFile( file.trim(), "r" );
	        lineas++;
	        
	        lineas++;
	        long fileLength = fileHandler.length() - 1;
	        StringBuilder sb = new StringBuilder();

	        for(long filePointer = fileLength; filePointer != -1; filePointer--){
	            fileHandler.seek( filePointer );
	            int readByte = fileHandler.readByte();

	            if( readByte == 0xA ) {
	                if( filePointer == fileLength ) {
	                    continue;
	                }
	                if( lineas <1 )
	                	break;
	                else
	                	lineas--;

	            } else if( readByte == 0xD ) {
	                if( filePointer == fileLength - 1 ) {
	                    continue;
	                }
	                if( lineas <1 )
	                	break;
	                else
	                	lineas--;
	            }

	            sb.append( ( char ) readByte );
	        }

	        String lastLine = sb.reverse().toString();
	        return lastLine;
	    } catch( java.io.FileNotFoundException e ) {
	        e.printStackTrace();
	        return null;
	    } catch( java.io.IOException e ) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        if (fileHandler != null )
	            try {
	                fileHandler.close();
	            } catch (IOException e) {
	            	e.printStackTrace();
	            }
	    }
	}
	
	
	public List<Line> workLines(String lines) {
		List<Line> linesList = new ArrayList<Line>();
		for(String linea : lines.split("\\r?\\n")){
			linesList.add(workLine(linea));
		}
		return linesList;
	}
	
	
	public Line workLine(String line) {
		Pattern regex = Pattern.compile("(^|\\s)\\s*([0-9]+) (\\S+) (\\S+).*$");
		 Matcher matcher = regex.matcher(line);
		 Line lineBean= new Line();
		 while(matcher.find()){
			 lineBean.setTiempo(Integer.valueOf(matcher.group(2)));
			 lineBean.setOrigen(matcher.group(3).trim());
			 lineBean.setDestino(matcher.group(4).trim());
		}
		return lineBean;
	}
}

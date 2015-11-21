package com.java.josefd8.misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Reune funciones para la conversion de datos.
 * @author José Fernández
 */
public class dataTypeFormater {
	
	private String dataIn;
	private String dataOut = "";
	private boolean error;
	private String errorDescription;
	private String formatType;

	public String getFormatType() {
		return formatType;
	}


	public dataTypeFormater() {
		super();
	}

	
	public String getDataIn() {
		return dataIn;
	}


	public void setDataIn(String dataIn) {
		this.dataIn = dataIn;
	}


	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}


	public boolean isError() {
		return error;
	}


	public void setError(boolean error) {
		this.error = error;
	}


	public String getErrorDescription() {
		return errorDescription;
	}


	public String getDataOut() {
		return dataOut;
	}
	

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}


	public void decode(){
		
		try {
			
			if (this.getFormatType().matches("HEX to DEC")) {
				String data[] = dataIn.split(",");
				
				for (String s : data) {
					
					dataOut = dataOut + (Integer.parseInt(s,16)) + ",";
				}
				
				this.dataOut = dataOut.substring(0, dataOut.length() - 1);
			}
			

			
			if (this.getFormatType().matches("DEC to HEX")) {
				String data[] = dataIn.split(",");
				
				for (String s : data) {
					dataOut = dataOut + Integer.toHexString(Integer.parseInt(s)) + ",";
				}
				
				this.dataOut = dataOut.substring(0, dataOut.length() - 1).toUpperCase();
			}
				
			
			
			if (this.getFormatType().matches("ASCII to HEX")) {
				byte[] data = dataIn.getBytes();
				
				for (byte s : data) {
					dataOut = dataOut + String.format("%02X", s) + ",";
				}
				
				this.dataOut = dataOut.substring(0, dataOut.length() - 1).toUpperCase();
			}
				
			
			
			if (this.getFormatType().matches("ASCII to DEC")) {
				byte[] data = dataIn.getBytes();
				
				for (byte s : data) {
					dataOut = dataOut + (int)s + ",";
				}
				
				this.dataOut = dataOut.substring(0, dataOut.length() - 1).toUpperCase();
			}
				
			
			
			if (this.getFormatType().matches("HEX to ASCII")){
				String data[] = dataIn.split(",");
				List<Byte> dataToByte = new ArrayList<Byte>();
				
				for (String s : data) {
					dataToByte.add(hexStringToByteArray(s));
				}
				
				dataOut = new String(toByteArray(dataToByte));
			}
				
		
			
			if (this.getFormatType().matches("DEC to ASCII")){
				
				String data[] = dataIn.split(",");
				List<Byte> dataToByte = new ArrayList<Byte>();
				
				for (String s : data) {
					int a = Integer.parseInt(s);
					dataToByte.add((byte) a);
				}
				
				dataOut = new String(toByteArray(dataToByte));				
				
			}						
			
		} catch (Exception e) {
			this.setError(true);
			this.setErrorDescription(e.getMessage());
		}
		
	}
	
	private byte[] toByteArray(List<Byte> in) {
	    final int n = in.size();
	    byte ret[] = new byte[n];
	    for (int i = 0; i < n; i++) {
	        ret[i] = in.get(i);
	    }
	    return ret;
	}
	
	private byte hexStringToByteArray(String s){
	    int len = s.length();
	    	 byte[] data = new byte[len / 2];
			    for (int i = 0; i < len; i += 2) {
			        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
			                             + Character.digit(s.charAt(i+1), 16));
			    }
			    return data[0];
			    
	}
}

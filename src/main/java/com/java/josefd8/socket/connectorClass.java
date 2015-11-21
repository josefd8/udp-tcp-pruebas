package com.java.josefd8.socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.naming.directory.InvalidAttributeIdentifierException;

import com.java.josefd8.misc.fieldsValidator;


/**
 * Esta clase proporciona una capa de abstraccion para la creacion de sockets TCP y UDP.
 * @author José Fernández
 */
public class connectorClass implements tcpServer.TCPServerListener, tcpClient.TCPClientListener, udpServer.UDPServerListener, udpClient.UDPClientListener{
	
	private String type;
	private int port;
	private String IP;
	private String mode;
	private tcpServer TCPServer;
	private tcpClient TCPClient;
	private udpServer UDPServer;
	private udpClient UDPClient;
	private List<connectorClassListener> listeners = new ArrayList<connectorClassListener>();
	private fieldsValidator fv;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) throws InvalidAttributeIdentifierException {
		if (fv.validatePort(String.valueOf(port))) {
			this.port = port;
		} else {
			throw new InvalidAttributeIdentifierException();
		}
		
	}
	public String getIP() {
		return IP;
	}
	
	public void setIP(String iP) throws InvalidAttributeIdentifierException {
		if (fv.validateIPFormat(iP)) {
			this.IP = iP;
		} else {
			throw new InvalidAttributeIdentifierException();
		}
		
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	/*
	 * Creates a new instance of connectorClass
	 */
	public connectorClass(connectorClassListener listener) {
		super();
		listeners.add(listener);
		fv = new fieldsValidator();
	}
	
	
	/**
	 * connectionClass methods
	 */
	public void startConnection() throws UnknownHostException, IllegalArgumentException, IOException{
		
		if (this.type.matches("TCP")) {
			if (this.mode.matches("Server") ) {
				TCPServer = new tcpServer(this, "");
				TCPServer.setPuertoEscucha(this.port);
				TCPServer.setIPEscucha(this.IP );
				TCPServer.startTcpServer();
			} else {
				TCPClient = new tcpClient(this.port,this.IP,this);
				TCPClient.startRemoteConection();
			}
			
		} 
		
		if (this.type.matches("UDP")) {
			if (this.mode.matches("Server") ) {
				UDPServer = new udpServer(this);
				UDPServer.setIPEscucha(IP);
				UDPServer.setPuertoEscucha(port);
				UDPServer.startUDPServer();
			} else {
				UDPClient = new udpClient(this);
				UDPClient.startRemoteConection(this.IP, this.port);
			}	
		} 
	}
	public void stopConnection() throws IOException {
		
		if (this.type.matches("TCP")) {
			if (this.mode.matches("Server") ) {
				this.TCPServer.stopTcpServer();
			} else {
				this.TCPClient.terminateRemoteConection();
			}	
		} 
		
		if (this.type.matches("UDP")) {
			if (this.mode.matches("Server") ) {
				this.UDPServer.stopUDPServer();
			} else {
				this.UDPClient.terminateRemoteConection();
			}	
		} 
		
		
	}
	public void sendData(String ipAdress, String message) throws IOException{
		
		if (this.type.matches("TCP")) {
			if (this.mode.matches("Server") ) {
				this.TCPServer.sendData(ipAdress, message);
			} else {
				this.TCPClient.sendData(message);
			}	
		} 
		
		if (this.type.matches("UDP")) {
			String[] data;
			data = ipAdress.split(":"); 
			if (this.mode.matches("Server")) {	
				this.UDPServer.sendData(InetAddress.getByName(data[0]), Integer.parseInt(data[1]), message.getBytes());
			} else {
				this.UDPClient.sendData(message.getBytes(), data[0], Integer.parseInt(data[1]));
			}	
		} 
		
		
	}
	public void sendData(String ipAdress, byte[] message) throws IOException{
		
		if (this.type.matches("TCP")) {
			if (this.mode.matches("Server") ) {
				this.TCPServer.sendData(ipAdress, message);
			} else {
				this.TCPClient.sendData(message);
			}	
		} 
		
		if (this.type.matches("UDP")) {
			String[] data;
			data = ipAdress.split(":"); 
			if (this.mode.matches("Server")) {
				InetAddress address;
				address = InetAddress.getByName(data[0]);
				this.UDPServer.sendData(address, Integer.parseInt(data[1]), message);
			} else {
				this.UDPClient.sendData(message, data[0], Integer.parseInt(data[1]));
			}	
		} 
	}
	
	
	/**
	 * Interface & interface definitions
	 * External classes must implement this interface to receive this events
	 *
	 */
	public static interface connectorClassListener extends EventListener{
		void newMessage(byte b[], String id, String identificador,Socket remote);
		void newClientConnected(String id, String identificador);
		void clientDisconnected(String id, String identificador);
		void errorOcurred(String message);
	}	
	protected synchronized void newMessage( byte b[],  String id,  String identificador, Socket remote){
		for (int i=0;i<listeners.size();i++) {
			listeners.get(i).newMessage(b, id, identificador,remote);	    
		}
	}
	protected synchronized void newClientConnected(String id, String identificador){
		for (int i=0;i<listeners.size();i++) {
			listeners.get(i).newClientConnected(id, identificador);    
		}
	}    
	protected synchronized void clientDisconnected(String id, String identificador){
		for (int i=0;i<listeners.size();i++) {
			listeners.get(i).clientDisconnected(id, identificador);
		}
	}
	protected synchronized void errorOcurred(String message){
		for (int i=0;i<listeners.size();i++) {
			listeners.get(i).errorOcurred(message);
		}
	}
	
	/**
	 * Events from the tcpServer class
	 */
	public void onMessageReceived(byte[] b, String id, String identificador,
			Socket remote) {
		this.newMessage(b, id, identificador, remote);
		
	}
	public void onClientConnected(String id, String identificador) {
		this.newClientConnected(id, identificador);
		
	}
	public void onClientDisconnected(String id, String identificador) {
		this.clientDisconnected(id, identificador);
		
	}
	
	/**
	 * Events from the tcpClient class
	 */
	public void onMessageReceived(byte[] b, String id) {
		this.newMessage(b, id, "", null);
		
	}
	public void onErrorOcurredTcpClient(String message) {
		this.errorOcurred(message);
	} 
	
	/**
	 * Events from the udpServer class
	 */
	public void onMessageReceivedUDPServer(byte[] b, String id) {
		this.newMessage(b, id, "", null);
		
	}
	
	/**
	 * Events from the udpClient class
	 */
	public void onMessageReceivedUDPClient(byte[] b, String id) {
		this.newMessage(b, id, "", null);
		
	}
	
	
	
}

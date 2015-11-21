package com.java.josefd8.main;
import java.awt.EventQueue;

import javax.naming.directory.InvalidAttributeIdentifierException;
import javax.swing.JFrame;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.java.josefd8.logging.log_file;
import com.java.josefd8.misc.dataTypeFormater;
import com.java.josefd8.socket.connectorClass;
import com.java.josefd8.window.windowParameters;
import com.java.josefd8.window.windowParametersParser;

import javax.swing.JTextArea;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.UIManager;

import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

public class principal implements connectorClass.connectorClassListener, ClipboardOwner{

	private static JFrame frmUdptcpPrueba;
	private JTextField textField_Local_IP;
	private JTextField textField_Local_Port;
	private JTextField textField_Remote_IP;
	private JTextField textField_Remote_Port;
	private JComboBox<String> comboBox_TCPclients;
	private JTextArea textArea_in;
	private connectorClass connector;
	private JComboBox<String> comboBox_Protocol;
	private JComboBox<String> comboBox_Mode;
	private JButton ConnectButton;
	private log_file log;
	private JComboBox<String> comboBox_Data_Type_Send;
	private JComboBox<String> comboBox_Format;
	private JTextArea textArea_toSend;
	private JTextArea textArea_Status;
	private long byteCount = 0;
	private JLabel lblBytes;
	private JScrollPane sp;
	private JComboBox<String> comboBox_Data_Type_Receive;
	private JLabel lblNewLabel_Connected_Clients;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					principal window = new principal();
					window.frmUdptcpPrueba.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public principal() {
		initializeUI();
		connector = new connectorClass(this);
		log = new log_file();
		loadConfig();
		protocolModeChanged();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeUI() {
		frmUdptcpPrueba = new JFrame();
		frmUdptcpPrueba.setBackground(UIManager.getColor("window"));
		frmUdptcpPrueba.setResizable(false);
		frmUdptcpPrueba.setForeground(SystemColor.control);
		frmUdptcpPrueba.setTitle("UDP/TCP Prueba 3.0");
		frmUdptcpPrueba.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				saveConfig();
			}
		});
		frmUdptcpPrueba.setBounds(100, 100, 932, 395);
		frmUdptcpPrueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUdptcpPrueba.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(9, 11, 203, 191);
		panel.setToolTipText("");
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frmUdptcpPrueba.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField_Local_IP = new JTextField();
		textField_Local_IP.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Local_IP.setBounds(87, 11, 106, 20);
		
		panel.add(textField_Local_IP);
		textField_Local_IP.setColumns(10);
		
		textField_Local_Port = new JTextField();
		textField_Local_Port.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Local_Port.setBounds(107, 31, 86, 20);
		panel.add(textField_Local_Port);
		textField_Local_Port.setColumns(10);
		
		textField_Remote_IP = new JTextField();
		textField_Remote_IP.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Remote_IP.setBounds(87, 62, 106, 20);
		panel.add(textField_Remote_IP);
		textField_Remote_IP.setColumns(10);
		
		textField_Remote_Port = new JTextField();
		textField_Remote_Port.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Remote_Port.setBounds(107, 82, 86, 20);
		panel.add(textField_Remote_Port);
		textField_Remote_Port.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Local IP:");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBounds(10, 14, 67, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Local Port:");
		lblNewLabel_1.setBounds(10, 34, 87, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Remote IP:");
		lblNewLabel_2.setBounds(10, 65, 75, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Remote Port:");
		lblNewLabel_3.setBounds(10, 85, 99, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblProtocolo = new JLabel("Protocol:");
		lblProtocolo.setBounds(10, 113, 57, 14);
		panel.add(lblProtocolo);
		
		comboBox_Protocol = new JComboBox<String>();
		comboBox_Protocol.setBounds(10, 129, 57, 20);
		comboBox_Protocol.addItem("TCP");
		comboBox_Protocol.addItem("UDP");
		panel.add(comboBox_Protocol);
		
		JLabel lblModo = new JLabel("Mode:");
		lblModo.setBounds(87, 113, 46, 14);
		panel.add(lblModo);
		
		comboBox_Mode = new JComboBox<String>();	
		comboBox_Mode.setBounds(86, 129, 75, 20);
		comboBox_Mode.addItem("Server");
		comboBox_Mode.addItem("Client");
		
		comboBox_Mode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {	
				protocolModeChanged();	
			}
		});
		
		panel.add(comboBox_Mode);

		ConnectButton = new JButton("Connect");
		ConnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainButtonPressed();
			}
		});
		ConnectButton.setBounds(44, 160, 114, 23);
		panel.add(ConnectButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(223, 11, 203, 191);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "State", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmUdptcpPrueba.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		comboBox_TCPclients = new JComboBox<String>();
		comboBox_TCPclients.setBounds(10, 160, 183, 20);
		panel_1.add(comboBox_TCPclients);
		
		textArea_Status = new JTextArea();
		textArea_Status.setWrapStyleWord(true);
		textArea_Status.setEditable(false);
		textArea_Status.setBackground(UIManager.getColor("CheckBox.background"));
		textArea_Status.setLineWrap(true);
		textArea_Status.setBounds(10, 21, 183, 103);
		panel_1.add(textArea_Status);
		
		lblNewLabel_Connected_Clients = new JLabel("Connected clients: 0");
		lblNewLabel_Connected_Clients.setBounds(10, 135, 183, 14);
		panel_1.add(lblNewLabel_Connected_Clients);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(9, 202, 417, 153);
		frmUdptcpPrueba.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Send format:");
		lblNewLabel_5.setBounds(10, 22, 81, 14);
		panel_2.add(lblNewLabel_5);
		
		comboBox_Data_Type_Send = new JComboBox<String>();
		comboBox_Data_Type_Send.setBounds(91, 19, 103, 20);
		comboBox_Data_Type_Send.addItem("Binay");
		comboBox_Data_Type_Send.addItem("ASCII");
		panel_2.add(comboBox_Data_Type_Send);
		
		JButton btnNewButton_Send = new JButton("Send");
		btnNewButton_Send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendButtonPressed();
			}
		});
		btnNewButton_Send.setBounds(318, 18, 89, 23);
		panel_2.add(btnNewButton_Send);
		
		comboBox_Format = new JComboBox<String>();
		comboBox_Format.setBounds(10, 122, 295, 20);
		comboBox_Format.addItem("HEX to DEC");
		comboBox_Format.addItem("DEC to HEX");
		comboBox_Format.addItem("ASCII to HEX");
		comboBox_Format.addItem("ASCII to DEC");
		comboBox_Format.addItem("HEX to ASCII");
		comboBox_Format.addItem("DEC to ASCII");

		panel_2.add(comboBox_Format);
		
		JButton btnNewButton_2 = new JButton("Convert");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				convertButton();
			}
		});
		btnNewButton_2.setBounds(318, 121, 89, 23);
		panel_2.add(btnNewButton_2);
		
		textArea_toSend = new JTextArea();
		textArea_toSend.setLineWrap(true);
		textArea_toSend.setBounds(10, 46, 397, 71);
		JScrollPane sp0 = new JScrollPane(textArea_toSend);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textArea_toSend, popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Copy");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				copy(textArea_toSend);
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmPaste = new JMenuItem("Paste");
		mntmPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paste(textArea_toSend);
			}
		});
		popupMenu.add(mntmPaste);
		
		JMenuItem mntmSlectAll = new JMenuItem("Select all");
		mntmSlectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll(textArea_toSend);
			}
		});
		popupMenu.add(mntmSlectAll);
		sp0.setBounds(10, 46, 397, 71);
		panel_2.add(sp0);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Incoming data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(436, 10, 470, 344);
		frmUdptcpPrueba.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		textArea_in = new JTextArea();
		textArea_in.setWrapStyleWord(true);
		textArea_in.setLineWrap(true);
		textArea_in.setBounds(12, 22, 448, 285);
		sp = new JScrollPane(textArea_in);
		
		JPopupMenu popupMenu_1 = new JPopupMenu();
		addPopup(textArea_in, popupMenu_1);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mntmCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				copy(textArea_in);
			}
		});
		popupMenu_1.add(mntmCopy);
		
		JMenuItem mntmPaste_1 = new JMenuItem("Paste");
		mntmPaste_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paste(textArea_in);
			}
		});
		popupMenu_1.add(mntmPaste_1);
		
		JMenuItem mntmSelectAll = new JMenuItem("Select all");
		mntmSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll(textArea_in);
			}
		});
		popupMenu_1.add(mntmSelectAll);
		sp.setBounds(12, 22, 448, 285);
		panel_3.add(sp);
		
		
		lblBytes = new JLabel("0 bytes");
		lblBytes.setBounds(12, 318, 105, 14);
		panel_3.add(lblBytes);
		
		JButton btnNewButton_clear = new JButton("Clear");
		btnNewButton_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_in.setText("");
			}
		});
		btnNewButton_clear.setBounds(371, 314, 89, 23);
		panel_3.add(btnNewButton_clear);
		
		JLabel lblNewLabel_4 = new JLabel("Receive format");
		lblNewLabel_4.setBounds(103, 318, 107, 14);
		panel_3.add(lblNewLabel_4);
		
		comboBox_Data_Type_Receive = new JComboBox<String>();
		comboBox_Data_Type_Receive.setBounds(199, 315, 103, 20);
		comboBox_Data_Type_Receive.addItem("Binay");
		comboBox_Data_Type_Receive.addItem("ASCII");
		panel_3.add(comboBox_Data_Type_Receive);
		
		frmUdptcpPrueba.setVisible(true);
	}

	/**
	 * Carga lo campos de la ventana principal desde un archivo en disco previamente
	 * guardado.
	 */
	private void loadConfig(){
		
		windowParametersParser wpp = new windowParametersParser();
		windowParameters wp = new windowParameters();
		wp = wpp.getFromXML();
		
		if (!(wp == null)) {
			textField_Local_IP.setText(wp.localIP);
			textField_Local_Port.setText(wp.localPort);
			textField_Remote_IP.setText(wp.remoteIP);
			textField_Remote_Port.setText(wp.remotePort);
			textArea_toSend.setText(wp.sendData);
			comboBox_Protocol.setSelectedItem(wp.type);
			comboBox_Data_Type_Send.setSelectedItem(wp.dataType_Send);
			comboBox_Data_Type_Receive.setSelectedItem(wp.dataType_Receive);
			comboBox_Format.setSelectedItem(wp.format);
			comboBox_Mode.setSelectedItem(wp.mode);
		}
	
	}
	
	/**
	 * Salva los campos de la ventana actual en un archivo de configuracion en disco.
	 */
	private void saveConfig(){
		
		windowParameters wp = new windowParameters();
		wp.dataType_Send = (String) comboBox_Data_Type_Send.getSelectedItem();
		wp.dataType_Receive = (String) comboBox_Data_Type_Receive.getSelectedItem();
		wp.format = (String) comboBox_Format.getSelectedItem();
		wp.localIP = textField_Local_IP.getText();
		wp.localPort = textField_Local_Port.getText();
		wp.mode = (String) comboBox_Mode.getSelectedItem();
		wp.remoteIP = textField_Remote_IP.getText();
		wp.remotePort = textField_Remote_Port.getText();
		wp.sendData = textArea_toSend.getText();
		wp.type = (String) comboBox_Protocol.getSelectedItem();
		
		windowParametersParser wpp = new windowParametersParser();
		wpp.saveInXML(wp);
		
	}
	
	/**
	 * Inicia el proceso de connexion o desconexion segun los parametros propocionados
	 * por el usuario.
	 */
	private void mainButtonPressed() {
		
		String c = ConnectButton.getText();
		String type = (String) comboBox_Protocol.getSelectedItem();
		String mode = (String) comboBox_Mode.getSelectedItem();
		javax.swing.JComponent[] array = {textField_Local_IP, textField_Local_Port, textField_Remote_IP, textField_Remote_Port, comboBox_Protocol, comboBox_Mode};
		
		if (c.matches("Connect")) {
			
				connector.setType(type);
				connector.setMode(mode);
				
				if (type.matches("TCP")) {
					if (mode.matches("Server")) {
						try {
							connector.setIP(textField_Local_IP.getText());
						} catch (InvalidAttributeIdentifierException e) {
							textArea_Status.setText("Invalid local IP address format");
							return;
						}
						
						try {
							connector.setPort(Integer.parseInt(textField_Local_Port.getText()));
						} catch (NumberFormatException e) {
							textArea_Status.setText("Invalid format for local port");
							return;
						} catch (InvalidAttributeIdentifierException e) {
							textArea_Status.setText("Local port is not in the valid range (1-65556)");
							return;
						}
					} else {
						try {
							connector.setIP(textField_Remote_IP.getText());
						} catch (InvalidAttributeIdentifierException e) {
							textArea_Status.setText("Invalid remote IP address format");
							return;
						}
						try {
							connector.setPort(Integer.parseInt(textField_Remote_Port.getText()));
						} catch (NumberFormatException e) {
							textArea_Status.setText("Invalid format for remote port");
							return;
						} catch (InvalidAttributeIdentifierException e) {
							textArea_Status.setText("Remote port is not in the valid range (1-65556)");
							return;
						}
					}
					
				} else {

					try {
						connector.setIP(textField_Local_IP.getText());
					} catch (InvalidAttributeIdentifierException e) {
						textArea_Status.setText("Invalid local IP address format");
						return;
					}
					try {
						connector.setPort(Integer.parseInt(textField_Local_Port.getText()));
					} catch (NumberFormatException e) {
						textArea_Status.setText("Invalid format for local port");
						return;
					} catch (InvalidAttributeIdentifierException e) {
						textArea_Status.setText("Local port is not in the valid range (1-65556)");
						return;
					}
				}
				
			try {
				connector.startConnection();
				textArea_Status.setText("Connected!");
				ConnectButton.setText("Disconnect");
				lockControls(array);
			} catch (UnknownHostException e) {
				textArea_Status.setText("Error: " + e.getMessage());
			} catch (IllegalArgumentException e) {
				textArea_Status.setText("Error: " + e.getMessage());	
			} catch (IOException e) {
				textArea_Status.setText("Error: " + e.getMessage());	
			}
			
		} else {

			try {
				connector.stopConnection();
				textArea_Status.setText("Disconnected!");
				ConnectButton.setText("Connect");
				unlockControls(array);
				protocolModeChanged();
				comboBox_TCPclients.removeAllItems();
				lblNewLabel_Connected_Clients.setText("Connected clients: " + String.valueOf(comboBox_TCPclients.getItemCount()));
				
			} catch (IOException e) {
				textArea_Status.setText("Error: " + e.getMessage());
			}
		}
		
			
	}
	
	/**
	 * Envia la cadena a la direccion y puerto remoto especificados.
	 */
	private void sendButtonPressed(){
		String format = (String) comboBox_Data_Type_Send.getSelectedItem();		
		
		if (!textArea_toSend.getText().matches("")) {
			try {
				if (connector.getMode().matches("Server")) {
					if (format.matches("ASCII")) {
						connector.sendData((String) comboBox_TCPclients.getSelectedItem(), textArea_toSend.getText());	
					} else {
						connector.sendData((String) comboBox_TCPclients.getSelectedItem(), convertoToString(textArea_toSend.getText()));
					}
					
				} else {
					if (format.matches("ASCII")) {
						connector.sendData(textField_Remote_IP.getText() + ":" + textField_Remote_Port.getText(), textArea_toSend.getText());
					} else {
						connector.sendData(textField_Remote_IP.getText() + ":" + textField_Remote_Port.getText(),convertoToString(textArea_toSend.getText()));
					}			
					
				}	
			} catch (IOException e) {
				textArea_Status.setText("Error: " + e.getMessage());
			} catch ( NullPointerException e) {
				textArea_Status.setText("Error: " + e.getMessage());
			}
		}

	}
	
	
	/**
	 * Events fired by connectorClass
	 */
	public void newMessage(byte[] b, String id, String identificador, Socket remote) {
		
		String currentTimeSTR = "";
		Date currentTime = new Date();
	    SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
	    SimpleDateFormat f2 = new SimpleDateFormat("hh:mm:ss");
	    currentTimeSTR = f1.format(currentTime) + " " + f2.format(currentTime);
		
		String c = "New message from: " + id.replace("/", "") + " (" + currentTimeSTR + ") :" + "\n";
		
		String format =  (String) comboBox_Data_Type_Receive.getSelectedItem();
		
		if (format.matches("ASCII")) {
			c = c + ">" + new String(b);
		} else {
			c = c + ">" + convertoToByte(b);
		}
		

		textArea_in.append(c + "\n\r");
		textArea_in.append("" + "\n\r");
		log.escribir(c,"");

		//In UDP-Server mode, clients don't "establish" a connection with the UDP server
		//which makes it harder to populate the comboBox_TCPclients in this mode. In this case, every time
		//a new message arrives, we search for the remote IP:port in comboBox_TCPclients. If it's
		//not there, it`s added to the list.	
		int n = comboBox_TCPclients.getItemCount();
		boolean isFound = false;
		
		if (connector.getMode().matches("Server") & connector.getType().matches("UDP")) {
			for (int i = 0; i < n; i++) {
				String idremote = (String) comboBox_TCPclients.getItemAt(i);
				if (idremote.matches(id)) {
					isFound = true;
				}
			}	
			
			if (!isFound) {
				comboBox_TCPclients.addItem(id);
				lblNewLabel_Connected_Clients.setText("Connected clients: " + String.valueOf(comboBox_TCPclients.getItemCount()));
			}
		}
		
		
		this.byteCount = this.byteCount + b.length;
		lblBytes.setText(this.byteCount + " bytes");
		
		//Scroll jscrollpane to bottom
		textArea_in.getCaret().setDot(textArea_in.getText().length());
		sp.scrollRectToVisible(textArea_in.getVisibleRect());
		
	}
	public void newClientConnected(String id, String identificador) {
		comboBox_TCPclients.addItem(id);
		lblNewLabel_Connected_Clients.setText("Connected clients: " + String.valueOf(comboBox_TCPclients.getItemCount()));
	}
	public void clientDisconnected(String id, String identificador) {
		
		int n = comboBox_TCPclients.getItemCount();
		
		for (int i = 0; i < n; i++) {
			String c = (String) comboBox_TCPclients.getItemAt(i);
			if (c.matches(id)) {
				comboBox_TCPclients.removeItemAt(i);
				lblNewLabel_Connected_Clients.setText("Connected clients: " + String.valueOf(comboBox_TCPclients.getItemCount()));
			}
		}	
	}
	
	public void errorOcurred(String message) {
		//In TCP Client mode, this method is fired every time the remote TCP server is closed. The mainButtonPressed
		//method is called to force the disconnection, and to make aware the user of this incident
		mainButtonPressed();
		
	}

	private void protocolModeChanged(){
		
		String item =(String) comboBox_Mode.getSelectedItem();
		javax.swing.JComponent[] arrayRemote = {textField_Remote_IP, textField_Remote_Port};
		javax.swing.JComponent[] arrayLocal = {textField_Local_IP, textField_Local_Port};
		
		if (item.matches("Server")) {
			comboBox_TCPclients.setEnabled(true);
			lockControls(arrayRemote);
			unlockControls(arrayLocal);
		} else {
			comboBox_TCPclients.setEnabled(false);
			lockControls(arrayLocal);
			unlockControls(arrayRemote);
		}		
		
	}

	/**
	 * Dado un arreglo de objetos tipo JComponent, itera cada elemento
	 * y llama a su metodo setEnabled asignandole false
	 * @param controls
	 */
	private void lockControls(javax.swing.JComponent[] controls){
		for (javax.swing.JComponent o : controls) {
			o.setEnabled(false);
		}
	}
	
	/**
	 * Dado un arreglo de objetos tipo JComponent, itera cada elemento
	 * y llama a su metodo setEnabled asignandole true
	 * @param controls
	 */
	private void unlockControls(javax.swing.JComponent[] controls){
		
		for (javax.swing.JComponent o : controls) {
			o.setEnabled(true);
		}
		
	}

	private void convertButton(){
		
		dataTypeFormater dtf = new dataTypeFormater();
		dtf.setDataIn(textArea_toSend.getText());
		dtf.setFormatType((String) comboBox_Format.getSelectedItem());
		dtf.decode();
		
		if (!dtf.isError()) {
			textArea_toSend.setText(dtf.getDataOut());
		} else {
			textArea_Status.setText(dtf.getErrorDescription());
		}
		
	}
	private String convertoToByte(byte[] data){
		
		String out = "";
		
		for (byte s : data) {
			out = out + String.format("%02X", s) + ",";
		}
		
		out = out.substring(0, out.length() - 1).toUpperCase();
		return out;
		
	}
	private byte[] convertoToString(String str){
		
		String data[] = str.split(",");
		List<Byte> dataToByte = new ArrayList<Byte>();
		
		for (String s : data) {
			dataToByte.add(hexStringToByteArray(s));
		}
		
		return toByteArray(dataToByte);
		
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
	private byte[] toByteArray(List<Byte> in) {
	    final int n = in.size();
	    byte ret[] = new byte[n];
	    for (int i = 0; i < n; i++) {
	        ret[i] = in.get(i);
	    }
	    return ret;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void copy(JTextArea area){
		String text = area.getSelectedText();
		StringSelection stringSelection = new StringSelection(text);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(stringSelection, this);
	}
	
	private void selectAll(JTextArea area){
		area.selectAll();
	}
	
	private void paste(JTextArea area){
		
		Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

		 if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			 try {
				String text = (String) transferable.getTransferData(DataFlavor.stringFlavor);
				area.insert(text, area.getCaretPosition());
			} catch (UnsupportedFlavorException e) {		
			} catch (IOException e) {	
			}

		 }	
	}

	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		// do nothing
	}
}

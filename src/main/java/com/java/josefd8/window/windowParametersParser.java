package com.java.josefd8.window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Esta clase proporiona metodos para guardar y recuperar los elementos de la ventana
 * principal.
 * @author José Fernández
 */
public class windowParametersParser {

	public windowParametersParser() {
		super();
	}
	
	/**
	 * Guarda los mienbros pertenecientes al objeto tipo windowPrameters en un archivo
	 * con formato xml
	 * @param wp objeto tipo windowParameters
	 */
	public void saveInXML(windowParameters wp){
		
		try
		{
			String archivo;
			archivo="config/config.xml";

			File folder = new File("config");

			if (!folder.exists()) {
				folder.mkdir();
			}

			folder = new File(archivo);
			
			//the file is erased and re-created with the new config
			if (folder.delete()) {
				if (!folder.exists()) {
					String contenido;
					contenido="<config>\n "
							+ "<localIP>" + wp.localIP + "</localIP>\n "
							+ "<localPort>" + wp.localPort + "</localPort>\n "
							+ "<remoteIP>" + wp.remoteIP + "</remoteIP>\n "
							+ "<remotePort>" + wp.remotePort + "</remotePort>\n "
							+ "<type>" + wp.type + "</type>\n "
							+ "<mode>" + wp.mode + "</mode>\n "
							+ "<dataType>" + wp.dataType_Send + "</dataType>\n "
							+ "<format>" + wp.format + "</format>\n "
							+ "<sendData>" + wp.sendData + "</sendData>\n "
							+ "<dataTypeRec>" + wp.dataType_Receive + "</dataTypeRec>\n "
							+ "</config>";
					File new_archivo=new File(archivo);
					FileWriter escribir=new FileWriter(new_archivo,true);
					escribir.write(contenido);
					escribir.close();				
				}
			}
	
		}catch ( IOException io ) {
			
		}
		
	}
	
	/**
	 * Lee la ultima configuracion de la ventana prinipal desde un archivo xml guardado
	 * previaente en diso (si existe)
	 * @return objeto tipo windowParameters
	 */
	public windowParameters getFromXML(){
		
		windowParameters wp = new windowParameters();

		try
		{
			String archivo;
			archivo="config/config.xml";

			File folder = new File("config");

			if (!folder.exists()) {
				folder.mkdir();
			}

			folder = new File(archivo);
			if (!folder.exists()) {
				String contenido;
				contenido="<config>\n <localIP></localIP>\n <localPort></localPort>\n <remoteIP></remoteIP>\n <remotePort></remotePort>\n <type></type>\n <mode></mode>\n <dataType></dataType>\n <format></format>\n <sendData></sendData>\n <dataTypeRec></dataTypeRec>\n </config>";
				File new_archivo=new File(archivo);
				FileWriter escribir=new FileWriter(new_archivo,true);
				escribir.write(contenido);
				escribir.close();
				return null;
			}

			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(archivo);
			Document document = (Document) builder.build( xmlFile );
			Element rootNode = document.getRootElement();

			wp.localIP = obtenerNodo(rootNode,"localIP");
			wp.localPort = obtenerNodo(rootNode,"localPort");
			wp.remoteIP = obtenerNodo(rootNode,"remoteIP");
			wp.remotePort = obtenerNodo(rootNode,"remotePort");
			wp.type = obtenerNodo(rootNode,"type");
			wp.mode = obtenerNodo(rootNode,"mode");
			wp.dataType_Send = obtenerNodo(rootNode,"dataType");
			wp.format = obtenerNodo(rootNode,"format");
			wp.sendData = obtenerNodo(rootNode,"sendData");
			wp.dataType_Receive = obtenerNodo(rootNode,"dataTypeRec");
			return wp;

		}catch ( IOException io ) {
			return null;
		}catch ( JDOMException jdomex ) {
			return null;
		}	
		
	}
	
	private static String obtenerNodo(Element rootNode, String parametro) {

		String valor = "";	

		valor = rootNode.getChild(parametro).getValue();

		try {
			if (valor.matches("")) {
				return "";
			} else {
				return valor;
			}
		} catch (NullPointerException e) {
			return "";
		}	
	}
	
	
}

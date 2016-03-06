import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Client {

	private final String USER_AGENT = "Mozilla/5.0";
	
	public static void main(String[] args) throws Exception {

		Client http = new Client();
		
		System.out.println("\nTesting 2 - Send Http POST request");	
		
		sendPost();

	}
	
	
	private static void sendPost() throws Exception {

		String Turl = "http://localhost:8080/restservices/FoodItem";

		URL url = new URL(Turl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setReadTimeout(10000);
		conn.setConnectTimeout(15000);
	    conn.setRequestMethod("POST");
	    conn.setDoInput(true);
		conn.setDoOutput(true);
		    
		    
		    String body = convertXMLFileToString("request.xml");
		    OutputStream output = new BufferedOutputStream(conn.getOutputStream());
		    output.write(body.getBytes());
		    output.flush();
		    
		    BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
		
		    //conn.disconnect();
		    
		    String inputLine;
		    
		    StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			System.out.println(response.toString());
		
	}

	
	
	public static String convertXMLFileToString(String fileName) 
	{ 
	   try{ 
	       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
	       InputStream inputStream = new FileInputStream(new File(fileName)); 
	       org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream); 
	       StringWriter stw = new StringWriter(); 
	       Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
	       serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
	       return stw.toString(); 
	   } 
	   catch (Exception e) { 
	       e.printStackTrace(); 
	   } 
	   return null; 
	}
}



	


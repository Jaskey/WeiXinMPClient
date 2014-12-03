package jaskey.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.swing.JTextArea;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;



public class XMLRequest {


	// Post message to URL
	public static void postMessage(String xmlstr, String address, String subAddress,JTextArea chatBox) throws DocumentException, IOException
			 {
		System.out.println("post address: " + address);

			URL url = new URL(address);
			URLConnection uc = url.openConnection();
			HttpURLConnection conn = (HttpURLConnection) uc;
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			//conn.setRequestProperty("Content-type", "text/xml;charset=utf-8");
			conn.setRequestProperty("Content-type", "text/xml");
			conn.setConnectTimeout(1000);
			System.out.println("before POST:\n"+xmlstr);

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			pw.write(xmlstr);
			pw.close();
			System.out.println("conn.getInputStream() =" + conn.getInputStream());
			InputStream is=conn.getInputStream();
			
			chatBox.append("system replys:\n"+parseXmlContent(is)+"\n");
			is.close();
		 
	}
	
	
	public static String testXMLStr(){
		 RequestTextMessage msg=new RequestTextMessage();
		 msg.setFromUserName("from user");
		 msg.setToUserName("to user");
		 msg.setCreateTime(1348831860);
		 msg.setMsgId(1234567890123456l);
		 msg.setMsgType("text");
		 return textMessageToXml(msg);
	}	
	
	public static String textMessageToXml(RequestTextMessage textMsg){
		xstream.alias("xml", textMsg.getClass());
		return xstream.toXML(textMsg);
	}
	   private static XStream xstream = new XStream(new XppDriver() {
		 	
	    	@Override
	        public HierarchicalStreamWriter createWriter(Writer out) {  
	            return new PrettyPrintWriter(out) {  
	                // 增加CDATA块标识  
	                boolean cdata = true;                  
	                protected void writeText(QuickWriter writer, String text) {  
	                    if (cdata) {  
	                        writer.write("<![CDATA[");  
	                        writer.write(text);  
	                        writer.write("]]>");  
	                    } else {  
	                        writer.write(text);  
	                    }  
	                }  
	            };  
	        }  
	    });  
	   
	   
		public  static String  parseXmlContent(InputStream is) throws IOException, DocumentException {
			SAXReader reader=new SAXReader();
			BufferedReader br=new BufferedReader(new InputStreamReader(is, "UTF-8"));
			Document document=reader.read(br);	
			System.out.println("---------response---------\n");
			Element root =document.getRootElement();
			List<Element> lists=root.elements();
			for(Element e: lists){
				System.out.println("name="+e.getName()+":"+"text="+e.getText());
			}
			System.out.println("---------end of response---------\n");
			return root.element("Content").getText();
		}
}

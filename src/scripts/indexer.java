package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordExtractor;
import org.snu.ids.ha.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings({"rawtypes","unchecked","nls"})
public class indexer {
	private String input_file;	
	private String output_file = "./index.post";

	public indexer(String path) throws Exception {
		this.input_file=path;
		File files = new File(input_file);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.parse(files);
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("doc");
		
		String bodyData;
		HashMap<String, Integer> docfre=new HashMap<>();
		ArrayList<HashMap<String,Integer>> tfL=new ArrayList<>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			HashMap<String,Integer> wordtf=new HashMap<>();
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				bodyData = eElement.getElementsByTagName("body").item(0).getTextContent();
				
			String [] re=bodyData.split("#");
			for(String s: re)
			{	
				String[] re1=s.split(":");
				String word= re1[0];
				int tf=Integer.parseInt(re1[1]);
				if(docfre.containsKey(word)) {
					docfre.put(word,docfre.get(word)+1);
				}
				else {
					docfre.put(word, 1);
				}
				wordtf.put(word, tf);
			}
			tfL.add(wordtf);
		}
		}
			HashMap<String, String> df =new HashMap<>();
			
			for (String word : docfre.keySet()) {
	            int filefre = docfre.get(word);
	            StringBuilder output = new StringBuilder();

            for (int i = 0; i < nList.getLength(); i++) {
	                HashMap<String, Integer> wordmap = tfL.get(i);
	                int wordfre=wordmap.getOrDefault(word, 0);
	                double tfidf = wordfre * Math.log(nList.getLength() / filefre);
	                output.append(String.format("%d %.1f ", i, tfidf));
	            }

	           df.put(word, output.toString());
	        }

	        FileOutputStream fileStream = new FileOutputStream(output_file);
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);

	        objectOutputStream.writeObject(df);
	        objectOutputStream.close();
	    }
	
	//굳이 할 필요 없는 부분
	public void readpost() throws Exception {
		FileInputStream filestream=new FileInputStream(output_file);
		ObjectInputStream objectInputStream = new ObjectInputStream(filestream);
		Object object=objectInputStream.readObject();
		objectInputStream.close();
		HashMap hashMap=(HashMap)object;
		Iterator<String> it=hashMap.keySet().iterator();
	while(it.hasNext()) {
		String key=it.next();
		String value = (String)hashMap.get(key);
		System.out.println(key+":::"+value);
	}
		
	}
		
	public void	harddf() {
		System.out.println("4주차실행완료!!");
	}
}
	



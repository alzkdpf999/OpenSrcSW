package scripts;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordExtractor;
import org.snu.ids.ha.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
@SuppressWarnings({"rawtypes","unchecked","nls"})
public class searcher {
	private String input_file;
	
	public searcher (String path) {
		this.input_file=path;		
	}
	
	public void calcsim(String Q)throws Exception {
		HashMap<String, String> titleinput= new HashMap<>();
		HashMap<String, Double> simword= new HashMap<>();
		//일단 post파일 읽고 해쉬맵에 일단 저장한뒤 title을 같이 저장해야한다.
		
		FileInputStream filestream=new FileInputStream(input_file);
		ObjectInputStream objectInputStream = new ObjectInputStream(filestream);
		Object object=objectInputStream.readObject();
		objectInputStream.close();
		HashMap store=(HashMap)object;
		// title뽑아오기 위해
		File files = new File("./collection.xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.parse(files);
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("doc"); // 제목얻기
		
		KeywordExtractor ke = new KeywordExtractor(); //질의어 키워드대로 일단 나누기
		KeywordList kl = ke.extractKeyword(Q, true);
		for(int temp=0;temp<nList.getLength();temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String title = eElement.getElementsByTagName("title").item(0).getTextContent();
				String id= nNode.getAttributes().getNamedItem("id").getTextContent();
						titleinput.put(id,title);
						double sim= 0.0;
			
						for(Keyword kw : kl) {
							String word = kw.getString();
							double wQ= kw.getCnt();
							if(!store.containsKey(word)) {
								continue;
							}
							HashMap weight =(HashMap) store.get(word);
							double weightdoc=(double) weight.get(id);
							
							double innerpro = wQ * weightdoc;
							sim += innerpro;	
						}
						simword.put(id,sim);
			}
		}
			HashMap <String, Double> sortlist=new HashMap<String,Double>();
			
			List<Entry<String,Double>> printlist =new ArrayList<Entry<String,Double>>(simword.entrySet());
			Collections.sort(printlist, new Comparator<Entry<String, Double>>() {
				// compare로 값을 비교
				public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2)
				{
					// 내림 차순으로 정렬
					return obj2.getValue().compareTo(obj1.getValue());
					
				}
			});
			for(Entry<String, Double> shorttimestore: printlist) {
				sortlist.put(shorttimestore.getKey(),shorttimestore.getValue());
			}
			
			ArrayList<String> finish=new ArrayList(sortlist.keySet());
			
	
			 for (int i = 0; i < 3; i++) {
		            String ki = finish.get(i);
		            String title = titleinput.get(ki);

		            System.out.println(title);
		        }
	}
}


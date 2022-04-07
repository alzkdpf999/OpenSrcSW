package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

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

@SuppressWarnings({ "rawtypes", "unchecked", "nls" })
public class searcher {
	private String input_file;

	public searcher(String path) {
		this.input_file = path;
	}

	public void harddf() {
		System.out.println("5주차실행완료!!");
	}

	public void calcsim(String Q) throws Exception {
		HashMap<String, String> titleinput = new HashMap<>();
		HashMap<String, Double> simword = new HashMap<>();
		double queryword=0.0;
		double docword=0.0;
		// 일단 post파일 읽고 해쉬맵에 일단 저장한뒤 title을 같이 저장해야한다.

		FileInputStream filestream = new FileInputStream(input_file);
		ObjectInputStream objectInputStream = new ObjectInputStream(filestream);
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		HashMap store = (HashMap) object;
		// title뽑아오기 위해
		File files = new File("./collection.xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.parse(files);
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("doc"); // 제목얻기

		KeywordExtractor ke = new KeywordExtractor(); // 질의어 키워드대로 일단 나누기
		KeywordList kl = ke.extractKeyword(Q, true);
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String title = eElement.getElementsByTagName("title").item(0).getTextContent();
				String id = eElement.getAttributes().getNamedItem("id").getTextContent();
				titleinput.put(id, title);
				
				double innerproductreturn = InnerProduct(Q,input_file);
				
				for (Keyword kw : kl) {
					String word = kw.getString();
					double wQ = kw.getCnt();
					if (!store.containsKey(word)) {
						continue;
					}
					HashMap weight = (HashMap) store.get(word);
					double weightdoc = (double) weight.get(id);
					 queryword += Math.pow(wQ,2); 
					 docword += Math.pow(weightdoc,2);
				}
				double Cosinedown= Math.sqrt(queryword) * Math.sqrt(docword);
				double Cosinesim= innerproductreturn/Cosinedown;
				simword.put(id, Cosinesim);
			}
		}

		// Map.Entry 리스트 작성
		HashMap<String, Double> show = new LinkedHashMap<>();
		ArrayList<Entry<String, Double>> list_entries = new ArrayList<Entry<String, Double>>(simword.entrySet());

		// 비교함수 Comparator를 사용하여 내림 차순으로 정렬
		Collections.sort(list_entries, new Comparator<Entry<String, Double>>() {
			// compare로 값을 비교
			public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2) {
				// 내림 차순으로 정렬
				return obj2.getValue().compareTo(obj1.getValue());
			}
		});
		for (Entry<String, Double> entry : list_entries) {
			show.put(entry.getKey(), entry.getValue());
		}
		ArrayList<String> print = new ArrayList(show.keySet());
		System.out.println();
		for (int k = 0; k < 3; k++) {
			String key = print.get(k);
			if (show.get(key) != 0) {
				String title = titleinput.get(key);
				System.out.println((k + 1) + "순위" + " " + title);
			} else if (show.get(key) == 0) {
				System.out.println("검색된 문서가 없습니다.");
			}
		}
	}

}

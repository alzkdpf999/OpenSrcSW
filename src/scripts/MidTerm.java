package scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

public class MidTerm {
private String input_file;

public MidTerm(String path)
{
	this.input_file=path;
}
public void showSnippet(String query) throws Exception {
	HashMap<String, String> titleinput = new HashMap<>(); //title store
	HashMap<Integer,Integer> wd=new HashMap<>(); // 30음절씩 한 아이디당
	ArrayList<String> content=new ArrayList<>();
File file = new File(input_file);
DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
Document document = docBuilder.parse(file);
document.getDocumentElement().normalize();

Document dd = docBuilder.newDocument();

Element docs = dd.createElement("docs");
dd.appendChild(docs);
NodeList nList = document.getElementsByTagName("doc");
String bodyData;
String titleData;
ArrayList<HashMap<Integer,String[]>> re=new ArrayList<HashMap<Integer,String[]>>();
KeywordExtractor ke = new KeywordExtractor(); // 질의어 키워드대로 일단 나누기
KeywordList kl = ke.extractKeyword(query, true);
for (int temp = 0; temp < nList.getLength(); temp++) {
	Node nNode = nList.item(temp);
	int cnt=0;

	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		Element eElement = (Element) nNode;
		titleData = eElement.getElementsByTagName("title").item(0).getTextContent();
		bodyData = eElement.getElementsByTagName("body").item(0).getTextContent();
		String id = eElement.getAttributes().getNamedItem("id").getTextContent();
		
		titleinput.put(id , titleData); //아이디와 타이틀 저장
	for(Keyword kw : kl)
	{
		
		String word=kw.getString();
		if(content.get(temp).contains(word)) cnt++;
		
	}
		wd.put(temp, cnt);
	}

	}
HashMap<Integer,Integer> show = new LinkedHashMap<>();
ArrayList<Entry<Integer,Integer>> list_entries = new ArrayList<Entry<Integer,Integer>>(wd.entrySet());

// 비교함수 Comparator를 사용하여 내림 차순으로 정렬
Collections.sort(list_entries, new Comparator<Entry<Integer,Integer>>() {
	// compare로 값을 비교
	public int compare(Entry<Integer,Integer> obj1, Entry<Integer,Integer> obj2) {
		// 내림 차순으로 정렬
		return obj2.getValue().compareTo(obj1.getValue());
//		return obj1.getValue().compareTo(obj2.getValue()); //오름 차순 정
	}
});
for (Entry<Integer,Integer> entry : list_entries) {
	show.put(entry.getKey(), entry.getValue());
}
ArrayList<Integer> print = new ArrayList(show.keySet());
System.out.println();
for (int k = 0; k < 3; k++) {
	int key = print.get(k);
	if (show.get(key) != 0) {
		String title = titleinput.get(key);
		System.out.println(title + ""+ content.get(key) +"" + show.get(key) );
	} else if (show.get(key) == 0) {
		System.out.println("검색된 문서가 없습니다.");
	}
}
}
}


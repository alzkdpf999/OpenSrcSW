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
import java.util.List;

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
		HashMap<String, Integer> docfre= new HashMap<>(); // df(x)계산 
		List hashmaplist =new ArrayList<Object>();
		String bodyData;
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			HashMap<String, Integer> wordtf = new HashMap<>(); //문서마다 단어와 빈도수 저장1
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				bodyData = eElement.getElementsByTagName("body").item(0).getTextContent();
				
			String [] re=bodyData.split("#");
			for(String s: re)
			{	
				String[] re1=s.split(":");
				String word = re1[0];
				int tf = Integer.parseInt(re1[1]);
				wordtf.put(word, tf);
				if(docfre.containsKey(word)) {
				docfre.put(word, docfre.get(word)+1);
				}
				else {
					docfre.put(word,1);
				}
			}
			
		}
			hashmaplist.add(wordtf);
		}
		HashMap<String, HashMap<String,Double>> call = new HashMap<>(); // 값을 저장 
		for(String word : docfre.keySet()) 
		{
			//단어 x 몇개의 문서에서 등장하는지
			int num = docfre.get(word); 
			HashMap<String,Double> store= new HashMap<>();
			for(int i=0; i<nList.getLength();i++)
			{
				//문서y에서 단어 x 등장한 횟수(빈도수) 저장 ,getordefault 사용해서 word가 있으면 value값을 없으면 0을 
				HashMap<String, Integer> caltfidf= (HashMap<String, Integer>) hashmaplist.get(i);
				int caltf=caltfidf.getOrDefault(word, 0);
				double tfidf = caltf * Math.log(nList.getLength()/num) ;
				store.put(String.format("%d", i), Double.valueOf(String.format("%.2f", tfidf)));
			}
			call.put(word, store);
		}
		 FileOutputStream fileStream = new FileOutputStream(output_file);
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);

	        objectOutputStream.writeObject(call);
	        objectOutputStream.close();
}
	
	
	//굳이 할 필요 없는 부분
	public void readpost() throws Exception {
		FileInputStream filestream=new FileInputStream(output_file);
		ObjectInputStream objectInputStream = new ObjectInputStream(filestream);
		Object object=objectInputStream.readObject();
		objectInputStream.close();
		HashMap hashMap=(HashMap)object;
//hasMap에 key값들만큼 돌리고 hashMap에 value를 valueMap에 저장 
		//밀:::0 1.61 1 0.00 2 0.00 3 0.00 4 0.00  이런식으로 저
        for (Object key : hashMap.keySet()) {
            HashMap valueMap = (HashMap) hashMap.get(key);
            StringBuilder store = new StringBuilder();

            for (Object valueKey : valueMap.keySet()) {
                double value = (double) valueMap.get(valueKey);
                store.append(String.format("%s %.2f ", valueKey, value));
            }

            System.out.println(key + ":::" + store);
        }
		
	}
		
	public void	harddf() {
		System.out.println("4주차실행완료!!");
	}
}
package scripts;

import java.util.ArrayList;
import java.util.HashMap;
import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordList;



@SuppressWarnings({ "rawtypes", "unchecked", "nls" })
public class searcher {

	public double InnerProduct(ArrayList<Double> wQlist, ArrayList<Double> weightdoclist){
		double innerproductreturn=0.0;
		for(int i=0;i<wQlist.size();i++) {
			double temp=wQlist.get(i)*weightdoclist.get(i);
			innerproductreturn+=temp;
		}
		return innerproductreturn;
}
}

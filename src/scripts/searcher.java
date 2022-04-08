package scripts;

import java.util.HashMap;
import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordList;



@SuppressWarnings({ "rawtypes", "unchecked", "nls" })
public class searcher {

	public double InnerProduct(double wQ,double weightdoc){
		double innerpro=wQ*weightdoc;
		
		
		return innerpro;
		}
	
}

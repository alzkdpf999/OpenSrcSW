package scripts;

import java.util.HashMap;
import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordList;



@SuppressWarnings({ "rawtypes", "unchecked", "nls" })
public class searcher {

	public double InnerProduct(HashMap store,String id,KeywordList kl){
		double asd = 0.0;
				for (Keyword kw : kl) {
					String word = kw.getString();
					double wQ = kw.getCnt();
					if (!store.containsKey(word)) {
						continue;
					}
					HashMap weight = (HashMap) store.get(word);
					double weightdoc = (double) weight.get(id);

					double innerpro = wQ * weightdoc;
					asd += innerpro;
				}
		return asd;
	}
}

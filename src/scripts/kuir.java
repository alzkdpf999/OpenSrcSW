package scripts;



public class kuir {

	public static void main(String[] args) {

		String command = args[0];
		String path = args[1];

		if (command.equals("-c")) {
			try {
				makeCollection collection = new makeCollection(path);
				collection.makeXml();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("-k")) {
			makeKeyword keyword;
			try {
				keyword = new makeKeyword(path);
				keyword.convertXml();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		else if (command.equals("-i")) {
			try {
				indexer index= new indexer(path);
				index.harddf();
				index.readpost();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		
	}
}

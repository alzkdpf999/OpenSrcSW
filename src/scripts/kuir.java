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
		else if (command.equals("-s")) {
			try {
				searcher search= new searcher(path);
				
				if(args.length==4) {
					String secondcommand=args[2];
					String Q=args[3];
					if(secondcommand.equals("-q")) {
						search.calcsim(Q);	
						search.harddf();		
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("-m")) {
			String secondcommand=args[2];
			String query=args[3];
			if(secondcommand.equals("-q")) {
				MidTerm sn=new MidTerm(path);
				try {
					sn.showSnippet(query);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

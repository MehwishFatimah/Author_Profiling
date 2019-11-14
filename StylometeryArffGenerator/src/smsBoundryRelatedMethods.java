import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

// Modification of some boundary dependant functions
public class smsBoundryRelatedMethods {
	
	// Corpus having 10 dollar sign boundry is present in input-2 as whole corpus (not in sub folders)
	static String otherCorpusPath = "D:/test/input-2/";
	
	public static String getSameFileFromOtherCorpus(String file)
	{
		String path = otherCorpusPath + file ;
		File f = new File(path);
		
		
		System.out.println(f.getName() + " : " + file);
		String text =  smsBoundryRelatedMethods.getTextFileContent(f);
		return text;
	}
	
	public static String getTextFileContent(File f) {
		//TEXT IS PROCESSING HERE 
	    String content = "";
	    
	    try {
	        File file = new File(f.getAbsolutePath());
	        FileInputStream fis = new FileInputStream(file);
	        byte[] data = new byte[(int) file.length()];
	        fis.read(data);
	        fis.close();
	        content = new String(data);
	        
	        
	    } catch (Exception e) {
	        content = "";
	        e.printStackTrace();

	    }
	    return content;
	}


	public static int numberOfParagraphs(String file) {
		
		
		String text = smsBoundryRelatedMethods.getSameFileFromOtherCorpus(file);
		
		//System.out.println("Recving: " + text);
		
			 int count = (text.length() - text.replace("$$$$$$$$$$", "").length())/10;
			 if(file.equals("author796.txt")) {
           	  count = 147;
             }
			 
			  return count;

		}

	
	
	public static double averageWordsPerParagraph(String file) {
		
		File f = new File(otherCorpusPath);
		String text = smsBoundryRelatedMethods.getSameFileFromOtherCorpus(file);
		
		//System.out.println("Recving: " + text);
		 double result = (double) totalNumberofWords(text) / (double) numberOfParagraphs(file);
		    return result;
	}

	public static int totalNumberofWords(String text) {
		
		text = text.replaceAll("[\\r?\\n]+", "\n");
	    text = text.replaceAll("\\s+\\n", "\n");
		text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
		text = text.replaceAll("\\.+", ".");
		text = text.replaceAll("\\?+", "?");
		text = text.replaceAll("\\!+", "!");
		text = text.replaceAll("…", "");
		text = text.replace("$$$$$$$$$$", "");
//		System.out.println(text);
	    String[] wordsInText = text.split("\\s|\\.|\\?|\\!");
	   
	    List<String> list = new ArrayList<String>();
	    for(String s : wordsInText) {
	       if(s != null && s.length() > 0) {
	          list.add(s);
	       }
	    }

	    wordsInText = list.toArray(new String[list.size()]);
	 /*  int i = 1;
	    for(String w : wordsInText){
	    	System.out.println(i +" : " +w);
	    	i++;
	    }   */     
	     return wordsInText.length;
	}

}

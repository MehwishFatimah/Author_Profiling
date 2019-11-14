import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class ClassHandler {
	
	static HashMap<String, String> annotations = new HashMap<>();
	
	
	// This method takes csv file for loading annotations
	public static void loadAnnotations(String filePath) {
	   
		//File file = new File(filePath);
	    String text = FileHandlers.getFileData(filePath);
	    String[] entries = text.split("\\r?\\n");
	    for (String obj : entries) {
	         String[] pair = obj.split(",");
	         annotations.put(pair[0], pair[1]);
	         System.out.println(pair[0] + " " + pair[1]);
	    }
	   
	}

	// This method generates class label for arff ()
	public static String getClassLabels() {
	    Iterator iter = annotations.keySet().iterator();
	    HashMap<String, String> classes = new HashMap<>();
	    String classList = "";
	    while (iter.hasNext()) {
	        String key = iter.next().toString();
	        String author = annotations.get(key);
	        classes.put(author, "1");

	    }
	    iter = classes.keySet().iterator();
	    while (iter.hasNext()) {
	        String author = iter.next().toString();
	        classList += author + ",";
	    }
	    classList = classList.substring(0, classList.length() - 1);

	    System.out.println("class label : " + classList);
	    return "{" + classList + "}";
	}
	
	public static void getAnnotationsByFolderName(String sourceFolder) {
	    
		 File Folder = new File(sourceFolder);
	        
	        File[] subFolders = Folder.listFiles(File::isDirectory);
	        	System.out.println(Arrays.toString(subFolders));
	        	
	        for (int i = 0; i < subFolders.length; i++)
	        {
	        	if (subFolders[i].isDirectory())
	        	{
	        			System.out.println("SubFolder : " +subFolders[i].getName());
	        			File[] listOfFiles = subFolders[i].listFiles();
	        			for (File file: listOfFiles)
	        			{
	        				String key = file.getName();
	        				String value = subFolders[i].getName();
	        				//System.out.println(name);
	        				key = key.replaceAll( "\\.\\w+", "");
	        				annotations.put(key, value);
	        				System.out.println(key +" , " + value);
	        			}
	       
	        	}
	        }
	    }
	   
	
	
	
	

}

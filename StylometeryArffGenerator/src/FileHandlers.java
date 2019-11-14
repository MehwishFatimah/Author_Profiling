import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class FileHandlers {
	
	public static String getFileData(String path) {
		
		File f = new File(path);
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
	    //System.out.println(content);
	    return content;
	}

	// to do
	public static void distributeFilesIntoClassFolders(String sourcePath, String destPath, String csvFile) {

        try {   		
        		ClassHandler.loadAnnotations(csvFile);    
            	        		
        		File folder = new File(sourcePath);
            	File[] listOfFiles = folder.listFiles();

            	String subFolder = "";
            	String key = "";
            	String val = "";
            	for (File file : listOfFiles) {
            		if (file.isFile()) {
            			
            			String fileName = file.getName();
            			fileName = fileName.replace(".txt", "");
            			//System.out.println("File Name : " + fileName);
            			    
            			for (HashMap.Entry<String, String> entry : ClassHandler.annotations.entrySet()) {
            				key = entry.getKey();
            			    val = entry.getValue();
            			  //  System.out.println("Key = " + key + ", Value = " + val);
                        	
            			    if (fileName.equals(key)){
            			    	//System.out.println("Break\n");
            			    	break;                    		
                        	}
            			}
            			//for gender
            		  if (val.equals("male"))
            					subFolder = "male";
            			else if (val.equals("female"))
            				subFolder = "female";
            			else
            				System.out.println("Problem @" + fileName);
            		/*
            			
            			if (val.equals("xx:19"))
        					subFolder = "xx-19";
        			else if (val.equals("20:24"))
        				subFolder = "20-24";
        			else if (val.equals("25:xx"))
        				subFolder = "25-xx";
        			else 
        				System.out.println("Problem @" + fileName); 
            			*/
            			//System.out.println(subFolder);
            			
            			String destName = file.getName();
            			
            			
	            		String absolutePath = destPath +'/'+ subFolder+'/'+ destName;
	            		//System.out.println(absolutePath);
            			
	            		File dest = new File (absolutePath);
            			file.renameTo(dest);
	            		
            		}
            }
        } catch (Exception e) {

            System.out.println(e);
        }
	}
}

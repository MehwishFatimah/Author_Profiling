import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import weka.core.Attribute;
import weka.core.Instances;

public class GroupOfWordFeatures {
	
//	static HashMap<String, Integer> countByWords = new HashMap<String, Integer>();
	
	/* This method returns instances dataset of whole corpus
 	 * This method will be called in main()  
	 */
	
	
	public static void GroupOfWFExtractor(String sourceFolder, String dest)  throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, IOException
	{
		File Folder = new File(sourceFolder);
        
		//1- Attributes Names 
		String list = getAllFeaturesNames();
		ArrayList <Attribute> attributes = new ArrayList<Attribute>();
		attributes = ArffHandler.ListOfFeatures(list);
		
		//2- Attribute values are going to calculate on per file basis
		ArrayList <AttributeValuesClassPair> valuesOfAttributes = new ArrayList<AttributeValuesClassPair>();
		
		File[] subFolders = Folder.listFiles(File::isDirectory);
	    //System.out.println(Arrays.toString(subFolders));
	        	
	        for (int i = 0; i < subFolders.length; i++)
	        {
	        	if (subFolders[i].isDirectory())
	        	{
	        			File[] listOfFiles = subFolders[i].listFiles();
	        			for (File file : listOfFiles)
	        			{
	        				String key = file.getName();
	        				String classLabel = subFolders[i].getName();
	        				//key = key.replaceAll( "\\.\\w+", "");
	        				String path = file.getPath();
	        				String text = FileHandlers.getFileData(path);
	        				
	        				// Feature extraction for single file
	        				double[] allValues =  GroupOfWFExtractorForSingleFile(key, text);
	        				String values = Arrays.toString(allValues);
	        				// To string introduces []
							values = values.replace("]", "").replace("[","");
							//values = values + ", " + classLabel;
							AttributeValuesClassPair p = new AttributeValuesClassPair(values, classLabel);
							valuesOfAttributes.add(p);
							
	        			}
	        	}
	        }
	        // End of outer for
	      /*  for (AttributeValuesClassPair p : valuesOfAttributes)
	        {
	        	System.out.println(p);
	        }*/
	        String relation = "GroupOfWordFeatures";
	        //3- creating data set
	        Instances data = ArffHandler.createInstances(relation, attributes, valuesOfAttributes);
		
	        //4- Write group file
	        dest = dest + relation +".arff";
	        ArffHandler.ArffWriter(dest, data);
	       // return data;
	}
	
	public static String  getAllFeaturesNames()
	{
		String allNames = "totalNumberofWords,"
				+ "numberOfUniqueWords,"
				+ "numberOfSentences,"
				+ "averageSentenceLengthinCharacters,"
				+ "averageSentenceLengthinWords,"
				+ "averageWordLength,"
				+ "averageWordsPerParagraph,"
				+ "numberOfParagraphs,"
				+ "percentageOfQuestionSentences,"
				+ "percentageOfWordsWithLength3,"
				+ "percentageOfWordsWithLength4";
		return allNames;
		
	}
	
	// This method returns the attribute values for a single File
	public static double[] GroupOfWFExtractorForSingleFile(String file, String text) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		
		//1- calculate word frequencies
	//	GroupOfWordFeatures.wordFrequency(text);
		//2- call all methods and save them in array
		
		String list = getAllFeaturesNames();
		String [] names = list.split(",");
		double[] WFfeatures = new double[names.length];
		
		WFfeatures[0] = totalNumberofWords(text);
		WFfeatures[1] = numberOfUniqueWords(text);
		WFfeatures[2] = numberOfSentences(text);
		WFfeatures[3] = averageSentenceLengthinCharacters(text);
		WFfeatures[4] = averageSentenceLengthinWords(text);
		WFfeatures[5] = averageWordLength(text);
		WFfeatures[6] = averageWordsPerParagraph(file, text);
		WFfeatures[7] = numberOfParagraphs(file, text);
		WFfeatures[8] = percentageOfQuestionSentences(text);
		WFfeatures[9] = percentageOfWordsWithLength3(text);
		WFfeatures[10] = percentageOfWordsWithLength4(text);
		
		return WFfeatures;
		
	}
		
	 //-------------------------------------------- feature calculation methods
    public static HashMap<String, Integer> wordFrequency(String text) {
        HashMap<String, Integer> countByWords = new HashMap<String, Integer>();
        Scanner s = new Scanner(text);
        while (s.hasNext()) {
            String next = s.next();
            if (countByWords.containsKey(next)) {
                countByWords.put(next, countByWords.get(next) + 1);
            } else {
                countByWords.put(next, 1);
            }
        }
        s.close();

        return countByWords;
    }

	
	
	 //1- modified
    public static int totalNumberofWords(String text) {
    	
    	text = text.replaceAll("[\\r?\\n]+", "\n");
        text = text.replaceAll("\\s+\\n", "\n");
    	text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
    	text = text.replaceAll("\\.+", ".");
    	text = text.replaceAll("\\?+", "?");
    	text = text.replaceAll("\\!+", "!");
    	text = text.replaceAll("…", "");
        String[] wordsInText = text.split("\\s|\\.|\\?|\\!");
       
        List<String> list = new ArrayList<String>();
        for(String s : wordsInText) {
           if(s != null && s.length() > 0) {
              list.add(s);
           }
        }

        wordsInText = list.toArray(new String[list.size()]);
      /* int i = 1;
        for(String w : wordsInText){
        	System.out.println(i +" : " +w);
        	i++;
        }        */
         return wordsInText.length;
    }
    
   
    //2- 
    public static int numberOfUniqueWords(String text) {
       
    	HashMap<String, Integer> s = new HashMap<String, Integer>();
    	s = GroupOfWordFeatures.wordFrequency(text);
        return s.size();
       
    }
    
    
    //3-
  //updated method by Mehwish 7 sep 2017
    public static int numberOfSentences(String text) {
    	
    	text = text.replaceAll("[\\r?\\n]+", "\n");
    	text = text.replaceAll("\\s+\\n", "\n");
    	text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
    	text = text.replaceAll("\\.+", ".");
    	text = text.replaceAll("\\?+", "?");
    	text = text.replaceAll("\\!+", "!");
    	
    	//System.out.println(text +"\n\n");
        String[] data = text.split("\\n|\\.\\n|\\?\\n|\\!\\n|\\.\\s|\\?\\s|\\!\\s");
      /* int i = 1;
        for(String d : data)
        {
        	System.out.println(i +" : " + d);
        	i++;
        }*/
        int sentenceCount = data.length; 
         
        return sentenceCount;
    }
    
    
  // 4 - modified by Mehwish 
    public static double averageSentenceLengthinCharacters(String text) {
    	
    	double sentenceCount = numberOfSentences(text);
    	 //   System.out.println("sentenceCount : " +sentenceCount);
    	    text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
    		text = text.replaceAll("\\s+\\n", "\n");
    		text = text.replaceAll("\\r", " ");
    	    text = text.replaceAll("\\n", " ");
    	//    System.out.println(text);
    	//    System.out.println(text.toCharArray().length);
    	    double result = text.toCharArray().length / sentenceCount;
    	     return result;
    }

    //5-  
    public static double averageSentenceLengthinWords(String text) {
	double result = (double) totalNumberofWords(text) / (double)numberOfSentences(text);
    return result;

}
	
    //6- 
    public static double averageWordLength(String text) {
    //    StylometricTechniques tech = new StylometricTechniques();
        double average = GroupOfCharacterFeatures.characterCount(text) / totalNumberofWords(text);
        return average;
    }

    //7-
    
    //Modified by Mehwish 7-sep-2017
    public static double averageWordsPerParagraph(String file, String text) {
    	
    //	double result = (double) totalNumberofWords(text) / (double) numberOfParagraphs(file, text);
    	 
    	// This method is called because this feature is dependant on sms boundry
    	double result = smsBoundryRelatedMethods.averageWordsPerParagraph(file);
    	    return result;
    }
    
    //8- 
    
	 public static int numberOfParagraphs(String file, String text) {
	   /*
		 text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
	    	text = text.replaceAll("\\s+\\n", "\n");
	    	//text = text.replaceAll("[\\r?\\n]+", "\n");
	    	// System.out.println(text+"\n\n");
	    	 String[] data = text.split("\n");
	    	//int i = 1;
	    	  //  for(String d : data)
	    	    //{
	    	    	//System.out.println(i +" : " + d);
	    	    	//i++;
	    	    //} 
	    	 int count = data.length; 
	    */
			// This method is called because this feature is dependant on sms boundry
		 int count = smsBoundryRelatedMethods.numberOfParagraphs(file);
		 return count;

	    }

	   
	 	    
	 // 9- modified by Mehwish 7-sep-2017
	    public static double percentageOfQuestionSentences(String text) {
	    	double count = 0;
	        String[] data = text.split(" ");
	        for (String token : data) {
	            if (token.contains("?")) {
	                count++;
	            }
	        }
	      //  System.out.println("QuesSentence : "+ count);
	        double sentenceCount = numberOfSentences(text);
	        return (count / sentenceCount) * 100;
	    }

	   
	    
	  //10- 
	 // verified
	    public static double percentageOfWordsWithLength3(String text) {
	        double totalWords = totalNumberofWords(text);
	        Scanner sc = new Scanner(text);
	        int counter = 0;
	        while (sc.hasNext()) {
	            String word = sc.next();
	            if (word.length() <= 3) {
	                counter++;
	            }
	        }
	        return (counter / totalWords) * 100;
	    }

	    //11-
	 // corrected
	    public static double percentageOfWordsWithLength4(String text) {
	        double totalWords = totalNumberofWords(text);
	        Scanner sc = new Scanner(text);
	        int counter = 0;
	        while (sc.hasNext()) {
	            String word = sc.next();
	            if (word.length() <= 4) {
	                counter++;
	            }
	        }
	        return (counter / totalWords) * 100;
	    }

}

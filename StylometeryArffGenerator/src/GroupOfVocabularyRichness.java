import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Scanner;

import weka.core.Attribute;
import weka.core.Instances;


public class GroupOfVocabularyRichness {

	/* This method returns instances dataset of whole corpus
 	 * This method will be called in main()  
	 */
	
	public static void GroupOfVRExtractor(String sourceFolder, String dest)  throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, IOException
	{
		File Folder = new File(sourceFolder);
       // System.out.println("I am in");
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
	        				//String key = file.getName();
	        				String classLabel = subFolders[i].getName();
	        				//key = key.replaceAll( "\\.\\w+", "");
	        				String path = file.getPath();
	        				String text = FileHandlers.getFileData(path);
	        				
	        				// Feature extraction for single file
	        				double[] allValues =  GroupOfVRExtractorForSingleFile(text);
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
	        String relation = "GroupOfVocabularyRichness";
	        //3- creating data set
	        Instances data = ArffHandler.createInstances(relation, attributes, valuesOfAttributes);
	       
	        //4- Write group file
	        dest = dest + relation +".arff";
	        ArffHandler.ArffWriter(dest, data);
		
	       // return data;
	}
	
	public static String  getAllFeaturesNames()
	{
		String allNames = "honoreRMeasure,"
				+ "sichelSMeasure,"
				+ "brunetWMeasure,"
				+ "yuleKMeasure,"
				+ "simpsonDMeasure,"
				+ "hapaxLegomena";
		return allNames;
		
	}
	
	// This method returns the attribute values for a single File
	public static double[] GroupOfVRExtractorForSingleFile(String text) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		
		
		//1- call all methods and save them in array
		
		String list = getAllFeaturesNames();
		String [] names = list.split(",");
		double[] VRfeatures = new double[names.length];
		
		VRfeatures[0] = honoreRMeasure(text);
		VRfeatures[1] = sichelSMeasure(text);
		VRfeatures[2] = brunetWMeasure(text);
		VRfeatures[3] = yuleKMeasure(text);
		VRfeatures[4] = simpsonDMeasure(text);
		VRfeatures[5] = hapaxLegomena(text);
		
		return VRfeatures;
		
	}
	
	
	
	
	//-----------------------------------Feature calculation methods
	
	public static double honoreRMeasure(String text) {
        
        Iterator it = GroupOfWordFeatures.wordFrequency(text).keySet().iterator();
        double V1 = 0;
        while (it.hasNext()) {
            String word = (String) it.next();
            if (Integer.parseInt(GroupOfWordFeatures.wordFrequency(text).get(word).toString()) == 1) {
                V1++;
            }
        }
        
        double V = GroupOfWordFeatures.wordFrequency(text).size();
        Scanner sc = new Scanner(text);
        double N = GroupOfWordFeatures.totalNumberofWords(text); // associated with word class
        return (100 * (Math.log(N))) / (1 - (V1 / V));
        
    }

    public static double sichelSMeasure(String text) {
     
    	Iterator it = GroupOfWordFeatures.wordFrequency(text).keySet().iterator();
        double V2 = 0;
        while (it.hasNext()) {
            String word = (String) it.next();
            if (Integer.parseInt(GroupOfWordFeatures.wordFrequency(text).get(word).toString()) == 2) {
                V2++;
            }
        }
        double V = GroupOfWordFeatures.wordFrequency(text).size();
        return V2 / V;
    }

    
    public static double brunetWMeasure(String text) {
       
    	double N = GroupOfWordFeatures.totalNumberofWords(text); // associated with word class
        double V = GroupOfWordFeatures.wordFrequency(text).size();
        double W = Math.pow(V, -.1654);
        W = Math.pow(N, W);
        return W;
    }
    
   
		// verified Yule K
		
    public static double yuleKMeasure(String text) {
        
    	double N = GroupOfWordFeatures.totalNumberofWords(text); // associated with word class
    	 HashMap<String, Integer> map = GroupOfWordFeatures.wordFrequency(text);
         HashMap<Integer, Integer> yule = new HashMap<>();
         Iterator iter = map.keySet().iterator();
       
        while (iter.hasNext()) {
            int val = (Integer) map.get(iter.next());
            if (yule.containsKey(val)) {
                yule.put(val, yule.get(val) + 1);
            } else {
                yule.put(val, 1);
            }
        }
        double S2 = 0;
        iter = yule.keySet().iterator();
        while (iter.hasNext()) {
            int m = (Integer) iter.next();
            double VmN = (Integer) yule.get(m);
            double temp = (m * m) * VmN;
            S2 += temp;
        }
        double K = 10000 * ((S2 - N) / Math.pow(N, 2));
        return K;
        

    }

    public static double simpsonDMeasure(String text) {
        
    	double N = GroupOfWordFeatures.totalNumberofWords(text); // associated with word class
       
    	HashMap<Integer, Integer> yule = new HashMap<>();
        Iterator iter = GroupOfWordFeatures.wordFrequency(text).keySet().iterator();

        while (iter.hasNext()) {
            int val = (Integer) GroupOfWordFeatures.wordFrequency(text).get(iter.next());
            if (yule.containsKey(val)) {
                yule.put(val, yule.get(val) + 1);
            } else {
                yule.put(val, 1);
            }
        }
        double D = 0;
        iter = yule.keySet().iterator();
        while (iter.hasNext()) {
            int m = (Integer) iter.next();
            double VmN = (Integer) yule.get(m);
            double mByN = m / N;
            double mMinus1ByNMinus1 = (m - 1) / (N - 1);
            double temp = VmN * mByN * mMinus1ByNMinus1;
            D += temp;
        }
        return D;
    }
    
    public static int hapaxLegomena(String text) {
       
    	Iterator it = GroupOfWordFeatures.wordFrequency(text).keySet().iterator();
        int V1 = 0;
        while (it.hasNext()) {
            String word = (String) it.next();
            if (Integer.parseInt(GroupOfWordFeatures.wordFrequency(text).get(word).toString()) == 1) {
                V1++;
            }
        }
        return V1;
    }
    
    
  
    
    
    
}

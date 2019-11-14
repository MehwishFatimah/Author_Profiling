import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class ArffHandler {
	
	
	public static void ArffWriter(String dest, Instances data) throws IOException
	{
		 File ff = new File(dest);
			
	        ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			saver.setFile(ff);
			saver.writeBatch();
			System.out.println("The End :)");
	}
	
	// This method returns attribute names and types for Arff
		public static ArrayList<Attribute> ListOfFeatures(String list)
	    {
			String[] listToken = list.split(",");
	    	ArrayList <Attribute> features = new ArrayList<Attribute>(listToken.length);
	    	// No need to mention numeric type
	    	
	    	for (String token: listToken)
	    	{
	    		Attribute p = new Attribute(token);
	    		features.add(p);
	    	}
	    	
			return features;
	    }
	
	public static Instances createInstances(String relation, ArrayList <Attribute> attributes, ArrayList <AttributeValuesClassPair> values) throws IOException
	{
		/* Note: Weka 3.8 Jar is being used for this code 
		 * Otherwise Attribute accepts only Fastvector in constructor 
		 * For this weka 3.6 is required
		 */
		
		ArrayList <String> classLabel = new ArrayList<String>();
		classLabel.add("female");
		classLabel.add("male");
		Attribute classes = new Attribute("@@Class@@", classLabel);
		attributes.add(classes);
		
		int numberOfAttributes= attributes.size();
		Instances data = new Instances(relation, attributes, 0);
		data.setClassIndex(numberOfAttributes-1);
		
		ArrayList<Instance> instanceList=new ArrayList<Instance>(); 
	     int numberOfInstances = values.size();
	     
	     for(int i=0; i<numberOfInstances; i++)
	     {
	    	String[] valuesTokens =values.get(i).attributeValues.split(",");
	    	 //valuesTokens length should = numberOfAttributes-1
	    	// instance will take numeric data as double 
	    	double [] attributeValues = new double[numberOfAttributes];
	    	 
	    	 int f;
	    	 //-----------append feature values
	    	 for(f=0; f < numberOfAttributes-1; f++)
	    		 attributeValues[f] = Double.parseDouble(valuesTokens[f]);
	    	
	    	 //append class_label
	    	attributeValues[f] = classLabel.indexOf(values.get(i).attributeClass);
	    	 
	    	 //create and add instance to list
	    		 Instance instance = new DenseInstance(1.0, attributeValues);
	    		 data.add(instance);
    		  	instanceList.add(instance);
	     }
	     
	     return data;
		
	}

}

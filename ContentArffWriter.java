/**
 * 
 */

/**
 * @author User
 *
 */

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.TextDirectoryLoader;
import weka.core.stopwords.MultiStopwords;
import weka.core.tokenizers.CharacterNGramTokenizer;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.*;
import weka.filters.supervised.instance.SpreadSubsample;
import weka.filters.unsupervised.attribute.Reorder;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class ContentArffWriter {

	/**
	 * 
	 */
	public ContentArffWriter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		String inputFolder = "D:/test/3gram";
		File input = new File (inputFolder);
		
		//Step-1 Create a single arff file with classes (sub folder names).
		TextDirectoryLoader loader = new TextDirectoryLoader();
		
		long stime=System.currentTimeMillis();
		System.out.println("base is started");
		loader.setDirectory(input);
				Instances data = loader.getDataSet();
		//System.out.println(data);
		
		// Creating base file
		String destination = "D:/test/Arff";
		String baseFile = destination + "/3gram.arff";
		
		ArffWriter(baseFile, data); 
		 
		//Word Gram Tokenizations
		int min = 1, max = 3;
	//	WordNGram(data, min, max, destination);
		 
		//Character Gram Tokenizations
		min = 2; max = 10;
	//	CharacterNGram(data, min, max, destination);
/*
		String sourcePath = "D:/test/Arff";
		String destPath = "D:/test/Arff";
		
		File Folder = new File (sourcePath);
		File[] files = Folder.listFiles();
		for (File file: files)
		{
			System.out.println(file.getName());
			String fileName = file.getName();
			Instances data = ArffLoader(file);
			applySpreadSpectrum(fileName, data, destPath);
		}
	*/
		long etime=System.currentTimeMillis();
		System.out.println("Time:"+((etime-stime)/1000)+" seconds");
		System.out.println("End :)");
	
		   
	}
	
	public static void ArffWriter(String destination, Instances data) throws IOException
	{
		File output = new File (destination);
		 ArffSaver saver = new ArffSaver();
		 saver.setInstances(data);
		 saver.setFile(output);
		
		 saver.writeBatch();
	}
	
	public static void WordNGram(Instances data, int min, int max, String destFolder) throws Exception
	{
		// Filter and its option settings
		 StringToWordVector filter = new StringToWordVector();
		 
		 filter.setIDFTransform(true);
		 filter.setTFTransform(true);
		 filter.setMinTermFreq(3);
		 MultiStopwords multi = new MultiStopwords();
		 filter.setStopwordsHandler(multi);
		 filter.setWordsToKeep(1000);
		 
		 //Tokenizer settings and application
		 NGramTokenizer tokenizer = new NGramTokenizer();
		 for(int i = min; i <= max; i++)
			{
				long stime=System.currentTimeMillis();
				System.out.println("Word_"+i+"gram is started");
				//----------------------------------Start
				tokenizer.setNGramMinSize(i);
				tokenizer.setNGramMaxSize(i);
				filter.setTokenizer(tokenizer);
				filter.setInputFormat(data);// have to place data here
		 
				//Filter is applied
				Instances dataFiltered = Filter.useFilter(data, filter);
				//set class index and move it to end
				//dataFiltered.setClassIndex(dataFiltered.numAttributes() - 1);
				dataFiltered.setClassIndex(0);
				dataFiltered = moveClassToEnd(dataFiltered);
				//Relation name is set
				dataFiltered.setRelationName("Word-"+i+"gram");
				
				// Creating File
				String destination= destFolder+"/Word-"+i+"gram.arff";
				ArffWriter(destination, dataFiltered);
				
				//----------------------------------End
				long etime=System.currentTimeMillis();
				System.out.println("Word_"+i+"gram is finished");
				System.out.println("Time:"+((etime-stime)/1000)+" seconds");
			}
		 
	}
	
	public static void CharacterNGram(Instances data, int min, int max, String destFolder) throws Exception
	{
		// Filter and its option settings
		 StringToWordVector filter = new StringToWordVector();
		 
		 filter.setIDFTransform(true);
		 filter.setTFTransform(true);
		 filter.setMinTermFreq(3);
		 MultiStopwords multi = new MultiStopwords();
		 filter.setStopwordsHandler(multi);
		 filter.setWordsToKeep(1000);
		 
		 //Tokenizer settings and application
		 CharacterNGramTokenizer tokenizer = new CharacterNGramTokenizer();
		 for(int i = min; i <= max; i++)
			{
				long stime=System.currentTimeMillis();
				System.out.println("Character-"+i+"gram is started");
				//----------------------------------Start
				tokenizer.setNGramMinSize(i);
				tokenizer.setNGramMaxSize(i);
				filter.setTokenizer(tokenizer);
				filter.setInputFormat(data);// have to place data here
		 
				//Filter is applied
				Instances dataFiltered = Filter.useFilter(data, filter);
				//set class index and move it to end
				//dataFiltered.setClassIndex(dataFiltered.numAttributes() - 1);
				dataFiltered.setClassIndex(0);
				dataFiltered = moveClassToEnd(dataFiltered);
				//Relation name is set
				dataFiltered.setRelationName("Character-"+i+"gram");
				
				// Creating File
				String destination= destFolder+"/Character-"+i+"gram.arff";
				ArffWriter(destination, dataFiltered);
				
				//----------------------------------End
				long etime=System.currentTimeMillis();
				System.out.println("Character_"+i+"gram is finished");
				System.out.println("Time:"+((etime-stime)/1000)+" seconds");
			}
		 
	}
	
	static Instances moveClassToEnd(Instances data) throws Exception
	{
		int classIndex=data.classIndex();
		int index;
		String range="";
		if(classIndex>=0)
		{
			for(index=0;index < data.numAttributes();index++)
			{
				if(classIndex==index)
					continue;
				else
				{
					if(index==data.numAttributes()-1)
						range+=(index+1);
					else
						range+=(index+1)+",";
				}
			}
			range+=","+(classIndex+1);
			//indexrange for Reorder starts from 1
			Reorder filter=new Reorder();
			filter.setAttributeIndices(range);
			filter.setInputFormat(data);
			data=Filter.useFilter(data, filter);
			
			data.setClassIndex(data.numAttributes() - 1);
		}
		return data;
	}

	public static Instances ArffLoader(File file) throws IOException
	{
		ArffLoader loader = new ArffLoader();
		  loader.setFile(file);
		  Instances structure = loader.getDataSet();
		  structure.setClassIndex(structure.numAttributes() - 1);
		 // System.out.println(structure);
		return structure;
	}
	
	public static void applySpreadSpectrum(String FileName, Instances data, String destFolder) throws Exception
	{
		// Filter and its option settings
		 SpreadSubsample filter = new SpreadSubsample();
		 filter.setDistributionSpread(1);
		 filter.setInputFormat(data);
		 // Apply filter
		 
		 Instances filteredData = Filter.useFilter(data, filter);
		//set class index and move it to end
			//dataFiltered.setClassIndex(dataFiltered.numAttributes() - 1);
			filteredData.setClassIndex(0);
			filteredData = moveClassToEnd(filteredData);
			//Relation name is set
			filteredData.setRelationName("SS-"+FileName.replace(".arff", ""));
			
			// Creating File
			String destination= destFolder+"/SS-"+FileName;//+".arff";
			ArffWriter(destination, filteredData);
	}
}

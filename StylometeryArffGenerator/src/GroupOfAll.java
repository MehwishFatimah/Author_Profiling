import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import weka.core.Attribute;
import weka.core.Instances;

public class GroupOfAll {

	public static void GroupOfAllExtractor(String sourceFolder, String dest) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
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
	        				double[] allValues =  GroupOfAllExtractorForSingleFile(key, text);
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
	        String relation = "GroupOfAllFeatures";
	        //3- creating data set
	        Instances data = ArffHandler.createInstances(relation, attributes, valuesOfAttributes);
		
	        //4- Write group file
	        dest = dest + relation +".arff";
	        ArffHandler.ArffWriter(dest, data);
	       // return data;
	}
	
	public static String  getAllFeaturesNames()
	{
		String allNames = GroupOfWordFeatures.getAllFeaturesNames() + "," 
				        + GroupOfCharacterFeatures.getAllFeaturesNames() + "," 
				        + GroupOfVocabularyRichness.getAllFeaturesNames();
	
		return allNames;
	}
	
	public static double[] GroupOfAllExtractorForSingleFile(String file, String text) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		
		
		//1- call all methods and save them in array
		
		String list = getAllFeaturesNames();
		String [] names = list.split(",");
		double[] features = new double[names.length];
		
		features[0] = GroupOfWordFeatures.totalNumberofWords(text);
		features[1] = GroupOfWordFeatures.numberOfUniqueWords(text);
		features[2] = GroupOfWordFeatures.numberOfSentences(text);
		features[3] = GroupOfWordFeatures.averageSentenceLengthinCharacters(text);
		features[4] = GroupOfWordFeatures.averageSentenceLengthinWords(text);
		features[5] = GroupOfWordFeatures.averageWordLength(text);
		features[6] = GroupOfWordFeatures.averageWordsPerParagraph(file, text);
		features[7] = GroupOfWordFeatures.numberOfParagraphs(file, text);
		features[8] = GroupOfWordFeatures.percentageOfQuestionSentences(text);
		features[9] = GroupOfWordFeatures.percentageOfWordsWithLength3(text);
		features[10] = GroupOfWordFeatures.percentageOfWordsWithLength4(text);
		
		features[11] = GroupOfCharacterFeatures.characterCount (text);
		features[12] = GroupOfCharacterFeatures.characterCountWithoutSpaces(text);
		features[13] = GroupOfCharacterFeatures.digitCount(text);
		features[14] = GroupOfCharacterFeatures.numberOfUpperCaseCharacters(text);
		features[15] = GroupOfCharacterFeatures.numberOfWhiteSpaces (text);
		features[16] = GroupOfCharacterFeatures.numberOfTabs(text);
		features[17] = GroupOfCharacterFeatures.numberOfRightCurlyBraces(text);
		features[18] = GroupOfCharacterFeatures.numberOfLeftCurlyBraces (text);
		features[19] = GroupOfCharacterFeatures.numberOfVerticalLines(text);
		features[20] = GroupOfCharacterFeatures.numberOfTilds(text);
		features[21] = GroupOfCharacterFeatures.numberOfDollarSigns (text);
		
		features[22] = GroupOfCharacterFeatures.numberOfPercentSigns(text);
		features[23] = GroupOfCharacterFeatures.numberOfAmpersands(text);
		features[24] = GroupOfCharacterFeatures.numberOfLeftparentheses(text);
		features[25] = GroupOfCharacterFeatures.numberOfRightparentheses(text);
		features[26] = GroupOfCharacterFeatures.numberOfAsterics(text);
		features[27] = GroupOfCharacterFeatures.numberOfPlusSigns(text);
		features[28] = GroupOfCharacterFeatures.numberOfLessThanSign(text);
		features[29] = GroupOfCharacterFeatures.numberOfGreaterThanSign(text);
		features[30] = GroupOfCharacterFeatures.numberOfEqualSign(text);
		features[31] = GroupOfCharacterFeatures.numberOfAtSign(text);
		
		features[32] = GroupOfCharacterFeatures.numberOfLeftSquareBracket(text);
		features[33] = GroupOfCharacterFeatures.numberOfRightSquareBracket(text);
		features[34] = GroupOfCharacterFeatures.numberOfUnderScore(text);
		features[35] = GroupOfCharacterFeatures.ratioOfDigitsToN(text);
		features[36] = GroupOfCharacterFeatures.ratioOfLettersToN(text);
		features[37] = GroupOfCharacterFeatures.ratioOfWhiteSpacesToN(text);
		features[38] = GroupOfCharacterFeatures.ratioOfSpecialCharacterToN(text);
		features[39] = GroupOfCharacterFeatures.ratioOfTabsToN(text);
		features[40] = GroupOfCharacterFeatures.ratioOfUpperCaseLettersToN(text);
		features[41] = GroupOfCharacterFeatures.countApostrophe(text);
		
		features[42] = GroupOfCharacterFeatures.countBrackets(text);
		features[43] = GroupOfCharacterFeatures.countColon(text);
		features[44] = GroupOfCharacterFeatures.countComma(text);
		features[45] = GroupOfCharacterFeatures.countDash (text);
		features[46] = GroupOfCharacterFeatures.countEllipsis(text);
		features[47] = GroupOfCharacterFeatures.countExclamation(text);
		features[48] = GroupOfCharacterFeatures.countFullStop(text);
		features[49] = GroupOfCharacterFeatures.countQMark(text);
		features[50] = GroupOfCharacterFeatures.countSemicolon(text);
		features[51] = GroupOfCharacterFeatures.countSlash(text);
		
		features[52] = GroupOfCharacterFeatures.numberOfSingleQuotes(text);
		features[53] = GroupOfCharacterFeatures.numberOfMultipleQuestionMarks(text);
		features[54] = GroupOfCharacterFeatures.numberOfMultipleExclamationMarks(text);
		features[55] = GroupOfCharacterFeatures.percentageOfCommas(text);
		features[56] = GroupOfCharacterFeatures.percentageOfPunctuationCharacters(text);
		features[57] = GroupOfCharacterFeatures.percentageOfSemiColons(text);
		
		features[58] = GroupOfVocabularyRichness.honoreRMeasure(text);
		features[59] = GroupOfVocabularyRichness.sichelSMeasure(text);
		features[60] = GroupOfVocabularyRichness.brunetWMeasure(text);
		features[61] = GroupOfVocabularyRichness.yuleKMeasure(text);
		features[62] = GroupOfVocabularyRichness.simpsonDMeasure(text);
		features[63] = GroupOfVocabularyRichness.hapaxLegomena(text);
		
		return features;
		
	}
}

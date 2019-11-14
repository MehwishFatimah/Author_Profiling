import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.core.Attribute;
import weka.core.Instances;

public class GroupOfCharacterFeatures {
	
	/* This method returns instances dataset of whole corpus
 	 * This method will be called in main()  
	 */
	
	public static void GroupOfCFExtractor(String sourceFolder, String dest)  throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, IOException
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
	        				//String key = file.getName();
	        				String classLabel = subFolders[i].getName();
	        				//key = key.replaceAll( "\\.\\w+", "");
	        				String path = file.getPath();
	        				String text = FileHandlers.getFileData(path);
	        				
	        				// Feature extraction for single file
	        				double[] allValues =  GroupOfCFExtractorForSingleFile(text);
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
	        String relation = "GroupOfCharacterFeatures";
	        //3- creating data set
	        Instances data = ArffHandler.createInstances(relation, attributes, valuesOfAttributes);
		
	        //4- Write group file
	        dest = dest + relation +".arff";
	        ArffHandler.ArffWriter(dest, data);
	       // return data;
	}
	
	public static String  getAllFeaturesNames()
	{
		String allNames = "characterCount," + //1
				"characterCountWithoutSpaces," + //2
				"digitCount," +  //3
				"numberOfUpperCaseCharacters," + //4 
				"numberOfWhiteSpaces," + //5
				"numberOfTabs," +  //6 
				"numberOfRightCurlyBraces," + //7 
				"numberOfLeftCurlyBraces," + //8
				"numberOfVerticalLines," + //9
				"numberOfTilds," + //10
				"numberOfDollarSigns," + //11
				
				"numberOfPercentSigns," + 	//12
				"numberOfAmpersands," +  //13
				"numberOfLeftparentheses," +  //14
				"numberOfRightparentheses," +  //15
				"numberOfAsterics," + //16
				"numberOfPlusSigns," +  //17
				"numberOfLessThanSign," +  //18
				"numberOfGreaterThanSign," +  //19
				"numberOfEqualSign," +  //20
				"numberOfAtSign," + //21
				
				"numberOfLeftSquareBracket," + //22 
				"numberOfRightSquareBracket," + //23
				"numberOfUnderScore," + //24
				"ratioOfDigitsToN," + //25
				"ratioOfLettersToN," + //26
				"ratioOfWhiteSpacesToN," + //27
				"ratioOfSpecialCharacterToN," + //28 
				"ratioOfTabsToN," +  //29
				"ratioOfUpperCaseLettersToN," + //30 
				"countApostrophe," +  //31
				
				"countBrackets," +  //32
				"countColon," + //33
				"countComma," + //34
				"countDash," +  //35
				"countEllipsis," + //36
				"countExclamation," +  //37
				"countFullStop," +  //38
				"countQMark," +  //39
				"countSemicolon," +  //40
				"countSlash," + //41
				
				"numberOfSingleQuotes," + //42 
				"numberOfMultipleQuestionMarks," + //43 
				"numberOfMultipleExclamationMarks," + //44
				"percentageOfCommas," +  //45
				"percentageOfPunctuationCharacters," + //46 
				"percentageOfSemiColons"; //47
		return allNames;
		
	}
	
	// This method returns the attribute values for a single File
	public static double[] GroupOfCFExtractorForSingleFile(String text) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		
		//1- call all methods and save them in array
		String list = getAllFeaturesNames();
		String [] names = list.split(",");
		
		double[] CFfeatures = new double[names.length];
		
		CFfeatures[0] = characterCount (text);
		CFfeatures[1] = characterCountWithoutSpaces(text);
		CFfeatures[2] = digitCount(text);
		CFfeatures[3] = numberOfUpperCaseCharacters(text);
		CFfeatures[4] = numberOfWhiteSpaces (text);
		CFfeatures[5] = numberOfTabs(text);
		CFfeatures[6] = numberOfRightCurlyBraces(text);
		CFfeatures[7] = numberOfLeftCurlyBraces (text);
		CFfeatures[8] = numberOfVerticalLines(text);
		CFfeatures[9] = numberOfTilds(text);
		CFfeatures[10] = numberOfDollarSigns (text);
		
		CFfeatures[11] = numberOfPercentSigns(text);
		CFfeatures[12] = numberOfAmpersands(text);
		CFfeatures[13] = numberOfLeftparentheses(text);
		CFfeatures[14] = numberOfRightparentheses(text);
		CFfeatures[15] = numberOfAsterics(text);
		CFfeatures[16] = numberOfPlusSigns(text);
		CFfeatures[17] = numberOfLessThanSign(text);
		CFfeatures[18] = numberOfGreaterThanSign(text);
		CFfeatures[19] = numberOfEqualSign(text);
		CFfeatures[20] = numberOfAtSign(text);
		
		CFfeatures[21] = numberOfLeftSquareBracket(text);
		CFfeatures[22] = numberOfRightSquareBracket(text);
		CFfeatures[23] = numberOfUnderScore(text);
		CFfeatures[24] = ratioOfDigitsToN(text);
		CFfeatures[25] = ratioOfLettersToN(text);
		CFfeatures[26] = ratioOfWhiteSpacesToN(text);
		CFfeatures[27] = ratioOfSpecialCharacterToN(text);
		CFfeatures[28] = ratioOfTabsToN(text);
		CFfeatures[29] = ratioOfUpperCaseLettersToN(text);
		CFfeatures[30] = countApostrophe(text);
		
		CFfeatures[31] = countBrackets(text);
		CFfeatures[32] = countColon(text);
		CFfeatures[33] = countComma(text);
		CFfeatures[34] = countDash (text);
		CFfeatures[35] = countEllipsis(text);
		CFfeatures[36] = countExclamation(text);
		CFfeatures[37] = countFullStop(text);
		CFfeatures[38] = countQMark(text);
		CFfeatures[39] = countSemicolon(text);
		CFfeatures[40] = countSlash(text);
		
		CFfeatures[41] = numberOfSingleQuotes(text);
		CFfeatures[42] = numberOfMultipleQuestionMarks(text);
		CFfeatures[43] = numberOfMultipleExclamationMarks(text);
		CFfeatures[44] = percentageOfCommas(text);
		CFfeatures[45] = percentageOfPunctuationCharacters(text);
		CFfeatures[46] = percentageOfSemiColons(text);
		
		return CFfeatures;
		
	}
	
	//-------------------------------------feature calculation method
	
	
	//1
	//modified by Mehwish 
    public static double characterCount(String text) {
    	text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
    	text = text.replaceAll("\\s+\\n", "\n");
    	text = text.replaceAll("\\r", " ");
        text = text.replaceAll("\\n", " ");
        return text.toCharArray().length;
    }

  //2
  //modified by Mehwish 
    public static double characterCountWithoutSpaces(String text) {
    	text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
    	text = text.replaceAll("\\s+\\n", "\n");
        text = text.replaceAll(" ", "");
        text = text.replaceAll("\\r", "");
        text = text.replaceAll("\\n", "");
        return text.toCharArray().length;
    }
    
    //3
    
    public static int digitCount(String text) {
        int digitCount = 0;
        for (char c : text.toCharArray()) {
            String temp = "" + c;
            try {
                Double.parseDouble(temp);
                digitCount++;
            } catch (NumberFormatException ex) {

            }
        }
        return digitCount;
    }

    
      //4 * with 100 all are percentages
	public static double ratioOfDigitsToN(String text) {
        double characterCount = characterCount(text);
        double digitCount = digitCount(text);
        double result = (digitCount / characterCount) * 100;
        return result;
    }

  //5
	public static double ratioOfLettersToN(String text) {
        char[] array = text.toCharArray();
        double count = 0;
        String expression = "^[a-zA-Z]+";
        for (int i = 0; i < array.length; i++) {
            CharSequence seq = "" + array[i];

            Pattern pat = Pattern.compile(expression);
            Matcher mat = pat.matcher(seq);
            if (mat.matches()) {
                count++;
            }  }

        return (count / (characterCount(text))) * 100;
    }

	//6
    public static double ratioOfUpperCaseLettersToN(String text) {
        char[] array = text.toCharArray();
        double count = 0;
        String expression = "^[A-Z]+";
        for (int i = 0; i < array.length; i++) {
            CharSequence seq = "" + array[i];

            Pattern pat = Pattern.compile(expression);
            Matcher mat = pat.matcher(seq);
            if (mat.matches()) {
                count++;
            }
        }

        return (count / (characterCount(text))) * 100;
    }

    //7
    public static int numberOfUpperCaseCharacters(String text) {
        char[] array = text.toCharArray();
        int count = 0;
        String expression = "^[A-Z]+";
        for (int i = 0; i < array.length; i++) {
            CharSequence seq = "" + array[i];

            Pattern pat = Pattern.compile(expression);
            Matcher mat = pat.matcher(seq);
            if (mat.matches()) {
                count++;
            }
        }

        return count;
    }
    
    //8
    public static double ratioOfWhiteSpacesToN(String text) {
        char[] array = text.toCharArray();
        double count = 0;
        String expression = "^[ ]+";
        for (int i = 0; i < array.length; i++) {
            CharSequence seq = "" + array[i];

            Pattern pat = Pattern.compile(expression);
            Matcher mat = pat.matcher(seq);
            if (mat.matches()) {
                count++;
            }
        }

        return (count / (characterCount(text))) * 100;

    }
    
//9
    public static int numberOfWhiteSpaces(String text) {
        char[] array = text.toCharArray();
        int count = 0;
        String expression = "^[ ]+";
        for (int i = 0; i < array.length; i++) {
            CharSequence seq = "" + array[i];

            Pattern pat = Pattern.compile(expression);
            Matcher mat = pat.matcher(seq);
            if (mat.matches()) {
                count++;
            }
        }

        return count;
    }

    //10
    public static double ratioOfTabsToN(String text) {
        String[] tokens = text.split("\t");
        double result = tokens.length - 1;
        return (result / characterCount(text)) * 100;

    }

    //11
    public static int numberOfTabs(String text) {
        String[] tokens = text.split("\t");
        int result = tokens.length - 1;
        return result;

    }

    //12
    public static double ratioOfSpecialCharacterToN(String text) {
        char[] array = text.toCharArray();
        double count = 0;
        String expression = "^[<>%j{}\\[\\]/\\@#wþ*()^&O${}]+";
        for (int i = 0; i < array.length; i++) {
            CharSequence seq = "" + array[i];

            Pattern pat = Pattern.compile(expression);
            Matcher mat = pat.matcher(seq);
            if (mat.matches()) {
                count++;
            }
        }

        return (count / (characterCount(text))) * 100;

    }
    
    //13
    public static double percentageOfPunctuationCharacters(String text) {
        char[] array = text.toCharArray();
        double count = 0;
        String expression = "^['\";:!.,]+";
        for (int i = 0; i < array.length; i++) {
            CharSequence seq = "" + array[i];

            Pattern pat = Pattern.compile(expression);
            Matcher mat = pat.matcher(seq);
            if (mat.matches()) {
                count++;
            }
        }
        return (count / (characterCount(text))) * 100;
    }

    //14
    public static double percentageOfSemiColons(String text) {
        char[] data = text.toCharArray();
        double count = 0;
        for (char c : data) {
            String obj = "" + c;
            if (obj.equals(";")) {
                count++;
            }
        }
        return (count / (characterCount(text))) * 100;
    }

    //15
    public static double percentageOfCommas(String text) {
        char[] data = text.toCharArray();
        double count = 0;
        for (char c : data) {
            String obj = "" + c;
            if (obj.equals(",")) {
                count++;
            }
        }
        return (count / (characterCount(text))) * 100;
    }
    
    //16
    public static int countApostrophe(String text) {
   	 int c = 0;
   	    for (int i = 0; i < text.length(); i++) {
   	        if (text.charAt(i) == '’' || text.charAt(i) == '\'') {

   	            c++;
   	        }
   	    }
   	    
   	    return c;
   }

    //17
   public static int countBrackets(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '[' || text.charAt(i) == ']' || text.charAt(i) == '(' || text.charAt(i) == ')'
                   || text.charAt(i) == '{' || text.charAt(i) == '}' || text.charAt(i) == '<' || text.charAt(i) == '>') {

               c++;
           }
       }
       return c;
   }

   //18
   public static int countColon(String text) {

       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == ':') {

               c++;
           }
       }
       return c;

   }

   //19
   public static int countComma(String text) {

       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if ( text.charAt(i) == ',' || text.charAt(i) == '`') {

               c++;
           }
       }
       return c;
   }

   //20
   public static int countDash(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '-') {

               c++;
           }
       }
       return c;
   }

   //21
   public static int countEllipsis(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {

           if (text.charAt(i) == '…') {

               c++;
           }
       }
       if (text.contains("...")) {

           c++;

       }
       return c;
   }

   //22
   public static int countExclamation(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '!') {

               c++;
           }
       }
       return c;
   }

   //23
   public static int countFullStop(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '.') {

               c++;
           }
       }
       return c;
   }

   //24
   public static int countQMark(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '?') {

               c++;
           }
       }
       return c;
   }

   //25
   public static int countSemicolon(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == ';') {

               c++;
           }
       }
       return c;
   }

   //26
   public static int countSlash(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '/' || text.charAt(i) == '\\') {

               c++;
           }
       }
       return c;
   }

   //27
   public static int numberOfRightCurlyBraces(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '}') {

               c++;
           }
       }
       return c;
   }
   
   
   //28
   public static int numberOfLeftCurlyBraces(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '{') {

               c++;
           }
       }
       return c;
   }

   //29
   public static int numberOfVerticalLines(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '|') {

               c++;
           }
       }
       return c;
   }

   //30
   public static int numberOfTilds(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '~') {

               c++;
           }
       }
       return c;
   }

   //31
       public static int numberOfDollarSigns(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '$') {

               c++;
           }
       }
       return c;
   }

       //32
   public static int numberOfPercentSigns(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '%') {

               c++;
           }
       }
       return c;
   }

   //33
   public static int numberOfAmpersands(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '&') {

               c++;
           }
       }
       return c;
   }
   
   
   //34
   public static int numberOfSingleQuotes(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '\'') {

               c++;
           }
       }
       return c;
   }

   //35
   public static int numberOfLeftparentheses(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '(') {

               c++;
           }
       }
       return c;
   }

   //36
   public static int numberOfRightparentheses(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == ')') {

               c++;
           }
       }
       return c;
   }
   
   //37
   public static int numberOfAsterics(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '*') {

               c++;
           }
       }
       return c;
   }

   //38
   public static int numberOfPlusSigns(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '+') {

               c++;
           }
       }
       return c;
   }

   //39
   public static int numberOfLessThanSign(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '<') {

               c++;
           }
       }
       return c;
   }

   //40
   public static int numberOfGreaterThanSign(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '>') {

               c++;
           }
       }
       return c;
   }

   //41
   public static int numberOfEqualSign(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '=') {

               c++;
           }
       }
       return c;
   }

   //42
   public static int numberOfMultipleQuestionMarks(String text) {
       String[] data = text.split("\\r?\\n");
       int numberOfMultipleQustionMarks = 0;
       for (String token : data) {
           for (String obj : token.split(" ")) {
               if (obj.contains("??")) {
                   numberOfMultipleQustionMarks++;

               }
           }
       }
       return numberOfMultipleQustionMarks;

   }
//43
   public static int numberOfMultipleExclamationMarks(String text) {
       String[] data = text.split("\\r?\\n");
       int numberOfMultipleExclamationMarks = 0;
       for (String token : data) {
           for (String obj : token.split(" ")) {
               if (obj.contains("!!")) {
                   numberOfMultipleExclamationMarks++;

               }
           }
       }
       return numberOfMultipleExclamationMarks;

   }

   //44
   public static int numberOfAtSign(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '@') {

               c++;
           }
       }
       return c;
   }

   //45
   public static int numberOfLeftSquareBracket(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '[') {

               c++;
           }
       }
       return c;
   }

   //46
   public static int numberOfRightSquareBracket(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == ']') {

               c++;
           }
       }
       return c;
   }

   //47
   public static int numberOfUnderScore(String text) {
       int c = 0;
       for (int i = 0; i < text.length(); i++) {
           if (text.charAt(i) == '_') {

               c++;
           }
       }
       return c;
   }
   


}

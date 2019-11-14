
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;



public class Main {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

		//1- Separate corpus files with the help of truth file
		
		//2- 
		String sourceFolder = "D:/test/input"; // currently whole
		String destFolder = "D:/test/Arff/";
		
		GroupOfAll.GroupOfAllExtractor(sourceFolder, destFolder);
		GroupOfVocabularyRichness.GroupOfVRExtractor(sourceFolder, destFolder);
		GroupOfWordFeatures.GroupOfWFExtractor(sourceFolder, destFolder);
		GroupOfCharacterFeatures.GroupOfCFExtractor(sourceFolder, destFolder);
		
		 }

	
}

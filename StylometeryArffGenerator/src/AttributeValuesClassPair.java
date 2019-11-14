// If Gender and age: then add one more class string here then modify arff handler
public class AttributeValuesClassPair {
	
	String attributeValues;
	String attributeClass;
	AttributeValuesClassPair(String values, String cls){
		attributeValues = values;
		attributeClass = cls;
	}
	
	public String toString(){
		return attributeValues +","+attributeClass;
	}

}

package utility;

import java.util.Iterator;
import java.util.Map;

public class Language {
	private Profile languageProfile;
	private String name;
	
	protected static int maxNgramLength = 5;
	protected static int numberOfNgramsConsidered = 120;
	
	public Language(String name, String sampleFile){
    	this.name = name;
		String baseString  = Parser.getFormattedFile("udhr_txt/udhr_" + sampleFile + ".txt");
    	languageProfile = new Profile(baseString, maxNgramLength);
	}
	
	public int calculateDistance(Profile textProfile) {
		int result = 0;
		
		Iterator<Map.Entry<String, Integer>> it = languageProfile.getIterator();
		while(it.hasNext()) {
			Map.Entry<String, Integer> mapEntry = it.next();
			int indexOfCurrentNgramInTextProfile = textProfile.getPosition(mapEntry.getKey());
			if(indexOfCurrentNgramInTextProfile == -1) {
				result += numberOfNgramsConsidered;
			} else {
				result += Math.abs(mapEntry.getValue() - indexOfCurrentNgramInTextProfile);
			}
		}
		
		return result;
	}
	
	public void printProfile(){
		Iterator<Map.Entry<String, Integer>> it = languageProfile.getIterator();
		while(it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	public String getName() {
		return name;
	}
}

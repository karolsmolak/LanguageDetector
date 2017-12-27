package utility;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Profile {
	private Map<String, Integer> ngramSet = new LinkedHashMap<>();
	protected Map<String, Integer> ngramPosition = new LinkedHashMap<>();
	
	private String baseString;
	private int maxLength;
	
	public Iterator<Map.Entry<String, Integer>> getIterator(){
		return ngramPosition.entrySet().iterator();
	}
	
	public int getPosition(String ngram) {
		if(!ngramPosition.containsKey(ngram)) {
			return -1;
		}
		return ngramPosition.get(ngram);
	}
	
	public Profile(String baseString, int maxLength) {
		this.maxLength = maxLength;
		this.baseString = baseString;
		buildNgramSet();
		buildNgramPositionMap();
	}

	private static List<Map.Entry<String, Integer>> sortByValue(Map<String, Integer> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .collect(Collectors.toList());
	}
	
	private void buildNgramPositionMap() {
		List<Map.Entry<String, Integer>> ngramList = sortByValue(ngramSet);
		int maxPosition = 0;
		int currentValue = 0;
		for(Map.Entry<String, Integer> substring : ngramList) {
			if(substring.getValue() != currentValue) {
				maxPosition++;
				currentValue = substring.getValue();
			}
			ngramPosition.put(substring.getKey(), maxPosition);
			if(ngramPosition.size() == Language.numberOfNgramsConsidered) {
				break;
			}
		}
	}

	private void buildNgramSet() {
		for(int currentLength = 1 ; currentLength <= maxLength ; currentLength++) {
			for(int i = 0 ; i < baseString.length() - currentLength + 1; i++){
				if(currentLength != 1 || baseString.charAt(i) != ' ')
				{
					String substring = baseString.substring(i, i + currentLength);
					if(ngramSet.get(substring) == null) {
						ngramSet.put(substring, 1);
					} else {
						ngramSet.put(substring, ngramSet.get(substring) + 1);
					}
				}
			}
		}		
	}
}
package services;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Profile {
	private static final int maxLength = 5;
	private static final int relevantNgrams = 500;
	
	private Map<String, Integer> ngramSet = new LinkedHashMap<>();
	private Map<String, Integer> ngramPosition = new LinkedHashMap<>();
	
	private String baseString;
	private String name;
	
	public Profile(String baseString, String name) {
		this.baseString = baseString;
		this.name = name;
		buildNgramSet();
		buildNgramPositionMap();
	}
	
	public String getBaseString() {
		return baseString;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return ngramPosition.size();
	}
	
	public int calculateDistance(Profile textProfile) {
		int result = 0;
		
		for(Map.Entry<String, Integer> entry : ngramPosition.entrySet()) {
			int indexOfNgramInTextProfile = textProfile.getPosition(entry.getKey());
			
			if(indexOfNgramInTextProfile == -1) {
				result += textProfile.getSize();
			} else {
				result += Math.abs(entry.getValue() - indexOfNgramInTextProfile);
			}
			
		}
		
		return result;
	}
	
	public int getPosition(String ngram) {
		if(!ngramPosition.containsKey(ngram)) {
			return -1;
		}
		return ngramPosition.get(ngram);
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
	
	private void buildNgramPositionMap() {
		List<Map.Entry<String, Integer>> ngramList = sortByValue(ngramSet);
		
		if(ngramList.size() > relevantNgrams) {
			ngramList = ngramList.subList(0, relevantNgrams - 1);
		}
		
		int maxPosition = 0;
		int currentValue = 0;
		for(Map.Entry<String, Integer> substring : ngramList) {
			if(substring.getValue() != currentValue) {
				maxPosition++;
				currentValue = substring.getValue();
			}
			ngramPosition.put(substring.getKey(), maxPosition);
		}
	}
	
	private List<Map.Entry<String, Integer>> sortByValue(Map<String, Integer> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toList());
	}
	
}
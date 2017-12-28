package utility;

import java.util.LinkedList;
import java.util.List;

public class LanguageRecognizer {
	
	private static int numberOfMatchedLanguages = 10;
	
	public static List<Match> recognize(String text) {
		Profile textProfile = new Profile(text, Language.maxNgramLength);
		return match(textProfile);
	}
	
	private static List<Match> match(Profile unknownProfile) {
		LanguageBuilder.buildKnownLanguages();
		List<Language> knownLanguages = LanguageBuilder.getKnownLanguages();
		
		List<Match> bestMatches = new LinkedList<>();
		
		for(Language language : knownLanguages) {
			int distance = language.calculateDistance(unknownProfile);
			int closerLanguages = 0;
			
			for(Match match : bestMatches) {
				if(distance > match.getMatchAccuracy()) {
					closerLanguages++;
				}
			}
			
			if(closerLanguages < numberOfMatchedLanguages) {
				if(bestMatches.size() == numberOfMatchedLanguages) {
					bestMatches.remove(bestMatches.get(bestMatches.size() - 1));
				}
				bestMatches.add(closerLanguages, new Match(language, distance));
			}
		
		}
		return bestMatches;
	}
	
}

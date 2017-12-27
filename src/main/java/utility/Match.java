package utility;

public class Match {
	private Language matchedLanguage;
	private double matchAccuracy;
	
	public Match(Language matchedLanguage, double matchAccuracy){
		this.matchAccuracy = matchAccuracy;
		this.matchedLanguage = matchedLanguage;
	}
	
	public Language getMatchedLanguage() {
		return matchedLanguage;
	}
	
	public double getMatchAccuracy() {
		return matchAccuracy;
	}
	
	public void setMatchAccuracy(double matchAccuracy) {
		this.matchAccuracy = matchAccuracy;
	}
}

package detector.services;

public class Match {
	private Profile matchedProfile;
	private double value;
	
	public Match(Profile matchedProfile, double value){
		this.value = value;
		this.matchedProfile = matchedProfile;
	}
	
	public Profile getMatchedProfile() {
		return matchedProfile;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
}

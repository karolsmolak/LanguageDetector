package services;

public interface IRecognitionResult {
	Profile getWinner();
	String getOutput();
	void clear();
	void add(Profile profile, int distance);
	void commit();
}

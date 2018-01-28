package services;

public interface IRecognitionResult {
	public Profile getWinner();
	public String getOutput();
	public void clear();
	public void add(Profile profile, int distance);
	public void commit();
}

package services;

public interface IProfileRecognizer {
	public IRecognitionResult recognize(String text);
	public IRecognitionResult matchToKnownProfiles(Profile profile);
}

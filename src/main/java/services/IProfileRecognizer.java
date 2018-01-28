package services;

public interface IProfileRecognizer {
	IRecognitionResult recognize(String text);
	IRecognitionResult matchToKnownProfiles(Profile profile);
}

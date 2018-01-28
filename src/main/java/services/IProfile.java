package services;

public interface IProfile {
	String getName();
	int getSize();
	int calculateDistance(Profile other);
	int getPosition(String ngram);
	void build();
}

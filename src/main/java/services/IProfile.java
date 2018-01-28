package services;

public interface IProfile {
	public String getName();
	public int getSize();
	public int calculateDistance(Profile other);
	public int getPosition(String ngram);
	public void build();
}

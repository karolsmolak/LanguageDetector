package repository;

import java.util.List;

import services.Profile;

public interface IRepository {
	public List<Profile> getKnownProfiles();
	public void addProfile(String baseString, String name);
	public void removeProfile(Profile profile);
}

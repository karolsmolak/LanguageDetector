package repository;

import java.util.List;

import services.Profile;

public interface IRepository {
	List<Profile> getKnownProfiles();
	void addProfile(String baseString, String name);
	void removeProfile(Profile profile);
}

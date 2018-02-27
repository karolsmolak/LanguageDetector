package detector.repository;

import detector.services.Profile;

public interface IRepository {
    Iterable<Profile> findAll();
    void save(Profile profile);
}

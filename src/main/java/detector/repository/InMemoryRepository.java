package detector.repository;

import detector.services.Profile;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryRepository implements IRepository {
    private static final List<Profile> profiles = new LinkedList<>();

    @Override
    public Iterable<Profile> findAll() {
        return profiles;
    }

    @Override
    public void save(Profile profile) {
        profiles.add(profile);
    }
}

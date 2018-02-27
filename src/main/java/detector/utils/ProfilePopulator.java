package detector.utils;

import detector.repository.IRepository;
import detector.services.Profile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Component
public class ProfilePopulator {
    private static final Logger logger = LogManager.getLogger(ProfilePopulator.class);
    private static final String resourcePath = "src/main/resources/";
    private static final String indexXML = "udhr_txt/index.xml";

    private IRepository repository;

    private static final Semaphore lock = new Semaphore(1);

    @Value("${numberOfThreads}")
    private int numberOfThreads;

    @Autowired
    public ProfilePopulator(IRepository repository) {
        this.repository = repository;
    }

    public void populate() {
        logger.info("Started profile population...");
        Parser parser = new Parser();
        List<Element> elements = parser.parseXML(resourcePath + indexXML, "udhr");
        ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);
        try {
            for (final Element element : elements) {
                exec.submit(() -> {
                    String samplePath = resourcePath + getSamplePathById(element.getAttribute("f"));
                    Parser myParser = new Parser();

                    if(myParser.existsFile(samplePath)) {
                        String baseString = myParser.getFormattedFile(samplePath);
                        Profile newProfile = new Profile(baseString, element.getAttribute("n"), 5, 100);
                        try {
                            lock.acquire();
                            repository.save(newProfile);
                        } catch (InterruptedException ie) {} finally {
                            lock.release();
                        }
                    }
                });
            }
        } finally {
            exec.shutdown();
            try {
                exec.awaitTermination(100, TimeUnit.SECONDS);
            } catch(Exception e) {}
        }
        logger.info("Finished profile population");
    }

    private String getSamplePathById(String id) {
        return "udhr_txt/udhr_" + id + ".txt";
    }
}

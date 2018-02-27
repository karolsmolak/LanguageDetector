package detector.application;

import detector.config.Config;
import detector.utils.ProfilePopulator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	private static AnnotationConfigApplicationContext context = 
			new AnnotationConfigApplicationContext(Config.class);
	
	private static Parent root;

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(root, 634, 426);
		stage.setTitle("Language detector");
		stage.setScene(scene);
		stage.show();
		logger.info("application is up and running");
	}
	
	@Override
	public void init() throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/languagedetector-main.fxml"));
		fxmlLoader.setControllerFactory(context::getBean);
		root = fxmlLoader.load();
	}
	
	public static void main(String[] args) {
		logger.info("launching the application...");
		context.getBean(ProfilePopulator.class).populate();
		launch(args);
	}
}

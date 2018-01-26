package application;
	
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static Logger logger = LogManager.getLogger(Main.class);
	
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/languagedetector-main.fxml"));
			Scene scene = new Scene(root, 634, 426);
			
			stage.setTitle("Language detector");
			stage.setScene(scene);
			stage.show();
			
			logger.info("creating language profiles...");
			logger.info("application is up and running");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		logger.info("launching the application...");
		launch(args);
		context.close();
	}
}

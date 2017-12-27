package application;
	
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.LanguageBuilder;

public class Main extends Application {
	public static Logger logger = LogManager.getLogger(Main.class);
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Sample.fxml"));
	       
	        Scene scene = new Scene(root, 634, 426);
	        
	        stage.setTitle("Language detector");
	        stage.setScene(scene);
	        stage.show();
	        logger.info("creating language profiles...");
	        LanguageBuilder.buildKnownLanguages();
	        logger.info("application is up and running");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
    	logger.info("launching the application...");
		launch(args);
	}
}

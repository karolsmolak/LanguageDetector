package control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import services.IProfileRecognizer;
import services.IRecognitionResult;

public class Controller {
	
	@Autowired
	IProfileRecognizer recognizer;
	
	@FXML
	private TextFlow actiontarget;
	@FXML
	private TextArea textArea;
	
	@FXML
	public void handleButtonAction(ActionEvent event) {
		Main.logger.info("Started detection of the language...");

		IRecognitionResult result = recognizer.recognize(textArea.getText());
		
		Main.logger.info("language detected: " + result.getWinner().getName());
		actiontarget.getChildren().clear();
		Text output = new Text("Text language possibilites:\n\n" + result.getOutput());
		actiontarget.getChildren().add(output);
	}
}

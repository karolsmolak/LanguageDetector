package control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import services.IProfileRecognizer;
import services.IRecognitionResult;

@org.springframework.stereotype.Controller
@Qualifier("control.Controller")
public class Controller {
	
	@Autowired
	private IProfileRecognizer recognizer;
	
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

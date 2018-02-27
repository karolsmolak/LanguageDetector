package detector.control;

import detector.services.IProfileRecognizer;
import detector.services.IRecognitionResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Controller
@Qualifier("detector.control.Controller")
public class Controller {
	private static final Logger logger = LogManager.getLogger(Controller.class);

	@Autowired
	private IProfileRecognizer recognizer;

	@SuppressWarnings("SpellCheckingInspection")
    @FXML
	private TextFlow actiontarget;
	@FXML
	private TextArea textArea;
	@FXML
	public void handleButtonAction(ActionEvent event) {
		logger.info("Started detection of the language...");
		IRecognitionResult result = recognizer.recognize(textArea.getText());
		logger.info("language detected: " + result.getWinner().getName());
		actiontarget.getChildren().clear();
		Text output = new Text("Text language possibilities:\n\n" + result.getOutput());
		actiontarget.getChildren().add(output);
	}
}

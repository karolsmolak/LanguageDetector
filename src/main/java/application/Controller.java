package application;

import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import utility.LanguageRecognizer;
import utility.Match;

public class Controller {
    @FXML private TextFlow actiontarget;
    @FXML private TextArea textArea;
    
    @FXML public void handleButtonAction(ActionEvent event){
    	Main.logger.info("Started detection of the language...");
    	List<Match> bestMatches = LanguageRecognizer.recognize(textArea.getText());
    	transformAccuraciesToPercentages(bestMatches);
    	String output = prepareOutput(bestMatches);
    	Main.logger.info("language detected: " + bestMatches.get(0).getMatchedLanguage().getName());
    	actiontarget.getChildren().clear();
    	Text result = new Text("Text language possibilites:\n\n" + output);
    	actiontarget.getChildren().add(result);
    }
    
    private String prepareOutput(List<Match> matches) {
    	String output = new String();
    	for(Match match : matches) {
    		output += match.getMatchedLanguage().getName() + ": " + round(match.getMatchAccuracy(), 2) + "%\n";
    	}
    	return output;
    }
    
    private void transformAccuraciesToPercentages(List<Match> matches){
    	normalize(matches);
    	calculateProbabilites(matches);
    	scaleProbabilitiesToPercentages(matches);
    }
    
    private void scaleProbabilitiesToPercentages(List<Match> matches) {
    	//a1 <= a2 <=, ... <= an - accuracies
    	//x(an/a1 + ... + an/an) = 100
    	//ai:= x * an/ai;
    	double x = 0;  	
    	for(Match match : matches) {
    		x += matches.get(matches.size() - 1).getMatchAccuracy() / match.getMatchAccuracy();
    	}
    	x = 100 / x;	
    	for(Match match : matches) {
    		match.setMatchAccuracy(x * matches.get(matches.size() - 1).getMatchAccuracy() / match.getMatchAccuracy());
    	}
    }
    
    private void normalize(List<Match> matches) {
    	double mean = meanOfAccuracies(matches);
    	double deviation = deviationOfAccuracies(matches);
    	for(Match match : matches) {
    		match.setMatchAccuracy((match.getMatchAccuracy() - mean)/deviation);
    	}
    }
    
    private void calculateProbabilites(List<Match> matches) {
    	for(Match match : matches) {
    		match.setMatchAccuracy(new NormalDistribution().cumulativeProbability(match.getMatchAccuracy()));
    	}    	
    }
    
    private double deviationOfAccuracies(List<Match> matches) {
    	double mean = meanOfAccuracies(matches);
    	double deviation = 0;
    	for(Match match : matches) {
    		deviation += Math.pow(match.getMatchAccuracy() - mean, 2);
    	}
    	deviation = Math.sqrt(deviation / matches.size());
    	return deviation;
    }
    
    private double meanOfAccuracies(List<Match> matches) {
    	double mean = 0;
    	for(Match match : matches) {
    		mean += match.getMatchAccuracy();
    	}
    	mean /= matches.size(); 
    	return mean;
    }
    
    private double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
}

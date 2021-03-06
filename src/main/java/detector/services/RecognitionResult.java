package detector.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecognitionResult implements IRecognitionResult {
	
	@Value("${numberOfResultsViewed}")
	private int resultSize;
	
	private List<Match> matches = new ArrayList<>();
	
	public void clear() {
		matches.clear();
	}
	
	public void add(Profile profile, int distance) {
		matches.add(new Match(profile, distance));
	}
	
	public void commit() {
		matches.sort(Comparator.comparingDouble(Match::getValue));
		transformProfileDistancesToProbabilities();
	}
	
	public Profile getWinner() {
		return matches.get(0).getMatchedProfile();
	}
	
	public String getOutput() {
		StringBuilder output = new StringBuilder();
		List<Match> trimmedList = matches = matches.subList(0, resultSize);
		for (Match match : trimmedList) {
			output.append(match.getMatchedProfile().getName())
					.append(": ")
					.append(round(match.getValue(), 2))
					.append("%\n");
		}
		return output.toString();
	}
	
	private void transformProfileDistancesToProbabilities() {
		normalize();
		calculateProbabilites();
		scaleProbabilitiesToPercentages();
	}

	private void scaleProbabilitiesToPercentages() {
		// a1 <= a2 <=, ... <= an - accuracies
		// x(an/a1 + ... + an/an) = 100
		// ai:= x * an/ai;
		double x = 0;
		for (Match match : matches) {
			x += matches.get(matches.size() - 1).getValue() / match.getValue();
		}
		x = 100 / x;
		for (Match match : matches) {
			match.setValue(x * matches.get(matches.size() - 1).getValue() / match.getValue());
		}
	}

	private void normalize() {
		double mean = meanOfAccuracies();
		double deviation = deviationOfAccuracies();
		for (Match match : matches) {
			match.setValue((match.getValue() - mean) / deviation);
		}
	}

	private void calculateProbabilites() {
		for (Match match : matches) {
			match.setValue(new NormalDistribution().cumulativeProbability(match.getValue()));
		}
	}

	private double deviationOfAccuracies() {
		double mean = meanOfAccuracies();
		double deviation = 0;
		for (Match match : matches) {
			deviation += Math.pow(match.getValue() - mean, 2);
		}
		deviation = Math.sqrt(deviation / matches.size());
		return deviation;
	}

	private double meanOfAccuracies() {
		double mean = 0;
		for (Match match : matches) {
			mean += match.getValue();
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

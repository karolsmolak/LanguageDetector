package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;

public class RecognitionResult implements IRecognitionResult {
	private static final int resultSize = 15;
	
	private List<Match> matches = new ArrayList<>();
	
	public void add(Profile profile, int distance) {
		matches.add(new Match(profile, distance));
	}
	
	public void commit() {
		matches.sort((Match m1, Match m2) -> Double.compare(m1.getValue(), m2.getValue()));
		if(matches.size() > resultSize) {
			matches = matches.subList(0, resultSize - 1);
		}
		transformProfileDistancesToProbabilities();
	}
	
	public Profile getWinner() {
		return matches.get(0).getMatchedProfile();
	}
	
	public String getOutput() {
		String output = new String();
		for (Match match : matches) {
			output += match.getMatchedProfile().getName() + ": " + round(match.getValue(), 2) + "%\n";
		}
		return output;
	}
	
	public void transformProfileDistancesToProbabilities() {
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

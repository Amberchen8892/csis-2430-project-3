package optimalSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Question3 {

	public static void main(String[] args) {

		// ArrayList containing all Experiment objects
		ArrayList<Experiment> experiments = GatherExperiment
				.getExperiments("src/main/java/optimalSelection/experimentlist.csv");

		// Empty ArrayList to contain our selected experiments
		ArrayList<Experiment> payload = new ArrayList<>();
		int totalWeight = 0;
		int totalRating = 0;
		int weightLimit = 700;

		// Sorting the Experiments by rating/weight ratio
		Collections.sort(experiments, Comparator.comparingDouble(Experiment::getRatio).reversed());

		/*
		 * Iterate through Experiments in payload until weight limit is reached update
		 * totalWeight and totalRating for each experiment in payload
		 */
		for (Experiment experiment : experiments) {
			if ((totalWeight + experiment.getWeight()) <= weightLimit) {
				totalWeight += experiment.getWeight();
				totalRating += experiment.getRating();
				payload.add(experiment);
			} else {
				break;
			}
		}

		System.out.println("Total Weight: " + totalWeight);
		System.out.println("Total Rating: " + totalRating);
		System.out.println("Experiments in Payload: ");
		for (Experiment experiment : payload) {
			System.out.println(experiment);
		}
	}

}

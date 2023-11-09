package optimalSelection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ques2{

	public static ArrayList<Experiment> getArtists(String filepath) {

		ArrayList<Experiment> experiments = new ArrayList<Experiment>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				experiments.add(new Experiment(Integer.parseInt(tokens[0]), tokens[1], Integer.parseInt(tokens[2]),
						Integer.parseInt(tokens[3])));
			}
		} catch (IOException e) {
			System.out.println("A problem occured reading in the data.");
			e.printStackTrace();
		}
		return experiments;

	}


	public static void main(String[] args) {
		
		basedOnRating();
	}

	private static void basedOnRating() {
		ArrayList<Experiment> al = new ArrayList<Experiment>();
		al = GatherExperiment.getExperiments("src/main/java/optimalSelection/experimentlist.csv");
		List<Experiment> selectedExperiments = new ArrayList<>();
		int totalWeight = 0;
		int totalRating = 0;
		Collections.sort(al, Comparator.comparingInt((Experiment e) -> e.getRating())
                .thenComparingInt(e -> e.getWeight()).reversed());
		
		for (Experiment experiment : al) {
			if (totalWeight + experiment.getWeight() <= 700) {
				selectedExperiments.add(experiment);
				totalWeight += experiment.getWeight();
				totalRating += experiment.getRating();
			}

		}
		System.out.println("Selected Experiments based on Rating:");
		for (Experiment experiment : selectedExperiments) {
			System.out.println("Experiment " + experiment.getNumber() + " - " + experiment.getExperiment() + " (Rating: "
					+ experiment.getRating() + ", Weight: " + experiment.getWeight() + " kg");
		}

		System.out.println("Total Weight: " + totalWeight + " kg");
		System.out.println("Total Rating: " + totalRating);
	}

	

}

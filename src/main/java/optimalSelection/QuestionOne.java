package optimalSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuestionOne {

    public static void main(String[] args) {
        // Get the list of experiments from the CSV file
        ArrayList<Experiment> experiments = GatherExperiment.getExperiments("src/main/java/optimalSelection/experimentlist.csv");

        // Sort the experiments by weight in ascending order
        Collections.sort(experiments, Comparator.comparingInt(Experiment::getWeight));

        // Initialize variables for total weight and total rating
        int totalWeight = 0;
        int totalRating = 0;

        // Set the weight constraint
        int weightConstraint = 700;

        // Initialize a list to keep track of selected experiments
        ArrayList<Experiment> selectedExperiments = new ArrayList<>();

        // Iterate through experiments and add them to the payload until weight constraint is met
        for (Experiment experiment : experiments) {
            if (totalWeight + experiment.getWeight() <= weightConstraint) {
                totalWeight += experiment.getWeight();
                totalRating += experiment.getRating();
                selectedExperiments.add(experiment); // Add the selected experiment to the list
            } else {
                // If adding the experiment exceeds weight constraint, break the loop
                break;
            }
        }

        // Print the total rating achieved, total weight, and the experiments selected
        System.out.println("Total Rating Achieved: " + totalRating);
        System.out.println("Total Weight of Selected Experiments: " + totalWeight + " kg");
        System.out.println("Experiments Selected:");
        for (Experiment experiment : selectedExperiments) {
            System.out.println(experiment);
        }
    }
}

package optimalSelection;

import java.util.ArrayList;
/**
 * Class that takes in a list of experiment objects from a .csv file and compares all the possible sets of those experiments 
 * to determine the best rated experiment using dynamic programming
 *
 * Phuong Tran
 */
public class QuestionFive {
    public static void main(String[] args) {
        // Define the path to the CSV file
        String csvFilePath = "src/main/java/optimalSelection/experimentlist.csv";

        // Get the list of experiments from the CSV file
        ArrayList<Experiment> experiments = GatherExperiment.getExperiments(csvFilePath);

        int shuttleCapacity = 700;
        int numExperiments = experiments.size();

        // Create a 2D table to store maximum ratings
        int[][] dpTable = new int[numExperiments + 1][shuttleCapacity + 1];

        // Fill the table using dynamic programming
        for (int i = 1; i <= numExperiments; i++) {
            Experiment experiment = experiments.get(i - 1);
            for (int w = 0; w <= shuttleCapacity; w++) {
                if (experiment.getWeight() <= w) {
                    dpTable[i][w] = Math.max(
                        dpTable[i - 1][w],
                        dpTable[i - 1][w - experiment.getWeight()] + experiment.getRating()
                    );
                } else {
                    dpTable[i][w] = dpTable[i - 1][w];
                }
            }
        }

        // Backtrack to find the selected experiments
        int w = shuttleCapacity;
        ArrayList<Experiment> selectedExperiments = new ArrayList<>();
        for (int i = numExperiments; i > 0 && w > 0; i--) {
            if (dpTable[i][w] != dpTable[i - 1][w]) {
                Experiment experiment = experiments.get(i - 1);
                selectedExperiments.add(experiment);
                w -= experiment.getWeight();
            }
        }

        // Print the selected experiments and their total rating
        System.out.println("Selected Experiments:");
        for (Experiment experiment : selectedExperiments) {
            System.out.println(experiment);
        }
        System.out.println("Total Rating: " + dpTable[numExperiments][shuttleCapacity]);
    }
}

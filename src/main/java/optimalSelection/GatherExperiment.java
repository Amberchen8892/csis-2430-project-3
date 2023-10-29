package optimalSelection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Using these classes, we should easily be able to run all the tests needed for each question.
 * Experiment.java creates an object representation for each row in the data set, corresponding to one experiment.
 * GatherExperiments.java reads in the data from the file we made yesterday, and creates an ArrayList ( I chose ArrayList 
 * so we can easily convert it into other data sets and give us some basic functionality) of all the Experiment objects. 
 * Individual experiments can be accessed via their index, and the data accessed using the '.' keyword and the 
 * getters and setters in the Experiment class. 
 * 
 * If you keep the package intact, provided file path should work
 * File path: "experimentlist.csv"
 * @author Zach Royer
 */
public class GatherExperiment {
	/*
	 * Reads our .csv file path, attributes each row to an Experiment object, and
	 * returns an ArrayList of Experiment objects
	 * 
	 * @param Specified file path
	 * 
	 * @returns An ArrayList of all Experiment objects
	 */
	public static ArrayList<Experiment> getExperiments(String filepath) {

		ArrayList<Experiment> experiments = new ArrayList<Experiment>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				experiments.add(new Experiment(Integer.parseInt(tokens[0]), tokens[1], Integer.parseInt(tokens[2]),
						Integer.parseInt(tokens[3])));
			}
		} catch (IOException e) {
			System.out.println("A problem occured reading in the songs.");
			e.printStackTrace();
		}
		return experiments;

	}

	public static void main(String[] args) {
		System.out.println(GatherExperiment.getExperiments("experimentlist.csv"));

	}
}

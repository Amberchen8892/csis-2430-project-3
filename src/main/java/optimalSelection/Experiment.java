package optimalSelection;

/*
 * Object representation of 'Experiments' corresponding to individual rows on 'experimentlist.java'
 * @author Zach Royer
 */
public class Experiment {

	//Attributes
	private int number;
	private String experiment;
	private int weight;
	private int rating;
	private double ratio;

	//Constructor
	public Experiment(int number, String experiment, int weight, int rating) {
		this.number = number;
		this.experiment = experiment;
		this.weight = weight;
		this.rating = rating;
		this.ratio = (double) rating / weight;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getExperiment() {
		return experiment;
	}

	public void setExperiment(String experiment) {
		this.experiment = experiment;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Experiment [number=" + number + ", experiment=" + experiment + ", weight=" + weight + ", rating="
				+ rating + ", ratio=" + ratio+ "]";
	}

	public double getRatio() {
		return ratio;
	}

}

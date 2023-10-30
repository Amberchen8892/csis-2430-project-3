package optimalSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Erin Mortensen
 * Class to find all the permutations possible for a list of experiments and then to see which permutation has the highest rating within a given weight limit of 700 by using a bruteforce approach.
 *
 */
public class BruteForceSelection
{

	private ArrayList<Experiment> originalExperimentList; //list of experiments we start with
	private ArrayList<Experiment[]> topRatedFinalists; //list of experiment permutations who potentially have the highest rating
	private int weightLimit = 700 ; //weight limit
	private int topRating = 0; //top rating of all experiments
	private ArrayList<Experiment[]> topRated; //set of experiments who have the top rating

	/**
	 * Initializes the array lists properties of the class.  A .csv file is used to initialize the originalExperimentList property using the GatherExperiment class.
	 * @param fileName
	 */
	public BruteForceSelection(String fileName)
	{
		originalExperimentList = GatherExperiment.getExperiments(fileName);
		topRatedFinalists = new ArrayList<Experiment[]>();

		for (Experiment[] e : topRatedFinalists)
		{
			e = null;
		}

		topRated = new ArrayList<Experiment[]>();

	}

	/**
	 * The driver of the class.  It calls the generateAllPossibleExperiments function to get all permutations of the experiments list and then calls the getTopRated formula to determine which
	 * permutation of the experiments is the one with the top rating.  It then prints the top rating and the array of experiments that matches this and prints them in the console.
	 */
	public void listExperiments()
	{

		generateAllPossibleExperiments(0, originalExperimentList.size() - 1); // start with first element, which is 0
		
		System.out.println("Top Rating " + topRating);
		getTopRated();
		
		for(Experiment[] top : topRated)
		{
			System.out.print("{");
			for(Experiment t: top)
			{
				System.out.print(t.getExperiment() + ", ");
			}
			System.out.println("}");
		}

	}

	/**
	 * Go through the list of potential top rated experiment arrays calculates their overall rating.  It then determines if these match the topRating field.  If 
	 * they do, and they are unique from any other array elements then they are added to the topRated ArrayList.  This is done in case there are two different arrays
	 * with unique sets that both match the top rating
	 * @return Returns ArrayList of Experiment arrays with the top rating 
	 */
	public ArrayList<Experiment[]> getTopRated()
	{
		for (Experiment[] experimentList : topRatedFinalists)
		{
			int currentRating = 0;

			for (int experiment = 0; experiment < experimentList.length; experiment++)
			{
				currentRating += experimentList[experiment].getRating();
			}

			//check which of the finalists matches the top rating amongst all permutations.  If there are multiple with the top include them
			if (currentRating == topRating)
			{
				boolean matchesAllElements = false;
				//check to make sure we aren't including the same list in a different order 
				for(int e = 0; e < experimentList.length; e++)
				{
					for(Experiment[] t : topRated)
					{
						for(Experiment topExperiment : t)
						{
							if(experimentList[e].getNumber() == topExperiment.getNumber())
							{
								matchesAllElements = true;
								break;
							}
						}
						
					}

					}
				if(!matchesAllElements) //if not same array, but with elements in different order
				{
					topRated.add(experimentList);

				}
			}

		}
		
		return topRated;

	}

	/**
	 * Generate the permutations of all the possible experiments in different orders.  Once permutation is found, start from the beginning of the list of experiments and only include up to the 
	 * element that is still within the weight limit.  Check what the rating of the experiments in the list are and if they are the same or more than the topRating for all permutations then
	 * add them to the topRatedFinalists list.  Not all permutations can be added to a list because this takes up a lot of space on the heap, so only the top rated are included.
	 * 
	 * Permutations are done by working from the end of an ArrayList and swapping the elements as we work our way up
	 * 
	 * @param left The position in the array list we are starting at
	 * @param right The position in the array list we are ending at
	 */
	private void generateAllPossibleExperiments(int left, int right)
	{
		// If we have reached the end of the array (base case), add the current
		// permutation to the list
		if (left == right)
		{
			//by using the weight limit determine how many elements in our current ArrayList are going to make it to our array
			//use this so we can use array of a set size instead of growing an ArrayList
			int experimentsToInclude = 0;
			int currentWeight = 0;
			for (int e = 0; e < originalExperimentList.size(); e++)
			{
				currentWeight += originalExperimentList.get(e).getWeight();
				if (currentWeight <= weightLimit)
				{
					experimentsToInclude++;
				}
				else
				{
					//everything else makes us go above the wight limit so no need to include
					break;
				}

			}

			//initialize array that we can add all the experiments to, with only the applicable experiments in it given the wight limit
			//print these on the console
			Experiment[] experimentSet = new Experiment[experimentsToInclude];
			int currentRating = 0;

			for (int e = 0; e < experimentsToInclude; e++)
			{
				experimentSet[e] = originalExperimentList.get(e);
				currentRating += experimentSet[e].getRating();
				System.out.print(experimentSet[e].getExperiment() + ", ");
			}
			System.out.println();

			//if the rating of this experiment array is equal to or greater than the current toprating add it to the list of potential top rated arrays
			//don't want to add all arrays because this takes up a significant amount of space, but want some way we can keep track of who the top rating belongs to
			if (currentRating >= topRating)
			{
				topRating = currentRating;
				topRatedFinalists.add(experimentSet);

			}

		}
		else
		{
			// Iterate through elements in the array and swap them to generate permutations
			for (int i = left; i <= right; i++)
			{
				// Swap elements at indices 'left' and 'i'
				swap(originalExperimentList, left, i);
				// Recursively generate permutations for the remaining elements
				generateAllPossibleExperiments(left + 1, right);
				// Backtrack by swapping the elements back to their original positions
				swap(originalExperimentList, left, i);
			}
		}

	}

	/** 
	 * Function to swap two Experiment elements in an ArrayList
	 */
	private void swap(ArrayList<Experiment> e, int i, int j)
	{
		Experiment temp = e.get(i);
		e.set(i, e.get(j));
		e.set(j, temp);
	}

	public static void main(String[] args)
	{
		BruteForceSelection experimentPermutations = new BruteForceSelection("src/main/java/optimalSelection/experimentlist.csv");
		experimentPermutations.listExperiments();

	}

}

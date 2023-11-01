package optimalSelection;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that takes in a list of experiment objects from a .csv file and compares all the possible sets of those experiments to determine the best rated experiment through a bruteforce technique.
 * @author Erin Mortensen
 *
 */
public class BruteForceSelection
{
	private ArrayList<Experiment> originalExperimentList; //list of experiments we start with and need to examine
	private ArrayList<ArrayList <Experiment>> experimentsToTake; //List of experiments we should take based on their weight and that they have the highest rating
	private int weightLimit; //weight limit
	private int topRating; //top rating of all experiments
	
	/**
	 * Initialize the fields for the class
	 * @param fileName Name of .csv file that has Experiment objects in it that can be read by the GatherExperiment class
	 */
	public BruteForceSelection(String fileName)
	{
		originalExperimentList = GatherExperiment.getExperiments(fileName);
		experimentsToTake = new ArrayList<ArrayList<Experiment>>();
		
		weightLimit = 700;
		topRating = 0;
	}
	
	/**
	 * Finds all the possible subset of experiments we are able to take and determines which of them are potentially the top rated.  Then goes through and based on the top rating found
	 * removes any list of experiments who do not have that rating.  This allows for there to be a tie among several experiments and to keep track of those ties.  It then prints out the top
	 * rating and the experiment subsets that have that top rating.
	 * @return Returns ArrayList of an ArrayList of Experiments who have the top rating and should be taken on the space ship.  Allows for ties 
	 * list of experiments
	 */
	public ArrayList<ArrayList<Experiment>> getTopExperiment()
	{
		//sets the exerimentsToTake ArrayList
		analyzePossibleExperimentLists();
		
		//Go through the potential experiments to take and if they don't match the highest rating found amongst them they are removed
		//This approach allows for us to have several sets of experiments that tie for the top rating
		Iterator<ArrayList<Experiment>> itr = experimentsToTake.iterator();
	    while (itr.hasNext()) 
	    {
	      ArrayList<Experiment> currentExperimentList = itr.next();
	      
	      if (determineExperimentListRatings(currentExperimentList) != topRating) 
	      {
	        itr.remove();
	      }
	      
	    }
	    
	    //Print out the results
	    System.out.println("Top Rating: " + topRating);
	    System.out.println("List of experiments you can take to get the top rating: ");
	    
	    for(ArrayList<Experiment> top : experimentsToTake)
		{
			System.out.print("{");
			for(int experimentImOn = 0; experimentImOn < top.size(); experimentImOn++)
			{
				System.out.print(top.get(experimentImOn).getExperiment());
				
				if(experimentImOn != top.size() - 1)
				{
					System.out.print(", ");
				}

			}
			
			System.out.println("}");
		}

		
		return experimentsToTake;
		
		
	}
	
	/**
	 * Returns the summation of individual experiment ratings in a list 
	 * @param myList list of experiments to get the rating for
	 * @return Returns int representing the rating for the list of experiments
	 */
	private int determineExperimentListRatings(ArrayList<Experiment> myList)
	{
		int listRating = 0;
		
		for(Experiment anExperiment : myList)
		{
			listRating += anExperiment.getRating();
		}
		
		return listRating;
	}

	/**
	 * Updates the experimentsToTake ArrayList with potential top rated experiment lists.  Takes the size of the original list of experiments and determines how many 
	 * subsets exist that represent the possible combination of experiments to include.  Creates a binary string for each of these representations and then uses them to see what subset is within
	 * the weight limit and which has the highest rating.  It is possible for more than one subset to tie for the top rating, so this function sets the initial list of all possible subsets 
	 * that are a finalists for the top rating, and then that list is narrowed down to the one with the top rating in the getTopExperiment function.  As the function goes through all possible subsets 
	 * it determines what the top ranking subset is.
	 */
	public void analyzePossibleExperimentLists()
	{
		int numberOfSubSets = (int) Math.pow(2, originalExperimentList.size());
		
		for(int subset = 0; subset < numberOfSubSets; subset++)
		{
			//get each subset to use to analyze the list of experiments
			String sub = numberAsBinaryString(subset, originalExperimentList.size());
			
			int currentWeight = 0;
			int currentRating = 0;
			ArrayList<Experiment> currentList = new ArrayList<Experiment>();

			//if the subset is a 1 the experiment is in the list, if it is 0 it is not. Add up the weight and rating for each
			for(int e = 0; e < originalExperimentList.size(); e++)
			{				
				if(sub.charAt(e) == '1')
				{
					Experiment includedExperiment = originalExperimentList.get(e);
					currentWeight += includedExperiment.getWeight();
					currentRating += includedExperiment.getRating();
					currentList.add(includedExperiment);
					
				}
			}
			
			//if within weight limit and better than the current rating add to finalists list
			if((currentWeight < weightLimit) && (currentRating >= topRating))
			{
				topRating = currentRating;
				experimentsToTake.add(currentList);		//will determine later if it is the top experiment		
				
			}
		}
		
	}
	
	/**
	 * Takes an integer and returns a bit string representation of it, of a specified length.
	 * @param n The number you want converted into a bit string
	 * @param size How long the bit string should be.  Helps with specifying number of preceding zeros
	 * @throws IllegalArgumentException If size does not allow for the full bit string to be represented because it is not large enough
	 * @return Returns bit string of the number
	 */
	private String numberAsBinaryString(int n, int size) throws IllegalArgumentException
	{
		String binaryString = "";
		
		//if the binary string can't be represented because the size specified isn't large enough throw exception
		if(Math.pow(2,size) <= n)
		{
			int appropriateSize = size;
			
			while(Math.pow(2,appropriateSize) <= n)
			{
				appropriateSize++;
			}
			
			String errorMessage = "Size too small.  Should be " + appropriateSize;
			throw new IllegalArgumentException(errorMessage);
		}

		for(int i = size - 1; i >= 0; i--)
		{
			
			int bin = n >> i; //shift to the right
			bin = bin & 1;	
			binaryString += bin;

			
		}
		
		return binaryString;
	}
	
	public static void main(String[] args)
	{
		
		BruteForceSelection bfs = new BruteForceSelection("src/main/java/optimalSelection/experimentlist.csv");
		bfs.getTopExperiment();
	

	}

}

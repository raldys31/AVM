import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import collections.list.LinkedList;

/**
 * @author Raldys Y. Cruz Serrano
 *
 */

public class Election {

	public static void main(String[] args) throws Exception {

		String ballotsFile = "./res/input/ballots.csv";
		String candidatesFile = "./res/input/candidates.csv";
		String line = "";
		BufferedReader br = null;

		LinkedList<Candidate> candidates = new LinkedList<Candidate>();

		//Read candidates file line by line
		try { 

			br = new BufferedReader(new FileReader(candidatesFile));
			while ((line = br.readLine()) != null) {

				//Use comma as separator and split line
				String[] data = line.split(",");  

				Candidate c = new Candidate(data[0], Integer.valueOf(data[1])); 
				candidates.add(c);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


		//Initializing list of list of ballots using a LinkedList as a set
		int candidatesNum = candidates.size();
		LinkedList<LinkedList<Ballot>> setList = new LinkedList<LinkedList<Ballot>>();

		//Initialize sets
		for (int i = 0; i < candidatesNum; i++) {
			LinkedList<Ballot> set = new LinkedList<Ballot>();
			setList.add(set);
		}

		int totalBallots = 0;
		int validBallots = 0;
		int blankBallots = 0;

		try { //Try to read ballots file line by line

			br = new BufferedReader(new FileReader(ballotsFile));
			while ((line = br.readLine()) != null) { 

				totalBallots++;
				Ballot ballot = new Ballot(line);

				if (ballot.candidates.size() == 0) {
					blankBallots++;
				}
				else if (Ballot.validBallot(ballot, candidatesNum)) {
					validBallots++;

					//Here we get the right set from setList and add the valid ballot
					setList.get(ballot.getCandidateByRank(1) - 1).add(ballot);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			FileWriter writer = new FileWriter("./res/output/results.txt");
			writer.write("Number of ballots: " + String.valueOf(totalBallots));
			writer.write("\r\n");
			writer.write("Number of blank ballots: " + String.valueOf(blankBallots));
			writer.write("\r\n");
			writer.write("Number of invalid ballots: " + String.valueOf(totalBallots - validBallots - blankBallots));
			writer.write("\r\n");

			Candidate winner = runRound(setList, candidates, candidatesNum, validBallots, blankBallots, writer);

			writer.write("Winner: " 
					+ winner.getName() + " wins with " 
					+ String.valueOf(winner.getScore()) + " #1's");

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Handles all the round logic and output logic
	// Returns candidate with top votes
	public static Candidate runRound(LinkedList<LinkedList<Ballot>> setList, LinkedList<Candidate> candidates, 
			int candidatesNum, int validBallots, int blankBallots, FileWriter writer) throws IOException {

		//Holds eliminated candidates
		LinkedList<Candidate> eliminated = new LinkedList<Candidate>();

		//Holds scores per round for ranks
		LinkedList<Integer> scores = new LinkedList<Integer>();

		//Initialize scores to 0
		for (int i  = 0; i < candidatesNum; i++) {
			scores.add(0);
		}

		int round = 0;
		LinkedList<Integer> tied = new LinkedList<Integer>(); // Holds candidate with same bottom scores

		while (true) {
			round++;
			for (int rank = 1; rank <= candidatesNum; rank++) {

				//Count votes for all candidates
				for (int i = 0; i < candidatesNum; i++) {
					scores.set(i, 0);

					//Only count votes of candidates that are not eliminated
					if (!eliminated.contains(candidates.get(i))) {
						for (int j = 0; j < candidatesNum; j++) {
							LinkedList<Ballot> set = setList.get(j);
							for(Ballot ballot: set) {
								if (ballot.getCandidateByRank(rank) -1 == i) {
									scores.set(i, scores.get(i) + 1);
								}
							}
						}
					}
					else {
						scores.set(i, -1);
					}
				}

				//If rank is one, we update candidate scores. 
				if (rank == 1) {
					int least = 999999;

					//Go through all candidates and get scores for rank one
					for (int i = 0; i < candidatesNum; i++) {
						if (scores.get(i) != -1) {
							candidates.get(i).setScore(scores.get(i));

							//We found candidate with majority
							if ((float) scores.get(i) / validBallots > 0.5) {
								return candidates.get(i);
							}															

							//If score is less than least, we make the score the least
							if (scores.get(i) < least) {
								least = scores.get(i);
								tied = new LinkedList<Integer>(); //Reset tied list
								tied.add(i); //Add current least candidate
							}

							//If a tie is found, add candidate index to tied
							else if (scores.get(i) == least) {
								tied.add(i);
							}

						}
					}

					//Since tied has one element, we eliminate it
					if (tied.size() == 1) {
						if (candidatesNum - 2 != eliminated.size()) {
							writer.write("Round " + String.valueOf(round) + ": " 
									+ candidates.get(tied.get(0)).getName() + " was eliminated with " 
									+ String.valueOf(candidates.get(tied.get(0)).getScore()) + " #1's");
							writer.write("\r\n");
						}

						eliminateCandidate(setList, candidates.get(tied.get(0)), eliminated); // Eliminate candidate
						tied = new LinkedList<Integer>(); //Reset tied
						break;
					}
				}
				else {

					int least = 999999;

					//Iterate through tied candidates
					for (int i = 0; i < tied.size(); i++) {
						if (scores.get(candidates.get(tied.get(i)).getCandidateID()-1) != -1) {

							// If score is less than least, we make the score the least
							if (scores.get(candidates.get(tied.get(i)).getCandidateID()-1) < least) {
								least = scores.get(candidates.get(tied.get(i)).getCandidateID()-1);
							}
						}
					}

					//Remove candidates with scores greater than least
					for (int i = 0; i < tied.size(); i++) {
						if (scores.get(candidates.get(tied.get(i)).getCandidateID()-1) > least) {
							tied.remove(i);
						}
					}

					//Since tied has one element, we eliminate it
					if (tied.size() == 1) {
						if (candidatesNum - 2 != eliminated.size()) {
							writer.write("Round " + String.valueOf(round) + ": " 
									+ candidates.get(tied.get(0)).getName() + " was eliminated with " 
									+ String.valueOf(candidates.get(tied.get(0)).getScore()) + " #1's");
							writer.write("\r\n");
						}

						eliminateCandidate(setList, candidates.get(tied.get(0)), eliminated); // Eliminate candidate
						tied = new LinkedList<Integer>(); //Reset tied
						break;
					}
				}
			}

			//Tie wasn't broken after counting all ranks. Remove last element of tied which is highest ID#
			if (tied.size() > 1) {
				if (candidatesNum - 2 != eliminated.size()) {
					writer.write("Round " + String.valueOf(round) + ": " 
							+ candidates.get(tied.last()).getName() + " was eliminated with " 
							+ String.valueOf(candidates.get(tied.last()).getScore()) + " #1's");
					writer.write("\r\n");
				}

				eliminateCandidate(setList, candidates.get(tied.last()), eliminated); //Eliminate candidate
				tied = new LinkedList<Integer>(); //Reset tied

			}

		}		
	}

	//Method to remove candidate
	@SuppressWarnings("unused")
	public static void eliminateCandidate(LinkedList<LinkedList<Ballot>> setList, Candidate toRemove, LinkedList<Candidate> eliminated ) {			

		//Eliminate candidate from every ballot
		for (int i = 0; i < setList.get(toRemove.getCandidateID() - 1).size(); i++) {
			boolean eliminated1 = setList.get(toRemove.getCandidateID() - 1).get(i).eliminate(toRemove.getCandidateID()); //Remove candidate from ballot 
		}

		//Go through every eliminated candidate to remove them from ballot
		for (int i = 0; i < setList.get(toRemove.getCandidateID() - 1).size(); i++) {
			for (int j = 0; j < eliminated.size(); j++) {

				if (setList.get(toRemove.getCandidateID() - 1).get(i).getCandidateByRank(1) == eliminated.get(j).getCandidateID()) {
					boolean eliminated1 = setList.get(toRemove.getCandidateID() - 1).get(i).eliminate(eliminated.get(j).getCandidateID()); //Remove candidate from ballot 
					i = 0;
					j = 0;
				}
			}
		}

		//Add candidate to eliminated list
		eliminated.add(toRemove);
	}

}


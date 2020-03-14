import collections.list.LinkedList;

public class Ballot {

	private int ballotNumber;
	public LinkedList<String> candidates;

	public Ballot(String line) {

		String[] data = line.split(","); //Split line by commas
		this.ballotNumber = Integer.valueOf(data[0]); //First string is ballot number

		candidates = new LinkedList<String>();
		for (int i = 1; i < data.length; i++) { //Go through all rankings in ballot
			candidates.add(data[i]); //Here we add "ci:x" string to candidate list
		}
	}

	public int getBallotNumber() {
		return ballotNumber;
	}

	public void setBallotNumber(int ballotNumber) {
		this.ballotNumber = ballotNumber;
	}

	//Returns the ballot number
	public int getBallotNum() {
		return ballotNumber;
	}

	//Returns rank for that candidate
	public int getRankByCandidate(int candidateId) {
		for (String candidate: candidates) {
			String[] data = candidate.split(":"); //Split "ci:x" by :

			if (Integer.valueOf(data[0]).equals(candidateId-1)) { //If ci == candidateId
				return Integer.valueOf(data[1]); //Return rank
			}
		}
		return 999999; //Default error return
	}

	//Returns candidate with that rank
	public int getCandidateByRank(int rank) {
		for (String candidate: candidates) {
			String[] data = candidate.split(":"); //Split "ci:x" by :
			if (Integer.valueOf(data[1]).equals(rank)) { //If x == rank
				return Integer.valueOf(data[0]); //Return candidateId
			}
		}
		return -1; //Default error return
	}

	//Eliminates a candidate
	public boolean eliminate(int candidateId) {
		for (int i = 0; i < candidates.size(); i++) {
			String[] data = candidates.get(i).split(":"); //Split "ci:x" by :

			if (Integer.valueOf(data[0]) == candidateId && Integer.valueOf(data[1]) == 1) { //If ci == candidateId
				candidates.remove(i); 

				//Here we modify values of remaining entries in ballot
				for (int j = 0; j < candidates.size(); j++) {
					String[] data2 = candidates.get(j).split(":"); 
					data2[1] = String.valueOf(Integer.valueOf(data2[1]) - 1);
					candidates.set(j, String.join(":", data2));
				}
				return true;
			}
		}
		return false; //Candidate wasn't found, so return false
	}
	
	//Method to check if ballot is valid
		public static boolean validBallot(Ballot ballot, int totalCandidates) {

			//If there are k candidates ranked, then 0 to k elements should be 1, else invalid ballot
			LinkedList<Integer> checkC = new LinkedList<Integer>(); 
			//Check count of ranks, uses same logic as above
			LinkedList<Integer> checkB = new LinkedList<Integer>(); 

			int candidatesNum = ballot.candidates.size();

			//Initialize arrays to zero
			for (int i = 0; i < totalCandidates; i++) {
				checkC.add(0);
				checkB.add(0);
			}

			for (String vote: ballot.candidates) {
				String[] data = vote.split(":");
				if (Integer.valueOf(data[0]) > totalCandidates || Integer.valueOf(data[1]) > candidatesNum) {
					return false;
				}

				//For candidate c:i, add one count in checkC
				checkC.set(Integer.valueOf(data[0]) - 1, checkC.get(Integer.valueOf(data[0]) - 1) + 1);

				//For candidate x, add one count in checkB
				checkB.set(Integer.valueOf(data[1]) - 1, checkB.get(Integer.valueOf(data[1]) - 1) + 1);

			}

			//Now we check for ranks from 0 to k-1. Values must be one on every check
			for (int i = 0; i < candidatesNum; i++) {
				if (checkB.get(i) != 1) {
					return false;
				}
			}

			int sum = 0;
			//Check all candidate sums. Candidates sum must equal 1
			for (int i = 0; i < totalCandidates; i++) {
				if (checkC.get(i) > 1) {
					return false;
				}
				else if (checkC.get(i) == 1) {
					sum++;
				}
			}

			//Checks for repeating candidates
			if (sum != candidatesNum) {
				return false;
			}
			return true;
		}
}

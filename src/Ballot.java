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
}

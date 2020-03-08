public class Ballot {
	
	private int ballotNumber;
	private int candidateID;
	private int candidateRank;
	
	public Ballot(int ballotNumber, int candidateID, int candidateRank) {
		this.ballotNumber = ballotNumber;
		this.candidateID = candidateID;
		this.candidateRank = candidateRank;
	}

	public int getBallotNumber() {
		return ballotNumber;
	}

	public void setBallotNumber(int ballotNumber) {
		this.ballotNumber = ballotNumber;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
	}

	public int getCandidateRank() {
		return candidateRank;
	}

	public void setCandidateRank(int candidateRank) {
		this.candidateRank = candidateRank;
	}

	//Returns the ballot number
	public int getBallotNum() {
		return 0;
	}
	
	//Returns rank for that candidate
	public int getRankByCandidate() {
		return 0;
	}
	
	//Returns candidate with that rank
	public int getCandidateByRank() {
		return 0;
	}
	
	//Eliminates a candidate
	public boolean eliminate(int candidateId) {
		return false;
	}

}

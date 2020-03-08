public class Candidate {

	public static void main(String[] args) {

	}

	public String name;
	public int candidateID;

	public Candidate(String name, int candidatenumber) {
		this.name = name;
		this.candidateID = candidatenumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidatenumber) {
		this.candidateID = candidatenumber;
	}


}
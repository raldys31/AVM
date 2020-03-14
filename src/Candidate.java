public class Candidate {

	public String name;
	public int candidateID;
	public int score;

	public Candidate(String name, int candidatenumber) {
		this.name = name;
		this.candidateID = candidatenumber;
		this.score = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore() {
		this.score++;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidatenumber) {
		this.candidateID = candidatenumber;
	}

}
import java.io.File;
import java.util.Scanner;

public class Election {

	public static void main(String[] args) throws Exception {

		String ballotsPath = "D:\\Documents\\Data Structures Projects\\AVM\\src\\docs\\ballots.csv";
		String candidatesPath = "D:\\Documents\\Data Structures Projects\\AVM\\src\\docs\\candidates.csv";

		File ballots = new File(ballotsPath); 
		File candidates = new File(candidatesPath); 

		organizeBallots(ballots, BALLOTS);
		organizeCandidates(candidates, CANDIDATES);

	} 

	public static final Ballot[][] BALLOTS = new Ballot[10][10];
	public static final Candidate[][] CANDIDATES = new Candidate[10][10];

	//Method to read and organize ballots by ID, candidate and rank
	@SuppressWarnings({ "resource"})
	public static void organizeBallots(File file, Ballot[][] ballots) throws Exception {
		Scanner ballotIn = new Scanner(file);
		String line = ballotIn.nextLine();
		String[] lineIndex = line.split(","); // getting only the id of the ballot

		for (int i = 0; i < ballots.length; i++) { // assigning ballots elements and filling Set with ballots
			for (int j = 1; j < lineIndex.length; j++) {
				String[] temp = lineIndex[j].split(":");
				String candidateID = temp[0];
				String rank = temp[1];

				ballots[i][j] = new Ballot(Integer.parseInt(lineIndex[0]), Integer.parseInt(candidateID), Integer.parseInt(rank));

				System.out.println("Ballot Number: " + ballots[i][j].getBallotNumber() + " Candidate ID: " + ballots[i][j].getCandidateID() + " Rank: " + ballots[i][j].getCandidateRank());
				if (j % 6 == 5) {
					System.out.print("\n");
				}
			}

			if (!(ballotIn.hasNext())) {
				break;
			}
			else {
				line = ballotIn.nextLine();
				lineIndex = line.split(",");	
			}
		}
	}

	//Method to read and organize candidates by name and number
	@SuppressWarnings("resource")
	public static void organizeCandidates(File file, Candidate[][] candidates) throws Exception {
		Scanner candidatesIn = new Scanner(file);
		String line = candidatesIn.nextLine();
		String[] lineIndex = line.split(",");
		String candidateName = lineIndex[0];
		String candidateID = lineIndex[1];

		for (int i = 0; i < lineIndex.length; i++) {
			for (int j = 0; j < lineIndex.length + 1; j++) { 
				candidateName = lineIndex[0];
				candidateID = lineIndex[1];
				candidates[i][j] = new Candidate(candidateName, Integer.parseInt(candidateID));

				System.out.println(candidates[i][j].getName() + " ID is: " + candidates[i][j].getCandidateID() );

				if (!(candidatesIn.hasNext())) {
					break;
				}
				else {
					line = candidatesIn.nextLine();
					lineIndex = line.split(",");		
				}
			}
		}
	}
}


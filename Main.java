import java.util.*;
import SuffixTreePackage.*;

/**
 * Main class - for accessing suffix tree applications
 * @author David Manlove
 */

public class Main {

	/**
	 * The main method.
	 * @param args the arguments
	 */
	public static void main(String args[]) {
	        String errorMessage = "Required syntax:\n";
                errorMessage += "  java Main SearchOne <filename> <query string> for Task 1\n";
                errorMessage += "  java Main SearchAll <filename> <query string> for Task 2\n";
                errorMessage += "  java Main LRS <filename> for Task 3\n" ;
                errorMessage += "  java Main LCS <filename1> <filename2> for Task 4";

		String request = args[0];

		if (args.length < 2)
			System.out.println(errorMessage);
		else {
			// get the command from the first argument
			String command = args[0];
			String filename = args[1];
			SuffixTree suffixTree;
			SuffixTreeAppl suffixTreeAppl;
			FileInput input;
			FileInput input2;
			byte [] text;
			byte [] text2;
			 

			switch (command) {
			case "SearchOne":
			    String query = args[2];
				// read file and convert to byte string
				input = new FileInput(filename);
				text = input.readFile();

				// convert byte string into suffixTreeAppl instance
				suffixTree = new SuffixTree(text);
				suffixTreeAppl = new SuffixTreeAppl(suffixTree);

				// loop to search for string and return index/position
				Task1Info t1 = suffixTreeAppl.searchSuffixTree(query.getBytes());
				if (t1.getPos() < 0){
					System.out.printf("Search string \"%s\" not found in %s\n", query, filename);
				}
				else{
					System.out.printf("Search string \"%s\" occurs at position %d of %s", query, t1.getPos(), filename);
				}
				break;
			case "SearchAll": {
					if (args.length < 3) {
						System.out.println(errorMessage);
					break;
				}
				String query1 = args[2];
				// read file and convert to byte string
				input = new FileInput(filename);
				text = input.readFile();

				// convert byte string into suffixTreeAppl instance
				suffixTree = new SuffixTree(text);
				suffixTreeAppl = new SuffixTreeAppl(suffixTree);
				
				//Find count and return
				Task2Info t2 = suffixTreeAppl.allOccurrences(query1.getBytes());
				if(t2.getPositions().size() < 1)
				{
					System.out.printf("The string \"%s\" does not occur in %s\n", query1, filename);
				}
				else{
					int no = t2.getPositions().size();
					System.out.printf("The string \"%s\" occurs in %s at positions:\n", query1, filename);
					// list out positions
					int counter = 0;
					while(counter < no)
					{
						System.out.println(t2.getPositions().get(counter));
						counter++;
					}
					System.out.printf("The total number of occurences is %d\n", no);
				}
				
				break;
			}
			case "LRS": {
				// read file and convert to byte string
				input = new FileInput(filename);
				text = input.readFile();

				// convert byte string into suffixTreeAppl instance
				suffixTree = new SuffixTree(text);
				suffixTreeAppl = new SuffixTreeAppl(suffixTree);
				
				//Find longest common substring
				Task3Info t3 = suffixTreeAppl.traverseForLrs();

				// detect if repeated string found
				if (t3.getLen() == 0)
				{
					System.out.printf("No repeated substrings were found in %s\n", filename);
				}else
				{
					// retrieve longest common substring
					String result = new String(text).substring(t3.getPos1(), t3.getPos1()+t3.getLen());

					// format output
					System.out.printf("An LRS in %s is ", filename);
					System.out.println('"' + result + '"' +'\n');
					System.out.printf("Its length is %d\n",t3.getLen());
					System.out.printf("Starting position of one occurence is %d\n",t3.getPos1());
					System.out.printf("Starting position of another occurence is %d\n",t3.getPos2());
				}
				
				break;
			}
			case "LCS": {
				if (args.length < 3) {
					System.out.println(errorMessage);
					break;
				}
				
				//negotiate second file argument
				String filename2 = args[2];

				input = new FileInput(filename);
				input2 = new FileInput(filename2);
				// read file and convert to byte string
				text = input.readFile();
				text2 = input2.readFile();

				// convert byte string into suffixTreeAppl instance
				suffixTree = new SuffixTree(text, text2);
				suffixTreeAppl = new SuffixTreeAppl(suffixTree);
				
				//Find longest common substring
				Task4Info t4 = suffixTreeAppl.traverseForLcs(text.length);
				if(t4.getLen() == 0){
					System.out.printf("No common substrings detected in the files %s and %s.", filename, filename2);
				}
				else
				{
					// retrieve longest common substring
					String result = new String(text).substring(t4.getPos1(), t4.getPos1()+t4.getLen());

					// format output
					System.out.printf("An LCS of %s and %s is\n", filename, filename2);
					System.out.println('"' + result + '"' +'\n');
					System.out.printf("Its length is %d\n",t4.getLen());
					System.out.printf("Starting position in %s is %d\n",filename ,t4.getPos1());
					System.out.printf("Starting position in %s is %d\n",filename2 ,t4.getPos2());
				}
				break;
			}
			default: System.out.println(errorMessage);
			}

		}
	}
}
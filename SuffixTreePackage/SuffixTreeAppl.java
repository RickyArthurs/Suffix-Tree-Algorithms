package SuffixTreePackage;

import java.util.*;

/**
 * Class with methods for carrying out applications of suffix trees
 * @author David Manlove
 */

public class SuffixTreeAppl {

	/** The suffix tree */
	private SuffixTree t;

	/**
	 * Default constructor.
	 */
	public SuffixTreeAppl () {
		t = null;
	}
	
	/**
	 * Constructor with parameter.
	 * 
	 * @param tree the suffix tree
	 */
	public SuffixTreeAppl (SuffixTree tree) {
		t = tree;
	}
	
	/**
	 * Search the suffix tree t representing string s for a target x.
	 * Stores -1 in Task1Info.pos if x is not a substring of s,
	 * otherwise stores p in Task1Info.pos such that x occurs in s
	 * starting at s[p] (p counts from 0)
	 * - assumes that characters of s and x occupy positions 0 onwards
	 * 
	 * @param x the target string to search for
	 * 
	 * @return a Task1Info object
	 */
	public Task1Info searchSuffixTree(byte[] x) {

		Task1Info t1 = new Task1Info();
		int i, k, max;
		SuffixTreeNode position;
		i = 0;
		position = t.getRoot();
		max = x.length-1;

		while(true){
			// locate child with left label x[i]
			position = t.searchList(position.getChild(), x[i]);
			// if no child exists then return failure
			if (position==null){
				t1.setPos(-1);
				return t1;
			}
			// if all characters processed return results
			if (i == max)
			{
				// success termination:
				t1.setPos(position.getLeftLabel()-max);
				t1.setMatchNode(position);
				return t1;
			}
			else
			{ 
				k = position.getLeftLabel()+1;
				// increment to next char on edge
				++i;

				// check for any matches for that node and the searhc string
				while (k<=position.getRightLabel())
				{
					if(t.getString()[k] == x[i])
					{	
					if(i == max)
					{ // we are done
						t1.setMatchNode(position);
						t1.setPos(k-max);
						return t1;
					}else {
						++k;
						++i;
						}
				    }
					else{// k has went past the end of the string thus key not found
						// unsuccessful termination
						t1.setPos(-1);
						return t1;	
					}

				}
			}

		}
	}

	

	/**
	 * Search suffix tree t representing string s for all occurrences of target x.
	 * Stores in Task2Info.positions a linked list of all such occurrences.
	 * Each occurrence is specified by a starting position index in s
	 * (as in searchSuffixTree above).  The linked list is empty if there
	 * are no occurrences of x in s.
	 * - assumes that characters of s and x occupy positions 0 onwards
	 * 
	 * @param x the target string to search for
	 * 
	 * @return a Task2Info object
	 */
	public Task2Info allOccurrences(byte[] x) {
		Task1Info t1 = searchSuffixTree(x);
		Task2Info t2 = new Task2Info();
		// create queue for nodes to be processed
		Queue<SuffixTreeNode> line = new LinkedList<SuffixTreeNode>();
		SuffixTreeNode position, descendant, sibling;

		// Failure termination in case of no occurences detected
		if(t1.getPos() < 0)
		return t2;
		// add child of intial root to queue to begin transversal
		line.add(t1.getMatchNode().getChild());
		// loop until queue is emptied
		while(!(line.isEmpty()))
		{
			// store current node at front of queue and assosicated descendants and siblings
			position = line.poll();
			descendant = position.getChild();
			sibling = position.getSibling();
			if (descendant == null)
			{
				// append suffix to linked list if is has no children ie. leaf
				t2.addEntry(position.getSuffix());
			}else // if children present append to the queue
			{
				line.add(descendant);
			}
			if(sibling != null) // if node has siblings append them to the queue
			{
				line.add(sibling);
			}
		}
		return t2;
		
	}

	/**
	 * Traverses suffix tree t representing string s and stores ln, p1 and
	 * p2 in Task3Info.len, Task3Info.pos1 and Task3Info.pos2 respectively,
	 * so that s[p1..p1+ln-1] = s[p2..p2+ln-1], with ln maximal;
	 * i.e., finds two embeddings of a longest repeated substring of s
	 * - assumes that characters of s occupy positions 0 onwards
	 * so that p1 and p2 count from 0
	 * 
	 * @return a Task3Info object
	 */
	public Task3Info traverseForLrs () {
		Task3Info t3 = new Task3Info();
		boolean siblingDetected;
		Queue<SuffixTreeNode> line = new LinkedList<SuffixTreeNode>();
		SuffixTreeNode position, descendant, sibling;
		// intialise queue by adding the root to begin transversal
		//SuffixTreeNode root;
		//root = t.getRoot();
		//line.add(root);
		line.add(t.getRoot());
		// Transverse tree just as in allOccurences method
		while(!(line.isEmpty()))
		{
			// store current node at front of queue and assosicated descendants and siblings
			position = line.poll();
			descendant = position.getChild();
			sibling = position.getSibling();
			
			if(sibling != null) // store value for current sibling present or not
			{
				siblingDetected = true; 
			}else
			{
				siblingDetected = false;
			}

			if (descendant == null)
			{
				//Validate whether size of substring is larger than max length
				if ((position.getLeftLabel()-position.getSuffix()) > t3.getLen() && siblingDetected)
				{
					// update current result accordingly
					t3.setLen(position.getLeftLabel()-position.getSuffix());
					t3.setPos1(position.getSuffix());
					t3.setPos2(sibling.getSuffix());
				}
			}else // if children present append to the queue
			{
				line.add(descendant);
			}
			if(siblingDetected) // if node has siblings append them to the queue
			{
				line.add(sibling);
			}
		}
		return t3;
	}

	/**
	 * Traverse generalised suffix tree t representing strings s1 (of length
	 * s1Length), and s2, and store ln, p1 and p2 in Task4Info.len,
	 * Task4Info.pos1 and Task4Info.pos2 respectively, so that
	 * s1[p1..p1+ln-1] = s2[p2..p2+ln-1], with len maximal;
	 * i.e., finds embeddings in s1 and s2 of a longest common substring 
         * of s1 and s2
	 * - assumes that characters of s1 and s2 occupy positions 0 onwards
	 * so that p1 and p2 count from 0
	 * 
	 * @param s1Length the length of s1
	 * 
	 * @return a Task4Info object
	 */
	public Task4Info traverseForLcs (int s1Length) {
		Task4Info t4 = new Task4Info();
		// Recursion implemented to process the tree for longest common substring
		recursiveLcs(t4, t.getRoot(), s1Length, 0);
		return t4;
	}
	// Helper method for transverseForLcs
	public void recursiveLcs(Task4Info t4, SuffixTreeNode position, int s1Length, int posLen)
	{
		SuffixTreeNode descendant = position.getChild();

		//Check whether node are present in both strings
		//then check whether it is an improvement than out current best length 
		if(position.getLeafNodeString1() && position.getLeafNodeString2())
		{
			if(posLen > t4.getLen())
			{
				// Update best
				t4.setLen(posLen);
				// Append the positions of new longest common substring
				t4.setPos1(position.getLeafNodeNumString1());
				t4.setPos2(position.getLeafNodeNumString2()-(s1Length+1));
			}
		}
		// crawl children of current note and adjust the posLen length accordingly
		while(descendant != null)
		{
			// update posLen relative to current position
			posLen = posLen + (descendant.getRightLabel() - descendant.getLeftLabel() + 1);
			// recursively call to check specific location in tree
			recursiveLcs(t4, descendant, s1Length, posLen);
			posLen = posLen - (descendant.getRightLabel() - descendant.getLeftLabel() + 1);
			// update current descendant node and loop
			descendant = descendant.getSibling();
		}
	}
}

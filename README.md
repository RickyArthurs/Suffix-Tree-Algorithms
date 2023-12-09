# Algorithmics 2 Assignment

Status Report

My code compiles without any errors on my system when running the javac Main.java command, and there are no run-time errors.

Implementation Report

Task 1:

My method for searchSuffixTree firstly assigns the root node to position then enters a while loop which will loop until the entire tree have been transversed. Firstly, at the beginning of the loop check whether a child is present then return failure if not and then check whether the index has reached the end of the byte string if so return the results. If none of these are the case, then transverse through the tree looking for a match to the search token and if found then set the task node to the current position and position in the tree and return to main.

Task 2:

My method for allOccurences employs a queue data structure which holds a linked list of suffix tree nodes. Firstly, it looks for a first occurrence of the key then gets the suffix tree node which belongs to that occurrence and adds it’s child to the queue, then transverses the tree below this child if it exists looking for more occurrences and appends them to the results if no more children exists for that node i.e. It is a leaf node, if not it will add this not to the queue to be processed which is just means it transverses all the children and siblings if they exist from the initial occurrence node. Then returns the result once the queue is emptied.

Task 3:

My method for transvereForLrs just like for task 2 employs a queue data structure filled with a linked list of SuffixTreeNodes, this queue is initialised by adding the root of the current suffix tree. Then will loop until the queue is completed and empty, each loop it checks whether the current size of the substring (the furthest left label of that edge minus the current suffix number) is greater than the current best and if so, updates the best and adds the corresponding position and it’s sibling to the t3 results. If this is not the case the child of the position is added to the queue and if there are sibling nodes these are also added to the queue. The loop continues until the queue is emptied.

Task 4:

My method for transverseForLcs uses a recursive helper method, it passes to this function the Task4Info object, the root of the current tree, the length of text to be checked and the starting largest lcs which is 0. Then it begins by checking whether the child is present in both strings and if so, adds it to the results if it is an improvement on the current lcs. Then it will transverse all the remaining children in order to check for any bigger lcs and adjust the position each time so that the recursive calls know where to check next. Incrementing and decrementing the current position accordingly. This is done until the entire tree has been crawled and the largest Lcs has been detected.

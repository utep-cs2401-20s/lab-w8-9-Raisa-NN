class AminoAcidLL {
  private char aminoAcid;
  private String[] codons;
  private int[] counts;
  private AminoAcidLL next;

  AminoAcidLL() {

  }


  /********************************************************************************************/
  /* Creates a new node, with a given amino acid/codon
   * pair and increments the codon counter for that codon.
   * NOTE: Does not check for repeats!! */
  AminoAcidLL(String inCodon) {
    aminoAcid = AminoAcidResources.getAminoAcidFromCodon(inCodon);
    codons = AminoAcidResources.getCodonListForAminoAcid(aminoAcid);
    counts = new int[codons.length];
    next = null;
  }

  /********************************************************************************************/
  /* Helper method to increment counts at specific index depending on input codon*/
  public void incrementCodons(String inCodon) {
    for (int i = 0; i < codons.length; i++) {
      if (inCodon.equals(codons[i])) {//if incoming codon is the same as a codon in the list, the count of that codon is incremented
        counts[i] = counts[i] + 1;
      }
    }
  }

  /********************************************************************************************/
  /* Recursive method that increments the count for a specific codon:
   * If it should be at this node, increments it and stops,
   * if not passes the task to the next node.
   * If there is no next node, add a new node to the list that would contain the codon.
   */
  private void addCodon(String inCodon) {//traverse list of codons n make sure count is correct. Create a helpermethod called increament codons
    if (AminoAcidResources.getAminoAcidFromCodon(inCodon) == aminoAcid) {//if aminoacid from this node is same as aminoacid produced by incodon
      incrementCodons(inCodon);//number of times that codon appears is incremented in counts array
    } else if (next != null) {
      next.addCodon(inCodon);//if end of the list is not reached, proceed to next node
    } else {
      next = new AminoAcidLL(inCodon);//otherwise, new node is created
    }
  }


  /********************************************************************************************/
  /* Shortcut to find the total number of instances of this amino acid */
  private int totalCount() {
    int sum = 0;
    for (int i = 0; i < counts.length; i++) {//sums up all the numbers in the counts array
      sum += counts[i];
    }
    return sum;
  }

  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
   *  must be matching, but this is not tracked */
  private int totalDiff(AminoAcidLL inList) {
    return Math.abs(totalCount() - inList.totalCount());
  }


  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
   *  must be matching, but this is not tracked */
  private int codonDiff(AminoAcidLL inList) {
    int diff = 0;
    for (int i = 0; i < codons.length; i++) {
      diff += Math.abs(counts[i] - inList.counts[i]);
    }
    return diff;
  }

  /********************************************************************************************/
  /* Recursive method that finds the differences in **Amino Acid** counts.
   * the list *must* be sorted to use this method */
  public int aminoAcidCompare(AminoAcidLL inList) {
    AminoAcidLL sortedInList = sort(inList); //sorts inList as method description indicates
    int diff = 0;

    if (sortedInList == null || this == null) {//if list is not empty, gets aminoacid counts of current array
      diff += sortedInList.totalCount();
    }

    if (inList.next == null) {
      diff += sortedInList.totalCount();
    }

    if (this.aminoAcid == inList.aminoAcid) {
      diff += this.totalDiff(sortedInList);
      if (this.next != null || inList.next != null) {
        diff += this.next.aminoAcidCompare(sortedInList);
      }
    }
    if (this.aminoAcid > sortedInList.aminoAcid) {
      diff += inList.totalCount();
      if (this.next == null) {
        diff += this.aminoAcidCompare(sortedInList);
      }
    }
    return diff;
  }

  /********************************************************************************************/
  /* Same ad above, but counts the codon usage differences
   * Must be sorted. */
  public int codonCompare(AminoAcidLL inList) {
    AminoAcidLL sortedInList = sort(inList); //sorts inList as method description indicates
    int difference = codonDiff(inList);
    if (next == null) {
      return difference;
    } else {
      difference = difference + next.codonCompare(inList.next);
    }
    return difference;
  }

  /********************************************************************************************/
  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList() {

    if (next == null) {//if end of list is reached, return char array containing current aminoacid
      return new char[]{aminoAcid};
    } else {
      char[] a = next.aminoAcidList();//a = char array of the next aminoacid
      char[] b = new char[a.length + 1];
      b[0] = aminoAcid;//assigns position 0 of b with the current aminoacid
      for (int i = 1; i < b.length; i++) {//populates b with all elements in a, leaving position 0 empty
        b[i] = a[i-1];
      }
      return b;
    }
  }

  /********************************************************************************************/
  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts() {

    if (next == null) {//if end of list is reached, return int array containing current aminoacid counts
      return new int[]{totalCount()};
    } else {
      int[] a = next.aminoAcidCounts();//a = int array of the next aminoacid counts
      int[] b = new int[a.length + 1];
      b[0] = totalCount();//assigns position 0 of b with the current counts
      for (int i = 1; i < b.length; i++) {//populates b with all elements in a, leaving position 0 empty
        b[i] = a[i-1];
      }
      return b;
    }
  }

  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted() {

    if (next != null) {
      if (aminoAcid > next.aminoAcid) {//if current aminoacid is bigger than the next one, it is not sorted
        return false;
      } else {
        next.isSorted(); //if it is not the end of the list, proceed to next node
      }
    }
    return true;
  }

  /********************************************************************************************/
  /* Static method for generating a linked list from an RNA sequence */
  public static AminoAcidLL createFromRNASequence(String inSequence) {

    AminoAcidLL list = new AminoAcidLL();
    int i;
    for (i = 0; i < inSequence.length(); i = i + 3) { //iterates after every 3rd character in string
      String codon = inSequence.substring(i, i + 3); //codon = stores a 3-character string
      list.addCodon(codon); //adds codon to list of codons
    }
    return list;
  }


  /********************************************************************************************/
  /* sorts a list by amino acid character*/
  //Sorts inList using bubblesort algorithm. I had gotten help from this website: https://www.javatpoint.com/program-to-sort-the-elements-of-the-singly-linked-list
  public static AminoAcidLL sort(AminoAcidLL inList) {
    AminoAcidLL head = new AminoAcidLL("GCG");//Creates node with aminoacid, A, which must always be the head
    head.next = inList; //points head node to incoming list
    AminoAcidLL index = null;
    AminoAcidLL current = head;
    char temp;

    while (current != null) {//while there is something in the list
      index = current.next; //AminoAcidLL index will point to AminoacidLL next to current
      while (index != null) {
        if (current.aminoAcid > index.aminoAcid) {
          temp = current.aminoAcid;//temp stores the current largest aminoacid
          current.aminoAcid = index.aminoAcid;//swaps current's data with the aminoacid ahead of it
          index.aminoAcid = temp;
        }
        index = index.next;//updates index pointers
      }
      current = current.next;//updates current pointers
    }
    return head.next;//returns starting node
  }
}
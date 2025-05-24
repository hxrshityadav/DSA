class Solution {

    // This method finds and returns the indices of the words containing a specific character.
    public List<Integer> findWordsContaining(String[] words, char targetChar) {
        // List to store indices of words containing the target character.
        List<Integer> indicesWithTargetChar = new ArrayList<>();
      
        // Iterate over the array of words.
        for (int index = 0; index < words.length; ++index) {
            // Check if the current word contains the target character.
            if (words[index].indexOf(targetChar) != -1) {
                // If it does, add the index of this word to the list.
                indicesWithTargetChar.add(index);
            }
        }
        // Return the list of indices.
        return indicesWithTargetChar;
    }
}
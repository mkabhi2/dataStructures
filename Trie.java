import java.util.HashMap;
import java.util.Optional;

class TrieNode {
    HashMap<Character, TrieNode> children;      //Child node reachable by adding each character
    boolean isEndOfWord;        //Denotes whether the word ending at this node is a valid word or not

    public TrieNode(){
        isEndOfWord = false;
        children = new HashMap<>();
    }
}


public class Trie {
    TrieNode root;

    public void insert(String newWord){
        TrieNode curr = root;
        for(char c: newWord.toCharArray()){       //Iterate through all the characters of the String
            if(curr.children.containsKey(c)){     // If the current character is current nodes child
                curr = curr.children.get(c);       // Make the current node = child node and move to next character
            } else {
                TrieNode newNode = new TrieNode(); //If the current character is not current nodes child
                curr.children.put(c, newNode);      //Make a new Node
                curr = newNode;                     //Make the current node = new Node and move to next character
            }
        }
        curr.isEndOfWord = true;                    //Once all characters of String are finished, marked the current Node as an endOfWord.
    }

    public Optional<TrieNode> search(String searchKey){
        TrieNode current = root;
        for(char c: searchKey.toCharArray()){       //Iterate through all the characters of the String
            if(current.children.containsKey(c)){    // If the current character is current nodes child
                current = current.children.get(c);  // Make the current node = child node and move to next character
            } else {                                //If the current character is not current nodes child
                return Optional.empty();            //Return as the word is not present in the trie
            }
        }
        if(current.isEndOfWord){
            return Optional.of(current);            //Checks if after all characters matched, if the String is complete
        }
        return Optional.empty();
    }
}

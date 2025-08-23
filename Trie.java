import java.util.HashMap;
import java.util.Optional;

class TrieNode {
    HashMap<Character, TrieNode> children;
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
        for(char c: newWord.toCharArray()){
            if(curr.children.containsKey(c)){
                curr = curr.children.get(c);
            } else {
                TrieNode newNode = new TrieNode();
                curr.children.put(c, newNode);
                curr = newNode;
            }
        }

        curr.isEndOfWord = true;
    }

    public Optional<TrieNode> search(String searchKey){
        TrieNode current = root;
        for(char c: searchKey.toCharArray()){
            if(current.children.containsKey(c)){
                current = current.children.get(c);
            } else {
                return Optional.empty();
            }
        }
        if(current.isEndOfWord){
            return Optional.of(current);
        }
        return Optional.empty();
    }
}

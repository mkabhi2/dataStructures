import java.util.ArrayList;

//Each BNode contains an array of keys.
//Min possible number of children = MIN_DEGREE, correspondingly
//Max possible number of children = 2*MIN_DEGREE
//Max possible number of keys = 2*MIN_DEGREE-1 and middle keys is denoted as MIN_DEGREE-1;
//For a value<key i, we have to go to child i
//If we are moving a key to i to somewhere that means we have to move it corresponding child i+1;

class BNode<KeyType extends Comparable<KeyType>>{   //BNode keys can be of any type, extends comparable so that we can compare key values and sort them too
    public static final int MIN_DEGREE = 10;   //The minimum degree for each node
    int keysCount;  //The current number of keys the node has
    ArrayList<KeyType> keys = new ArrayList<>();
    ArrayList<BNode<KeyType>> children = new ArrayList<>();
    boolean isLeafNode = true;
}

public class BTree {
    BNode<Integer> root;

    public BNode<Integer> searchKey(BNode<Integer> rootNode, int key){
        int i = 0;
        if(rootNode==null) return null;
        for(i=0; i<rootNode.keysCount; i++){
            if(key<rootNode.keys.get(i)) break; //Search key will be part of child[i]
            if(key==rootNode.keys.get(i)) return rootNode;  //Key Found
        }
        if(rootNode.isLeafNode) return null; //Key not found yet and its leaf node so it's not present in the B-Tree
        else return searchKey(rootNode.children.get(i), key);   //recurse on the ith child
    }

    //Steps
    //If root is full, create a new root and split the old root first
    public void insert(int key){
        //If root is full, create a new root and split the old root first
        if(root.keysCount == 2 * BNode.MIN_DEGREE-1){
            BNode<Integer> newNode = new BNode<>();
            newNode.isLeafNode = false;
            newNode.keysCount = 0;
            newNode.children.addFirst(root);
            splitNode(newNode, 0, root);    //Split a full child nodeY of nodeX at given position
            root = newNode;
        }
        insert(root, key);
    }

    public void insert(BNode<Integer> nodeX, int key){
        if(nodeX.isLeafNode){
            //Since the node is a lead node, we will add the new key at the appropriate spot in key list, as the key list needs to be sorted.
            int i= nodeX.keysCount-1; //initialised earlier than the loop, because the leaf node may have no key;
            while(i>=0 && key<nodeX.keys.get(i)){   //Use binary search instead in actual implementation
                i--;
            }
            nodeX.keys.add(i+1,key);
            nodeX.keysCount++;
        } else {
            //The current node is not a leaf node
            //The insertion will always be done on the leaf node, so we will need to traverse until we find the leaf node

            //Find the first key index of the node whose value is less than the key to be inserted.
            //This will be the index of the child node we need to traverse to
            int i= nodeX.keysCount-1;
            while(i>=0 && key<nodeX.keys.get(i)){   //Use binary search instead in actual implementation
                i--;
            }
            i++;

            BNode<Integer> childNode = nodeX.children.get(i);
            //If the child node found is full, split it at the ith index first then insert the key
            if(childNode.keysCount==2*BNode.MIN_DEGREE-1){
                splitNode(nodeX, i, childNode);
                if(key>nodeX.keys.get(i)) i++; //We need to do this as the median would have been promoted to index i at the earlier step
            }

            //recurse into a non-full child
            insert(nodeX.children.get(i), key);
        }
    }

    //Split a full child nodeY of nodeX at given position
    //This means that nodeY has 2*MIN_DEGREE - 1 keys and 2*MIN_DEGREE children
    //y.key[0 .. T-2] → remain in y
    //y.key[T-1] → moves up to x.key[pos]
    //y.key[T .. 2T-2] → copied into new node z.key[0 .. T-2]
    //Steps
    //1. Create a new nodeZ, add right half keys and children of Y to Z
    //2. Add z as a child to x at the provided position + 1
    //3. Add a new key to x for the new child added, the key will be added at the position, p.
    //4. This will be the max val
    private void splitNode(BNode<Integer> nodeX, int position, BNode<Integer> nodeY){
        BNode<Integer> nodeZ = new BNode<>(); //nodeZ will store right half of nodeY
        nodeZ.isLeafNode = nodeY.isLeafNode;  //nodeZ will be a leaf node if nodeY is
        nodeZ.keysCount = BNode.MIN_DEGREE - 1; //nodeZ will hold these many keys only (half of full nodeY)

        //Copy right half keys of nodeY into nodeZ
        for(int j=0; j<BNode.MIN_DEGREE - 1; j++){
            nodeZ.keys.add(j, nodeY.keys.get(j + BNode.MIN_DEGREE));
        }

        //If Y is non leaf node, copy the right half children of nodeY into nodeZ
        if(!nodeY.isLeafNode){
            for(int j=0; j<BNode.MIN_DEGREE; j++){
                nodeZ.children.add(j, nodeY.children.get(j + BNode.MIN_DEGREE));
            }
        }

        //Reduce Y to hold only the left half(MIN_DEGREE-1) keys
        nodeY.keysCount = BNode.MIN_DEGREE-1;

        //Place z as new child of z at position+1;
        nodeX.children.add(position+1, nodeZ);

        //Place middle value of keys of original Y as new key in X. This value will no longer be part of either Y or Z
        nodeX.keys.add(position, nodeY.keys.get(BNode.MIN_DEGREE-1));
    }


}

public class BSTDictionary implements BSTDictionaryADT {
    private BinarySearchTree bst;

    // Constructor: initializes a new Binary Search Tree for the dictionary
    public BSTDictionary() {
        this.bst = new BinarySearchTree();
    }

    // Returns the Record with key k, or null if it is not in the dictionary
    @Override
    public Record get(Key k) {
        BSTNode node = bst.get(bst.getRoot(), k);
        return (node != null) ? node.getRecord() : null;
    }

    // Inserts a new Record d into the dictionary
    // Throws DictionaryException if a Record with the same key already exists
    @Override
    public void put(Record d) throws DictionaryException {
        bst.insert(bst.getRoot(), d);
    }

    // Removes the Record with the specified key from the dictionary
    // Throws DictionaryException if the key is not found
    @Override
    public void remove(Key k) throws DictionaryException {
        bst.remove(bst.getRoot(), k);
    }

    // Returns the successor Record of key k, or null if no successor exists
    @Override
    public Record successor(Key k) {
        BSTNode successorNode = bst.successor(bst.getRoot(), k);
        return (successorNode != null) ? successorNode.getRecord() : null;
    }

    // Returns the predecessor Record of key k, or null if no predecessor exists
    @Override
    public Record predecessor(Key k) {
        BSTNode predecessorNode = bst.predecessor(bst.getRoot(), k);
        return (predecessorNode != null) ? predecessorNode.getRecord() : null;
    }

    // Returns the Record with the smallest key in the dictionary, or null if the dictionary is empty
    @Override
    public Record smallest() {
        BSTNode smallestNode = bst.smallest(bst.getRoot());
        return (smallestNode != null) ? smallestNode.getRecord() : null;
    }

    // Returns the Record with the largest key in the dictionary, or null if the dictionary is empty
    @Override
    public Record largest() {
        BSTNode largestNode = bst.largest(bst.getRoot());
        return (largestNode != null) ? largestNode.getRecord() : null;
    }
}

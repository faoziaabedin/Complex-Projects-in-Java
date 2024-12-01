public class BinarySearchTree {
    private BSTNode root; 
    //initializes the root as a leaf node
    public BinarySearchTree(){
        root = null; 
       
    }
    //returns root node
    public BSTNode getRoot(){
        return root;
    }
    //returns the node storing given key, returns null if  key is not stored in the tree with root r.
    public BSTNode get(BSTNode r, Key k){
        if (r == null){ // if root is null or root is equal to key then return root 
            return r;
        }
        if (k.compareTo(r.getRecord().getKey()) > 0){
            return get(r.getRightChild(), k); // if k is larger than root then the right node gets called and compared with k.
        }
        else if(k.compareTo(r.getRecord().getKey()) < 0){
            return get(r.getLeftChild(), k);
        }
        else 
            return r; 
    }

    public BSTNode insert(BSTNode r, Record d) throws DictionaryException {
        // Base case: If current node is null, create a new node and set its parent
        if (r == null) {
            BSTNode newNode = new BSTNode(d);
            newNode.setParent(null); // Set the parent to null for the root node (or parent node for non-root)
            return newNode; // Return the new node
        }
    
        // Compare the key of the record with the current node's key
        if (d.getKey().compareTo(r.getRecord().getKey()) > 0) { 
            // If d's key is greater, go to the right subtree
            if (r.getRightChild() == null) {
                BSTNode newNode = new BSTNode(d);
                newNode.setParent(r);  // Set the parent of the new node
                r.setRightChild(newNode);  // Set the right child of the current node
                return r; // Return the updated node
            } else {
                // Recurse to the right child
                r.setRightChild(insert(r.getRightChild(), d)); 
            }
        } else if (d.getKey().compareTo(r.getRecord().getKey()) < 0) {
            // If d's key is smaller, go to the left subtree
            if (r.getLeftChild() == null) {
                BSTNode newNode = new BSTNode(d);
                newNode.setParent(r);  // Set the parent of the new node
                r.setLeftChild(newNode);  // Set the left child of the current node
                return r; // Return the updated node
            } else {
                // Recurse to the left child
                r.setLeftChild(insert(r.getLeftChild(), d)); 
            }
        } else {
            // If keys are equal, throw a DictionaryException (duplicate key)
            throw new DictionaryException("Duplicate key");
        }
    
        return r; // Return the updated node 
    }

    public void remove(BSTNode r, Key k) throws DictionaryException {
        // removes node with specified key from BST
        BSTNode node = get(r, k);
        if (node == null) {
            throw new DictionaryException("Key not found");
        }
        removeNode(node);
    }

    private void removeNode(BSTNode node) {
        // helper method to remove a node. node = node to remove
        if (node.isLeaf()) {
            if (node == root) {
                root = null;
            } else {
                BSTNode parent = node.getParent();
                if (parent.getLeftChild() == node) {
                    parent.setLeftChild(null);
                } else {
                    parent.setRightChild(null);
                }
            }
        } else if (node.getLeftChild() != null && node.getRightChild() != null) {
            BSTNode successor = smallest(node.getRightChild());
            node.setRecord(successor.getRecord());
            removeNode(successor);
        } else {
            BSTNode child = (node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild();
            if (node == root) {
                root = child;
                child.setParent(null);
            } else {
                BSTNode parent = node.getParent();
                if (parent.getLeftChild() == node) {
                    parent.setLeftChild(child);
                } else {
                    parent.setRightChild(child);
                }
                child.setParent(parent);
            }
        }
    }

    public BSTNode successor(BSTNode r, Key k){
        BSTNode node = get(r, k);
        //return null if node is not found
        if (node == null){
            return node;
        }
        // Successor is the leftmost node in the right subtree
        if (node.getRightChild() != null){
            return smallest(node.getRightChild());
        }
        
        BSTNode parent = node.getParent();
        while (parent != null && node == parent.getRightChild()){
            node = parent; 
            parent = parent.getParent();
        }
        return parent; // This parent is the successor, or null if no successor exists
    }

    public BSTNode predecessor(BSTNode r, Key k){
        BSTNode node = get(r, k);
        //return null if node is not found
        if (node == null){
            return node;
        }
        // Predecessor is the rightmost node in the left subtree
        if (node.getLeftChild() != null){
            return largest (node.getRightChild());
        }
        
        BSTNode parent = node.getParent();
        while (parent != null && node == parent.getRightChild()){
            node = parent; 
            parent = parent.getParent();
        }
        return parent;  // This parent is the predecessor, or null if no predecessor exists

    }

    //goes to the leftmost node which is the smallest 
    public BSTNode smallest (BSTNode r){ 
        if (r == null){
            return null;
        }
        while (r.getLeftChild()!= null){
            r = r.getLeftChild();
        }
        return r; 
    }

    //goes to the leftmost node which is the largest 
    public BSTNode largest (BSTNode r){ 
        if (r == null){
            return null;
        }
        while (r.getRightChild()!= null){
            r = r.getRightChild();
        }
        return r; 
    }
}

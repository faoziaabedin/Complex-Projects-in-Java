public class BSTNode {
    private Record item; 
    private BSTNode left_child, right_child, parent;

    public BSTNode(Record item){
        this.item = item;
    }
    //returns the record object in this node
    public Record getRecord(){ 
        return item; 
    }
    //stores the given record 
    public void setRecord (Record d){
        this.item = d; 
    }
    //returns left child
    public BSTNode getLeftChild(){
        return left_child;
    }
    //returns right child
    public BSTNode getRightChild(){
        return right_child; 
    }
    //returns parent 
    public BSTNode getParent(){
        return parent; 
    }
    //sets left child to specific value
    public void setLeftChild(BSTNode u){
        this.left_child = u; 
    }
    //sets right child to specified value
    public void setRightChild(BSTNode u) {
        this.right_child = u; 
    }
    //sets the parent to specified value 
    public void setParent(BSTNode u){
        this.parent = u;
    }
    //returns true if a leaf, returns false otherwise
    public boolean isLeaf(){
        if (left_child == null && right_child ==null){
            return true;
        }
        else 
            return false; 
    }
    
}

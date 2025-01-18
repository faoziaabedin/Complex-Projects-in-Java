public class Key implements Comparable<Key> {
    private String label;
    private int type;

    public Key(String theLabel, int theType){
        this.label = theLabel.toLowerCase(); //changes label to lower case
        this.type = theType; 
    }

    public String getLabel(){
        return label; //returns label 
    }

    public int getType() {
        return type; //returns type
    }

    @Override
    public int compareTo(Key k) {
            // Check if both label and type are equal
        if (this.label.equals(k.label) && this.type == k.type){
            return 0;
        }
            // Check if this label lexicographically precedes k's label
        else if (this.label.compareTo(k.label) < 0) {
            return -1;
        }
            // Check if labels are the same but this type is less than k's type
        else if (this.label.equals(k.label) && this.type < k.type) {
            return -1;
        }
        else
            return 1; 
    }
    
    
}

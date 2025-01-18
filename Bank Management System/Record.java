public class Record {
    private Key theKey;
    private String data; 

    //constructor that initalizes with the specified perameters 
    public Record(Key k, String theData){
        this.theKey= k; 
        this.data= theData; 
    }

    //returns theKey
    public Key getKey(){
        return theKey; 
    }

    //returns data
    public String getDataItem(){
        return data; 
    }
}

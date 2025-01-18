
/* Description of these methods is given in the assignment */
public interface DictionaryADT {
    
	public int put (Data pair) throws DictionaryException;

    public void remove (String config) throws DictionaryException;

    public int get (String config);

    public int numRecords();
}

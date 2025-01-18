public interface BSTDictionaryADT {
    /* Ordered Dictionary ADT */

    /* Returns the Record object with the Key as k, or it returns null if such 
       a Record is not in the dictionary. */
    public Record get (Key k);

    /* Inserts the Record d into the ordered dictionary. It throws a DictionaryException 
       if a Record with the same Key attribute as d is already in the dictionary. */
    public void put (Record d) throws DictionaryException;

    /*  Removes the Record with the same Key attribute as k from the dictionary. It throws a 
        DictionaryException if such a Record is not in the dictionary. */
    public void remove (Key k) throws DictionaryException;

    /* Returns the successor of k (the Record from the ordered dictionary 
       with smallest key larger than k); it returns null if the given key has
       no successor. Note that the given key k DOES NOT need to be in the dictionary. */
    public Record successor (Key k);

    /* Returns the predecessor of k (the Record from the ordered dictionary 
       with largest key smaller than k; it returns null if the given key has 
       no predecessor. Note that the given key k DOES NOT need to be in the dictionary.  */
    public Record predecessor (Key k);

    /* Returns the Record with smallest key in the ordered dictionary. 
       Returns null if the dictionary is empty.  */
    public Record smallest ();

    /* Returns the Record with largest key in the ordered dictionary. 
       Returns null if the dictionary is empty.  */
    public Record largest ();
}
		



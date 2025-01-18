import java.util.LinkedList;

public class HashDictionary implements DictionaryADT {
    private LinkedList<Data>[] hashBuckets;

    private int bucketCount;

    private int recordCount;

    private static final double RESIZE_THRESHOLD = 0.75;

    private static final int HASH_CONSTANT = 43;

    /**
     * Constructs a new HashDictionary with an initial capacity,
     * which will be rounded up to the next prime number for efficiency.
     *
     * @param initialCapacity the initial capacity of the hash table.
     */
    @SuppressWarnings("unchecked")
    public HashDictionary(int initialCapacity) {
        this.bucketCount = getNextPrime(initialCapacity);
        this.hashBuckets = new LinkedList[this.bucketCount];

        // Initialize each bucket as an empty linked list
        int i = 0;
        while (i < this.bucketCount) {
            hashBuckets[i] = new LinkedList<>();
            i++;
        }
        this.recordCount = 0;
    }

    /**
     * Generates a hash index for the provided configuration key using a polynomial hash function.
     *
     * @param key the configuration string to be hashed.
     * @return an integer representing the bucket index in the hash table.
     */
    private int generateHashIndex(String key) {
        int hashValue = 0;
        for (int j = 0; j < key.length(); j++) {
            hashValue = (HASH_CONSTANT * hashValue + key.charAt(j)) % bucketCount;
        }
        return Math.abs(hashValue);
    }

    /**
     * Inserts a new Data record into the hash table. If a record with the same configuration
     * already exists, a DictionaryException is thrown.
     *
     * @param entry the Data object to insert into the dictionary.
     * @return 1 if a collision occurs, 0 otherwise.
     * @throws DictionaryException if an entry with the same configuration already exists.
     */
    @Override
    public int put(Data entry) {
        if (recordCount >= bucketCount * RESIZE_THRESHOLD) {
            expandAndRehash(); // Resize and rehash if load factor is exceeded
        }

        int bucketIndex = generateHashIndex(entry.getConfiguration());

        // Check if the entry already exists
        for (Data existingEntry : hashBuckets[bucketIndex]) {
            if (existingEntry.getConfiguration().equals(entry.getConfiguration())) {
                throw new DictionaryException();
            }
        }

        hashBuckets[bucketIndex].add(entry); // Add the entry to the computed bucket
        recordCount++;

        // Return 1 if there's a collision, 0 otherwise
        return hashBuckets[bucketIndex].size() > 1 ? 1 : 0;
    }

    /**
     * Expands the hash table to twice its current size, rounded up to the next prime,
     * and rehashes all existing entries to the new table.
     */
    @SuppressWarnings("unchecked")
    private void expandAndRehash() {
        int newBucketCount = getNextPrime(bucketCount * 2);
        LinkedList<Data>[] newHashBuckets = new LinkedList[newBucketCount];

        for (int i = 0; i < newBucketCount; i++) {
            newHashBuckets[i] = new LinkedList<>();
        }

        // Rehash each existing entry into the new table
        for (LinkedList<Data> bucket : hashBuckets) {
            for (Data data : bucket) {
                int newIndex = Math.abs(generateHashIndex(data.getConfiguration()) % newBucketCount);
                newHashBuckets[newIndex].add(data);
            }
        }

        this.bucketCount = newBucketCount;
        this.hashBuckets = newHashBuckets;
    }

    /**
     * Finds the next prime number greater than or equal to the given number.
     *
     * @param num the starting number to check for primality.
     * @return the next prime number greater than or equal to num.
     */
    private int getNextPrime(int num) {
        while (num <= 1 || !isPrime(num)) {
            num++;
        }
        return num;
    }

    /**
     * Determines if a given number is prime.
     *
     * @param num the number to check.
     * @return true if num is prime, false otherwise.
     */
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        int divisor = 2;
        while (divisor * divisor <= num) {
            if (num % divisor == 0) return false;
            divisor++;
        }
        return true;
    }

    /**
     * Removes a Data record with the specified configuration key from the dictionary.
     * If the key is not found, throws a DictionaryException.
     *
     * @param key the configuration string of the record to be removed.
     * @throws DictionaryException if no entry with the specified configuration is found.
     */
    @Override
    public void remove(String key) {
        int bucketIndex = generateHashIndex(key);

        // Using an iterator to safely remove while iterating
        var iterator = hashBuckets[bucketIndex].iterator();
        while (iterator.hasNext()) {
            Data entry = iterator.next();
            if (entry.getConfiguration().equals(key)) {
                iterator.remove();
                recordCount--;
                return;
            }
        }
        throw new DictionaryException();
    }

    /**
     * Retrieves the score associated with the specified configuration key.
     *
     * @param key the configuration string whose score is to be retrieved.
     * @return the score associated with the key, or -1 if the key is not found.
     */
    @Override
    public int get(String key) {
        int bucketIndex = generateHashIndex(key);

        for (Data entry : hashBuckets[bucketIndex]) {
            if (entry.getConfiguration().equals(key)) {
                return entry.getScore();
            }
        }
        return -1; // Return -1 if the configuration is not found
    }

    /**
     * Returns the current number of records in the dictionary.
     *
     * @return the number of records currently stored in the hash table.
     */
    @Override
    public int numRecords() {
        return recordCount;
    }
}

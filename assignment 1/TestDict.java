
public class TestDict {

	  /*
	  ** Test program for the Dictionary class.
	  */

	  // **************************************
	  public static void main(String[] args) {
	  // **************************************
	    HashDictionary dict = new HashDictionary(13441);
	    boolean[] test = new boolean[11];
	    int i,j;

	    if (args.length == 0) 
			for (i = 0; i < 11; ++i) test[i] = true;
	    else {
			if (args[0].equals("help")) {
				System.out.println("Usage: java TestDict, or java TestDict n1 n2 n3 ... ");
				System.out.println("ni have value 1 - 10, only those tests will be run");
				System.exit(0);
	        }

			for (i = 0; i < 11; ++i) test[i] = false;
			for (i = 0; i < args.length; ++i) {
				j = Integer.parseInt(args[i]);
				if (j >= 1 && j <= 10) test[j] = true;
			}
	    }

	    // Test 1: insert several data items in the dictionary.
	    // Should not throw an exception.

	    if (test[1] || test[2] || test[3] || test[4] || test[5] || test[6])
			try {
				dict.put(new Data("answer", 42));
				dict.put(new Data("record2",42));
				dict.put(new Data("record3",40));
				dict.put(new Data("2record",42));
				if (test[1]) System.out.println("   Test 1 succeeded");
			} catch (DictionaryException e) {
				if (test[1]) System.out.println("***Test 1 failed");
			}

	    // Test 2: try to insert another data item with a key already in the dictionary.
	    // Should throw an exception.
	    if (test[2] || test[3] || test[6])
			try {
				dict.put(new Data("answer", 56));
				if (test[2]) System.out.println("***Test 2 failed");
			} catch (DictionaryException e) {
				if (test[2]) System.out.println("   Test 2 succeeded");
			}

	    // Test 3: find a key in the table.
		boolean passed = true;
	    if (test[3]) 
	    	try {
		    if (dict.get("answer") == -1) passed = false;
	 	    if (dict.get("record2") == -1) passed = false;
			if (dict.get("record3") == -1) passed = false; 
			if (dict.get("2record") == -1) passed = false;
			if (!passed)
				System.out.println("***Test 3 failed");
			else System.out.println("   Test 3 succeeded");
		} catch (Exception e) {
			System.out.println("***Test 3 failed");
		}

	    // Test 4: look for an inexistent key

	    passed = true;
	    if (test[4]) 
	    	try {
			if (dict.get("chicken") != -1) passed = false;
			if (dict.get("2") != -1) passed = false;
			if (dict.get("record") != -1) passed = false;
			if (!passed) System.out.println("***Test 4 failed");
			else System.out.println("   Test 4 succeeded");
	    	} catch (Exception e) {
	    		System.out.println("***Test 4 failed");
	    	}

	    // Test 5: try to delete a nonexistent entry.
	    // Should throw an exception.
	    if (test[5])
			try {
				dict.remove("chicken");
				dict.remove("record2");
				System.out.println("***Test 5 failed");
			} catch (DictionaryException e) {
				System.out.println("   Test 5 succeeded");
			}

	    // Test 6: delete actual entries.
	    // Should not throw an exception.
		passed = true;
	    if (test[6])
			try {
				dict.remove("answer");
				dict.remove("record2");
				if (dict.get("answer") != -1) passed = false;
				if (dict.get("record2") != -1) passed = false;
				if (passed) System.out.println("   Test 6 succeeded");
				else System.out.println("***Test 6 failed");
			} catch (DictionaryException e) {
				System.out.println("***Test 6 failed");
			}

	    int collisions = 0;
	    String s;

	    // Test 7: insert 10000 different records into the Dictionary
	    if (test[7] || test[8] || test[9] || test[10])
			try {
				for (i = 0; i < 10000; ++i) {
					s = Integer.toString(i);
					for (j = 0; j < 5; ++j) s += s;
					collisions += dict.put(new Data(s,i));
				}
				if (test[7]) System.out.println("   Test 7 succeeded");
			} catch (DictionaryException e) {
				if (test[7]) System.out.println("***Test 7 failed");
			}


	    // Test 8: check that all above records are in the Dictionary
	    passed = true;
	    if (test[8]) {
			for (i = 0; i < 10000; ++i) {
				s = Integer.toString(i);
				for (j = 0; j < 5; ++j) s += s;
				if (dict.get(s) == -1) {
					System.out.println("***Test 8 failed");
					passed = false;
					break;
				}
			}
			if (passed) System.out.println("   Test 8 succeeded");
	    }

	    // Test 9: Remove the first 1000 data items and verify that the rest
	    // are in the dictionary 
	    passed = true;
	    if (test[9])
			try {
				for (i = 0; i < 1000; ++i) {
					s = Integer.toString(i);
					for (j = 0; j < 5; ++j) s += s;
					dict.remove(s);
				}

				for (i = 1000; i < 10000; ++i) {
					s = Integer.toString(i);
					for (j = 0; j < 5; ++j) s += s;
						if (dict.get(s) == -1) {
							System.out.println("***Test 9 failed");
							passed = false;
							break;
						}
				}
				if (passed) System.out.println("   Test 9 succeeded");
			}	
			catch (DictionaryException e) {
				System.out.println("***Test 9 failed");
			}

	    //Test 10: Number of collisions
	    if (test[10])
			if (collisions >= 2800) {
				System.out.println("***Test 10 failed");
				System.out.println("Too many collisions: "+collisions);
			}
			else  System.out.println("   Test 10 succeeded");
	  }
	}
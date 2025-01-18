import java.io.*;
import java.util.StringTokenizer;

public class Interface {
    private static BSTDictionary dictionary = new BSTDictionary();

    public static void main(String[] args) {
        // Check if the input file is provided
        if (args.length != 1) {
            System.out.println("Usage: java Interface <inputFile>");
            return;
        }

        try {
            // Load records from the specified input file
            loadRecords(args[0]);
            // Run the command interface
            runUserInterface();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Loads records from the input file into the dictionary
    private static void loadRecords(String fileName) throws IOException, DictionaryException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String label;
        
        while ((label = br.readLine()) != null) {
            String data = br.readLine();
            int type = determineType(data);

            if (type == 2) {
                data = data.substring(1); // Remove the '/' prefix for translations
            }

            Key key = new Key(label, type);
            Record record = new Record(key, data);
            dictionary.put(record);
        }
        br.close();
    }

    // Determines the type based on the prefix or suffix of the data
    private static int determineType(String data) {
        if (data.startsWith("-")) return 3; // sound file
        if (data.startsWith("+")) return 4; // music file
        if (data.startsWith("*")) return 5; // voice file
        if (data.startsWith("/")) return 2; // translation
        if (data.endsWith(".gif")) return 7; // animated image
        if (data.endsWith(".jpg")) return 6; // image file
        if (data.endsWith(".html")) return 8; // webpage
        return 1; // default to definition
    }

    // Main user interface method that continuously reads and processes commands
    private static void runUserInterface() {
        StringReader keyboard = new StringReader();
        String command;

        while (true) {
            command = keyboard.read("Enter next command: ");
            if (command == null || command.isEmpty()) continue;
            if (command.equals("exit")) break;

            StringTokenizer st = new StringTokenizer(command);
            String cmd = st.nextToken();

            try {
                switch (cmd) {
                    case "define": define(st); break;
                    case "translate": translate(st); break;
                    case "sound": sound(st); break;
                    case "play": play(st); break;
                    case "say": say(st); break;
                    case "show": show(st); break;
                    case "animate": animate(st); break;
                    case "browse": browse(st); break;
                    case "delete": delete(st); break;
                    case "add": add(st); break;
                    case "list": list(st); break;
                    case "first": first(); break;
                    case "last": last(); break;
                    default: System.out.println("Invalid command.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void define(StringTokenizer st) {
        if (!st.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        String label = st.nextToken();
        Record record = dictionary.get(new Key(label, 1)); // definition type
        System.out.println(record != null ? record.getDataItem() : "The word " + label + " is not in the dictionary");
    }

    private static void translate(StringTokenizer st) {
        if (!st.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        String label = st.nextToken();
        Record record = dictionary.get(new Key(label, 2)); // translation type
        System.out.println(record != null ? record.getDataItem() : "There is no translation for " + label);
    }

    private static void sound(StringTokenizer st) {
        playMedia(st, 3, "sound");
    }

    private static void play(StringTokenizer st) {
        playMedia(st, 4, "music");
    }

    private static void say(StringTokenizer st) {
        playMedia(st, 5, "voice");
    }

    private static void playMedia(StringTokenizer st, int type, String mediaType) {
        if (!st.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        String label = st.nextToken();
        Record record = dictionary.get(new Key(label, type));

        if (record != null) {
            try {
                SoundPlayer player = new SoundPlayer();
                player.play(record.getDataItem());
            } catch (Exception e) {
                System.out.println("Error playing " + mediaType + " file: " + e.getMessage());
            }
        } else {
            System.out.println("There is no " + mediaType + " file for " + label);
        }
    }

    private static void show(StringTokenizer st) {
        displayMedia(st, 6, "image");
    }

    private static void animate(StringTokenizer st) {
        displayMedia(st, 7, "animated image");
    }

    private static void browse(StringTokenizer st) {
        displayMedia(st, 8, "webpage");
    }

    private static void displayMedia(StringTokenizer st, int type, String mediaType) {
        if (!st.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        String label = st.nextToken();
        Record record = dictionary.get(new Key(label, type));

        if (record != null) {
            try {
                if (type == 8) {
                    ShowHTML browser = new ShowHTML();
                    browser.show(record.getDataItem());
                } else {
                    PictureViewer viewer = new PictureViewer();
                    viewer.show(record.getDataItem());
                }
            } catch (Exception e) {
                System.out.println("Error showing " + mediaType + ": " + e.getMessage());
            }
        } else {
            System.out.println("There is no " + mediaType + " for " + label);
        }
    }

    private static void delete(StringTokenizer st) {
        if (!st.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        String label = st.nextToken();
        int type = Integer.parseInt(st.nextToken());
        try {
            dictionary.remove(new Key(label, type));
        } catch (DictionaryException e) {
            System.out.println("No record in the ordered dictionary has key (" + label + "," + type + ")");
        }
    }

    private static void add(StringTokenizer st) {
        if (!st.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        String label = st.nextToken();
        int type = Integer.parseInt(st.nextToken());
        StringBuilder dataBuilder = new StringBuilder();

        while (st.hasMoreTokens()) {
            dataBuilder.append(st.nextToken()).append(" ");
        }
        String data = dataBuilder.toString().trim();
        Key key = new Key(label, type);
        Record record = new Record(key, data);

        try {
            dictionary.put(record);
        } catch (DictionaryException e) {
            System.out.println("A record with the given key (" + label + "," + type + ") is already in the ordered dictionary");
        }
    }

    private static void list(StringTokenizer st) {
        if (!st.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        String prefix = st.nextToken().toLowerCase();
        Record currentRecord = dictionary.smallest();
        boolean found = false;

        while (currentRecord != null) {
            Key key = currentRecord.getKey();
            if (key.getLabel().startsWith(prefix)) {
                System.out.print((found ? ", " : "") + key.getLabel());
                found = true;
            }
            currentRecord = dictionary.successor(key);
        }

        if (!found) {
            System.out.println("No label attributes in the ordered dictionary start with prefix " + prefix);
        } else {
            System.out.println();
        }
    }

    private static void first() {
        Record record = dictionary.smallest();
        if (record != null) {
            Key key = record.getKey();
            System.out.println(key.getLabel() + "," + key.getType() + "," + record.getDataItem());
        } else {
            System.out.println("The dictionary is empty");
        }
    }

    private static void last() {
        Record record = dictionary.largest();
        if (record != null) {
            Key key = record.getKey();
            System.out.println(key.getLabel() + "," + key.getType() + "," + record.getDataItem());
        } else {
            System.out.println("The dictionary is empty");
        }
    }
}

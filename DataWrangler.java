// --== CS400 File Header Information ==--
// Name: Jason Jenson
// Email: jjenson3@wisc.edu
// Team: GE
// Role: Data Wrangler
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Contains useful Data Wrangling methods. These include load() and save(),
 * which respectively load a text file into a red black tree implementation and
 * save an existing red black tree as a text file.
 * 
 * @author jason
 *
 */
public class DataWrangler {

    /**
     * Loads in SamplePhoneBook.txt file, inserting each line into the
     * RedBlackTree as individual contacts.
     * 
     * @param tree is the red black tree that the file is being loaded into
     */
    public static void load(BackEndRBTree tree) {
        File file = new File("SamplePhoneBook.txt");

        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                String[] currLine = in.nextLine().trim().split(" ");

                String lastName = currLine[0];
                String firstName = currLine[1];
                Long phoneNumber = Long.parseLong(currLine[2]);

                if (currLine.length > 3) {
                    String organization = currLine[3];
                    tree.insert(new Contact(lastName, firstName, phoneNumber, organization));
                } else {
                    tree.insert(new Contact(lastName, firstName, phoneNumber));
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception q) {
            System.out.println(q.getMessage());
            q.printStackTrace();
        }

    }

    /**
     * Correctly formats the phone book to be saved with the same format as with the
     * load operation.
     * 
     * @param tree converted to String for formatting conversion
     * @return formatted String of the tree to be saved
     */
    private static String saveHelper(String tree) {
        String currLine;
        String temp = "";
        
        try {
            Scanner scan = new Scanner(tree);

            // put each contact on its own line
            while (scan.hasNext()) {
                currLine = scan.nextLine();
                if (currLine.contains(","))
                    temp += "\n";
                temp += currLine;
            }
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // remove new line created on top of file
        if (temp.length() > 1) temp = temp.substring(1);

        // remove unnecessary characters
        String out = "";

        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == ")".charAt(0))
                i += 2;
            if (temp.charAt(i) != ",".charAt(0) && 
                temp.charAt(i) != ":".charAt(0) && 
                temp.charAt(i) != "\t".charAt(0) && 
                temp.charAt(i) != "(".charAt(0) && 
                temp.charAt(i) != ")".charAt(0) && 
                temp.charAt(i) != "-".charAt(0))
                out += temp.charAt(i);
        }
        return out;
    }

    /**
     * Creates a new file and writes the values of the red black tree to the file.
     *
     * @param tree that is being saved
     */
    public static void save(BackEndRBTree tree) {
        try {
            FileWriter file = new FileWriter("MyPhoneBook.txt");
            file.write(saveHelper(tree.toString()));

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
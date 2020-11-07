// --== CS400 File Header Information ==--
// Name: Pranav Agrawal
// Email: pragrawal@wisc.edu
// Team: GE
// TA: Daniel K
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Serves as the front end by using the back end of the phone book project 
 * to interface with the user, and allow them to add, lookup, edit contacts.
 * 
 * @author Pranav Agrawal
 *
 */
public class PhoneBookFront {
  
  public static BackEndRBTree tree = new BackEndRBTree();

  public static void load() {
      System.out.println("LOAD INITIATED...");
      tree.load();
      System.out.println("\nLoad complete.");
  }
    
  public static void save() {
      System.out.println("SAVE INITIATED...");
      tree.save();
      System.out.println("\nSave complete.");
  }
    
  /**
   * Uses lookup() from back end to search for a contact, given the first and last name
   */
  public static void lookup() {
    Scanner lookupScnr = new Scanner(System.in);
    String firstName = "";
    String lastName = "";
    boolean con = false;    //conditional for while loop
    System.out.println("LOOKUP CONTACT\n");
    while (!con) {  //loops till contact is found or user exits
      System.out.println("Enter full first name: ");
      firstName = lookupScnr.nextLine().trim();
      System.out.println("Enter full last name: ");
      lastName = lookupScnr.nextLine().trim();
      try {
        Contact contact = tree.lookup(lastName, firstName); //looking up using the given strings
        //May throw NoSuchElementException
        System.out.println(contact.toString()); //prints contact using toString from Contact class
        con = true; //conditional to exit loop
      } catch (NoSuchElementException e) {  //if element is not found
        System.out.println(e.getMessage());
        System.out.println("Do you want to lookup another contact? [y/n]\n");
        //exit if the user enters n, otherwise reiterate the loop
        if (lookupScnr.nextLine().toLowerCase().trim().equals("n")) con = true; 
      }
    }
  }
  
  /**
   * Uses insert from the back end to add a contact to the contact list using the
   * first name, last name, phone number, and organization given by the user.
   */
  public static void add() {
    Scanner addScnr = new Scanner(System.in);
    Long phoneNumber = null;
    String firstName = "";
    String lastName = "";
    String organization = "";
    boolean con = false;    //conditional for while loop
    System.out.println("ADD CONTACT\n");
    while (!con) {  //loops till contact is added or user exits
      try { 
        System.out.println("Enter phone number: ");
        phoneNumber = addScnr.nextLong();
        addScnr.nextLine();
        System.out.println("Enter First name: ");
        firstName = addScnr.nextLine().trim();
        System.out.println("Enter Last name: ");
        lastName = addScnr.nextLine().trim();
        System.out.println("Enter organization: ");
        organization = addScnr.nextLine().trim();
      } catch (InputMismatchException e1) { //if the number entered is not an integer
        System.out.println("\nInvalid format.");
        addScnr.nextLine();
      }
      Contact contact = new Contact(lastName, firstName, phoneNumber, organization);    //create contact
      try {
        tree.insert(contact);   //inserting contact
        //May throw NullPointerException
        con = true;    //conditional for while loop
        System.out.println("Contact added.\n");
      } catch(Exception e2) {   //if contact could not be added
        System.out.println("Contact could not be added.\n");
        System.out.println("Do you want to re-enter the details? [y/n]\n");
        //exit if the user enters n, otherwise reiterate the loop
        if (addScnr.nextLine().toLowerCase().trim().equals("n")) con = true;       
      }
    }
  }
  
  /**
   * Uses edit() from the back end to change the phone number and organization of a contact
   * whose first and last name is given by the user to lookup and edit
   */
  public static void edit() {
    Scanner editScnr = new Scanner(System.in);
    String firstName = "";
    String lastName = "";
    Long phoneNumber = null;
    String organization = "";
    boolean con = false;    //conditional for while loop
    System.out.println("EDIT CONTACT\n");
    while (!con) {  //loops till contact is added or user exits
      System.out.println("Enter the contact name to change the number and organization");
      try {
        System.out.println("Enter First name: ");
        firstName = editScnr.nextLine().trim();
        System.out.println("Enter Last name: ");
        lastName = editScnr.nextLine().trim();
        System.out.println("Enter new phone number: ");
        phoneNumber = editScnr.nextLong();
        editScnr.nextLine();
        System.out.println("Enter new organization: ");
        organization = editScnr.nextLine().trim();
      } catch (InputMismatchException e1) { //if the number entered is not an integer
        System.out.println("\nInvalid format.");
        editScnr.nextLine();
      } 
      Contact contact = new Contact(lastName, firstName, phoneNumber, organization);    //create new contact
      try{
        contact = tree.edit(contact);   //changes the contact to have the new details, and returns a reference
        System.out.println("The new contact is " + contact.toString());
        con = true; //conditional for while loop
      } catch (NoSuchElementException e2) { //if the contact is not found
        System.out.println(e2.getMessage());
        //exit if the user enters n, otherwise reiterate the loop
        System.out.println("Do you want to edit another contact? [y/n]\n");
        if (editScnr.nextLine().toLowerCase().trim().equals("n")) con = true; 
      }
    }
  }

  /**
   * Prints the full contact list in the phone book
   */
  public static void printList() {
    System.out.print("CONTACT LIST\n");
    try {
      System.out.println(tree.toString() + "\n");
      //throws exception is contact list is empty
    } catch(NullPointerException e) {
      System.out.println("\nContact List is empty.");
    }
  }
  
  /**
   * Saves the contact list and quits the application
   */
  public static void quit() {
    Scanner quitScnr = new Scanner(System.in);
    boolean con = false;
    while (!con) {
      System.out.print("Would you like to save the contact list? [Y] or [N]: ");
      String input = quitScnr.nextLine().trim().toLowerCase();
      if (input.equals("y")) {
        tree.save();
        System.out.println("\nSAVED.");
        con = true;
      } else if (input.equals("n")) {
        con = true;
      }
    }
    System.out.println("\nQUITTING APPLICATION");
  }
  
  /**
   * Documentation for available commands for the user
   */
  public static void help() {
    System.out.println("[LOAD] Contact List: Loads ");
    System.out.println("[SAVE] Contact List: saves ");
    System.out.println("[L]ookup: Search for a contact by first or last name in the phone book.\n");
    System.out.println("[A]dd: Add a contact by entering first name, last name, organization, and phone number.\n");
    System.out.println("[P]rint: Print entire contact list of the phone book.\n");
    System.out.println("[E]dit: Lookup a contact and edit it's phone number or organization.\n");
    System.out.println("[Q]uit: Exit the application.\n");
  }
  
  public static void main(String[] args) {
    String userInput = "";
    Scanner scnr = new Scanner(System.in);
    System.out.println("        PHONE BOOK-\n");
    System.out.println("   [LOAD] : Load Contact List");
    System.out.println("   [SAVE] : Save Contact List");
    System.out.println("    [L] : Lookup by First or Last name");
    System.out.println("    [A] : Add Contact");
    System.out.println("    [E] : Edit Contact");
    System.out.println("    [P] : Print Contact list");
    System.out.println("    [Q] : Quit Application");
    
    while (!userInput.equals("q")) {
      System.out.println("------------------------------------");
      System.out.println("[H]        : Help with Commands");
      System.out.print("Enter command: \n");
      userInput = scnr.nextLine().toLowerCase().trim();
      switch (userInput) {  
    case "save":
          System.out.println("------------------------------------");
          save();
          break;
        case "load":
          System.out.println("------------------------------------");
          load();
          break;
        case "l":
          System.out.println("------------------------------------");
          lookup();
          break;
        case "a":
          System.out.println("------------------------------------");
          add();
          break;
        case "e":
          System.out.println("------------------------------------");
          edit();
          break;
        case "p":
          System.out.println("------------------------------------");
          printList();
          break;
        case "q":
          System.out.println("------------------------------------");
          quit();
          break;
        case "h":
          System.out.println("------------------------------------");
          help();
          break;
        default:
          System.out.println("Invalid command input.");
      }
    }
    scnr.close();
  }

}
// --== CS400 File Header Information ==--
// Name: Aaron Dudor
// Email: dudor@wisc.edu
// Team: GE
// Role: Test Engineer
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

/**
 * Creates and defines test methods for the methods found in BackEndBRTree to test the functionality
 * of the PhoneBook given the RedBlackTree implementation
 * 
 * @author aaron
 */
public class TestPhoneBook {

  /**
   * tests addContact method to see if a contact is properly added to phone book
   */
  @Test
  public void testInsertContact() {
    { // test with valid contact and check size
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      try {
        book.insert(contact);
        if (book.size() != 1) {
          fail("Contact not added.");
        }
      } catch (NullPointerException e1) {
        fail("Null contact.");
      } catch (IllegalArgumentException e2) {
        fail("Illegal contact.");
      } catch (Exception e3) {
        fail("Unkown exception.");
      }
    }

    { // test with null contact
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = null;
      try {
        book.insert(contact);
        fail("Did not throw null pointer.");
      } catch (NullPointerException e1) {

      } catch (IllegalArgumentException e2) {
        fail("Illegal contact.");
      } catch (Exception e3) {
        fail("Unkown exception.");
      }
    }

  } // end testInsertContact

  /**
   * tests lookup method to see if the correct contact is gotten
   */
  @Test
  public void testLookupContact() {
    { // test looking up a valid contact
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      try {
        book.insert(contact);
        if (!book.lookup("Smith", "Zach").equals(contact)) {
          fail("Lookup failed.");
        }
      } catch (NoSuchElementException e) {
        fail("No Such Contact.");
      }  catch (Exception e) {
        fail("Unkown exception.");
      }
    }

    { // test looking up an invalid contact
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      try {
        book.insert(contact);
        if (!book.lookup("Johnson", "Bob").equals(contact)) {
          fail("Lookup failed.");
        }
      } catch (NoSuchElementException e) {

      } catch (Exception e) {
        fail("Unkown exception.");
      }
    }

  } // end testGetContact

  /**
   * tests editContact method to see if contact is changed after edit
   */
  @Test
  public void testEditContact() {
    { // test editing 1 contact
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      try {
        book.insert(contact);
        Contact newContact = new Contact("Smith", "Zach", 4113232244L);
        book.edit(newContact);
        if (!book.toString().equals("Smith, Zach: \n\t(411) 323-2244")) {
          fail("Edit failed.");
        }
      } catch (NoSuchElementException e) {
        fail("No Such Contact.");
      } catch (Exception e2) {
        fail("Unkown exception.");
      }
    }

    { // test editing 2 contacts
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      Contact contact2 = new Contact("Johnson", "Bob", 4123321234L);
      try {
        book.insert(contact);
        book.insert(contact2);
        Contact newContact = new Contact("Smith", "Zach", 4113232244L);
        Contact newContact2 = new Contact("Johnson", "Bob", 9231909421L);
        book.edit(newContact);
        book.edit(newContact2);
        if (!book.toString()
            .equals("Smith, Zach: \n\t(411) 323-2244\nJohnson, Bob: \n\t(923) 190-9421")) {
          fail("Edit failed.");
        }
      } catch (NoSuchElementException e) {
        fail("No Such Contact.");
      } catch (Exception e2) {
        fail("Unkown exception.");
      }
    }

  } // end testEditContact

  /**
   * tests printDirectory method to see if all contacts are printed out
   */
  @Test
  public void testPrintDirectory() {
    { // test printing 1 contact directory
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      try {
        book.insert(contact);
        if (!book.toString().equals("Smith, Zach: \n\t(923) 249-1234")) {
          fail("Print failed.");
        }
      } catch (Exception e) {
        fail("Unkown exception.");
      }
    }

    { // test printing 2 contact directory
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      Contact contact2 = new Contact("Johnson", "Bob", 4123321234L);
      try {
        book.insert(contact);
        book.insert(contact2);
        if (!book.toString()
            .equals("Smith, Zach: \n\t(923) 249-1234\nJohnson, Bob: \n\t(412) 332-1234")) {
          fail("Print failed.");
        }
      } catch (Exception e) {
        fail("Unkown exception.");
      }
    }

  } // end testPrintDirectory

  /**
   * tests clear method to see if contacts properly clear from phone book
   */
  @Test
  public void testClear() {
    { // test clearing 1 contact directory
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      try {
        book.insert(contact);
        book.clear();
        if (book.size() != 0 || !book.toString().equals("")) {
          fail("Directory did not clear.");
        }
      } catch (Exception e) {
        fail("Unkown exception.");
      }
    }
    
    { // test clearing 2 contact directory
      BackEndRBTree book = new BackEndRBTree();
      Contact contact = new Contact("Smith", "Zach", 9232491234L);
      Contact contact2 = new Contact("Johnson", "Bob", 4123321234L);
      try {
        book.insert(contact);
        book.insert(contact2);
        book.clear();
        if (book.size() != 0 || !book.toString().equals("")) {
          fail("Directory did not clear.");
        }
      } catch (Exception e) {
        fail("Unkown exception.");
      }
    }

  } // end testClear

  /*
   * Runs all of the test methods defined in the class
   */
  public void main(String[] args) {
    testInsertContact();
    testLookupContact();
    testEditContact();
    testPrintDirectory();
    testClear();

  } // end main

} // end class
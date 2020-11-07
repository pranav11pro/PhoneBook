// --== CS400 File Header Information ==--
// Name: Jackson Glisczinski
// Email: jglisczinski@wisc.edu
// Team: GE
// Role: Back-End Developer
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Makes use of RedBlackTree to hold Contact objects in a red black tree. Additionally, provides
 * size, lookup, and clear methods, with a modified toString method.
 * 
 * @author jackson
 */
public class BackEndRBTree extends RedBlackTree<Contact> {

  private RedBlackTree<Contact> tree;
  private int size;

  /**
   * Constructor; makes use of a RedBlackTree.
   */
  public BackEndRBTree() {
    tree = new RedBlackTree<Contact>();
  } // end BackEndRBTree constructor

  /**
   * Uses insert method from RedBlackTree and increments size.
   * 
   * @throws NullPointerException     when contact is null
   * @throws IllegalArgumentException when contact is already in tree
   */
  public void insert(Contact contact) throws NullPointerException, IllegalArgumentException {
    tree.insert(contact);
    size++;
  } // end insert

  /**
   * Uses similar code from RedBlackTree, but places Contacts into a more readable String.
   * 
   * @return string containing all Contacts in the red black tree
   */
  public String toString() {
    String output = "";
    if (tree.root != null) {
      LinkedList<Node<Contact>> q = new LinkedList<>();
      q.add(tree.root);
      while (!q.isEmpty()) {
        Node<Contact> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += "\n";
      }
    }
    return output;
  } // end toString

  /**
   * @return size of the red black tree
   */
  public int size() {
    return size;
  } // end size

  /**
   * Looks up and returns a contact based on a given lastName and firstName by calling lookupHelper.
   * 
   * @param lastName
   * @param firstName
   * @return Contact that matches
   * @throws NoSuchElementException if the Contact is not found in the tree
   */
  public Contact lookup(String lastName, String firstName) throws NoSuchElementException {
    Contact toFind = new Contact(lastName, firstName, 0);
    return this.lookupHelper(toFind, tree.root);
  } // end lookup

  /**
   * Recursive method to find a Contact in the tree based on a given lastName and firstName.
   * 
   * @param toFind  Contact to be searched for
   * @param current Contact being compared to
   * @return Contact that matches
   * @throws NoSuchElementException if the Contact is not found in the tree
   */
  private Contact lookupHelper(Contact toFind, Node<Contact> current)
      throws NoSuchElementException {
    Contact toReturn = null;
    try {
      if (toFind.compareTo(current.data) == 0) {
        toReturn = current.data;
        return toReturn;
      } else if (toFind.compareTo(current.data) < 0) {
        return lookupHelper(toFind, current.leftChild);
      } else {
        return lookupHelper(toFind, current.rightChild);
      }
    } catch (NullPointerException e) {
      throw new NoSuchElementException("No search results.");
    }
  } // end loookupHelper

  /**
   * Clears the entire tree by creating a new RedBlackTree.
   */
  public void clear() {
    tree = new RedBlackTree<Contact>();
    size = 0;
  } // end clear

  /**
   * This method calls DataWrangler.load() in order to read a test phone book into the application.
   */
  public void load() {
    DataWrangler.load(this);
  }

  /**
   * This method calls DataWrangler.save() in order to save this rbtree as a txt file.
   */
  public void save() {
    DataWrangler.save(this);
  }
  
  /**
   * Edits a contact by looking it up based on the newContact by calling lookupHelper. 
   * Sets the phone number and organization to the new values given by the user.
   * 
   * @param newContact
   * @return Contact that is edited
   * @throws NoSuchElementException if the Contact is not found in the tree
   * @author Pranav Agrawal
   */
  public Contact edit(Contact newContact) throws NoSuchElementException {
      Contact contact =  this.lookupHelper(newContact, tree.root);
      contact.setPhoneNumber(newContact.getPhoneNumber());
      contact.setOrganization(newContact.getOrganization());
      return contact;
  }

} // end BackEndRBTree
// --== CS400 File Header Information ==--
// Name: Pranav Agrawal
// Email: pragrawal@wisc.edu
// Team: GE
// TA: Daniel K
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;


/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes within a binary search tree.  You can use this class' insert
 * method to build a binary search tree, and its toString method to display
 * the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always be maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;
        public boolean isBlack;
        public Node(T data) { 
          this.data = data; 
          isBlack = false;
        }
        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }
        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node.  The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() { // display subtree in order traversal
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                //output = output + " isBlack is " + next.isBlack;
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }
    protected Node<T> root; // reference to root node of tree, null when empty

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree.  After
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the tree already contains data
     */
    public void insert(T data) throws NullPointerException,
                                      IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if(root == null) { root = newNode; } // add first node to an empty tree
        else insertHelper(newNode,root); // recursively insert into subtree
        root.isBlack = true;    //root property says the root will be black
    }
    /**
         * Recursive helper method to find the subtree with a null reference in the
         * position that the newNode should be inserted, and then extend this tree
         * by the newNode in that position.
         * @param newNode is the new node that is being added to this tree
         * @param subtree is the reference to a node within this tree which the
         *      newNode should be inserted as a descenedent beneath
         * @throws IllegalArgumentException when the newNode and subtree contain
         *      equal data references (as defined by Comparable.compareTo())
         */
        private void insertHelper(Node<T> newNode, Node<T> subtree) {
            int compare = newNode.data.compareTo(subtree.data);
            // do not allow duplicate values to be stored within this tree
            if(compare == 0) throw new IllegalArgumentException(
                "This RedBlackTree already contains that value.");

            // store newNode within left subtree of subtree
            else if(compare < 0) {
                if(subtree.leftChild == null) { // left subtree empty, add here
                    subtree.leftChild = newNode;
                    newNode.parent = subtree;
                // otherwise continue recursive search for location to insert
                } else insertHelper(newNode, subtree.leftChild);
            }

            // store newNode within the right subtree of subtree
            else {
                if(subtree.rightChild == null) { // right subtree empty, add here
                    subtree.rightChild = newNode;
                    newNode.parent = subtree;
                // otherwise continue recursive search for location to insert
                } else insertHelper(newNode, subtree.rightChild);
            }
            enforceRBTreePropertiesAfterInsert(newNode);
        }
        
        /**
         * Helper method called during insert to enforce red black tree properties. If a new node is inserted as a 
         * child of a red node, it violates the red property. The method considers all possible cases of the violation
         * and performs either a left or a right rotation, depending on the case and reassigns the correct colors to
         * the nodes
         *
         * @param newNode is the node being inserted, which is always red during insertion
         */
        private void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
          if(!(newNode.parent == null)) {   //skip when parent is root, root is always black
            if(!newNode.parent.isBlack) {   //violation of red property, where parent to newNode is red
              if(!newNode.parent.parent.isBlack) {  //ensure grandparent is black to avoid errors
                System.out.println("error in enforceRBTreePropertiesAfterInsert");
                enforceRBTreePropertiesAfterInsert(newNode.parent);
              }
              else {
                //If newNode is leftChild, rotate right if sibling of parent is black or null
                if(newNode.parent.isLeftChild()) {
                  //if sibling of parent is null
                  if(newNode.parent.parent.rightChild == null) {
                    //Rotate right
                    rotate(newNode.parent, newNode.parent.parent);
                    //Recolor parent to black and sibling to red
                    newNode.parent.isBlack = true;
                    newNode.parent.rightChild.isBlack = false;
                  }
                  else if(newNode.parent.parent.rightChild.isBlack) {   //if sibling of parent is black 
                      //Rotate right
                      rotate(newNode.parent, newNode.parent.parent);
                      //Recolor parent to black and sibling to red
                      newNode.parent.isBlack = true;
                      newNode.parent.rightChild.isBlack = false;
                    }
                    //else if sibling of parent is red
                    else {
                    //grandparent turns red if it is not root
                      if(!(newNode.parent.parent.parent == null)) {
                        newNode.parent.parent.isBlack = false;
                      }
                      //parent and sibling turn black
                      newNode.parent.isBlack = true;
                      newNode.parent.parent.rightChild.isBlack = true;
                    }
                }
                //else newNode is rightChild, rotate left if sibling of parent is black or null
                else {
                  //if sibling of parent is null
                  if(newNode.parent.parent.leftChild == null) {
                    rotate(newNode.parent, newNode.parent.parent);
                    //Recolor parent to black and sibling to red
                    newNode.parent.isBlack = true;
                    newNode.parent.leftChild.isBlack = false;
                  }
                  else if(newNode.parent.parent.leftChild.isBlack) {    //if sibling of parent is black
                    //Rotate left
                    rotate(newNode.parent, newNode.parent.parent);
                    //Recolor parent to black and sibling to red
                    newNode.parent.isBlack = true;
                    newNode.parent.leftChild.isBlack = false;
                  }
                  //else if sibling of parent is red
                  else {
                    //grandparent turns red if it is not root
                    if(!(newNode.parent.parent.parent == null)) {
                      newNode.parent.parent.isBlack = false;
                    }
                    //parent and sibling turn black
                    newNode.parent.isBlack = true;
                    newNode.parent.parent.leftChild.isBlack = true;
                  }
                }
              }
            }
          }
        }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { return root.toString(); }

    /**
     * Performs the rotation operation on the provided nodes within this BST.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation.  When the provided child is a
     * rightChild of the provided parent, this method will perform a left
     * rotation.  When the provided nodes are not related in one of these ways,
     * this method will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */

     private void rotate(Node<T> child, Node<T> parent)
        throws IllegalArgumentException {
          if (child.parent != parent) throw  new IllegalArgumentException("Child and parent unrelated");
          
          //Right Rotation
          if (parent.leftChild == child) {
            parent.leftChild = child.rightChild;
            if (child.rightChild != null) child.rightChild.parent = parent;
            child.parent = parent.parent;  
            if (parent.parent == null) root = child;
            else if (parent == parent.parent.rightChild) parent.parent.rightChild = child;
                  else parent.parent.leftChild = child;            
            child.rightChild = parent;
            parent.parent = child;
          }
          
          //Left Rotation
          if (parent.rightChild == child) {
            parent.rightChild = child.leftChild;
            if (child.leftChild != null) child.leftChild.parent = parent;
            child.parent = parent.parent;  
            if (parent.parent == null) root = child;
            else if (parent == parent.parent.leftChild) parent.parent.leftChild = child;
                  else parent.parent.rightChild = child;            
            child.leftChild = parent;
            parent.parent = child;
          }
      
     }
}

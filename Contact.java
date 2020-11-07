// --== CS400 File Header Information ==--
// Name: Jason Jenson
// Email: jjenson3@wisc.edu
// Team: GE
// Role: Data Wrangler
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: the logic and format for the toString() method was originally 
// created by Back End Developer Jackson Glisczinski, with stylistic tweaks made by 
// Jason Jason.

/**
 * This class stores contact information for a single Contact that may then
 * be added into a phone book red black tree data structure.
 * 
 * @author jason
 */
public class Contact implements Comparable<Contact> {
    private String lastName;
    private String firstName;
    private long phoneNumber;
    private String organization;

    /**
     * Constructs a new Contact object, provided the contact's last name,
     * first name, and phone number.
     * 
     * @param last name of contact
     * @param first name of contact
     * @param phone number of contact
     */
    public Contact(String lastName, String firstName, long phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }
     
    /**
     * Constructs a new Contact object, provided the contact's last name,
     * first name, phone number, and organization type.
     * 
     * @param last name of contact
     * @param first name of contact
     * @param phone number of contact
     * @param organization of contact
     */
    public Contact(String lastName, String firstName, long phoneNumber, String organization) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.organization = organization;
    }

    /**
     * Return last name of this Contact object.
     * 
     * @return last name of the Contact
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Return first name of this Contact object.
     * 
     * @return first name of the Contact
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Return phone number of this Contact object.
     * 
     * @return phone number of the Contact
     */
    public long getPhoneNumber() {
        return this.phoneNumber;
    }
    
    /**
     * Return organization of this Contact object.
     * 
     * @return organization of the Contact
     */
    public String getOrganization() {
        return this.organization;
    }

    /**
     * Sets phone number of this Contact object.
     * 
     * @param new phone number of the Contact
     */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets organization of this Contact object.
     * 
     * @param new organization of the Contact
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Returns -1 if last name of this contact comes alphabetically 
     * before contact provided as a parameter and 1 if contact comes 
     * alphabetically before this contact. If last names are identical,
     * the same operation will be performed on the contacts' first names.
     * Returns 0 if both first and last name are identical.
     * 
     * @param contact to be compared to this contact
     * @return int comparison value
     */
    public int compareTo(Contact contact) {
        if (this.lastName.equals(contact.getLastName())) 
            return this.firstName.compareTo(contact.getFirstName());
        return lastName.compareTo(contact.getLastName());
    }

    /**
     * Return this contact as a conveniently formatted String.
     * 
     * @author jackson
     * @author jason
     * @return String representation of this contact
     */
    public String toString() {
        String number = String.valueOf(this.phoneNumber);
        String formattedNumber = "(";
        for (int i = 0; i < number.length(); i++) {
            if (i == 3) formattedNumber += ") ";
            if (i == 6) formattedNumber += "-";
            formattedNumber += number.charAt(i);
        }
        if (organization != null) 
            return lastName + ", " + firstName + ": \n\t" + formattedNumber + " \n\t" + organization;
        return lastName + ", " + firstName + ": \n\t" + formattedNumber;
    }

}
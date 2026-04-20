import java.util.ArrayList;

public class EmailSorter {              /*Again, basically matches the UML--- Dropped splitEmail: String [] and splitter(String email)
                                         , both are in Email class (email has the full access to its text)
                                         -Also replaced interpreter(String email) with addEmail(String text, int Label) since we create
                                         and process email objs here, not just interpreting
                                         -ANNNDD using ArrayList instead of Array just for flexibility ;) 
                                         */

        /*---UPDATE 4/19/2026--This isn't really used in Main rn, just stores and prints email objects---May be somewhat obsolete */


    private ArrayList<Email> emails;

    public EmailSorter() {
        emails = new ArrayList<>();
    }

    public void addEmail(Email e) {     //now takes in an already-created Email object
        emails.add(e);                  //storing processed Email into list
    }

    public void addEmails(ArrayList<Email> emailList) {   // helpful for loading a whole CSV worth of emails
        emails.addAll(emailList);
    }

    public void printAllEmails() {      //again, just for testing/seeing output for now
        for (Email e : emails) {
            e.printFeatures();          //calling Email.printFeatures()
            System.out.println("-----");
        }
    }

    public ArrayList<Email> getEmails() {
        return emails;
    }
}
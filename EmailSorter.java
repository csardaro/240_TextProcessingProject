import java.util.ArrayList;

public class EmailSorter {              /*Again, basically matches the UML--- Dropped splitEmail: String [] and splitter(String email)
                                         , both are in Email class (email has the full access to its text)
                                         -Also replaced interpreter(String email) with addEmail(String text, int Label) since we create
                                         and process email objs here, not just interpreting
                                         -ANNNDD using ArrayList instead of Array just for flexibility ;) 
                                         */




    private ArrayList<Email> emails;

    public EmailSorter() {
        emails = new ArrayList<>();
    }

    public void addEmail(String text, int label) {
        Email e = new Email(text, label);           //creating email obj from Email class

        e.splitText();                          //split & feature handled in Email now
        e.generateFeatures();                   

        emails.add(e);                          //storing processed Email into list
    }

    public void printAllEmails() {              //again, just for testing/seeing output for now
        for (Email e : emails) {

            e.printFeatures();                  //calling Email.printFeatures()
            System.out.println("-----");
        }
    }
}
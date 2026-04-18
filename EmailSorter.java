import java.util.ArrayList;

public class EmailSorter {              /*Again, basically matches the UML--- Dropped splitEmail: String [] and splitter(String email)
                                         , both are in Email class (email has the full access to its text)
                                         -Also replaced interpreter(String email) with addEmail(String text, int Label) since we create
                                         and process email objs here, not just interpreting
                                         -ANNNDD using ArrayList instead of Array just for flexibility ;) 
                                         */




    public ArrayList<Email> emails;

    public EmailSorter() {
        emails = new ArrayList<>();
    }

    public void addEmail(String text, int label) {
        Email e = new Email(text, label);           //creating email obj from Email class

        e.splitText();                          //split & feature handled in Email now
        e.generateFeatures();                   

        emails.add(e);                          //storing processed Email into list
    }

    //build spam/ham lists based on labels 
    public ArrayList<Email> emailSort(ArrayList<Email> emails){
        ArrayList<Email> spamEmails = new ArrayList<>();
        ArrayList<Email> hamEmails = new ArrayList<>();

        for(Email e : emails){
            if(e.getLabel() == 1){              //if label is 1, add to spam list
                spamEmails.add(e);
            }else{                              //if label is 0, add to ham list
                hamEmails.add(e);
            }
        }

        return emails; 
    }


    //return arraylist of emails that are assigned spam or ham based on features
    public ArrayList<Email> emailSortFeature(ArrayList<Email> emails){
        for(Email e : emails){
            //Learning method to tell EmailSorter how to sort spam/ham and assign label to email obj
            
        }


        return emails;
    }

    public void printAllEmails() {              //again, just for testing/seeing output for now
        for (Email e : emails) {

            e.printFeatures();                  //calling Email.printFeatures()
            System.out.println("-----");
        }
    }
}

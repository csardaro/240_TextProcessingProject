import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmailSplitter {
    public String emailText;
    public int label;
    private String[] emailRow; 
    public ArrayList<Email> emails;
    public String[] splitEmail;
    
    //EmailText constructor taking emails labeled with spam or ham
    
    public EmailSplitter(String filename) {
        emails = new ArrayList<>();             //needed so we can actually add Email objects to the list

        File file = new File(filename);
        try (Scanner scan = new Scanner(file)) {
            //Header
            if (scan.hasNextLine()) {
                String header = scan.nextLine();
            }

            //take email lines
            String line = "";
            while (scan.hasNextLine()) {

                line = scan.nextLine();
                emailRow = line.split(",", 2);  //UPDATED --4/30/26 for safer parsing,
                                                             //current dataset is fine but will prevent over splitting
                //Email Text
                emailText = emailRow[0];
                splitText();

                //label
                label = Integer.parseInt(emailRow[1]);

                //Build email obj array
                Email e = new Email(emailText, splitEmail, label);  //changed this a bit to call generateFeatures()
                e.generateFeatures();
                emails.add(e);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);          
        }
    }

    public void splitText() {                        //added in splitText() in this class (can change later)
        this.splitEmail = emailText.split("\\s+");   // \\s+ for whitespaces
    }

    public ArrayList<Email> getEmails() {
        return emails;
    }
}
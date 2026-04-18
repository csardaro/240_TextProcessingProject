import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmailSplitter{
    public String emailText;
    public int label;
    private String[] emailRow; 
    public ArrayList<Email> emails;
    public String[] splitEmail;
    
    //EmailText constructor taking emails labeled with spam or ham
    public EmailSplitter(String filename){
        File file = new File(filename);
        try(Scanner scan = new Scanner(file)){
            //Header
            if(scan.hasNextLine()){
                String header = scan.nextLine();
            }
            //take email lines
            String line = "";
            while(scan.hasNextLine()){
                line = scan.nextLine();
                String[] emailRow = line.split(",");

                //Email Text
                emailText = emailRow[0];
                splitEmail = emailText.split("\\s+");
                //label
                label = Integer.parseInt(emailRow[1]);

                //Build email obj array
                emails.add(new Email(splitEmail, label));
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found: " + filename);
            e.printStackTrace();
        }
    }

    public void splitText() {                        //added in splitText() in this class (can change later)
        this.splitEmail = emailText.split("\\s+");   // \\s+ for whitespaces
    }

}
import java.util.ArrayList;
import java.util.HashMap;

public class Email {
    private String emailText;
    private int label;
    private ArrayList<Feature> features;
    private String[] splitEmail;
    

    public Email(String emailText, int label) {     //emailtext* - changed naming from our UML)
        this.emailText = emailText;
        this.label = label;
        this.features = new ArrayList<>();
    }

    public void splitText() {                               //added in splitText() in this class (can change later)
        this.splitEmail = emailText.split("\\s+");   // \\s+ for whitespaces
    }

    public void generateFeatures() {                            //for now, feature is word + # of times that word appears in this email

        HashMap<String, Integer> wordCounts = new HashMap<>();  //hash map to count words

        for (String word : splitEmail) {                        //for each word from our split email
            word = word.toLowerCase();                                                  //to lowercase ofc, no case sensitivity :'(


            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1); //(had to look this all up) - if word already exists in
        }                                                                            //the map, add 1 to its count, if it doesnt, start @ 1

        for (String word : wordCounts.keySet()) {                   //going through all unique words in map

            features.add(new Feature(word, wordCounts.get(word)));  //for each unique, create a feature obj (uses the word itself and
        }                                                           //how many times word appeared)
    }

    public void printFeatures() {                           //just for testing/seeing output - prints our email txt, label, feats.
        System.out.println("Email Text: " + emailText);
        System.out.println("Label: " + label);
        System.out.println("Features:");

        for (Feature f : features) {                                    //prints each feature from feature list
            System.out.println(f.getWord() + "=" + f.getCount());
        }
    }

    public String[] getSplitEmail() {       //getter for splitEmail
        return splitEmail;
    }

    public String getText() {               //getter for the raw email text
        return emailText;
    }

    public int getLabel() {                 //getter for label
        return label;
    }

    public void setFeatures(ArrayList<Feature> features) {  //our setter for features list
        this.features = features;
    }

    public ArrayList<Feature> getFeatures() {               //then the getter
        return features;
    }
}
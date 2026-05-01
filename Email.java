import java.util.ArrayList;
import java.util.HashMap;

public class Email {
    private String emailText;
    private int label;
    private ArrayList<Feature> features;
    private String[] splitEmail;
    

    public Email(String emailText, String[] splitEmail, int label) {     //keeps raw email text + split text + label
        this.emailText = emailText;
        this.splitEmail = splitEmail;
        this.label = label;
        this.features = new ArrayList<>();
    }

    public void generateFeatures() {                            //for now, feature is word + # of times that word appears in this email

        features.clear();                                       //added this in too, some features were duplicating (method might be called twice)
        HashMap<String, Integer> wordCounts = new HashMap<>();  //hash map to count words



        //---04/18/2026--ADDED: spam word list ---
        String[] spamWords = {"free", "win", "offer", "click", "urgent", "money"};



        //---04/18/2026--ADDED: counters for new features ---
        int spamWordCount = 0;
        int hasURL = 0;

        for (String word : splitEmail) {                        //for each word from our split email
            String cleanedWord = word.toLowerCase();            //to lowercase, no case sensitivity :)



            //---04/18/2026--ADDED: URL detection ---
            if (cleanedWord.contains("http") ||
            cleanedWord.contains("www") ||
            cleanedWord.contains(".com") ||
            cleanedWord.contains("url") ||
            cleanedWord.contains("hyperlink")) {
            hasURL = 1;                                 //updated AGAIN, to include url and hyperlink (the csv has
}                                                       //blatant mention of them) --(FYI- Even if 5 urls, for example, appear, it only shows 1)



            //---04/18/2026--ADDED: clean punctuation ---
            cleanedWord = cleanedWord.replaceAll("[^a-z0-9]", "");      //removing any punctuation/symbols

            if (cleanedWord.isEmpty()) {
                continue;
            }

            wordCounts.put(cleanedWord, wordCounts.getOrDefault(cleanedWord, 0) + 1); //if word already exists in
        }                                                                                           //the map, add 1 to its count, if it doesnt, start @ 1

        for (String word : wordCounts.keySet()) {                   //going through all unique words in map (only useful for feature display/export)
            features.add(new Feature(word, wordCounts.get(word)));  //for each unique, create a feature obj and store them in email's feature list
        }



        //---04/18/2026--ADDED: spam word count feature --- (this is the second pass through the email, instead of counting each unique word,
        //it counts how many words match one of chosen spam words

        for (String word : splitEmail) {
            String cleanedWord = word.toLowerCase().replaceAll("[^a-z0-9]", "");

            for (String spam : spamWords) {         //checks against spamWords list
                if (cleanedWord.equals(spam)) {
                    spamWordCount++;
                }
            }
        }


        //------04/18/2026--ADDED: total word count feature ---
        features.add(new Feature("totalWords", splitEmail.length));     //just the length of the splitEmail Array

        //------04/18/2026--ADDED: spam word count feature ---
        features.add(new Feature("spamWordCount", spamWordCount));      

        //------04/18/2026--ADDED: URL / hyperlink feature ---
        features.add(new Feature("hasURL", hasURL));
    }

    public void printFeatures() {                           //just for testing/seeing output - prints our email txt, label, feats.
        System.out.println("Email Text: " + emailText);
        System.out.println("Label: " + label);
        System.out.println("Features:");

        for (Feature f : features) {                                    //prints each feature from feature list
            System.out.println(f.getName() + "=" + f.getValue());       //now uses getName/getValue instead of getWord/getCount
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
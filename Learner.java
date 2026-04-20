//--ADDED 04/19/26--LEARNER CLASS

import java.util.ArrayList;
import java.util.HashMap;

public class Learner {

    private ArrayList<Email> emails;

    private ArrayList<Email> spamEmails;
    private ArrayList<Email> hamEmails;

    //feature counts across all spam/ham training emails
    private HashMap<String, Integer> spamFeatureCounts;  //note these arent used in the final prediction model
    private HashMap<String, Integer> hamFeatureCounts;   //they're part of learning (--the prediction uses average summary features)


    //average value fields for spam  emails
    private double spamAvgSpamWordCount;      
    private double spamAvgHasURL;
    private double spamAvgTotalWords;

    //average value fields for ham emails
    private double hamAvgSpamWordCount;
    private double hamAvgHasURL;
    private double hamAvgTotalWords;                //so now learner has two reference points, one point repr. avg spam, the other avg ham


    public Learner(ArrayList<Email> emails) {       //constructor for learner v

        this.emails = emails;                       //full training list (for "training" emails)
        this.spamEmails = new ArrayList<>();        //empty spam list
        this.hamEmails = new ArrayList<>();         //empty ham list
        this.spamFeatureCounts = new HashMap<>();   //empty spam feature map
        this.hamFeatureCounts = new HashMap<>();    //empty ham feature map
    }

    
    public void separateEmails() {          //loop for sorting each training email into either spam or ham based on Email's label
        for (Email e : emails) {            //so EmailSplitter -> reads CSV -> builds email -> email stores label/features -> learner grabs that label
            if (e.getLabel() == 1) {
                spamEmails.add(e);
            } else {
                hamEmails.add(e);
            }
        }
    }

                                            
    public void learnFeatureCounts() {          //method to go through every feature of every spam/ham email and adds up how often each feature
                                                //appears (for a group level total) - *NOTE* - This isn't used in our final prediction (just good
                                                //for analysis/debugging) 
        for (Email e : spamEmails) {
            for (Feature f : e.getFeatures()) {         //so for our word count features ("free", "click", etc) and summary features ("totalWords", "SpamWordCount", "hasURL")
                String name = f.getName();
                int value = f.getValue();

                spamFeatureCounts.put(         //adding to spamFeatureCounts
                    name,
                    spamFeatureCounts.getOrDefault(name, 0) + value
                );
            }
        }

        for (Email e : hamEmails) {                 //doing the same for ham (hamFeatureCounts)
            for (Feature f : e.getFeatures()) {
                String name = f.getName();
                int value = f.getValue();

                hamFeatureCounts.put(
                    name,
                    hamFeatureCounts.getOrDefault(name, 0) + value
                );
            }
        }
    }


    public void computeAverages() {                 //method where learner builds the model it'll use later for prediction

        double spamWordSum = 0;                     //using just our 3 summary features
        double spamURLSum = 0;
        double spamTotalWordsSum = 0;

        for (Email e : spamEmails) {                                    //**USES HELPER METHOD JUST BELOW THIS**
            spamWordSum += getFeatureValue(e, "spamWordCount");   //so learner extracts values of our 3 features, adds them to running sums
            spamURLSum += getFeatureValue(e, "hasURL");
            spamTotalWordsSum += getFeatureValue(e, "totalWords");
        }

        spamAvgSpamWordCount = spamWordSum / spamEmails.size();         //calculating averages (/ size of each arrayList)
        spamAvgHasURL = spamURLSum / spamEmails.size();
        spamAvgTotalWords = spamTotalWordsSum / spamEmails.size();

        double hamWordSum = 0;                                      //same thing for ham v
        double hamURLSum = 0;
        double hamTotalWordsSum = 0;

        for (Email e : hamEmails) {
            hamWordSum += getFeatureValue(e, "spamWordCount");
            hamURLSum += getFeatureValue(e, "hasURL");
            hamTotalWordsSum += getFeatureValue(e, "totalWords");
        }

        hamAvgSpamWordCount = hamWordSum / hamEmails.size();
        hamAvgHasURL = hamURLSum / hamEmails.size();
        hamAvgTotalWords = hamTotalWordsSum / hamEmails.size();
    }
    // ============================================================


    //FEATURE HELPER for other methods

    public int getFeatureValue(Email e, String name) {          //given email and name of a feature, searches email feature list + returns feature value
        for (Feature f : e.getFeatures()) {
            if (f.getName().equals(name)) {
                return f.getValue();
            }
        }
        return 0;
    }
    // =============================================

    
    //Euclidean distance (le big one)

    public double distanceToSpam(Email e) {     //current spam email we're testing

        double d1 = getFeatureValue(e, "spamWordCount") - spamAvgSpamWordCount; //line gets test email's feature, then subtracts the spam average for result
        double d2 = getFeatureValue(e, "hasURL") - spamAvgHasURL;               //(all calculated in computeAverages() above)
        double d3 = getFeatureValue(e, "totalWords") - spamAvgTotalWords;

        return Math.sqrt(d1*d1 + d2*d2 + d3*d3);        //= Euclidean Distance Formula (combines all 3 feature differences, then returns distance to spam)
                                                        //so... Smaller # = more similar to spam average / Bigger # = less similar to spam average
    }

    public double distanceToHam(Email e) {      //do the same for each ham email

        double d1 = getFeatureValue(e, "spamWordCount") - hamAvgSpamWordCount;
        double d2 = getFeatureValue(e, "hasURL") - hamAvgHasURL;
        double d3 = getFeatureValue(e, "totalWords") - hamAvgTotalWords;

        return Math.sqrt(d1*d1 + d2*d2 + d3*d3);        //distance to ham

                                                        //so for example, if spam distance = 8.38 and ham distance = 2.60, then *this* email is closer to ham
                                                        //and thus predicted as ham
    }
    // ==============================================

    //Prediction Method (For our Classification Desision)


    public int predict(Email e) {                   

        double spamDist = distanceToSpam(e);            //find email's distance to spam
        double hamDist = distanceToHam(e);              //find emails distance to ham

        if (spamDist < hamDist) {                   //whichever distance is smaller *wins* (returns 1 for spam label or 0 for ham label)
            return 1;                               //(example would be if spamDist = 3.1, hamDist = 7.4, 3.1 < 7.4 = True so return 1 (spam))
        } else {
            return 0;
        }
    }
    // =================================


    //For testing our accuracy on if we classified correctly

    public double calculateAccuracy(ArrayList<Email> testEmails) {      //testing set passed in from Main (these are emails learner did NOT train on)

    int correct = 0;                //starting our counter

    for (Email e : testEmails) {            //one test email at a time: -predict it , -then compare it to the real label

        int predicted = predict(e);         //from predict method above
        int actual = e.getLabel();          //already stored in email obj

        if (predicted == actual) {
            correct++;
        }
    }

    return (double) correct / testEmails.size();        //returns our fraction 'correct' = accuracy = correct/# of test emails
                                                        //(fyi casted to double for decimal division)
}                                                       //(example: if theres 20 test emails and 15 predicted correct, = return (double) 15/20; = 0.75, = 75% accuracy)


//Just Optional Methods to print out what the model learned (averages / counts)

    public void printAverages() {                           
    System.out.println("---Spam Averages---");
    System.out.println("spamWordCount: " + spamAvgSpamWordCount);
    System.out.println("hasURL: " + spamAvgHasURL);
    System.out.println("totalWords: " + spamAvgTotalWords);

    System.out.println("---Ham Averages---");
    System.out.println("spamWordCount: " + hamAvgSpamWordCount);
    System.out.println("hasURL: " + hamAvgHasURL);
    System.out.println("totalWords: " + hamAvgTotalWords);
}

    public void printCountsSummary() {
        System.out.println("Total Emails: " + emails.size());
        System.out.println("Spam Emails: " + spamEmails.size());
        System.out.println("Ham Emails: " + hamEmails.size());
    }
}
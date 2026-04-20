import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //build splitter object -> reads CSV, creates Email objs, and generates features for each one
        EmailSplitter splitter = new EmailSplitter("spam_or_not_spam.csv");

        
        ArrayList<Email> allEmails = splitter.getEmails();      //grabs full dataset from EmailSplitter

        
        ArrayList<Email> spamEmails = new ArrayList<>();    //separate lists so we can split spam/ham individually
        ArrayList<Email> hamEmails = new ArrayList<>();

        
        for (Email e : allEmails) {             //loop through every email from CSV and sort by label (1 = spam, 0 = ham)
            if (e.getLabel() == 1) {
                spamEmails.add(e);
            } else {
                hamEmails.add(e);
            }
        }

        
        ArrayList<Email> trainingEmails = new ArrayList<>();        //list gets passed into Learner (where model learns from)
        ArrayList<Email> testingEmails = new ArrayList<>();         //the unseen emails (used later in calculateAccuracy)

        
        int spamTrainSize = (int)(spamEmails.size() * 0.8); //80% of spam emails for training (20% goes to testing)

        
        for (int i = 0; i < spamEmails.size(); i++) {   
            if (i < spamTrainSize) {
                trainingEmails.add(spamEmails.get(i));          //if true, grab email at pos i, add to training
            } else {
                testingEmails.add(spamEmails.get(i));           //if false, take remaining emails put them into testing, again 80/20 split
            }
        }

        
        int hamTrainSize = (int)(hamEmails.size() * 0.8);       //same split for ham

        for (int i = 0; i < hamEmails.size(); i++) {
            if (i < hamTrainSize) {
                trainingEmails.add(hamEmails.get(i));
            } else {
                testingEmails.add(hamEmails.get(i));
            }
        }

        
        Learner learner = new Learner(trainingEmails);   //create learner using ONLY training set (so testing emails stay unseen until accuracy check)

        
        learner.separateEmails();           //training phase: separate labels, count features, then compute average spam/ham feature values
        learner.learnFeatureCounts();
        learner.computeAverages();

        //testing phase: run predictions on test set only and return overall accuracy
        double accuracy = learner.calculateAccuracy(testingEmails);

        //final summary output
        System.out.println("Total Emails: " + allEmails.size());
        System.out.println("Training Emails: " + trainingEmails.size());
        System.out.println("Testing Emails: " + testingEmails.size());
        System.out.println("Accuracy: " + accuracy);


        //~~~~~~For Exploration/Debugging~~~~~~
    
        // ---RAW EMAIL + FEATURE CHECK---
        //for (int i = 0; i < 5; i++) {
        //    allEmails.get(i).printFeatures();
        //    System.out.println("-----");
        //}



        //--FULL DATASET DUMPAGE--
        //for (Email e : allEmails) {
        //    e.printFeatures();
        //    System.out.println("-----");
        //}

        //--FOR LEARNER/INSPECTING--
        //learner.printCountsSummary();    //shows how many spam/ham emails were used in training
        //learner.printAverages();         //shows average feature values for spam vs ham (this is basically the "model")

    }
}
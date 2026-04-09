public class Main {
    public static void main(String[] args) {

        EmailSorter sorter = new EmailSorter();         //creating EmailSorter

        sorter.addEmail("Free money now", 1);           //just hardcoded a few in (label: 1 = spam, 0 = ham)
        sorter.addEmail("Meeting tomorrow at 10", 0);
        sorter.addEmail("Free free free offer", 1);

        sorter.printAllEmails();            //prints those puppies out (can see splitting/feature generation working)
    }
}
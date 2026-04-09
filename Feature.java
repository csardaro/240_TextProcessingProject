public class Feature {          /*basically matches the UML ---FYI Dropped splitEmail: String[] and wordCount(splitEmail[]) from UML
                                * , both are in Email class for now. splitEmail = represents 1 piece of data, not whole email &
                                * wordCounter was moved there as well since Email has full access to full text and split words :D, feature
                                * just stores the results (also left out value: int and label int)
                                */
    private String word;
    private int count;          

    public Feature(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }
}
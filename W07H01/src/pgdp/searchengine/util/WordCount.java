package pgdp.searchengine.util;

public class WordCount {
    private String word;
    private int count;
    private double weight;
    private double normalizedWeight;
    
    public WordCount(String word) {
        this.word = word;
        this.count = 0;
    }
    
    public WordCount(String word, int count) {
        this.word = word;
        if (count > 0) {
            this.count = count;
            this.weight = 1;
            this.normalizedWeight = 1;
        } else {
            this.count = 0;
            this.weight = 0;
            this.normalizedWeight = 0;
        }
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }
    
    public void increment() {
        count++;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getNormalizedWeight() {
        return normalizedWeight;
    }

    public void setNormalizedWeight(double normalizedWeight) {
        this.normalizedWeight = normalizedWeight;
    }
}

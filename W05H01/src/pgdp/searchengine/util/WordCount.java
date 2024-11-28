package pgdp.searchengine.util;

public class WordCount {

    private String word;
    private int count;

    public WordCount(String word, int count) {
        this.word = word;
        this.count = Math.max(count, 0);
    }

    public WordCount(String word) {
        this(word, 0);
    }

    public void increment() {
        count++;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

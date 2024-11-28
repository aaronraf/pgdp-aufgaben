package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;
import pgdp.searchengine.util.WordCount;

import java.util.*;

public class Document {
    private final int documentId;
    private static int nextDocumentId = 0;

    private String title;
    private String description;
    private String content;

    private final Date releaseDate;
    private Date lastUpdateDate;

    private final Author author;

    public Document(String title, String description, String content, Date releaseDate, Author author) {
        documentId = nextDocumentId++;
        this.title = title;
        this.description = description;
        this.content = content;
        this.releaseDate = releaseDate;
        this.lastUpdateDate = releaseDate;
        this.author = author;
    }

    public int yearsSinceRelease() {
        return releaseDate.yearsUntil(Date.today());
    }

    public int daysSinceLastUpdate() {
        return lastUpdateDate.daysUntil(Date.today());
    }

    public WordCount[] getWordCountArray() {
        String[] words = content.split(" ");
        HashMap<String, Integer> wordToCount = new HashMap<>();
        for (String word : words) {
            String base = word.toLowerCase(Locale.ROOT).replaceAll("[.;!?()*,]", "");
            int count = 0;

            if (wordToCount.containsKey(base)) {
                count = wordToCount.get(base);
            }
            wordToCount.put(base, ++count);
        }

        Map.Entry<String, Integer>[] entrySet = wordToCount.entrySet().toArray(new Map.Entry[0]);
        WordCount[] wordCounts = new WordCount[wordToCount.size()];
        int index = 0;
        for (Map.Entry<String, Integer> entry : entrySet) {
            wordCounts[index++] = new WordCount(entry.getKey(), entry.getValue());
        }

        return wordCounts;
    }

    private static boolean containsWord(WordCount[] array, String word) {
        for (WordCount wc : array) {
            if (wc.getWord().equals(word)) {
                return true;
            }
        }

        return false;
    }

    public static WordCount[] equalizeWordCountArray(WordCount[] first, WordCount[] second) {
        WordCount[] equal = new WordCount[first.length + second.length];
        for (int i = 0; i < first.length; i++) {
            equal[i] = first[i];
        }

        int index = first.length;
        for (int i = 0; i < second.length; i++) {
            if (!containsWord(first, second[i].getWord())) {
                equal[index++] = new WordCount(second[i].getWord());
            }
        }

        WordCount[] noNulls = new WordCount[index];
        for (int i = 0; i < index; i++) {
            noNulls[i] = equal[i];
        }
        return noNulls;
    }

    public static void sort(WordCount[] wordCount) {
        mergeSort(wordCount, 0, wordCount.length - 1);
    }

    private static void mergeSort(WordCount[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private static void merge(WordCount[] array, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        WordCount[] leftSide = new WordCount[leftSize];
        WordCount[] rightSide = new WordCount[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftSide[i] = array[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightSide[j] = array[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;
        while (i < leftSize && j < rightSize) {
            WordCount l = leftSide[i];
            WordCount r = rightSide[i];
            if (l.getWord().compareTo(r.getWord()) < 0 || (l.getWord().compareTo(r.getWord())) == 0 && l.getCount() <= r.getCount()) {
                array[k++] = leftSide[i++];
            } else {
                array[k++] = rightSide[j++];
            }
        }
        while (i < leftSize) {
            array[k++] = leftSide[i++];
        }
        while (j < rightSize) {
            array[k++] = rightSide[j++];
        }
    }

    public static double similarity(WordCount[] first, WordCount[] second) {
        if (first.length != second.length) {
            return -1;
        }

        if (first.length * second.length == 0) {
            return 0;
        }

        double result = 0.;
        for (int i = 0; i < first.length; i++) {
            result += first[i].getCount() * second[i].getCount();
        }

        return result / (first.length * second.length);
    }

    public double computeSimilarity(Document other) {
        WordCount[] a = getWordCountArray();
        WordCount[] b = other.getWordCountArray();

        a = equalizeWordCountArray(a, b);
        b = equalizeWordCountArray(b, a);

        sort(a);
        sort(b);

        return similarity(a, b);
    }


    //Getter
    public int getDocumentId() {
        return documentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Author getAuthor() {
        return author;
    }


    //Setter
    public void setTitle(String title) {
        lastUpdateDate = Date.today();
        this.title = title;
    }

    public void setDescription(String description) {
        lastUpdateDate = Date.today();
        this.description = description;
    }

    public void setContent(String content) {
        lastUpdateDate = Date.today();
        this.content = content;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public boolean equals(Document other) {
        return documentId == other.documentId;
    }

    @Override
    public String toString() {
        return title + ", by " + author + ", released " + releaseDate;
    }

    public String toPrintText() {
        return "<|==== " + title + " ====|>" + "\nBy " + author + "\n" + description + "\nLast updated at "
                + lastUpdateDate;
    }

    public static int numberOfCreatedDocuments() {
        return nextDocumentId;
    }

    public static void main(String[] args) {
        Document d = new Document("", "", "aaron Aaron AARON", null, null);
        WordCount[] wc = d.getWordCountArray();
        for (WordCount wordCount : wc) {
            System.out.println(wordCount.getWord() + ", " + wordCount.getCount());
        }
    }
}

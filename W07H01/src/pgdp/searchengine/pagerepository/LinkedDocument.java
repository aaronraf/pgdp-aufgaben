package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;
import pgdp.searchengine.util.WordCount;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class LinkedDocument extends AbstractLinkedDocument {
    private final LinkedDocumentCollection outgoingLinks;

    public LinkedDocument(String title, String description, String content, Date releaseDate, Author author, String address, int numberOfBuckets) {
        super(title, description, content, releaseDate, author, address, numberOfBuckets);
        this.outgoingLinks = new LinkedDocumentCollection(numberOfBuckets);
    }

    public String[] getOutgoingAddresses() {
        List<String> results = new ArrayList<>();
        String content = super.getContent();
        String regex = "link::[^\\s]+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String address = matcher.group().substring(6);
            results.add(address);
        }

        return results.toArray(new String[0]);
    }

    @Override
    public WordCount[] getWordCountArray() {
        String text = super.getContent();
        text = text.replaceAll("\\.|,|\\!|\\?|;|\\*|\\(|\\)", "");
        text = text.replaceAll("link::", "");

        String words[] = text.split(" ");

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase();
        }

        int size = 0;

        WordCount[] wordCount = new WordCount[words.length];

        for (String word : words) {
            boolean contains = false;
            for (int i = 0; i < size; ++i) {
                if (word.equals(wordCount[i].getWord())) {
                    wordCount[i].increment();
                    contains = true;
                    break;
                }
            }
            if (!contains && !word.isBlank()) {
                wordCount[size] = new WordCount(word.toLowerCase());
                wordCount[size++].increment();
            }
        }

        WordCount[] tmp = new WordCount[size];
        for (int i = 0; i < size; ++i) {
            tmp[i] = wordCount[i];
        }

        return tmp;
    }

    public boolean addOutgoingLink(AbstractLinkedDocument outgoingLink) {
        if (outgoingLink == null || outgoingLink.getAddress().equals(getAddress())) {
            return false;
        }

        AbstractLinkedDocument foundALD = outgoingLinks.find(outgoingLink.getAddress());
        if (foundALD != null) {
            if (foundALD instanceof DummyLinkedDocument) {
                outgoingLinks.removeDummy((DummyLinkedDocument) foundALD);
            } else {
                return false;
            }
        }

        return outgoingLinks.add(outgoingLink);
    }

    public LinkedDocumentCollection getOutgoingLinks() {
        return outgoingLinks;
    }
}

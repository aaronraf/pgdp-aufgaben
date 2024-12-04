package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

import java.util.Arrays;
import java.util.Objects;

public class DocumentCollection {

    private final Bucket[] buckets;

    public DocumentCollection(int length) {
        this.buckets = new Bucket[Math.max(length, 1)];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new Bucket();
        }
    }

    private int indexFunction(int documentID) {
        return documentID % buckets.length;
    }

    public boolean add(Document element) {
        if (element == null || element.getDocumentId() < 0) {
            return false;
        }

        return buckets[indexFunction(element.getDocumentId())].add(element);
    }

    public boolean isEmpty() {
        return Arrays.stream(buckets).allMatch(Bucket::isEmpty);
    }

    public Document find(int id) {
        if (id < 0) {
            return null;
        }

        return Arrays.stream(buckets).map(bucket -> bucket.find(id)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public boolean removeDocument(int id) {
        if (id < 0) {
            return false;
        }

        return buckets[indexFunction(id)].removeDocument(id);
    }

    public boolean removeDocumentsFromAuthor(Author author) {
        if (author == null) {
            return false;
        }

        return Arrays.stream(buckets).anyMatch(bucket -> bucket.removeDocument(author));
    }

    public boolean removeAll() {
        if (isEmpty()) {
            return false;
        }

        Arrays.stream(buckets).forEach(Bucket::removeAll);
        return true;
    }

    public boolean removeOldDocuments(Date releaseDate, Date lastUpdated) {
        if (releaseDate == null && lastUpdated == null) {
            return false;
        }

        return Arrays.stream(buckets).anyMatch(bucket -> bucket.removeOldDocuments(releaseDate, lastUpdated));
    }

    public DocumentListElement getHead(int bucketIndex) {
        if (bucketIndex < 0 || bucketIndex >= buckets.length) {
            return null;
        }

        return buckets[bucketIndex].getHead();
    }

    public DocumentListElement getTail(int bucketIndex) {
        if (bucketIndex < 0 || bucketIndex >= buckets.length) {
            return null;
        }

        return buckets[bucketIndex].getTail();
    }

    public boolean contains(Document document) {
        return Arrays.stream(buckets).anyMatch(b -> b.contains(document));
    }

    public int size() {
        return Arrays.stream(buckets).mapToInt(Bucket::size).sum();
    }
}

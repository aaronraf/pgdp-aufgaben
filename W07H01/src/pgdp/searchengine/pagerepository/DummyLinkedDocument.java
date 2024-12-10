package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

public class DummyLinkedDocument extends AbstractLinkedDocument {

    public DummyLinkedDocument(String address, int numberOfBuckets) {
        super("", "", "", null, null, address, numberOfBuckets);
    }
}

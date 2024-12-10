package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

public abstract class AbstractLinkedDocument extends Document {
    private final String address;
    private final LinkedDocumentCollection incomingLinks;

    public AbstractLinkedDocument(String title, String description, String content, Date releaseDate, Author author, String address, int numberOfBuckets) {
        super(title, description, content, releaseDate, author);
        this.address = address;
        this.incomingLinks = new LinkedDocumentCollection(numberOfBuckets);
    }

    public boolean addIncomingLink(AbstractLinkedDocument incomingLink) {
        if (incomingLink == null || incomingLink.getAddress().equals(getAddress())) {
            return false;
        }

        return incomingLinks.find(incomingLink.getAddress()) == null && incomingLinks.add(incomingLink);
    }

    public String getAddress() {
        return address;
    }

    public LinkedDocumentCollection getIncomingLinks() {
        return incomingLinks;
    }
}

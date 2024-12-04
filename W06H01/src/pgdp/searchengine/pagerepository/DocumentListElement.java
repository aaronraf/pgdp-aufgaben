package pgdp.searchengine.pagerepository;

public class DocumentListElement {

    private Document document;
    private DocumentListElement pre;
    private DocumentListElement next;

    public DocumentListElement(Document document) {
        this(document, null, null);
    }

    public DocumentListElement(Document document, DocumentListElement pre, DocumentListElement next) {
        this.document = document;
        this.pre = pre;
        this.next = next;
    }

//    -- Getter --
    public Document getDocument() {
        return document;
    }

    public DocumentListElement getPre() {
        return pre;
    }

    public DocumentListElement getNext() {
        return next;
    }

//    -- Setter --
    public void setPre(DocumentListElement pre) {
        this.pre = pre;
    }

    public void setNext(DocumentListElement next) {
        this.next = next;
    }

}

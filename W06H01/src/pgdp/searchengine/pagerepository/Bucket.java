package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

public class Bucket {

    private DocumentListElement head;
    private DocumentListElement tail;
    private int size;

    public Bucket() {
        size = 0;
    }

    public boolean contains(Document document) {
        if (document == null) {
            return false;
        }

        int id = document.getDocumentId();
        if (head == null || id < head.getDocument().getDocumentId() || id > tail.getDocument().getDocumentId()) {
            return false;
        }

        for (DocumentListElement current = head; current != null; current = current.getNext()) {
            if (current.getDocument().getDocumentId() == id) {
                return true;
            }
        }

        return false;
    }

    public boolean add(Document document) {
        if (document == null) {
            return false;
        }

        if (isEmpty()) {
            head = tail = new DocumentListElement(document);
            size++;
            return true;
        }

        DocumentListElement current = head;
        while (current != null) {
            if (current.getDocument().getDocumentId() == document.getDocumentId()) {
                return false;
            }

            if (current.getDocument().getDocumentId() > document.getDocumentId()) {
                DocumentListElement docElement = new DocumentListElement(document, current.getPre(), current);

                if (current.getPre() != null) {
                    current.getPre().setNext(docElement);
                } else {
                    head = docElement;
                }

                current.setPre(docElement);
                size++;
                return true;
            }

            if (current == tail) {
                DocumentListElement docElement = new DocumentListElement(document, current, null);
                current.setNext(docElement);
                tail = docElement;
                size++;
                return true;
            }

            current = current.getNext();
        }

        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Document find(int id) {
        for (DocumentListElement current = head; current != null; current = current.getNext()) {
            if (current.getDocument().getDocumentId() == id) {
                return current.getDocument();
            }
        }

        return null;
    }

    public boolean removeDocument(int id) {
        for (DocumentListElement current = head; current != null; current = current.getNext()) {
            if (current.getDocument().getDocumentId() == id) {
                return removeItem(current);
            }
        }

        return false;
    }

    public boolean removeDocument(Author author) {
        boolean anyMatch = false;
        DocumentListElement current = head;

        while (current != null) {
            DocumentListElement next = current.getNext();
            if (current.getDocument().getAuthor() == author) {
                removeItem(current);
                anyMatch = true;
            }
            current = next;
        }

        return anyMatch;
    }

    private boolean removeItem(DocumentListElement current) {
        if (current.getPre() != null) {
            current.getPre().setNext(current.getNext());
        } else {
            head = current.getNext();
        }

        if (current.getNext() != null) {
            current.getNext().setPre(current.getPre());
        } else {
            tail = current.getPre();
        }

        size--;
        return true;
    }

    public void removeAll() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean removeOldDocuments(Date releaseDate, Date lastUpdate) {
        boolean anyMatch = false;
        DocumentListElement current = head;

        while (current != null) {
            Document document = current.getDocument();
            boolean proceedWithRemoval = true;

            if (releaseDate != null) {
                proceedWithRemoval = document.getReleaseDate().daysUntil(releaseDate) > 0;
            }

            if (lastUpdate != null && proceedWithRemoval) {
                proceedWithRemoval = document.getLastUpdateDate().daysUntil(lastUpdate) > 0;
            }

            if (proceedWithRemoval) {
                DocumentListElement toRemove = current;
                current = current.getNext();
                removeItem(toRemove);
                anyMatch = true;
            } else {
                current = current.getNext();
            }
        }

        return anyMatch;
    }

    public int size() {
        return size;
    }

    public DocumentListElement getHead() {
        return head;
    }

    public DocumentListElement getTail() {
        return tail;
    }

}

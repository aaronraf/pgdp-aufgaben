package pgdp.searchengine.pagerepository;

public class LinkedDocumentCollection extends DocumentCollection {
    public LinkedDocumentCollection(int numberOfBuckets) {
        super(numberOfBuckets);
    }

    private int indexFunction(String address) {
        return address == null ? -1 : (address.hashCode() % getNumberOfBuckets() + getNumberOfBuckets()) % getNumberOfBuckets();
    }

    @Override
    public boolean add(Document document) {
        if (!(document instanceof AbstractLinkedDocument)) {
            return false;
        }

        int bucketIndex = indexFunction(((AbstractLinkedDocument) document).getAddress());

        return super.getBuckets()[bucketIndex].add(document);
    }

    public AbstractLinkedDocument find(String address) {
        int bucketIndex = indexFunction(address);
        if (bucketIndex == -1) {
            return null;
        }

        DocumentListElement bucketHead = super.getBuckets()[bucketIndex].getHead();
        for (DocumentListElement current = bucketHead; current != null; current = current.getNext()) {
            if (current.getDocument() instanceof AbstractLinkedDocument && ((AbstractLinkedDocument) current.getDocument()).getAddress().equals(address)) {
                return (AbstractLinkedDocument) current.getDocument();
            }
        }

        return null;
    }

    public boolean removeDummy(DummyLinkedDocument dummyLinkedDocument) {
        if (dummyLinkedDocument == null) {
            return false;
        }

        boolean removedSomething = false;
        for (Bucket bucket : super.getBuckets()) {
            for (DocumentListElement current = bucket.getHead(); current != null; current = current.getNext()) {
                if (current.getDocument() instanceof DummyLinkedDocument && current.getDocument().equals(dummyLinkedDocument)) {
                    removedSomething |= bucket.remove(current);
                }
            }
        }

        return removedSomething;
    }

    private void updateIncomingLinks(LinkedDocument linkedDocument, String[] addresses) {
        if (linkedDocument == null || addresses == null) {
            return;
        }

        for (String address : addresses) {
            AbstractLinkedDocument targetDocument = find(address);

            if (targetDocument == null) {
                DummyLinkedDocument dummy = new DummyLinkedDocument(address, getNumberOfBuckets());
                add(dummy);
                targetDocument = dummy;
            }

            targetDocument.addIncomingLink(linkedDocument);
            linkedDocument.addOutgoingLink(targetDocument);
        }
    }

    private void updateOutgoingLinks(AbstractLinkedDocument abstractLinkedDocument) {
        if (abstractLinkedDocument == null) {
            return;
        }

        String address = abstractLinkedDocument.getAddress();
        DummyLinkedDocument foundDummy = null;
        for (Bucket bucket : super.getBuckets()) {
            for (DocumentListElement current = bucket.getHead(); current != null; current = current.getNext()) {
                if (current.getDocument() instanceof LinkedDocument) {
                    LinkedDocument linkedDocument = (LinkedDocument) current.getDocument();
                    AbstractLinkedDocument outgoingDummy = linkedDocument.getOutgoingLinks().find(address);

                    if (outgoingDummy instanceof DummyLinkedDocument) {
                        linkedDocument.addOutgoingLink(abstractLinkedDocument);
                        foundDummy = (DummyLinkedDocument) outgoingDummy;
                    }
                }
            }
        }

        if (foundDummy != null) {
            removeDummy(foundDummy);
        }

        for (Bucket bucket : getBuckets()) {
            for (DocumentListElement current = bucket.getHead(); current != null; current = current.getNext()) {
                if (current.getDocument() instanceof LinkedDocument) {
                    LinkedDocument linkedDocument = (LinkedDocument) current.getDocument();

                    if (linkedDocument.getOutgoingLinks().find(address) != null) {
                        abstractLinkedDocument.addIncomingLink(linkedDocument);
                    }
                }
            }
        }
    }

    public boolean addToResultCollection(AbstractLinkedDocument abstractLinkedDocument) {
        if (abstractLinkedDocument == null) {
            return false;
        }

        AbstractLinkedDocument current = find(abstractLinkedDocument.getAddress());
        if (current != null) {
            if (current instanceof DummyLinkedDocument) {
                removeDummy((DummyLinkedDocument) current);
                super.add(abstractLinkedDocument);
                updateOutgoingLinks(abstractLinkedDocument);
                return true;
            }

            return false;
        }

        boolean documentAdded = super.add(abstractLinkedDocument);
        if (documentAdded) {
            updateOutgoingLinks(abstractLinkedDocument);
        }

        return documentAdded;
    }
}

package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

public class Document {
    private static int counter = 0;
    private int documentId;
    private String title;
    private String description;
    private String content;
    private Date releaseDate;
    private Date lastUpdateDate;
    private Author author;

    public Document(String title, String description, String content, Date releaseDate, Author author) {
        this.documentId = counter++;
        this.title = title;
        this.description = description;
        this.content = content;
        this.releaseDate = releaseDate;
        this.lastUpdateDate = this.releaseDate;
        this.author = author;
    }

    public boolean equals(Document other) {
        return this.documentId == other.getDocumentId();
    }

    @Override
    public String toString() {
        return String.format("%s (%s), %s", title, releaseDate.toString(), author.toString());
    }

    public String toPrintText() {
        return String.format("Title: %s\nAuthor: %s\nDescription: %s\nLast Updated: %s", title, author.toString(), description, lastUpdateDate.toString());
    }

    public static int numberOfCreatedDocuments() {
        return counter;
    }

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}

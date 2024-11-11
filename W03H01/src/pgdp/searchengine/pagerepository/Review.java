package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

public class Review {
    private static int counter = 0;
    private int postId;
    private String title;
    private String content;
    private Date postDate;
    private Author reviewer;
    private Document reviewedDocument;
    private int rating;

    public Review(String title, String content, Date postDate, Author reviewer, Document reviewedDocument, int rating) {
        this.postId = counter++;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.reviewer = reviewer;
        this.reviewedDocument = reviewedDocument;
        this.rating = rating;
    }

    public boolean equals(Review other) {
        return this.postId == other.postId;
    }

    @Override
    public String toString() {
        return String.format("%s(%d), %s", title, rating, reviewedDocument.toString());
    }

    public String toPrintText() {
        return String.format("Reviewed Document: %s\nRating: %d\nTitle: %s\nContent: %s\nRelease Date: %s\nReviewer: %s", reviewedDocument.toString(), rating, title, content, reviewedDocument.getReleaseDate().toString(), reviewer.toString());
    }

    public static int numberOfCreatedReviews() {
        return counter;
    }

    public int getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public Author getReviewer() {
        return reviewer;
    }

    public Document getReviewedDocument() {
        return reviewedDocument;
    }

    public int getRating() {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;
import pgdp.searchengine.util.WordCount;

import java.util.*;

public class DocumentCollection {
	private final Bucket[] buckets;

	public DocumentCollection(int numberOfBuckets) {
		buckets = new Bucket[Math.max(numberOfBuckets, 1)];
		for (int i = 0; i < buckets.length; ++i) {
			buckets[i] = new Bucket();
		}
	}

	public boolean add(Document document) {
		if (document == null || document.getDocumentId() < 0) {
			return false;
		}

		int bucketIndex = indexFunction(document.getDocumentId());

		return buckets[bucketIndex].add(document);
	}

	public Document find(int documentId) {
		if (documentId < 0) {
			return null;
		}

		int bucketIndex = indexFunction(documentId);

		DocumentListElement foundListElement = buckets[bucketIndex].find(documentId);

		return foundListElement != null ? foundListElement.getDocument() : null;
	}

	public boolean removeDocument(int documentId) {
		if (documentId < 0) {
			return false;
		}

		int bucketIndex = indexFunction(documentId);

		DocumentListElement listElementToRemove = buckets[bucketIndex].find(documentId);

		return buckets[bucketIndex].remove(listElementToRemove);
	}

	public boolean removeDocumentsFromAuthor(Author author) {
		if (author == null) {
			return false;
		}

		DocumentListElement currentListElement;
		boolean removedSomething = false;

		for (int i = 0; i < buckets.length; ++i) {
			currentListElement = buckets[i].getHead();
			while (currentListElement != null) {
				if (currentListElement.getDocument().getAuthor().equals(author)) {
					DocumentListElement listElementToRemove = currentListElement;
					currentListElement = currentListElement.getNext();
					removedSomething |= buckets[i].remove(listElementToRemove);
				} else {
					currentListElement = currentListElement.getNext();
				}
			}
		}
		return removedSomething;
	}

	public boolean removeAll() {
		if (this.isEmpty()) {
			return false;
		}

		for (int i = 0; i < buckets.length; ++i) {
			buckets[i] = new Bucket();
		}
		return true;
	}

	public boolean removeOldDocuments(Date releaseDate, Date lastUpdated) {
		if (releaseDate == null && lastUpdated == null) {
			if (!removeAll()) {
				System.out.println("Hello there!");
				return false;
			}
			return true;
		}

		if (releaseDate != null && lastUpdated != null && releaseDate.daysUntil(lastUpdated) < 0) {
			return false;
		}

		DocumentListElement currentListElement;
		boolean removedSomething = false;

		for (int i = 0; i < buckets.length; ++i) {
			currentListElement = buckets[i].getHead();
			while (currentListElement != null) {
				if (releaseDate != null
						&& currentListElement.getDocument().getReleaseDate().daysUntil(releaseDate) > 0) {
					if (lastUpdated != null
							&& currentListElement.getDocument().getLastUpdateDate().daysUntil(lastUpdated) > 0) {
						DocumentListElement listElementToRemove = currentListElement;
						removedSomething |= buckets[i].remove(listElementToRemove);
					} else if (lastUpdated == null) {
						DocumentListElement listElementToRemove = currentListElement;
						removedSomething |= buckets[i].remove(listElementToRemove);
					}
				} else if (releaseDate == null
						&& currentListElement.getDocument().getLastUpdateDate().daysUntil(lastUpdated) > 0) {
					DocumentListElement listElementToRemove = currentListElement;
					removedSomething |= buckets[i].remove(listElementToRemove);
				}
				currentListElement = currentListElement.getNext();
			}
		}
		return removedSomething;
	}

	private int indexFunction(int documentId) {
		return documentId % buckets.length;
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

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public boolean contains(Document document) {
		if (document == null) {
			return false;
		}
		return this.find(document.getDocumentId()) != null;
	}

	public int getNumberOfBuckets() {
		return buckets.length;
	}

	public Bucket[] getBuckets() {
		return buckets;
	}

	public int size() {
		int size = 0;
		for (int i = 0; i < buckets.length; ++i) {
			size += buckets[i].size();
		}
		return size;
	}

	public WordCount[] getCompleteWordCountArray() {
//		hashset to eliminate dupes
		HashSet<String> distinctWords = new HashSet<>();
		for (Bucket bucket : buckets) {
			for (DocumentListElement current = bucket.getHead(); current != null; current = current.getNext()) {
				WordCount[] wordCounts = current.getWordCountArray();
				if (wordCounts != null) {
					for (WordCount wc : wordCounts) {
						distinctWords.add(wc.getWord());
					}
				}
			}
		}

//		map distinct words (string) to wordcount array
		WordCount[] resultArray = distinctWords.stream()
				.map(WordCount::new)
				.toArray(WordCount[]::new);

//		sort lexicographically
		Arrays.sort(resultArray, Comparator.comparing(WordCount::getWord));

		return resultArray;
	}


	public void equalizeAllWordCountArrays() {
		WordCount[] completeSet = getCompleteWordCountArray();
		for (Bucket bucket : buckets) {
			for (DocumentListElement current = bucket.getHead(); current != null; current = current.getNext()) {
				WordCount[] updated = new WordCount[completeSet.length];
				HashMap<String, Integer> wordToCountMap = new HashMap<>();

//				to synchronize the existing count value with the new one
				for (WordCount wc : current.getWordCountArray()) {
					wordToCountMap.put(wc.getWord(), wc.getCount());
				}
				for (int i = 0; i < completeSet.length; i++) {
					String word = completeSet[i].getWord();
					int count = wordToCountMap.getOrDefault(word, 0);
					updated[i] = new WordCount(word, count);
				}

				current.setWordCountArray(updated);
			}
		}
	}

	public int getNumberOfDocumentsContaining(String word) {
		if (word == null || word.isEmpty()) {
			return 0;
		}

		String base = word.toLowerCase(Locale.ROOT).replaceAll("[.,!?;*()]", "");
		int count = 0;
		for (Bucket bucket : buckets) {
			for (DocumentListElement current = bucket.getHead(); current != null; current = current.getNext()) {
				WordCount[] wordCounts = current.getWordCountArray();
				if (Arrays.stream(wordCounts)
						.map(WordCount::getWord)
						.anyMatch(w -> w.equals(base))) {
					count++;
				}
			}
		}

        return count;
    }

	private double invertedFrequency(String word) {
		int documentsCount = Arrays.stream(buckets).mapToInt(Bucket::size).sum() + 1;
		int documentsContainingWord = getNumberOfDocumentsContaining(word);
		return Math.log((double) documentsCount / documentsContainingWord) / Math.log(2.0);
	}

	private void calculateWeights(WordCount[] wordCounts) {
		double sumSquaredWeight = 0.;
		for (WordCount wc : wordCounts) {
			wc.setWeight(wc.getCount() * invertedFrequency(wc.getWord()));
			sumSquaredWeight += Math.pow(wc.getWeight(), 2.);
		}
		for (WordCount wc : wordCounts) {
			wc.setNormalizedWeight(wc.getWeight() / Math.sqrt(sumSquaredWeight));
		}
	}

	public Document[] query(String word) {
		if (word == null || word.isEmpty()) {
			return new Document[] {};
		}

		Document queryDoc = new Document("", "", word, Date.today(), null);
		add(queryDoc);
		equalizeAllWordCountArrays();
		WordCount[] temp = getCompleteWordCountArray();
		calculateWeights(temp);

		Map<Document, Double> documentSimilarityMap = new HashMap<>();
		for (Bucket bucket : buckets) {
			for (DocumentListElement current = bucket.getHead(); current != null; current = current.getNext()) {
				Document currentDoc = current.getDocument();
				if (currentDoc == queryDoc) {
					continue;
				}

				calculateWeights(currentDoc.getWordCountArray());
				double similarity = Document.complexSimilarity(temp, currentDoc.getWordCountArray());
				current.setSimilarity(similarity);

				documentSimilarityMap.put(currentDoc, similarity);
			}
		}

		removeDocument(queryDoc.getDocumentId());
		return documentSimilarityMap.entrySet().stream().sorted(Map.Entry.<Document, Double>comparingByValue().reversed()).map(Map.Entry::getKey).toArray(Document[]::new);
	}

	public void sortBuckets() {
		for (Bucket bucket : buckets) {
			if (bucket.getHead() == null) {
				continue;
			}

			List<DocumentListElement> elements = new ArrayList<>();
			for (DocumentListElement current = bucket.getHead(); current != null; current = current.getNext()) {
				elements.add(current);
			}

			elements.sort((e1, e2) -> {
				int similarityComparison = Double.compare(e2.getSimilarity(), e1.getSimilarity());
				if (similarityComparison == 0) {
					return Integer.compare(e1.getDocument().getDocumentId(), e2.getDocument().getDocumentId());
				}
				return similarityComparison;
			});

			DocumentListElement newHead = elements.get(0);
			DocumentListElement prev = null;
			for (DocumentListElement element : elements) {
				element.setPre(prev);
				if (prev != null) {
					prev.setNext(element);
				}
				prev = element;
			}

			DocumentListElement newTail = elements.get(elements.size() - 1);
			newTail.setNext(null);
			bucket.setHead(newHead);
			bucket.setTail(newTail);
		}
	}

}

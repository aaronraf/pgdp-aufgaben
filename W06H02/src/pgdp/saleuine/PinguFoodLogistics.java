package pgdp.saleuine;

import java.math.BigDecimal;

public class PinguFoodLogistics {

	private TradeOrderQueue orderBook;
	private BigDecimal ppgAnchovies;
	private BigDecimal ppgCrustaceans;
	private BigDecimal ppgSardines;

	public PinguFoodLogistics(BigDecimal ppgAnchovies, BigDecimal ppgCrustaceans, BigDecimal ppgSardines) {
		this.orderBook = new TradeOrderQueue();
		this.ppgAnchovies = ppgAnchovies;
		this.ppgCrustaceans = ppgCrustaceans;
		this.ppgSardines = ppgSardines;
	}

	public void acceptNewOrder(TradeOrder order) {
		orderBook.add(order);
	}

	public void clearOrderBook() {
		System.out.printf("Es können %d Bestellungen abgearbeitet werden.", orderBook.size());
//		while (!orderBook.isEmpty()) {
//			orderBook.poll();
//		}
	}

	private void registerUnusedFood(PinguFood food) {

	}

	public void printWasteStatistics() {

	}

	public static void main(String[] args) {
		//		PinguFoodLogistics market = new PinguFoodLogistics(BigDecimal.ONE, BigDecimal.valueOf(0.5),
		//				BigDecimal.valueOf(2));
		//		market.acceptNewOrder(new TradeOrder());
		//		market.acceptNewOrder(new WeightOrder(1000));
		//		market.acceptNewOrder(new AmountOrder(2, 2, 2));
		//		market.clearOrderBook();
		//		market.printWasteStatistics();
	}

	/**
	 * The following methods generate Anchovie, Crustacean or Sardine object
	 * WARNING: do NOT change these methods unless you want to fail the tests
	 */

	/* remove after implementing PinguFood + subclasses
	public static PinguFood generatePinguFood() {
		switch (randomInt(0, 2)) {
		case 0:
			return generateAnchovie();
		case 1:
			return generateCrustacean();
		case 2:
			return generateSardine();
		default:
			throw new SecurityException("You changed the code!");
		}
	}
	
	public static Anchovie generateAnchovie() {
		return new Anchovie(randomInt(0, 5), randomInt(1, 55));
	}
	
	public static Crustacean generateCrustacean() {
		return new Crustacean(randomInt(1, 10));
	}
	
	public static Sardine generateSardine() {
		return new Sardine(randomInt(0, 10), randomInt(20, 300), randomInt(1, 22));
	}
	*/ // remove after implementing PinguFood + subclasses
}

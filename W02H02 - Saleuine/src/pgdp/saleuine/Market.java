package pgdp.saleuine;

import static pgdp.PinguLib.*;

public class Market {

	private int daysInBusiness;
	private int soldCrustaceansDay;
	private int soldAnchoviesDay;
	private int soldSardinesDay;
	private double moneyMadeDay;
	private double moneyMadeTotal;
	private double priceCrustaceans;
	private double priceAnchovies;
	private double priceSardines;

	public Market(double priceCrustaceans, double priceAnchovies, double priceSardines) {
		daysInBusiness = 1;
		soldCrustaceansDay = 0;
		soldAnchoviesDay = 0;
		soldSardinesDay = 0;
		moneyMadeDay = 0.0;
		moneyMadeTotal = 0.0;
		this.priceCrustaceans = priceCrustaceans;
		this.priceAnchovies = priceAnchovies;
		this.priceSardines = priceSardines;
	}

	public void serveCustomer(Kaufuin customer) {
		int crustaceans = customer.getOrder().getAmountCrustaceans();
		int anchovies = customer.getOrder().getAmountAnchovies();
		int sardines = customer.getOrder().getAmountSardines();
		System.out.println("Neue Bestellung wird angenommen: " + customer.getName() + "(" + customer.getAge() + ") hätte gerne " + crustaceans + " Krustentiere, " + anchovies +  " Sardellen und " + sardines + " Sardinen.");

		soldCrustaceansDay += crustaceans;
		soldAnchoviesDay += anchovies;
		soldSardinesDay += sardines;

		double totalCost = crustaceans * priceCrustaceans + anchovies * priceAnchovies + sardines * priceSardines;
		System.out.println("Die Bestellung kostet " + totalCost + "PD.");

		customer.pay(totalCost);
		moneyMadeDay += totalCost;
		moneyMadeTotal += totalCost;

		System.out.println("");
	}

	public void endDay() {
		System.out.println("Der Laden der Saleuine Claudia und Karl-Heinz hat am " + daysInBusiness + ". Tag " + moneyMadeDay + "PD eingenommen.");
		System.out.println("Dafür wurden " + soldCrustaceansDay + " Krustentiere, " + soldAnchoviesDay + " Sardellen und " + soldSardinesDay + " Sardinen verkauft.");
		System.out.println("Insgesamt hat der Laden " + moneyMadeTotal + "PD eingenommen.\n");

		daysInBusiness++;
		soldAnchoviesDay = 0;
		soldSardinesDay = 0;
		soldCrustaceansDay = 0;
		priceSardines /= 2;
		priceAnchovies /= 2;
		priceCrustaceans /= 2;
//		moneyMadeDay = 0.0;
	}

	public static void main(String[] args) {
		// Example from task description. You may change the content of this method to test your code

		// remove '//' from line start to comment the line in -> it will be compiled and executed.
		// in eclipse on windows you can mark all lines and press STRG+Shift+c to toggle comments on/off

		//		Market market = new Market(1.99, 9.99, 100);
		//
		//		Order jessiesOrder = new Order(5, 6, 7);
		//		Kaufuin jessie = new Kaufuin("Jessie", 19, 1337, jessiesOrder);
		//
		//		market.serveCustomer(jessie);
		//		market.endDay();

		Market market = new Market(1.99, 9.99, 100);

		Order jessiesOrder = new Order(5, 6, 7);

		Kaufuin jessie = new Kaufuin("Jessie", 19, 1337, jessiesOrder);
		Kaufuin alex = new Kaufuin("Alex", 23, 9001, new Order(0, 0, 10));

		market.serveCustomer(jessie);
		market.endDay();

		jessie.giveNewOrder(new Order(1, 2, 3));
		jessie.addToOrder(new Order(1, 1, 1));
		market.serveCustomer(jessie);
		market.serveCustomer(alex);
		market.endDay();
	}

}

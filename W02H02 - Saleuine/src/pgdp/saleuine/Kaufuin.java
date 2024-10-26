package pgdp.saleuine;

import static pgdp.PinguLib.*;

public class Kaufuin {
    private String name;
    private int age;
    private double money;
    private Order order;

    public Kaufuin(String name, int age, double money) {
        this.name = name;
        this.age = age;
        this.money = money;
    }

    public Kaufuin(String name, int age, double money, Order order) {
        this(name, age, money);
        this.order = order;
    }

    public Kaufuin(String name, int age, double money, int amountCrustaceans, int amountAnchovies, int amountSardines) {
        this(name, age, money, new Order(amountCrustaceans, amountAnchovies, amountSardines));
    }

    public Order getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void giveNewOrder(Order newOrder) {
        order = newOrder;
    }

    public void addToOrder(Order additionalOrder) {
        order.addOrder(additionalOrder);
    }

    public String giveInformation() {
        return name + "(" + age + ") hätte gerne " + order.getAmountCrustaceans() + " Krustentiere, " + order.getAmountAnchovies() + " Sardellen und " + order.getAmountSardines() + " Sardinen.";
    }

    public void pay(double amount) {
        money -= amount;
        System.out.println(name + " zahlt " + amount + " und hat noch " + money + "PD übrig.");
    }

}

package pgdp.expedition;

import static pgdp.PinguLib.write;

public class Entdeckuin {

	private String name;
	private int age;
	private Backpack backpack;

	public Entdeckuin(String name, int age, String favoriteFishType, String favoritePreparation) {
		this.name = name;
		this.age = age;
		backpack = new Backpack(favoriteFishType, favoritePreparation);
	}

	public void addProvisions(Fish fish) {
		String preparation = backpack.getProvisions().getPreparation();
		fish.prepare(preparation);
		backpack.getProvisions().add(fish);
		System.out.println(toString() + " " + preparation + " some " + fish.getType() + " and added it to their provisions.");
	}

	public Fish fish(String type) {
		Tool fishingRod = backpack.getFishingRod();
		System.out.println(toString() + " used the " + fishingRod.toString() + " to catch some " + type + ". It has " + fishingRod.use() + " durability left.");
		return new Fish(type);
	}

	public void eat() {
		Fish provisions = backpack.getProvisions();
		System.out.println(toString() + " ate some " + provisions.toString() + ". There is " + provisions.eat() + " " + provisions.toString() + " left.");
	}

	public void usePickaxe() {
		Tool pickaxe = backpack.getPickaxe();
		System.out.println(toString() + " used the " + pickaxe.toString() + " to crush some ice. It has " + pickaxe.use() + " durability left.");
	}

	public void findScepter() {
		Scepter scepter = Scepter.findScepter(this);
		backpack.putScepterIntoBackpack(scepter);
	}

	public void steal(Entdeckuin other) {
		Scepter scepter = other.getBackpack().steal(this);
		backpack.putScepterIntoBackpack(scepter);
	}

	@Override
	public String toString() {
		return "Entdeckuin " + name + "(" + age + ")";
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Backpack getBackpack() {
		return backpack;
	}

	public static void main(String[] args) {
		/*
		 * TODO: Remove the comment symbols and run the main-method for testing. You can
		 * change this method however you like!
		 */

		Entdeckuin antonia = new Entdeckuin("Antonia", 5, "salmon", "salted");

		antonia.addProvisions(new Fish("salmon", "salted"));
		antonia.usePickaxe();
		antonia.findScepter();
		antonia.eat();
	}
}

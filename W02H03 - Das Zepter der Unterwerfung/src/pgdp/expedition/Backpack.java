package pgdp.expedition;

public class Backpack {

    private Scepter scepter;
    private Tool pickaxe;
    private Tool fishingRod;
    private Fish provisions;

    public Backpack(String fishType, String fishPreparation) {
        provisions = new Fish(fishType, fishPreparation);
        pickaxe = new Tool("pickaxe");
        fishingRod = new Tool("fishing rod");
        scepter = null;
    }

    public void putScepterIntoBackpack(Scepter scepter) {
        this.scepter = scepter;
    }

    public Scepter steal(Entdeckuin stealer) {
        Scepter temp = scepter;
        scepter.steal(stealer);
        scepter = null;
        return temp;
    }

    public Scepter getScepter() {
        return scepter;
    }

    public Tool getPickaxe() {
        return pickaxe;
    }

    public Tool getFishingRod() {
        return fishingRod;
    }

    public Fish getProvisions() {
        return provisions;
    }
}

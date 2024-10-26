package pgdp.expedition;

public class Tool {

    private String type;
    private double durability;

    public Tool(String type) {
        this.type = type;
        durability = 100.0;
    }

    public double use() {
        return --durability;
    }

    @Override
    public String toString() {
        return type + " with " + durability + " points of durability";
    }

    public String getType() {
        return type;
    }

    public double getDurability() {
        return durability;
    }
}

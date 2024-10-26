package pgdp.expedition;

import java.util.function.DoubleToIntFunction;

public class Scepter {

    private Entdeckuin owner;
    private static Scepter instance = new Scepter();

    private Scepter() {
        owner = null;
    }

    public static Scepter findScepter(Entdeckuin newOwner) {
        instance.owner = newOwner;
        System.out.println(instance.toString());
        return instance;
    }

    public Scepter steal(Entdeckuin newOwner) {
        instance.owner = newOwner;
        System.out.println(instance.toString());
        return instance;
    }

    @Override
    public String toString() {
        return "The scepter of submission belongs to " + owner + " now.";
    }
}

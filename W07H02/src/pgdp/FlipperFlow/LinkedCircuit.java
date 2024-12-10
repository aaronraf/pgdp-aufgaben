package pgdp.FlipperFlow;

import pgdp.FlipperFlow.Components.Cable;
import pgdp.FlipperFlow.Components.PowerSource;
import pgdp.FlipperFlow.Components.Resistor;

public class LinkedCircuit<T extends Cable, R extends PowerSource> {
    private CableLink start;
    private CableLink end;
    private R powerSource;

    public class CableLink {
        private T cable;
        private CableLink next;

        public T getCable() {
            return cable;
        }

        public CableLink getNext() {
            return next;
        }

        public void propagate() {
            if (next != null) {
                next.getCable().setCurrentInput(cable.getCurrentOutput());
                next.getCable().setVoltageInput(cable.getVoltageOutput());
            }
        }
    }

    public LinkedCircuit() {}

    public CableLink getStart() {
        return start;
    }

    public CableLink getEnd() {
        return end;
    }

    public void link(T cable) {
        CableLink next = new CableLink();
        next.cable = cable;

        if (start == null) {
            start = next;
        } else {
            end.next = next;
        }
        end = next;
    }

    public void setPowerSource(R powerSource) {
        this.powerSource = powerSource;
    }

    public R getPowerSource() {
        return powerSource;
    }

    public boolean isClosed() {
        return powerSource != null;
    }

    public double getTotalResistance() {
        double sum = 0.0;
        for (CableLink current = start; current != null; current = current.getNext()) {
            sum += current.getCable().getResistance();
        }
        return sum;
    }

    public String printStatusReport() {
        StringBuilder sb = new StringBuilder();
        for (CableLink current = start; current != null; current = current.getNext()) {
            Cable cable = current.getCable();
            if (cable.getClass().equals(Resistor.class)) {
                sb.append(cable.toString());
            }
        }
        return sb.toString();
    }
}

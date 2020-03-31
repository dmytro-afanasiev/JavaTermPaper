package objects.firstMacro;


import java.util.Objects;

public class Guitar implements Cloneable{
    private byte strings;
    private int cost;

    public byte getStrings() {
        return strings;
    }

    public void setStrings(byte strings) {
        this.strings = strings;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Guitar(byte strings, int cost) {
        this.strings = strings;
        this.cost = cost;
    }

    @Override
    public Guitar clone() throws CloneNotSupportedException {
        return (Guitar) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guitar guitar = (Guitar) o;
        return strings == guitar.strings &&
                cost == guitar.cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(strings, cost);
    }
}

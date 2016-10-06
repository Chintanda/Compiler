/*
 * a data structure to store a (non-terminal, terminal) pair
 */
public class key {
    int x = 0;
    int y = 0;

    public key(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals (final Object object) {
        if (!(object instanceof key)) return false;
        if (((key) object).x != x) return false;
        if (((key) object).y != y) return false;
        return true;
      }

    @Override
    public int hashCode() {
        return (x << 16) + y;
    }
}

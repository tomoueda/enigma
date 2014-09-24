package enigma;


/** Class that represents a reflector in the enigma.
 *  @author Tomo Ueda
 */
class Reflector extends Rotor {

    /** Constructor of Reflector.
        @param posit determines the position of the rotor
        @param num determines the type of reflector */
    public Reflector(int posit, int num) {
        super(posit, num);
        if (num != 8 && num != 9) {
            System.exit(1);
        }
        if (posit != 0) {
            System.exit(1);
        }
    }

    /** A constructor that takes in the Rotor number. The initial 
     *  position is set to zero or 'A' position. */
    public Reflector(int num) {
        super(num);
        if (num != 8 && num != 9) {
            System.exit(1);
        }
    }

    /** Returns a useless value; should never be called. */
    @Override
    public int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }

    /** Reflectors do not advance. */
    @Override
    public void advance() {
        throw new UnsupportedOperationException();
    }
}

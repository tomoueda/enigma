package enigma;

/** Class that represents a rotor in the enigma machine.
 *  @author Tomo Ueda
 */
public class Rotor {

    /** The names and definitions of the rotors and reflectors in M4.  The
     *  first string in each entry is the name of a rotor or reflector.  The
     *  second is a 26-character string whose first character is the mapping
     *  (when the rotor is at the 'A' setting), of 'A' in the right-to-left
     *  direction, whose second is that of 'B', etc.
     *
     *  The third entry, if present, is the inverse of the
     *  second---the left-to-right permutation of the rotor.  It is
     *  not present for reflectors.
     *
     *  The fourth entry, if present, gives the positions of the
     *  notches. These are the settings of the rotors just before the
     *  wheels advanced (wheels advance before a character is
     *  translated).  Other written accounts of the Enigma generally
     *  show instead the character settings just after a character is
     *  coded (e.g., 'R', rather than 'Q', or 'A' rather than 'Z').
     *  The entry is absent in rotors that do not advance. */

    public static final String[][] ROTOR_SPECS = {
        { "I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "UWYGADFPVZBECKMTHXSLRINQOJ",
          "Q" },
        { "II", "AJDKSIRUXBLHWTMCQGZNPYFVOE", "AJPCZWRLFBDKOTYUQGENHXMIVS",
          "E" },
        { "III", "BDFHJLCPRTXVZNYEIWGAKMUSQO", "TAGBPCSDQEUFVNZHYIXJWLRKOM",
          "V" },
        { "IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB", "HZWVARTNLGUPXQCEJMBSKDYOIF",
          "J" },
        { "V", "VZBRGITYUPSDNHLXAWMJQOFECK", "QCYLXWENFTZOSMVJUDKGIARPHB",
          "Z" },
        { "VI", "JPGVOUMFYQBENHZRDKASXLICTW", "SKXQLHCNWARVGMEBJPTYFDZUIO",
          "ZM" },
        { "VII", "NZJHGRCXMYSWBOUFAIVLPEKQDT", "QMGYVPEDRCWTIANUXFKZOSLHJB",
          "ZM" },
        { "VIII", "FKQHTLXOCBJSPDZRAMEWNIUYGV", "QJINSAYDVKBFRUHMCPLEWZTGXO",
          "ZM" },
        { "B", "YRUHQSLDPXNGOKMIEBFZCWVJAT" },
        { "C", "FVPJIAOYEDRZXWGCTKUQSBNMHL" }
    };

    /** This is the index to access the permutation ordering. **/
    public static final int PERM_INDEX = 1;

    /** This is the index for the inverse ordering. **/
    public static final int INV_INDEX = 2;

    /** This is the index for the notch letter. **/
    public static final int NOTCH_INDEX = 3;

    /** The length of the alphabet. */
    public static final int ALPHA_LENGTH = 26;

    /** 
     * POSIT determins the position of the rotor, where 0 - 25
     * is equivalent to A-Z. NUM determines the Rotor number
     * where 0-7 is equivalent to I-VIII, and 8 and 9 are
     * the reflectors B and C respectively. */
    public Rotor(int posit, int num) {
        position = posit;
        rotorNumber = num;
    }

    /** Same explanation as above except this constructor only takes
     * in NUM. The position starts at the position 'A'. */
    public Rotor(int num) {
        position = 0;
        rotorNumber = num;
    }

    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    public static char toLetter(int p) {
        return (char) (p + 'A');
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    public static int toIndex(char c) {
        return (int) (c - 'A');
    }

    /** Return my current rotational position as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    public int getPosition() {
        return position;
    }

    /** Set position of the Rotor to POSN.  */
    public void setPosition(int posn) {
        position = posn;
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    public int convertForward(int p) {
        p = (p + position) % ALPHA_LENGTH;
        char charNextPerm = ROTOR_SPECS[rotorNumber][PERM_INDEX].charAt(p);
        int nextPerm = (toIndex(charNextPerm) - position);
        if (nextPerm < 0) {
            nextPerm += ALPHA_LENGTH;
        }
        return nextPerm;
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    public int convertBackward(int e) {
        e = (e + position) % ALPHA_LENGTH;
        char charInvPerm = ROTOR_SPECS[rotorNumber][INV_INDEX].charAt(e);
        int invPerm = (toIndex(charInvPerm) - position);
        if (invPerm < 0) {
            invPerm += ALPHA_LENGTH;
        }
        return invPerm;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    public boolean atNotch() {
        if (rotorNumber < 5) {
            char notch = ROTOR_SPECS[rotorNumber][NOTCH_INDEX].charAt(0);
            return position == toIndex(notch);
        } else if (rotorNumber < 8) {
            char notch0 = ROTOR_SPECS[rotorNumber][NOTCH_INDEX].charAt(0);
            char notch1 = ROTOR_SPECS[rotorNumber][NOTCH_INDEX].charAt(1);
            return position == toIndex(notch0) || position == toIndex(notch1);
        }
        return false;
    }

    /** Advance me one position. */
    public void advance() {
        position = (position + 1) % ALPHA_LENGTH;
    }

    /** My current position (index 0..25, with 0 indicating that 'A'
     *  is showing).e.*/
    private int position;
    /** The type of Rotor we are using. */
    private int rotorNumber;

}

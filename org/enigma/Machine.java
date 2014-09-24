package enigma;


/** Class that represents a complete enigma machine.
 *  @author Tomo Ueda
 */
class Machine {

    /** The reflector that sits at the end of the machine. */
    private Reflector reflector;
    /** The left rotor, the last rotor the signals go through. */
    private Rotor rotorLeft;
    /** The rotor in the middle. */
    private Rotor rotorMid;
    /** The first rotor the signals go through. */
    private Rotor rotorRight;
    /** In order to start a new line of outputs. */
    private static int group3 = 5;

    /** Standardize the INPUT string. The function returns all letters
     *  upper-cased and all white spaces deleted. **/
    public static String standardize(String input) {
        input = input.toUpperCase();
        input = input.replace(" ", "");
        return input;
    }

    /** Set my rotors according to integers 0-9, where 0-7 corresponds 
     *  to Rotors I-VIII. 8 and 9 corresponds to B and C. */
    void setRotors(int reflect, int left, int middle, int right) {
        reflector = new Reflector(reflect);
        rotorLeft = new Rotor(left);
        rotorMid = new Rotor(middle);
        rotorRight = new Rotor(right);
    }

    /** Set my rotors to (from left to right), REFLECTION, LEFT,
     *  MIDDLE, and RIGHT.\ */
    void setRotors(Reflector reflection,
                   Rotor left, Rotor middle, Rotor right) {
        reflector = reflection;
        rotorLeft = left;
        rotorMid = middle;
        rotorRight = right;
    }

    /** Set the positions of my rotors according to SETTING, which
     *  must be a string of 4 upper-case letters. The first letter
     *  refers to the reflector position, and the rest to the rotor
     *  positions, left to right. */
    void setPositions(String setting) {
        reflector.setPosition(Rotor.toIndex(setting.charAt(0)));
        rotorLeft.setPosition(Rotor.toIndex(setting.charAt(1)));
        rotorMid.setPosition(Rotor.toIndex(setting.charAt(2)));
        rotorRight.setPosition(Rotor.toIndex(setting.charAt(3)));
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        msg = standardize(msg);
        String returnMessage = "";
        for (int a = 0; a < msg.length(); a++) {
            int encoded_char = Rotor.toIndex(msg.charAt(a));
            movePosition();
            encoded_char = rotorRight.convertForward(encoded_char);
            encoded_char = rotorMid.convertForward(encoded_char);
            encoded_char = rotorLeft.convertForward(encoded_char);
            encoded_char = reflector.convertForward(encoded_char);
            encoded_char = rotorLeft.convertBackward(encoded_char);
            encoded_char = rotorMid.convertBackward(encoded_char);
            encoded_char = rotorRight.convertBackward(encoded_char);
            char tLetter = Rotor.toLetter(encoded_char);
            returnMessage = returnMessage.concat(Character.toString(tLetter));
        }
        return returnMessage;
    }

    /** at the end of conversions I move all the positions. */
    public void movePosition() {
        if (rotorMid.atNotch()) {
            rotorLeft.advance();
            rotorMid.advance();
        } else if (rotorRight.atNotch()) {
            rotorMid.advance();
        }
        rotorRight.advance();
    }
    /** For testing purposes. Returns left Rotor position. */
    public int getLRotorPos() {
        return rotorLeft.getPosition();
    }
    /** For testing purposes. Returns right Rotor position.*/
    public int getRRotorPos() {
        return rotorRight.getPosition();
    }
    /** For Testing purpose. Returns middle Rotor position.*/
    public int getMRotorPos() {
        return rotorMid.getPosition();
    }
    /** For testing purposes. Returns Reflector Rotor position.*/
    public int getReflectPos() {
        return reflector.getPosition();
    }
}

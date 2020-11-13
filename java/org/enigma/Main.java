package enigma;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;


/** Enigma simulator.
 *  @author Tomo Ueda
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] unused) {
        Machine M = new Machine();
        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    configure(M, line);
                } else {
                    try {
                        printMessageLine(M.convert(standardize(line)));
                    } catch (NullPointerException e) {
                        System.exit(1);
                    }
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }

    /** Return true iff LINE is an Enigma configuration line. */
    static boolean isConfigurationLine(String line) {
        String rotors = "((I|II|III|IV|V|VI|VII|VIII) )";
        Pattern p = Pattern.compile("\\* [BC] " + rotors + "{3}+[A-Z]{4}+");
        Matcher m = p.matcher(line);
        if (m.matches()) {
            String[] uniqueRotor = line.split(" ");
            if (uniqueRotor[2].equals(uniqueRotor[3])
                || uniqueRotor[3].equals(uniqueRotor[4])
                || uniqueRotor[2].equals(uniqueRotor[4])) {
                return false;
            }
            return true;
        }
        return false;
    }

    /** Configure M according to the specification given on CONFIG,
     *  which must have the format specified in the assignment. */
    static void configure(Machine M, String config) {
        String[] configure = config.split(" ");
        String[] romanNumeral = {"I", "II", "III", "IV",
                                 "V", "VI", "VII", "VIII"};
        String[] reflectors = {"B", "C"};
        int reflect = Arrays.asList(reflectors).indexOf(configure[1]) + 8;
        int left = Arrays.asList(romanNumeral).indexOf(configure[2]);
        int mid = Arrays.asList(romanNumeral).indexOf(configure[3]);
        int right = Arrays.asList(romanNumeral).indexOf(configure[4]);
        M.setRotors(reflect, left, mid, right);
        M.setPositions(configure[5]);
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks.  It is an error if LINE contains
     *  characters other than letters and blanks. */
    static String standardize(String line) {
        line = line.toUpperCase();
        line = line.replace(" ", "");
        for (int a = 0; a < line.length(); a++) {
            if (!Character.isLetter(line.charAt(a))) {
                System.exit(1);
            }
        }
        return line;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    static void printMessageLine(String msg) {
        char[] msgChar = msg.toCharArray();
        int spaceCounter = 1;
        for (int i = 0; i < msgChar.length; i++) {
            if (spaceCounter != 0 && spaceCounter % 6 == 0) {
                System.out.print(" ");
                spaceCounter++;
            }
            System.out.print(msgChar[i]);
            spaceCounter++;
        }
        System.out.print("\n");
    }
}


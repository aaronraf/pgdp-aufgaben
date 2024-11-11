package pgdp;

public class HelperBook {

    /**
     * Converts an integer in [0; 25] to its corresponding uppper case alphabet character.
     *
     * @param i the integer to convert
     * @return the corresponding alphabet character as a String
     */
    public static String intToAlphabetCharacter(int i) {
        if (i >= 0 && i < 26) {
            return Character.toString((char) (i + 65));
        }
        else if (i < 0 && i > -27) {
            return Character.toString((char) (-i + 64)).toLowerCase();
        }
        else {
            return "?";
        }
    }

    public static void main(String[] args) {
        System.out.println(HelperBook.intToAlphabetCharacter(0));
    }
}

package io.PaySky.pages.utiles;
import java.util.Random;


public class RandomData {

    private static String email;
    private static String password;

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIALS = "@#$%&*!?";
    private static final String LETTERS_DIGITS = LOWER + DIGITS;
    private static final Random random = new Random();


    public static String generateRandomEmail() {
        StringBuilder sb = new StringBuilder();
        sb.append("test");
        for (int i = 0; i < 6; i++) {
            sb.append(LETTERS_DIGITS.charAt(random.nextInt(LETTERS_DIGITS.length())));
        }
        sb.append("@gmail.com");

        email = sb.toString();
        return email;
    }

    public static String generateRandomPassword() {
        StringBuilder sb = new StringBuilder();

        // guarantee one of each required character type
        sb.append(UPPER.charAt(random.nextInt(UPPER.length())));
        sb.append(LOWER.charAt(random.nextInt(LOWER.length())));
        sb.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        sb.append(SPECIALS.charAt(random.nextInt(SPECIALS.length())));

        String allChars = LOWER + UPPER + DIGITS + SPECIALS;
        while (sb.length() < 12) {
            sb.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        password = sb.toString();
        return password;
    }

    public static String getLastEmail()    { return email; }
    public static String getLastPassword() { return password; }

}
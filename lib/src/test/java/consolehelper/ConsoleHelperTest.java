package consolehelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.PropertyResourceBundle;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleHelperTest {

    private ByteArrayOutputStream output;

    /** Feeds the given lines to ConsoleHelper and captures its output. */
    private void input(String... lines) {
        String text = String.join("\n", lines) + "\n";
        ConsoleHelper.setInput(new ByteArrayInputStream(text.getBytes(Charset.defaultCharset())));
        output = new ByteArrayOutputStream();
        ConsoleHelper.setOutput(new PrintStream(output, true));
        ConsoleHelper.setLocale(Locale.ENGLISH);
    }

    private String output() {
        return output.toString();
    }

    @AfterEach
    void restore() {
        ConsoleHelper.setInput(System.in);
        ConsoleHelper.setOutput(System.out);
        ConsoleHelper.setLocale(Locale.getDefault());
    }

    @Test
    void readIntRetriesUntilValid() {
        input("abc", "", " 42 ");
        assertEquals(42, ConsoleHelper.readInt("n? "));
        assertEquals(2, output().split("Error").length - 1);
    }

    @Test
    void readIntInIntervalRejectsOutOfRange() {
        input("0", "11", "10");
        assertEquals(10, ConsoleHelper.readInt("n? ", 1, 10));
        assertTrue(output().contains("between 1 and 10"));
    }

    @Test
    void readIntInEmptyIntervalFails() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleHelper.readInt("n? ", 5, 1));
    }

    @Test
    void readDoubleAcceptsCommaAndDot() {
        input("1,5", "2.25", "x", "NaN", "3");
        assertEquals(1.5, ConsoleHelper.readDouble("x? "));
        assertEquals(2.25, ConsoleHelper.readDouble("x? "));
        assertEquals(3.0, ConsoleHelper.readDouble("x? "));
    }

    @Test
    void readLongReadsLargeValues() {
        input("10000000000");
        assertEquals(10_000_000_000L, ConsoleHelper.readLong("n? "));
    }

    @Test
    void readStringRejectsEmptyAnswers() {
        input("   ", "  Ada Lovelace  ");
        assertEquals("Ada Lovelace", ConsoleHelper.readString("name? "));
    }

    @Test
    void readLineKeepsSpaces() {
        input("  hello  ");
        assertEquals("  hello  ", ConsoleHelper.readLine("> "));
    }

    @Test
    void readCharRequiresOneCharacter() {
        input("ab", " x ");
        assertEquals('x', ConsoleHelper.readChar("c? "));
    }

    @Test
    void readBooleanAcceptsSeveralForms() {
        input("Y", "no", "maybe", "si", "N");
        assertTrue(ConsoleHelper.readBoolean("? "));
        assertFalse(ConsoleHelper.readBoolean("? "));
        assertTrue(ConsoleHelper.readBoolean("? "));
        assertFalse(ConsoleHelper.readBoolean("? "));
    }

    @Test
    void readIntArrayAsksForEachElement() {
        input("3", "1", "4");
        assertArrayEquals(new int[] {3, 1, 4}, ConsoleHelper.readIntArray("a", 3));
        assertTrue(output().contains("a[0] = "));
        assertTrue(output().contains("a[2] = "));
    }

    @Test
    void readDoubleArrayAsksForEachElement() {
        input("0,5", "2");
        assertArrayEquals(new double[] {0.5, 2.0}, ConsoleHelper.readDoubleArray("v", 2));
    }

    @Test
    void readChoiceShowsOptionsAndValidates() {
        input("3", "2");
        assertEquals(2, ConsoleHelper.readChoice("Operation?", "Sum", "Product"));
        assertTrue(output().contains("1) Sum"));
        assertTrue(output().contains("2) Product"));
    }

    @Test
    void endOfInputThrows() {
        input();
        ConsoleHelper.readLine("");
        assertThrows(NoSuchElementException.class, () -> ConsoleHelper.readInt("n? "));
    }

    @Test
    void outputMethods() {
        input();
        ConsoleHelper.printTitle("Menu");
        ConsoleHelper.printArray(new int[] {1, 2});
        ConsoleHelper.printf("%d-%s%n", 7, "x");
        String nl = System.lineSeparator();
        assertEquals("Menu" + nl + "====" + nl + "[1, 2]" + nl + "7-x" + nl, output());
    }

    @Test
    void messagesFollowTheLocale() {
        input("abc", "0", "7");
        ConsoleHelper.setLocale(Locale.ITALIAN);
        assertEquals(7, ConsoleHelper.readInt("n? ", 1, 10));
        assertTrue(output().contains("Errore: inserire un numero intero."));
        assertTrue(output().contains("compreso tra 1 e 10"));
    }

    @Test
    void italianMessagesWithApostrophe() {
        input();
        ConsoleHelper.setLocale(Locale.ITALY);
        ConsoleHelper.readLine("");
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> ConsoleHelper.readLine(""));
        assertEquals("Fine dell'input.", e.getMessage());
    }

    @Test
    void missingTranslationFallsBackToEnglish() {
        input("x", "1");
        ConsoleHelper.setLocale(Locale.JAPANESE);
        ConsoleHelper.readInt("n? ");
        assertTrue(output().contains("Error: please enter an integer number."));
    }

    @Test
    void largeNumbersAreNotGrouped() {
        input("0", "5");
        ConsoleHelper.setLocale(Locale.ITALIAN);
        ConsoleHelper.readInt("n? ", 1, 100000);
        assertTrue(output().contains("tra 1 e 100000"));
    }

    @Test
    void everyTranslationHasAllTheKeys() throws IOException {
        assertEquals(keysOf("messages.properties"), keysOf("messages_it.properties"));
    }

    /** Returns the keys defined in a message file, without fallback to other files. */
    private Set<String> keysOf(String file) throws IOException {
        try (InputStream stream = ConsoleHelper.class.getResourceAsStream(file)) {
            assertNotNull(stream, file + " not found");
            return new PropertyResourceBundle(stream).keySet();
        }
    }
}

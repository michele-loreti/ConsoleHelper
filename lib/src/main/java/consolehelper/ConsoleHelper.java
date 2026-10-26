package consolehelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * A collection of static utility methods that simplify reading from and
 * writing to the console.
 *
 * <p>The class is meant for students who are writing their first programs:
 * it hides the details of {@link java.util.Scanner} and of the standard
 * streams, so that a program can focus on the algorithm. For instance:</p>
 *
 * <pre>{@code
 * int a = ConsoleHelper.readInt("First number: ");
 * int b = ConsoleHelper.readInt("Second number: ");
 * ConsoleHelper.println("The sum is " + (a + b));
 * }</pre>
 *
 * <p>All the reading methods follow the same rules:</p>
 * <ul>
 *   <li>the input is read <em>one line at a time</em>: whatever the user
 *       types before pressing Enter is a single answer;</li>
 *   <li>spaces at the beginning and at the end of the answer are ignored
 *       (except in {@link #readLine(String)});</li>
 *   <li>when the answer is not valid (for instance, a word where a number is
 *       expected), an error message is printed and the question is asked
 *       again, until a valid answer is given;</li>
 *   <li>if the input ends (for instance, because it comes from a file that
 *       has no more lines), a {@link NoSuchElementException} is thrown.</li>
 * </ul>
 *
 * <p>Decimal numbers can be written either with a dot ({@code 1.5}) or with
 * a comma ({@code 1,5}).</p>
 *
 * <p>The messages printed by the class (errors, the prompt of
 * {@link #readChoice(String, String...)}, ...) are in the language of the
 * system where the program runs, when a translation is available; otherwise
 * they are in English. The language can also be chosen explicitly with
 * {@link #setLocale(Locale)}. Translations are stored in the files
 * {@code consolehelper/messages_xx.properties}, where {@code xx} is the
 * language code (for instance {@code it} for Italian).</p>
 *
 * <p>The class cannot be instantiated: all its methods are {@code static}.</p>
 */
public final class ConsoleHelper {

    /** Base name of the resource bundle with the translated messages. */
    private static final String MESSAGES = "consolehelper.messages";

    /** Default length of the line printed by {@link #printSeparator()}. */
    private static final int SEPARATOR_LENGTH = 40;

    /** The source of the input lines. */
    private static BufferedReader in = readerOf(System.in);

    /** The destination of the output. */
    private static PrintStream out = System.out;

    /** The messages in the current language. */
    private static ResourceBundle messages = bundleOf(Locale.getDefault());

    /** No instances: this class only contains static methods. */
    private ConsoleHelper() {
    }

    // ------------------------------------------------------------------
    // Output
    // ------------------------------------------------------------------

    /**
     * Prints a text without moving to a new line.
     *
     * @param text the text to print
     */
    public static void print(String text) {
        out.print(text);
        out.flush();
    }

    /**
     * Prints a text and moves to a new line.
     *
     * @param text the text to print
     */
    public static void println(String text) {
        out.println(text);
    }

    /**
     * Moves to a new line (prints an empty line).
     */
    public static void println() {
        out.println();
    }

    /**
     * Prints an integer number of type {@code byte} without moving to a new line.
     *
     * @param value the value to print
     */
    public static void print(byte value) {
        print("" + value);
    }

    /**
     * Prints an integer number of type {@code byte} and moves to a new line.
     *
     * @param value the value to print
     */
    public static void println(byte value) {
        println("" + value);
    }

    /**
     * Prints an integer number of type {@code short} without moving to a new line.
     *
     * @param value the value to print
     */
    public static void print(short value) {
        print("" + value);
    }

    /**
     * Prints an integer number of type {@code short} and moves to a new line.
     *
     * @param value the value to print
     */
    public static void println(short value) {
        println("" + value);
    }

    /**
     * Prints an integer number without moving to a new line.
     *
     * @param value the value to print
     */
    public static void print(int value) {
        print("" + value);
    }

    /**
     * Prints an integer number and moves to a new line.
     *
     * @param value the value to print
     */
    public static void println(int value) {
        println("" + value);
    }

    /**
     * Prints a character without moving to a new line.
     *
     * @param value the value to print
     */
    public static void print(char value) {
        print("" + value);
    }

    /**
     * Prints a character and moves to a new line.
     *
     * @param value the value to print
     */
    public static void println(char value) {
        println("" + value);
    }

    /**
     * Prints an integer number of type {@code long} without moving to a new line.
     *
     * @param value the value to print
     */
    public static void print(long value) {
        print("" + value);
    }

    /**
     * Prints an integer number of type {@code long} and moves to a new line.
     *
     * @param value the value to print
     */
    public static void println(long value) {
        println("" + value);
    }

    /**
     * Prints a decimal number of type {@code float} without moving to a new line.
     *
     * @param value the value to print
     */
    public static void print(float value) {
        print("" + value);
    }

    /**
     * Prints a decimal number of type {@code float} and moves to a new line.
     *
     * @param value the value to print
     */
    public static void println(float value) {
        println("" + value);
    }

    /**
     * Prints a decimal number without moving to a new line.
     *
     * @param value the value to print
     */
    public static void print(double value) {
        print("" + value);
    }

    /**
     * Prints a decimal number and moves to a new line.
     *
     * @param value the value to print
     */
    public static void println(double value) {
        println("" + value);
    }

    /**
     * Prints a formatted text, as {@link PrintStream#printf(String, Object...)}.
     * For instance, {@code printf("%d + %d = %d%n", 2, 3, 5)} prints
     * {@code 2 + 3 = 5} and moves to a new line.
     *
     * @param format the format string
     * @param args   the values that replace the placeholders in {@code format}
     */
    public static void printf(String format, Object... args) {
        out.printf(format, args);
        out.flush();
    }

    /**
     * Prints a line made of {@code -} characters, useful to separate parts of
     * the output.
     */
    public static void printSeparator() {
        println("-".repeat(SEPARATOR_LENGTH));
    }

    /**
     * Prints a title, underlined with {@code =} characters.
     *
     * @param title the title to print
     */
    public static void printTitle(String title) {
        println(title);
        println("=".repeat(title.length()));
    }

    /**
     * Prints the elements of an array of integers on one line, between square
     * brackets and separated by commas, for instance {@code [1, 2, 3]}.
     *
     * @param values the array to print
     */
    public static void printArray(int[] values) {
        println(Arrays.toString(values));
    }

    /**
     * Prints the elements of an array of decimal numbers on one line, between
     * square brackets and separated by commas, for instance {@code [1.5, 2.0]}.
     *
     * @param values the array to print
     */
    public static void printArray(double[] values) {
        println(Arrays.toString(values));
    }

    /**
     * Prints the elements of an array of strings on one line, between square
     * brackets and separated by commas, for instance {@code [red, green]}.
     *
     * @param values the array to print
     */
    public static void printArray(String[] values) {
        println(Arrays.toString(values));
    }

    // ------------------------------------------------------------------
    // Input: text
    // ------------------------------------------------------------------

    /**
     * Prints a prompt and reads a whole line, exactly as it is typed
     * (spaces included). The answer can be empty.
     *
     * @param prompt the message shown to the user
     * @return the line typed by the user, without the final new line
     * @throws NoSuchElementException if there is no more input
     */
    public static String readLine(String prompt) {
        print(prompt);
        try {
            String line = in.readLine();
            if (line == null) {
                throw new NoSuchElementException(message("error.endOfInput"));
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Prints a prompt and reads a non-empty text. Spaces at the beginning and
     * at the end are removed; if nothing remains, the question is asked again.
     *
     * @param prompt the message shown to the user
     * @return the text typed by the user, never empty
     * @throws NoSuchElementException if there is no more input
     */
    public static String readString(String prompt) {
        while (true) {
            String answer = readLine(prompt).strip();
            if (!answer.isEmpty()) {
                return answer;
            }
            println(message("error.emptyAnswer"));
        }
    }

    /**
     * Prints a prompt and reads a single character (spaces at the beginning
     * and at the end are ignored).
     *
     * @param prompt the message shown to the user
     * @return the character typed by the user
     * @throws NoSuchElementException if there is no more input
     */
    public static char readChar(String prompt) {
        while (true) {
            String answer = readLine(prompt).strip();
            if (answer.length() == 1) {
                return answer.charAt(0);
            }
            println(message("error.notAChar"));
        }
    }

    /**
     * Prints a prompt and reads a yes/no answer. Accepted answers, ignoring
     * case, are {@code y}, {@code yes}, {@code s}, {@code si}, {@code true}
     * for yes and {@code n}, {@code no}, {@code false} for no.
     *
     * @param prompt the message shown to the user
     * @return {@code true} if the answer is yes, {@code false} if it is no
     * @throws NoSuchElementException if there is no more input
     */
    public static boolean readBoolean(String prompt) {
        while (true) {
            String answer = readLine(prompt).strip().toLowerCase();
            switch (answer) {
                case "y", "yes", "s", "si", "sì", "true":
                    return true;
                case "n", "no", "false":
                    return false;
                default:
                    println(message("error.notYesNo"));
            }
        }
    }

    // ------------------------------------------------------------------
    // Input: numbers
    // ------------------------------------------------------------------

    /**
     * Prints a prompt and reads an integer number of type {@code byte}, that
     * is between {@value Byte#MIN_VALUE} and {@value Byte#MAX_VALUE}.
     *
     * @param prompt the message shown to the user
     * @return the number typed by the user
     * @throws NoSuchElementException if there is no more input
     */
    public static byte readByte(String prompt) {
        return (byte) readLong(prompt, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    /**
     * Prints a prompt and reads an integer number of type {@code byte} between
     * {@code min} and {@code max}, both included. The bounds are {@code int}s,
     * so that a call such as {@code readByte("Age? ", 0, 120)} compiles without
     * casts, but they must be valid {@code byte} values.
     *
     * @param prompt the message shown to the user
     * @param min    the smallest accepted value
     * @param max    the largest accepted value
     * @return the number typed by the user, such that {@code min <= n <= max}
     * @throws IllegalArgumentException if {@code min > max}, or if a bound is
     *                                  not between {@value Byte#MIN_VALUE} and
     *                                  {@value Byte#MAX_VALUE}
     * @throws NoSuchElementException   if there is no more input
     */
    public static byte readByte(String prompt, int min, int max) {
        checkInterval(min, max, Byte.MIN_VALUE, Byte.MAX_VALUE);
        return (byte) readLong(prompt, min, max);
    }

    /**
     * Prints a prompt and reads an integer number of type {@code short}, that
     * is between {@value Short#MIN_VALUE} and {@value Short#MAX_VALUE}.
     *
     * @param prompt the message shown to the user
     * @return the number typed by the user
     * @throws NoSuchElementException if there is no more input
     */
    public static short readShort(String prompt) {
        return (short) readLong(prompt, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    /**
     * Prints a prompt and reads an integer number of type {@code short}
     * between {@code min} and {@code max}, both included. As in
     * {@link #readByte(String, int, int)}, the bounds are {@code int}s but
     * they must be valid {@code short} values.
     *
     * @param prompt the message shown to the user
     * @param min    the smallest accepted value
     * @param max    the largest accepted value
     * @return the number typed by the user, such that {@code min <= n <= max}
     * @throws IllegalArgumentException if {@code min > max}, or if a bound is
     *                                  not between {@value Short#MIN_VALUE} and
     *                                  {@value Short#MAX_VALUE}
     * @throws NoSuchElementException   if there is no more input
     */
    public static short readShort(String prompt, int min, int max) {
        checkInterval(min, max, Short.MIN_VALUE, Short.MAX_VALUE);
        return (short) readLong(prompt, min, max);
    }

    /**
     * Prints a prompt and reads an integer number of type {@code int}.
     *
     * @param prompt the message shown to the user
     * @return the number typed by the user
     * @throws NoSuchElementException if there is no more input
     */
    public static int readInt(String prompt) {
        while (true) {
            String answer = readLine(prompt).strip();
            try {
                return Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                println(message("error.notAnInteger"));
            }
        }
    }

    /**
     * Prints a prompt and reads an integer number between {@code min} and
     * {@code max}, both included.
     *
     * @param prompt the message shown to the user
     * @param min    the smallest accepted value
     * @param max    the largest accepted value
     * @return the number typed by the user, such that {@code min <= n <= max}
     * @throws IllegalArgumentException if {@code min > max}
     * @throws NoSuchElementException   if there is no more input
     */
    public static int readInt(String prompt, int min, int max) {
        checkInterval(min, max);
        while (true) {
            int value = readInt(prompt);
            if (min <= value && value <= max) {
                return value;
            }
            println(message("error.outOfRange", min, max));
        }
    }

    /**
     * Prints a prompt and reads an integer number of type {@code long}, for
     * values too large for an {@code int}.
     *
     * @param prompt the message shown to the user
     * @return the number typed by the user
     * @throws NoSuchElementException if there is no more input
     */
    public static long readLong(String prompt) {
        while (true) {
            String answer = readLine(prompt).strip();
            try {
                return Long.parseLong(answer);
            } catch (NumberFormatException e) {
                println(message("error.notAnInteger"));
            }
        }
    }

    /**
     * Prints a prompt and reads an integer number of type {@code long} between
     * {@code min} and {@code max}, both included. Used by
     * {@link #readByte(String)} and {@link #readShort(String)}, so that a value
     * too large for the type gets the out-of-range message.
     */
    private static long readLong(String prompt, long min, long max) {
        while (true) {
            long value = readLong(prompt);
            if (min <= value && value <= max) {
                return value;
            }
            println(message("error.outOfRange", min, max));
        }
    }

    /**
     * Prints a prompt and reads a decimal number of type {@code float}. Both
     * {@code 1.5} and {@code 1,5} are accepted.
     *
     * @param prompt the message shown to the user
     * @return the number typed by the user
     * @throws NoSuchElementException if there is no more input
     */
    public static float readFloat(String prompt) {
        while (true) {
            String answer = readLine(prompt).strip().replace(',', '.');
            try {
                float value = Float.parseFloat(answer);
                if (Float.isFinite(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // the error message is printed below
            }
            println(message("error.notANumber"));
        }
    }

    /**
     * Prints a prompt and reads a decimal number of type {@code float} between
     * {@code min} and {@code max}, both included.
     *
     * @param prompt the message shown to the user
     * @param min    the smallest accepted value
     * @param max    the largest accepted value
     * @return the number typed by the user, such that {@code min <= x <= max}
     * @throws IllegalArgumentException if {@code min > max}
     * @throws NoSuchElementException   if there is no more input
     */
    public static float readFloat(String prompt, float min, float max) {
        if (!(min <= max)) {
            throw new IllegalArgumentException(message("error.emptyInterval", min, max));
        }
        while (true) {
            float value = readFloat(prompt);
            if (min <= value && value <= max) {
                return value;
            }
            println(message("error.outOfRange", min, max));
        }
    }

    /**
     * Prints a prompt and reads a decimal number. Both {@code 1.5} and
     * {@code 1,5} are accepted.
     *
     * @param prompt the message shown to the user
     * @return the number typed by the user
     * @throws NoSuchElementException if there is no more input
     */
    public static double readDouble(String prompt) {
        while (true) {
            String answer = readLine(prompt).strip().replace(',', '.');
            try {
                double value = Double.parseDouble(answer);
                if (Double.isFinite(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // the error message is printed below
            }
            println(message("error.notANumber"));
        }
    }

    /**
     * Prints a prompt and reads a decimal number between {@code min} and
     * {@code max}, both included.
     *
     * @param prompt the message shown to the user
     * @param min    the smallest accepted value
     * @param max    the largest accepted value
     * @return the number typed by the user, such that {@code min <= x <= max}
     * @throws IllegalArgumentException if {@code min > max}
     * @throws NoSuchElementException   if there is no more input
     */
    public static double readDouble(String prompt, double min, double max) {
        if (!(min <= max)) {
            throw new IllegalArgumentException(message("error.emptyInterval", min, max));
        }
        while (true) {
            double value = readDouble(prompt);
            if (min <= value && value <= max) {
                return value;
            }
            println(message("error.outOfRange", min, max));
        }
    }

    // ------------------------------------------------------------------
    // Input: arrays and menus
    // ------------------------------------------------------------------

    /**
     * Reads {@code n} integer numbers, asking for them one at a time.
     * The prompt of each question is {@code prompt} followed by the position
     * of the element, starting from 0: for instance {@code "a[0] = "},
     * {@code "a[1] = "}, ... when {@code prompt} is {@code "a"}.
     *
     * @param prompt the name shown to the user before each position
     * @param n      how many numbers to read
     * @return a new array with the {@code n} numbers typed by the user
     * @throws IllegalArgumentException if {@code n} is negative
     * @throws NoSuchElementException   if there is no more input
     */
    public static int[] readIntArray(String prompt, int n) {
        checkLength(n);
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = readInt(prompt + "[" + i + "] = ");
        }
        return values;
    }

    /**
     * Reads {@code n} decimal numbers, asking for them one at a time, as
     * {@link #readIntArray(String, int)} does.
     *
     * @param prompt the name shown to the user before each position
     * @param n      how many numbers to read
     * @return a new array with the {@code n} numbers typed by the user
     * @throws IllegalArgumentException if {@code n} is negative
     * @throws NoSuchElementException   if there is no more input
     */
    public static double[] readDoubleArray(String prompt, int n) {
        checkLength(n);
        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = readDouble(prompt + "[" + i + "] = ");
        }
        return values;
    }

    /**
     * Shows a numbered list of options and reads the choice of the user.
     * For instance, {@code readChoice("Operation?", "Sum", "Product")} prints
     *
     * <pre>
     * Operation?
     *   1) Sum
     *   2) Product
     * Choice:
     * </pre>
     *
     * and returns {@code 1} or {@code 2}.
     *
     * @param question the question shown above the options
     * @param options  the options, at least one
     * @return the number of the chosen option, between 1 and {@code options.length}
     * @throws IllegalArgumentException if no option is given
     * @throws NoSuchElementException   if there is no more input
     */
    public static int readChoice(String question, String... options) {
        if (options.length == 0) {
            throw new IllegalArgumentException(message("error.noOptions"));
        }
        println(question);
        for (int i = 0; i < options.length; i++) {
            println("  " + (i + 1) + ") " + options[i]);
        }
        return readInt(message("prompt.choice"), 1, options.length);
    }

    /**
     * Prints a message and waits until the user presses Enter.
     *
     * @throws NoSuchElementException if there is no more input
     */
    public static void pressEnterToContinue() {
        readLine(message("prompt.pressEnter"));
    }

    // ------------------------------------------------------------------
    // Redirection (useful for automatic tests)
    // ------------------------------------------------------------------

    /**
     * Changes the source of the input, which is {@link System#in} by default.
     * This method is useful to test a program by providing its input from a
     * string or a file instead of typing it.
     *
     * @param input the new source of the input
     */
    public static void setInput(InputStream input) {
        in = readerOf(input);
    }

    /**
     * Changes the language of the messages printed by this class. By default
     * it is the language of the system ({@link Locale#getDefault()}). If no
     * translation is available for {@code locale}, English is used.
     * For instance, {@code setLocale(Locale.ITALIAN)} switches to Italian.
     *
     * @param locale the new language
     */
    public static void setLocale(Locale locale) {
        messages = bundleOf(locale);
    }

    /**
     * Changes the destination of the output, which is {@link System#out} by
     * default.
     *
     * @param output the new destination of the output
     */
    public static void setOutput(PrintStream output) {
        out = output;
    }

    // ------------------------------------------------------------------
    // Private helpers
    // ------------------------------------------------------------------

    /** Wraps a stream in a reader that uses the encoding of the console. */
    private static BufferedReader readerOf(InputStream input) {
        Charset charset = System.console() != null ? System.console().charset() : Charset.defaultCharset();
        return new BufferedReader(new InputStreamReader(input, charset));
    }

    /** Loads the messages for {@code locale}, falling back to English. */
    private static ResourceBundle bundleOf(Locale locale) {
        return ResourceBundle.getBundle(MESSAGES, locale,
                ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));
    }

    /**
     * Returns the message with the given key in the current language, where
     * the placeholders {@code {0}}, {@code {1}}, ... are replaced by
     * {@code args}. Numbers are inserted as they are, without grouping
     * separators.
     */
    private static String message(String key, Object... args) {
        Object[] texts = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            texts[i] = String.valueOf(args[i]);
        }
        return new MessageFormat(messages.getString(key), messages.getLocale()).format(texts);
    }

    /** Checks that the interval {@code [min, max]} is not empty. */
    private static void checkInterval(long min, long max) {
        if (min > max) {
            throw new IllegalArgumentException(message("error.emptyInterval", min, max));
        }
    }

    /**
     * Checks that the interval {@code [min, max]} is not empty and that both
     * bounds are between {@code lowest} and {@code highest}.
     */
    private static void checkInterval(long min, long max, long lowest, long highest) {
        checkInterval(min, max);
        if (min < lowest || max > highest) {
            throw new IllegalArgumentException(message("error.boundsOutOfType", min, max, lowest, highest));
        }
    }

    /** Checks that {@code n} is a valid array length. */
    private static void checkLength(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(message("error.negativeLength", n));
        }
    }
}

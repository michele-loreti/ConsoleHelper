# ConsoleHelper

Simple Java API for input/output console applications, designed for students
of an introductory course in Computer Programming.

`consolehelper.ConsoleHelper` is a class of static methods that hides the
details of `Scanner` and of the standard streams, so that a first program can
focus on the algorithm rather than on how to read a number:

```java
import consolehelper.ConsoleHelper;

public class Sum {
    public static void main(String[] args) {
        int a = ConsoleHelper.readInt("First number: ");
        int b = ConsoleHelper.readInt("Second number: ");
        ConsoleHelper.println("The sum is " + (a + b));
    }
}
```

## Download

Download the latest version of the library:
**[consolehelper.jar](https://github.com/michele-loreti/ConsoleHelper/releases/latest/download/consolehelper.jar)**
(Java 17 or later is required).

Previous versions are available on the
[Releases](https://github.com/michele-loreti/ConsoleHelper/releases) page.
See [Using the library in a program](#using-the-library-in-a-program) for how
to compile and run a program with it.

## How input works

All the reading methods follow the same rules:

- **One answer per line.** Whatever the user types before pressing Enter is a
  single answer. This avoids the classic `nextInt()` / `nextLine()` pitfall of
  `Scanner`.
- **Spaces around the answer are ignored**, except in `readLine`, which returns
  the line exactly as typed.
- **Invalid answers are asked again.** If the user types a word where a number
  is expected, or a number outside the allowed range, an error message is
  printed and the question is repeated until the answer is valid. Programs
  therefore never crash on wrong input, and students do not need exceptions
  to write robust programs.
- **Decimal numbers** can be written with either a dot (`1.5`) or a comma
  (`1,5`).
- **End of input** (for instance, when the input is redirected from a file
  with no more lines) throws a `NoSuchElementException`.

A sample session (wrong answers included):

```
First: three
Error: please enter an integer number.
First: 3
Operation?
  1) Sum
  2) Product
Choice: 5
Error: the number must be between 1 and 2.
Choice: 1
```

## API

### Output

| Method | Description |
|---|---|
| `print(String text)` | Prints a text without a new line. |
| `println(String text)`, `println()` | Prints a text (or nothing) and moves to a new line. |
| `printf(String format, Object... args)` | Prints a formatted text, as `System.out.printf`. |
| `printTitle(String title)` | Prints a title underlined with `=`. |
| `printSeparator()` | Prints a line of `-`. |
| `printArray(int[])`, `printArray(double[])`, `printArray(String[])` | Prints an array as `[1, 2, 3]`. |

### Input

| Method | Returns |
|---|---|
| `readLine(String prompt)` | The whole line, as typed (it may be empty). |
| `readString(String prompt)` | A non-empty text, without surrounding spaces. |
| `readChar(String prompt)` | A single character. |
| `readBoolean(String prompt)` | `true` for `y`, `yes`, `s`, `si`, `sì`, `true`; `false` for `n`, `no`, `false` (case is ignored). |
| `readByte(String prompt)` | A `byte` (between -128 and 127). |
| `readByte(String prompt, int min, int max)` | A `byte` between `min` and `max`, both included (the bounds must be valid `byte` values). |
| `readShort(String prompt)` | A `short` (between -32768 and 32767). |
| `readShort(String prompt, int min, int max)` | A `short` between `min` and `max`, both included (the bounds must be valid `short` values). |
| `readInt(String prompt)` | An `int`. |
| `readInt(String prompt, int min, int max)` | An `int` between `min` and `max`, both included. |
| `readLong(String prompt)` | A `long`. |
| `readFloat(String prompt)` | A `float`. |
| `readFloat(String prompt, float min, float max)` | A `float` between `min` and `max`, both included. |
| `readDouble(String prompt)` | A `double`. |
| `readDouble(String prompt, double min, double max)` | A `double` between `min` and `max`, both included. |
| `readIntArray(String name, int n)` | An array of `n` integers, asked one at a time as `name[0] = `, `name[1] = `, ... |
| `readDoubleArray(String name, int n)` | An array of `n` decimal numbers, asked as above. |
| `readChoice(String question, String... options)` | Shows a numbered menu and returns the number of the chosen option (from 1). |
| `pressEnterToContinue()` | Waits until the user presses Enter. |

### Configuration

| Method | Description |
|---|---|
| `setLocale(Locale locale)` | Changes the language of the messages (default: the system language). |
| `setInput(InputStream input)` | Reads from `input` instead of `System.in`. |
| `setOutput(PrintStream output)` | Writes to `output` instead of `System.out`. |

The complete documentation is in the Javadoc of the class
(`./gradlew javadoc`, output in `lib/build/docs/javadoc`).

## Example: a menu-driven program

```java
import consolehelper.ConsoleHelper;

public class Calculator {
    public static void main(String[] args) {
        ConsoleHelper.printTitle("Calculator");
        boolean again = true;
        while (again) {
            double x = ConsoleHelper.readDouble("x = ");
            double y = ConsoleHelper.readDouble("y = ");
            int op = ConsoleHelper.readChoice("Operation?", "Sum", "Product");
            double result = (op == 1) ? x + y : x * y;
            ConsoleHelper.printf("Result: %.2f%n", result);
            again = ConsoleHelper.readBoolean("Again? ");
        }
    }
}
```

## Languages

The messages printed by `ConsoleHelper` (errors, the `Choice:` prompt, the
messages of exceptions) are shown in the language of the system where the
program runs, if a translation is available, and in English otherwise.
Available languages: English, Italian.

The texts written by the program itself (prompts, menu options) are not
translated.

To choose the language explicitly:

```java
ConsoleHelper.setLocale(Locale.ITALIAN);
```

To add a language, copy
`lib/src/main/resources/consolehelper/messages.properties` to
`messages_xx.properties` in the same folder, where `xx` is the language code
(for instance `messages_fr.properties`), and translate the values. The files
are read as UTF-8; values are `MessageFormat` patterns, so `{0}`, `{1}` are
replaced by the arguments and an apostrophe must be written twice (`''`).

## Building

Requirements: JDK 17 (Gradle downloads it automatically if missing).

```sh
./gradlew build      # compiles, runs the tests and builds the jar
./gradlew test       # runs the tests only
```

The jar is created in `lib/build/libs/consolehelper.jar`.

## Using the library in a program

### From the command line

Compile and run a program with the jar on the classpath (on Windows, use `;`
instead of `:` as separator):

```sh
javac -cp consolehelper.jar Sum.java
java -cp consolehelper.jar:. Sum
```

### In IntelliJ IDEA

These steps are for a project created with **File | New | Project...**,
choosing *Java* and the build system *IntelliJ*. For Gradle or Maven projects,
see below.

1. **Copy the jar into the project.** Create a folder named `lib` in the root
   of the project (right-click the project name in the *Project* tool window,
   then **New | Directory**) and copy `consolehelper.jar` into it, for
   instance by dragging it from the file manager onto the folder.
2. **Add the jar as a library.** Right-click `consolehelper.jar` in the
   *Project* tool window and choose **Add as Library...**. In the dialog,
   leave the default values (level *Project Library*, added to your module)
   and click **OK**.
3. **Use the class.** In your Java files, add the import

   ```java
   import consolehelper.ConsoleHelper;
   ```

   and run the program as usual (the green *Run* button next to `main`).
   The input is typed in the *Run* tool window at the bottom of the screen.

If **Add as Library...** is not in the menu, the same can be done from
**File | Project Structure... | Modules**: select your module, open the
*Dependencies* tab, click **+**, choose **JARs or Directories...** and select
`lib/consolehelper.jar`.

To check that everything works, expand *External Libraries* in the *Project*
tool window: `consolehelper` should be listed. If the `import` is shown in
red, the jar has not been added to the module.

### In a Gradle or Maven project

In a Gradle or Maven project, dependencies must be declared in the build file:
libraries added with **Add as Library...** are removed when IntelliJ reloads
the project. Copy the jar into a `lib` folder of the project and add it to
the build file:

- **Gradle** (`build.gradle.kts`):

  ```kotlin
  dependencies {
      implementation(files("lib/consolehelper.jar"))
  }
  ```

- **Maven**: install the jar in your local repository once

  ```sh
  mvn install:install-file -Dfile=lib/consolehelper.jar \
      -DgroupId=consolehelper -DartifactId=consolehelper \
      -Dversion=1.0.1 -Dpackaging=jar
  ```

  and add to `pom.xml`:

  ```xml
  <dependency>
      <groupId>consolehelper</groupId>
      <artifactId>consolehelper</artifactId>
      <version>1.0.1</version>
  </dependency>
  ```

Then reload the project in IntelliJ (the *Load Gradle/Maven Changes* button
that appears in the editor).

## Testing programs that use ConsoleHelper

`setInput` and `setOutput` make it possible to run a program on a given input
and check what it prints, for instance in a JUnit test:

```java
ConsoleHelper.setInput(new ByteArrayInputStream("3\n4\n".getBytes()));
ByteArrayOutputStream output = new ByteArrayOutputStream();
ConsoleHelper.setOutput(new PrintStream(output, true));

Sum.main(new String[0]);

assertTrue(output.toString().contains("The sum is 7"));
```

## License

MIT, see [LICENSE](LICENSE).

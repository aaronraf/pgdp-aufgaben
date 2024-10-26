package pgdp;

/**
 * This class provides convenience methods to make the barrier of entry into
 * programming easier.
 * <p>
 * <i>Diese Klasse stellt simple Methoden zur Verfügung um den Einstieg in die
 * Programmierung zu vereinfachen.</i>
 */
public class PinguLib {

	/**
	 * Prints the {@link String} to the console and breaks the line.
	 * <p>
	 * <i>Gibt den {@link String} auf der Konsole aus und beginnt eine neue
	 * Zeile.</i>
	 */
	public static void write(String output) {
		System.out.println(output);
	}

	/**
	 * Prints the <code>int</code> to the console and breaks the line.
	 * <p>
	 * <i>Gibt den <code>int</code>-Wert auf der Konsole aus und beginnt eine neue
	 * Zeile.</i>
	 */
	public static void write(int output) {
		write(String.valueOf(output));
	}

	/**
	 * Prints the <code>double</code> to the console and breaks the line.
	 * <p>
	 * <i>Gibt den <code>double</code>-Wert auf der Konsole aus und beginnt eine
	 * neue Zeile.</i>
	 */
	public static void write(double output) {
		write(String.valueOf(output));
	}

	/**
	 * Identical to {@link #write(String)}.
	 * <p>
	 * <i>Identisch zu {@link #write(String)}</i>
	 */
	public static void writeLineConsole(String output) {
		System.out.println(output);
	}

	/**
	 * Identical to {@link #write(int)}.
	 * <p>
	 * <i>Identisch zu {@link #write(int)}</i>
	 */
	public static void writeLineConsole(int output) {
		writeLineConsole(String.valueOf(output));
	}

	/**
	 * Identical to {@link #write(double)}.
	 * <p>
	 * <i>Identisch zu {@link #write(double)}</i>
	 */
	public static void writeLineConsole(double output) {
		writeLineConsole(String.valueOf(output));
	}

	/**
	 * Prints a line separator in the console.
	 * <p>
	 * <i>Gibt einen Zeilenumbruch auf der Konsole aus.</i>
	 */
	public static void writeLineConsole() {
		writeLineConsole("");
	}

	/**
	 * Prints the {@link String} to the console. Does not end with a line break.
	 * <p>
	 * <i>Gibt den {@link String} auf der Konsole aus. Endet nicht mit einem
	 * Zeilenumbruch.</i>
	 */
	public static void writeConsole(String output) {
		System.out.print(output);
	}

	/**
	 * Prints the <code>int</code> to the console. Does not end with a line break.
	 * <p>
	 * <i>Gibt den <code>int</code>-Wert auf der Konsole aus. Endet nicht mit einem
	 * Zeilenumbruch.</i>
	 */
	public static void writeConsole(int output) {
		writeConsole(String.valueOf(output));
	}

	/**
	 * Prints the <code>double</code> to the console. Does not end with a line
	 * break.
	 * <p>
	 * <i>Gibt den <code>double</code>-Wert auf der Konsole aus. Endet nicht mit
	 * einem Zeilenumbruch.</i>
	 */
	public static void writeConsole(double output) {
		writeConsole(String.valueOf(output));
	}

}

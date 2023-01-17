package flashcards;

import java.util.Scanner;


public class InputOutput {
    private final Scanner scanner;
    private final LogManager logManager;

    public InputOutput(Scanner scanner, LogManager log) {
        this.scanner = scanner;
        this.logManager = log;
    }

    public void println(String message) {
        System.out.println(message);
        logManager.add(message);
    }

    public void printlnf(String format, Object... args) {
        System.out.printf(format + "%n", args);
        logManager.add(String.format(format, args));
    }

    public String read() {
        String input = scanner.nextLine();
        logManager.add(input);
        return input;
    }

    public int readInt() {
        String input = scanner.nextLine();
        logManager.add(input);
        return Integer.parseInt(input);
    }

    public void saveLog(String fileName) {
        logManager.save(fileName);
    }

}

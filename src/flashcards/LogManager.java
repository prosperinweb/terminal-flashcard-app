package flashcards;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private final List<String> logs = new ArrayList<>();

    public void add(String logMessage) {
        logs.add(logMessage);
    }

    public void save(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (String log : logs) {
                writer.println(log);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void view() {
        for (String log : logs) {
            System.out.println(log);
        }
    }
}

/*TODO
    * Add a timestamp to each log entry, so that you know when the entry was made.
    * Add a log level to each log entry, so that you can filter the logs by level.
    * Allow the user to choose the log level, so that they can decide how verbose the logs should be.
    * Add the ability to read logs from a file, so that the user can view previous logs.
 */
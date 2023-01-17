package flashcards;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager cardManager = new Manager(new LinkedHashMap<>(), new LinkedHashMap<>());
        LogManager log = new LogManager();
        InputOutput inputOutput = new InputOutput(scanner, log);
        Logic logic = new Logic(cardManager, inputOutput);
        logic.run(args);
    }
}


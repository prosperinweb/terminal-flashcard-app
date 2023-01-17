package flashcards;

import java.util.Objects;

public class Logic {
    private final Manager manager;
    private final InputOutput inputOutput;

    public Logic(Manager manager, InputOutput inputOutput) {
        this.manager = manager;
        this.inputOutput = inputOutput;
    }

    public void run(String[] args) {
        String importFileName = null;
        String exportFileName = null;
        for (int i = 0; i < args.length; i++) {
            if (Objects.equals(args[i], "-import")) {
                importFileName = args[i + 1];
            } else if (Objects.equals(args[i], "-export")) {
                exportFileName = args[i + 1];
            }
        }
        if (importFileName != null) {
            int count = manager.importCards(importFileName);
            inputOutput.printlnf("%d cards have been loaded.", count);
        }
        while (true) {
            inputOutput.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String action = inputOutput.read();
            switch (action) {
                case "add" -> add();
                case "remove" -> remove();
                case "import" -> importCards();
                case "export" -> exportCards();
                case "ask" -> ask();
                case "exit" -> {
                    inputOutput.println("Bye bye!");
                    if (exportFileName != null) {
                        int count = manager.exportCards(exportFileName);
                        inputOutput.printlnf("%d cards have been saved.", count);
                    }
                    return;
                }
                case "log" -> log();
                case "hardest card" -> hardestCard();
                case "reset stats" -> resetStats();
                default -> inputOutput.println("Unknown action");
            }
        }

    }

    private void add() {
        inputOutput.println("The card:");
        String term = inputOutput.read();
        if (manager.containsTerm(term)) {
            inputOutput.println("The card \"" + term + "\" already exists.");
            return;
        }

        inputOutput.println("The definition of the card:");
        String definition = inputOutput.read();
        if (manager.containsDefinition(definition)) {
            inputOutput.println("The definition \"" + definition + "\" already exists.");
            return;
        }
        manager.addCard(term, definition);
        inputOutput.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }

    private void remove() {
        inputOutput.printlnf("Which card");
        String term = inputOutput.read();
        if (manager.containsTerm(term)) {
            manager.remove(term);
            inputOutput.println("The card has been removed.");
        } else {
            inputOutput.println("Can't remove \"" + term + "\": there is no such card.");
        }
    }

    private void importCards() {
        inputOutput.println("File name:");
        String fileName = inputOutput.read();
        int count = manager.importCards(fileName);
        inputOutput.println(count + " cards have been loaded.");
    }

    private void exportCards() {
        inputOutput.println("File name:");
        String fileName = inputOutput.read();
        int count = manager.exportCards(fileName);
        inputOutput.printlnf("%d cards have been saved.", count);
    }

    private void ask() {
        inputOutput.println("How many times to ask?");
        int count = inputOutput.readInt();
        for (int i = 0; i < count; i++) {
            String term = manager.getRandomTerm();
            inputOutput.printlnf("Print the definition of \"%s\":", term);
            String answer = inputOutput.read();
            if (manager.isCorrect(term, answer)) {
                inputOutput.println("Correct!");
            } else {
                String correctDefinition = manager.getDefinition(term);
                String wrongTerm = manager.getTerm(answer);
                if (wrongTerm == null) {
                    inputOutput.printlnf("" +
                            "Wrong. The right answer is \"%s\".",
                            correctDefinition
                    );
                } else {
                    inputOutput.printlnf(
                            "Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".",
                            correctDefinition, wrongTerm
                    );
                }
            }
        }
    }

    private void log() {
        inputOutput.println("File name:");
        String fileName = inputOutput.read();
        inputOutput.saveLog(fileName);
        inputOutput.println("The log has been saved.");
    }

    private void hardestCard() {
        String hardestCard = manager.getHardestCard();
        inputOutput.println(Objects.requireNonNullElse(hardestCard, "There are no cards with errors."));
    }

    private void resetStats() {
        manager.resetStats();
        inputOutput.println("Card statistics have been reset.");
    }
}

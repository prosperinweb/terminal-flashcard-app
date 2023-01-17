package flashcards;

import java.io.PrintWriter;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class Manager {
    private final Map<String, String> cards;
    private final Map<String, Integer> mistakes;

    public Manager(Map<String, String> cards, Map<String, Integer> mistakes) {
        this.cards = cards;
        this.mistakes = mistakes;
    }

    public void addCard(String term, String definition) {
        cards.put(term, definition);
        mistakes.put(term, 0);
    }

    public void remove(String term) {
        cards.remove(term);
        mistakes.remove(term);
    }

    public boolean containsTerm(String term) {
        return cards.containsKey(term);
    }

    public boolean containsDefinition(String definition) {
        return cards.containsValue(definition);
    }

    public String getDefinition(String term) {
        return cards.get(term);
    }

    public String getTerm(String definition) {
        for (Map.Entry<String, String> entry : cards.entrySet()) {
            if (entry.getValue().equals(definition)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String getRandomTerm() {
        Random random = new Random();
        int index = random.nextInt(cards.size());
        return (String) cards.keySet().toArray()[index];
    }

    public boolean isCorrect(String term, String definition) {
        if (cards.get(term).equals(definition)) {
            return true;
        } else {
            mistakes.put(term, mistakes.get(term) + 1);
            return false;
        }
    }

    public int importCards(String fileName) {
        int count = 0;
        try (Scanner scanner = new Scanner(Paths.get(fileName))) {
            while (scanner.hasNext()) {
                String term = scanner.nextLine();
                String definition = scanner.nextLine();
                int mistake = Integer.parseInt(scanner.nextLine());
                if (cards.containsKey(term)) {
                    cards.replace(term, definition);
                    mistakes.replace(term, mistake);
                } else {
                    cards.put(term, definition);
                    mistakes.put(term, mistake);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return count;
    }

    public int exportCards(String fileName) {
        int count = 0;
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))) {
            for (Map.Entry<String, String> entry : cards.entrySet()) {
                writer.println(entry.getKey());
                writer.println(entry.getValue());
                writer.println(mistakes.get(entry.getKey()));
                count++;
            }

        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return count;
    }

    // handling mistakes
    public void resetStats() {
        for (Map.Entry<String, Integer> entry : mistakes.entrySet()) {
            mistakes.replace(entry.getKey(), 0);
        }
    }

    public String getHardestCard() {
        int max = 0;
        List<String> hardestCards = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mistakes.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                hardestCards.clear();
                hardestCards.add(entry.getKey());
            } else if (entry.getValue() == max) {
                hardestCards.add(entry.getKey());
            }
        }
        if (max == 0) {
            return null;
        } else if (hardestCards.size() == 1) {
            return "The hardest card is \"" + hardestCards.get(0) + "\". You have " + max + " errors answering it.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("The hardest cards are ");
            for (int i = 0; i < hardestCards.size(); i++) {
                if (i == hardestCards.size() - 1) {
                    sb.append("\"").append(hardestCards.get(i)).append("\". ");
                } else {
                    sb.append("\"").append(hardestCards.get(i)).append("\", ");
                }
            }
            sb.append("You have ").append(max).append(" errors answering them.");
            return sb.toString();
        }
    }

    public int getMistakes(String hardestCard) {
        return mistakes.get(hardestCard);
    }

}
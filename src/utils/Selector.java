package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Selector {

    public static int creatureSellingSelect(List<? extends HasName> boardCreatures) {
        return creatureSellingSelect(boardCreatures, new ArrayList<>(), true);
    }

    public static int creatureSellingSelect(List<? extends HasName> boardCreatures, List<? extends HasName> benchCreatures, boolean hasBackOption) {
        int selectedNumber = -1;
        int from = hasBackOption ? 0 : 1;
        while (selectedNumber == -1) {
            int counter = from;
            counter++;
            for (HasName ignored : boardCreatures) {
                counter += 1;
            }
            for (HasName ignored : benchCreatures) {
                counter += 1;
            }
            if (counter >= 1) {
                selectedNumber = readCommandNumber(from, counter - 1);
                if (selectedNumber == -1) {
                    MessageController.print("Input number from " + from + " to " + (counter - 1));
                }
            } else {
                selectedNumber = 0;
            }
        }
        return selectedNumber;
    }

    public static int select(List<?> options, int from) {
        int selectedNumber = -1;
        while (selectedNumber == -1) {
            int counter = from;
            for (Object ignored : options) {
                counter += 1;
            }
            if (counter >= 1) {
                selectedNumber = readCommandNumber(from, counter - 1);
                if (selectedNumber == -1) {
                    MessageController.print(
                            "Введите число от " + from + " до " + (counter - 1),
                            "Input number from " + from + " to " + (counter - 1)
                    );
                }
            } else {
                selectedNumber = 0;
            }
        }
        return selectedNumber;
    }

    public static int select(Object... options) {
        return select(List.of(options), 0);
    }

    public static int itemSelect(List<?> items, Integer... additionalOptions) {
        int selectedNumber = -1;

        while (selectedNumber == -1) {
            int counter = 0;
            for (Object ignored : items) {
                counter += 1;
            }
            if (counter + additionalOptions.length >= 1) {
                selectedNumber = readCommandNumber(0, counter, Arrays.stream(additionalOptions).collect(Collectors.toList()));
                if (selectedNumber == -1) {
                    StringBuilder additional = new StringBuilder();
                    Arrays.stream(additionalOptions).forEach(additional::append);
                    MessageController.print("Input number from 0 to " + counter + " " + additional);
                }
            } else {
                selectedNumber = 0;
            }
        }
        return selectedNumber;
    }

    private static int readCommandNumber(int from, int to, List<Integer> additionals) {
        Scanner in = new Scanner(System.in);
        int input = 123123;
        try {
            input = in.nextInt();
            if ((input >= from && input <= to) || additionals.contains(input)) {
                return input;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    private static int readCommandNumber(int from, int to) {
        ConsoleInput consoleInput = new ConsoleInput();
        int input;
        try {
            input = Integer.parseInt(consoleInput.readLine());
            if ((input >= from && input <= to)) {
                return input;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }
}
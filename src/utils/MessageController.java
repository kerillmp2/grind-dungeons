package utils;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import battlecore.BattleStatus;
import item.Item;
import viewers.ListViewer;

public class MessageController {
    public static void print(String message) {
        if (Constants.PRINT_MESSAGES_IN_CONTROLLER.value == 1) {
            if (!message.equals("") && !message.equals(Constants.UNDEFINED.name())) {
                System.out.println(message);
            }
        }
    }

    public static void print(String messageRU, String messageENG) {
        if (Constants.PRINT_RU_MESSAGES.value == 1) {
            print(messageRU);
        }
        if (Constants.PRINT_ENG_MESSAGES.value == 1) {
            print(messageENG);
        }
    }

    public static void forcedPrint(String message) {
        if (!message.equals("") && !message.equals(Constants.UNDEFINED.name())) {
            System.out.println(message);
        }
    }

    public static void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ignored) {}
    }

    public static void printLoot(Map<Item, Integer> loot, String tableName) {
        List<String> sortedLoot = loot.entrySet().stream()
                .sorted(Comparator.comparingInt(o -> o.getKey().getValue() * o.getValue()))
                .map(entry -> entry.getKey().getBasicView() + " [X" + entry.getValue() + "]")
                .collect(Collectors.toList());
        MessageController.print(ListViewer.viewListWithHeader(sortedLoot, tableName));
    }

    public static void printBattleStatus(BattleStatus battleStatus, String profileName) {
        switch (battleStatus) {
            case FIRST_PLAYER_WIN -> MessageController.print(profileName + " won!");
            case SECOND_PLAYER_WIN, DRAW -> MessageController.print(profileName + " lost!");
            case TURN_LIMIT_REACHED, UNDEFINED -> MessageController.print(profileName + " can't kill this dungeon monster!");
            case INTERRUPTED -> MessageController.print(profileName + " runs away from battle!");
        }
    }
}

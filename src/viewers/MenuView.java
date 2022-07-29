package viewers;

import java.util.ArrayList;
import java.util.List;

import profile.Profile;

public class MenuView extends Viewer {

    public static String getMenuView(Profile player) {
        Window window = new Window();
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("Play");
        menuOptions.add("Exit");
        window
                .lineWithAngles()
                .centeredText("Welcome to the Arphei!")
                .lineWithAngles()
                .line("Player " + player.getName())
                .lineWithAngles()
                .emptyLine()
                .list(menuOptions, true, 1, true,false, true)
                .lineWithAngles();

        return window.getView();
    }
}

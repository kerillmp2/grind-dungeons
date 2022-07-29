package viewers;

import java.util.Arrays;
import java.util.List;

import utils.Constants;
import utils.Pair;

public class CreatureBattleViewer extends Viewer {

    public static List<String> getCreatureView(String name, int currentHP, int maxHP, List<Pair<String, String>> additionalParams) {
        int rowSize = Constants.BATTLE_VIEW_LENGTH.value;
        int height = Constants.BATTLE_VIEW_HEIGHT.value;
        int offset = Constants.BATTLE_VIEW_OFFSET.value;
        String curHp = "[" + currentHP + " / " + maxHP + "] HP";

        Window window = new Window(rowSize, offset);
        window.lineWithAngles();
        window.line(name);
        window.lineWithAngles();
        for (Pair<String, String> param : additionalParams) {
            if ((height - 4) - window.getCurrentHeight() < 0) {
                break;
            }
            window.line(param.first + " " + param.second);
        }
        while (window.getCurrentHeight() < height - 2) {
            window.emptyLine();
        }
        window.line(curHp);
        window.lineWithAngles();
        return Arrays.stream(window.getView().split("\n")).toList();
    }
}

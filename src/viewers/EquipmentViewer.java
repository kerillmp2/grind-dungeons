package viewers;

import java.util.ArrayList;
import java.util.List;

import profile.Profile;
import profile.character.BodyPart;

public class EquipmentViewer extends Viewer {

    public static String getEquipmentView(Profile profile, List<BodyPart> bodyParts) {
        Window window = new Window();
        window.add(getHeader(profile));
        List<String> equipment = new ArrayList<>();
        for (BodyPart bodyPart : bodyParts) {
            equipment.add(bodyPart.getName() + ": " + profile.getCharacter().getEquipmentOn(bodyPart).getBasicView());
        }
        window.list(equipment);
        window.lineWithAngles();
        return window.getView();
    }

    private static String getHeader(Profile profile) {
        Window header = new Window();
        header.lineWithAngles();

        header.centeredText(profile.getName() + "'s equipment");
        header.centeredText(profile.getCharacter().getStatsView());
        header.lineWithAngles();
        return header.getView();
    }
}

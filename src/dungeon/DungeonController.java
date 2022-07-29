package dungeon;

import battlecore.Battle;
import profile.Profile;
import utils.HasName;
import utils.MessageController;
import utils.threads.ExitThread;

public class DungeonController implements HasName {
    private final Dungeon dungeon;
    private final Profile profile;

    public DungeonController(Dungeon dungeon, Profile profile) {
        this.profile = profile;
        this.dungeon = dungeon;
    }

    public void dungeonProcessing() {
        MessageController.print(profile.getName() + " enters in the " + dungeon.getName() + "!");
        Battle.processBattleSeries(profile, dungeon.getGenerator(), c -> false, new ExitThread());
        profile.saveProfile();
    }

    @Override
    public String getName() {
        return dungeon.getName();
    }

}

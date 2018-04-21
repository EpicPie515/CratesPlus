package plus.crates.Opener;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import plus.crates.CratesPlus;
import plus.crates.Crates.Crate;

public class NoGUIOpener extends Opener {
    private boolean chestSound = true;

    public NoGUIOpener(CratesPlus cratesPlus) {
        super(cratesPlus, "NoGUI");
    }

    @Override
    public void doSetup() {
        FileConfiguration config = getOpenerConfig();
        if (config.isSet("Chest Sound")) {
            chestSound = config.getBoolean("Chest Sound", true);
        } else {
            config.set("Chest Sound", true);
            try {
                config.save(getOpenerConfigFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doOpen(Player player, Crate crate, Location location) {
        if (chestSound) {
            Sound sound = null;
            try {
                sound = Sound.valueOf("CHEST_OPEN");
            } catch (Exception e) {
                try {
                    sound = Sound.valueOf("BLOCK_CHEST_OPEN");
                } catch (Exception ee) {
                    // This should never happen!
                }
            }
            if (sound != null)
                player.playSound(player.getLocation(), sound, (float) 0.5, 1);
        }
        for(int i = 0; i < new Random().nextInt(1)+2; i++) {
            crate.handleWin(player);
//    		getWinning(crate).runWin(player);
        }
        finish(player);
    }

    @Override
    public void doReopen(Player player, Crate crate, Location location) {

    }

    public boolean doesSupport(Crate crate) {
        return true;
    }

}

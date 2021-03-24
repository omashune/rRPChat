package ru.rey.rrpchat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ru.rey.rrpchat.Core;
import ru.rey.rrpchat.Utils;

/**
 * Created by Rey on 24.03.2021
 */
public class CoinCommand extends Command {

    private final ConfigurationSection config = Core.getInstance().getConfig().getConfigurationSection("commands.coin");
    private final String obverse = config.getString("obverse");
    private final String reverse = config.getString("reverse");

    public CoinCommand() {
        super("coin");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return true;

        String result = Utils.getRandom(2) == 0 ? obverse : reverse;

        String message = config.getString("format")
                .replace("%sender%", sender.getName())
                .replace("%result%", result);

        Utils.getNearbyPlayers((Player) sender, config.getInt("radius"))
                .forEach(p -> p.sendMessage(message));
        return false;
    }

}

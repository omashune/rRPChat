package ru.rey.rrpchat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ru.rey.rrpchat.Core;
import ru.rey.rrpchat.Utils;

/**
 * Created by Rey on 24.03.2021
 */
public class GlobalMeCommand extends Command {

    private final ConfigurationSection config = Core.getInstance().getConfig().getConfigurationSection("commands.gme");

    public GlobalMeCommand() {
        super("gme");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return true;

        if (args.length < 1) {
            sender.sendMessage(Utils.parseColors(config.getString("usage")));
            return true;
        }

        String message = config.getString("format")
                .replace("%sender%", sender.getName())
                .replace("%message%", Utils.extractMessage(args));

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Utils.parseColors(message)));
        return false;
    }

}

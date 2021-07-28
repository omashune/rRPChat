package ru.rey.rrpchat;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Rey on 24.03.2021
 */
public final class Utils {
    private static final boolean hexSupport = checkHexSupport();
    private static final Pattern hexPattern = Pattern.compile("\\{#([A-Fa-f0-9]){6}}");
    private static final Random RANDOM = new Random();

    public static String extractMessage(String... message) {
        return String.join(" ", message);
    }

    public static List<Player> getNearbyPlayers(Player player, int range) {
        return Bukkit.getOnlinePlayers().parallelStream()
                .filter(p -> p.getWorld().equals(player.getWorld()) && p.getLocation().distanceSquared(player.getLocation()) <= range * range)
                .collect(Collectors.toList());
    }

    public static int getRandom(int max) {
        return RANDOM.nextInt(max);
    }

    private static boolean checkHexSupport(){
        String version = Bukkit.getVersion();
        int start = version.indexOf("MC: ") + 4;
        int end = version.length() - 1;
        return Integer.parseInt(version.substring(start, end).split("\\.")[1]) >= 16;
    }

    public static String parseColors(String message) {
        String parsed = message;

        if (hexSupport){
            Matcher matcher = hexPattern.matcher(parsed);
            while (matcher.find()) {
                ChatColor hexColor = ChatColor.of(matcher.group().replaceAll("[{}]", ""));
                String before = parsed.substring(0, matcher.start());
                String after = parsed.substring(matcher.end());
                parsed = before + hexColor + after;
            }
        }
        return ChatColor.translateAlternateColorCodes('&', parsed);
    }

}

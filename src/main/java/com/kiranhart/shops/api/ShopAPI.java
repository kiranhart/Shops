package com.kiranhart.shops.api;

import com.kiranhart.shops.api.enums.DefaultFontInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 12:00 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ShopAPI {

    private static ShopAPI instance;

    // set constructor to private
    private ShopAPI() {
    }

    // constants
    private final static int CENTER_PX = 154;

    /**
     * Get an instance of the shop API
     *
     * @return the Shop API instance
     */
    public static ShopAPI get() {
        if (instance == null) {
            instance = new ShopAPI();
        }
        return instance;
    }

    /**
     * Used to determine of a player has a set of permissions
     *
     * @param sender is the command sender
     * @param perms  is the array of permissions you want to check
     * @return whether or not they have permissions to any of the param perms
     */
    public boolean hasPerm(CommandSender sender, String... perms) {
        for (String s : perms) {
            if (sender.hasPermission(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Used to send a centered message
     *
     * @param player is the command sender
     * @param message is what you want to send
     */
    public void centerMsg(CommandSender player, String message) {
        if (message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
                continue;
            } else if (previousCode) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }
}

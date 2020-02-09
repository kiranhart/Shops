package com.kiranhart.shops.commands.subcommands;

import com.kiranhart.shops.Core;
import com.kiranhart.shops.api.ShopAPI;
import com.kiranhart.shops.api.statics.ShopLang;
import com.kiranhart.shops.api.statics.ShopPerm;
import com.kiranhart.shops.commands.Subcommand;
import org.bukkit.command.CommandSender;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/9/2020
 * Time Created: 12:02 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class HelpCommand extends Subcommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!ShopAPI.get().hasPerm(sender, ShopPerm.CMD_ALL, ShopPerm.CMD_HELP)) {
            Core.getInstance().getLocale().getMessage(ShopLang.NO_PERMISSION).sendPrefixedMessage(sender);
            return;
        }

        if (args.length == 1) {
            ShopAPI.get().centerMsg(sender, "&e&b&nShops Help");
            ShopAPI.get().centerMsg(sender, "");
            ShopAPI.get().centerMsg(sender, "&6[] &f- Optional");
            ShopAPI.get().centerMsg(sender, "&d<> &f- Required");
            ShopAPI.get().centerMsg(sender, "");
            ShopAPI.get().centerMsg(sender, "&f/&bshops &6[&bcategory&6]");
            ShopAPI.get().centerMsg(sender, "&f/&bshops create &d<&bcategory&d>");
            ShopAPI.get().centerMsg(sender, "&f/&bshops edit &d<&bcategory&d>");
            ShopAPI.get().centerMsg(sender, "");
        }
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String[] aliases() {
        return new String[] {"?", "info"};
    }
}

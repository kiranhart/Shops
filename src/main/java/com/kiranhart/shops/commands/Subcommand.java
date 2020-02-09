package com.kiranhart.shops.commands;

import org.bukkit.command.CommandSender;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 2/8/2020
 * Time Created: 11:59 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public abstract class Subcommand {

    public Subcommand() {}

    public abstract void onCommand(CommandSender sender, String[] args);

    public abstract String name();

    public abstract String[] aliases();
}

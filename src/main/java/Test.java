/*
    Created by Kiran Hart
    Date: February / 19 / 2020
    Time: 3:59 p.m.
*/

import com.kiranhart.shops.util.helpers.DiscordWebhook;

import java.awt.*;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        DiscordWebhook hook = new DiscordWebhook("https://discordapp.com/api/webhooks/679794521361612839/Ior7Oooji3UHY94viDQPU-KjkIJEN9BvcekccvYwciN3LQacPGBmZjmYMXmtXM-7h5fD");

        hook.setContent("Test");
        hook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle("Title")
                .setDescription("This is a description")
                .setColor(Color.RED));

        try {
            hook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

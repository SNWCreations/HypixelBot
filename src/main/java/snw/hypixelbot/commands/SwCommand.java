package snw.hypixelbot.commands;

import com.google.gson.JsonObject;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.PlayerReply;
import snw.hypixelbot.Main;
import snw.hypixelbot.wrapper.Wrapper;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

import static snw.hypixelbot.commands.HypCommand.reply;

public class SwCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User sender, String[] arguments, Message message) {
        if (message == null) return;
        if (arguments.length == 0) {
            reply(sender, message, "您没有提供参数！用法: /sw <玩家名>");
        } else {
            HypixelAPI hypixelAPI = Main.getInstance().getHypixelAPI();
            String target = arguments[0];
            PlayerReply.Player player = Wrapper.getPlayerByName(hypixelAPI, target, sender, message);
            if (player == null) {
                return;
            }

            String name = player.getName();
            JsonObject swStat = player.getRaw().getAsJsonObject("stats").getAsJsonObject("SkyWars");
            int souls = swStat.get("souls").getAsInt();
            int coins = swStat.get("coins").getAsInt();
            int fails = swStat.get("losses").getAsInt();
            int winstreak = swStat.get("win_streak").getAsInt();
            int deaths = swStat.get("deaths").getAsInt();
            int blocks_placed = swStat.get("blocks_placed").getAsInt();

            MultipleCardComponent card = new CardBuilder()
                    .setTheme(Theme.INFO)
                    .setSize(Size.LG)
                    .addModule(
                            new HeaderModule(new PlainTextElement(String.format("%s 的空岛战争数据", name), false))
                    )
                    .addModule(
                            new SectionModule(
                                    new PlainTextElement(
                                            String.format(
                                                    "硬币: %s\n" +
                                                            "灵魂: %s\n" +
                                                            "当前连胜: %s\n" +
                                                            "方块放置数: %s\n" +
                                                            "失败: %s\n" +
                                                            "死亡: %s",
                                                    coins, souls, winstreak, blocks_placed, fails, deaths
                                            ), false
                                    ), null, null
                            )
                    )
                    .build();
            message.reply(card);
        }
    }
}

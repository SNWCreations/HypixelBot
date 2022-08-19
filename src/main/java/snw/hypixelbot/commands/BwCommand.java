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

public class BwCommand implements UserCommandExecutor {

    @Override
    public void onCommand(User sender, String[] arguments, Message message) {
        if (message == null) return;
        if (arguments.length == 0) {
            reply(sender, message, "您没有提供参数！用法: /bw <玩家名>");
        } else {
            HypixelAPI hypixelAPI = Main.getInstance().getHypixelAPI();
            String target = arguments[0];
            PlayerReply.Player player = Wrapper.getPlayerByName(hypixelAPI, target, sender, message);
            if (player == null) {
                reply(sender, message, "无法获取目标玩家的信息。这个玩家可能不存在？");
                return;
            }

            String name = player.getName();
            JsonObject bwStat = player.getRaw().getAsJsonObject("stats").getAsJsonObject("Bedwars");
            int coins = bwStat.get("coins").getAsInt();
            int played_times = bwStat.get("games_played_bedwars").getAsInt();
            int fails = bwStat.get("losses_bedwars").getAsInt();
            int wins = bwStat.get("wins_bedwars").getAsInt();
            int winstreak = bwStat.get("winstreak").getAsInt();
            int kills = bwStat.get("kills_bedwars").getAsInt();
            int deaths = bwStat.get("deaths_bedwars").getAsInt();


            MultipleCardComponent card = new CardBuilder()
                    .setTheme(Theme.INFO)
                    .setSize(Size.LG)
                    .addModule(
                            new HeaderModule(new PlainTextElement(String.format("%s 的起床战争数据", name), false))
                    )
                    .addModule(
                            new SectionModule(
                                    new PlainTextElement(
                                            String.format(
                                                    "硬币: %s\n" +
                                                            "游玩次数: %s\n" +
                                                            "胜利: %s\n" +
                                                            "当前连胜: %s\n" +
                                                            "失败: %s\n" +
                                                            "击杀: %s\n" +
                                                            "死亡: %s",
                                                    coins, played_times, wins, winstreak, fails, kills, deaths
                                            ), false
                                    ), null, null
                            )
                    )
                    .build();
            message.reply(card);
        }
    }

}

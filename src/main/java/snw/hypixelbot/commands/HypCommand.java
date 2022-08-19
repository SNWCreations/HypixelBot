package snw.hypixelbot.commands;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.data.type.GameType;
import net.hypixel.api.data.type.LobbyType;
import net.hypixel.api.data.type.ServerType;
import net.hypixel.api.reply.PlayerReply;
import net.hypixel.api.reply.StatusReply;
import snw.hypixelbot.Main;
import snw.hypixelbot.wrapper.GameTypeCN;
import snw.hypixelbot.wrapper.LobbyTypeCN;
import snw.hypixelbot.wrapper.Rank;
import snw.hypixelbot.wrapper.Wrapper;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.PrivateMessage;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;
import snw.jkook.message.component.card.module.DividerModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;

public class HypCommand implements UserCommandExecutor {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");


    @Override
    public void onCommand(User sender, String[] arguments, Message message) {
        try {
            onCommand0(sender, arguments, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void onCommand0(User sender, String[] arguments, Message message) throws Exception {
        if (arguments.length == 0) {
            reply(sender, message, "您没有提供参数！用法: /hyp <玩家名>");
        } else {
            HypixelAPI hypixelAPI = Main.getInstance().getHypixelAPI();
            String target = arguments[0];
            PlayerReply.Player player = Wrapper.getPlayerByName(hypixelAPI, target, sender, message);
            if (player == null) {
                reply(sender, message, "无法获取目标玩家的信息。这个玩家可能不存在？");
                return;
            }

            StatusReply.Session status = hypixelAPI.getStatus(player.getUuid()).get().getSession();

            String playMsg;
            if (status.isOnline()) {
                ServerType serverType = status.getServerType();
                if (serverType instanceof LobbyType) {
                    playMsg = "正在 " + LobbyTypeCN.value((LobbyType) serverType).getName();
                } else {
                    playMsg = "正在游玩 " + GameTypeCN.value((GameType) serverType).getName();
                }
            } else {
                playMsg = "目前离线\n";
                playMsg += "最后登录时间: " + formatDate(player.getLastLoginDate());
            }

            long karma = player.getKarma();
            double networkLevel = player.getNetworkLevel();
            String rawRank = player.getHighestRank();

            String playerinfo = String.format(
                            "等级: Lv%s\n" +
                            "人品值: %s\n" +
                            "首次登录 Hypixel: %s"
                    , (int) networkLevel, karma, formatDate(player.getFirstLoginDate())
            );

            MultipleCardComponent card = new CardBuilder()
                    .setTheme(Theme.INFO)
                    .setSize(Size.LG)
                    .addModule(
                            new HeaderModule(new PlainTextElement(rawRank.equals("NONE") ? "" : "[" + Rank.value(rawRank).getTranslation() + "] " + player.getName(), false))
                    )
                    .addModule(DividerModule.INSTANCE)
                    .addModule(
                            new SectionModule(
                                    new PlainTextElement(
                                            playerinfo, false
                                    ),
                                    null, null
                            )
                    )
                    .addModule(
                            new SectionModule(
                                    new PlainTextElement(playMsg, false), null, null
                            )
                    )
                    .addModule(DividerModule.INSTANCE)
                    .addModule(
                            new ContextModule(Collections.singletonList(new PlainTextElement("注: 若请求的用户名与实际用户名不一致，属于正常现象。\n可能是目标用户修改了 Minecraft 用户名。", false)))
                    )
                    .addModule(
                            new ContextModule(Collections.singletonList(new PlainTextElement("若目标用户的登录时间是不可能的值 (如 1970-1-1)，\n则此用户可能没有登录过 Hypixel。", false)))
                    )
                    .build();
            reply(sender, message, card);
        }
    }

    public static void reply(User sender, Message message, String content) {
        reply(sender, message, new MarkdownComponent(content));
    }

    public static void reply(User sender, Message message, BaseComponent component) {
        if (message instanceof TextChannelMessage) {
            ((TextChannelMessage) message).getChannel().sendComponent(component, (TextChannelMessage) message, sender);
        } else {
            sender.sendPrivateMessage(component, message != null ? (PrivateMessage) message : null);
        }
    }

    public static String formatDate(ZonedDateTime time) {
        return SDF.format(new Date(time.toInstant().toEpochMilli()));
    }
}

package snw.hypixelbot;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reactor.ReactorHttpClient;
import snw.hypixelbot.commands.BwCommand;
import snw.hypixelbot.commands.HypCommand;
import snw.hypixelbot.commands.SwCommand;
import snw.jkook.JKook;
import snw.jkook.command.JKookCommand;
import snw.jkook.plugin.BasePlugin;

import java.util.Objects;
import java.util.UUID;

public final class Main extends BasePlugin {
    private static Main INSTANCE;
    private HypixelAPI hypApi;

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        String rawApikey = getConfig().getString("api-key");
        if (rawApikey == null || rawApikey.isEmpty()) {
            getLogger().error("No API-Key provided. You can get it by executing command /api at Hypixel server.");
            getLogger().error("Plugin won't work.");
            JKook.getPluginManager().disablePlugin(this);
            return;
        }
        UUID apiKey = UUID.fromString(Objects.requireNonNull(getConfig().getString("api-key")));
        hypApi = new HypixelAPI(new ReactorHttpClient(apiKey));
        new JKookCommand("hyp")
                .setDescription("请求一个 Minecraft 玩家的 Hypixel 基本信息。")
                .executesUser(new HypCommand())
                .register();
        new JKookCommand("bw")
                .setDescription("请求一个 Minecraft 玩家的 Hypixel 起床战争信息。")
                .executesUser(new BwCommand())
                .register();
        new JKookCommand("sw")
                .setDescription("请求一个 Minecraft 玩家的 Hypixel 空岛战争信息。")
                .executesUser(new SwCommand())
                .register();
    }

    public static Main getInstance() {
        return INSTANCE;
    }

    public HypixelAPI getHypixelAPI() {
        return hypApi;
    }
}

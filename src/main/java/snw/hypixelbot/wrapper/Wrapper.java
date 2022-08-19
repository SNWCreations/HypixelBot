package snw.hypixelbot.wrapper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.PlayerReply;
import snw.jkook.entity.User;
import snw.jkook.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static snw.hypixelbot.commands.HypCommand.reply;

public class Wrapper {

    public static PlayerReply.Player getPlayerByName(HypixelAPI api, String name, User sender, Message message) {
        String result;
        // region Mojang API request
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(3000);
            connection.connect();
            int code = connection.getResponseCode();
            StringBuilder msg = new StringBuilder();
            if (code == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    msg.append(line).append("\n");
                }
                reader.close();
                result = msg.toString();
            } else {
                return null;
            }
            connection.disconnect();
        } catch (IOException e) {
            return null;
        }
        // endregion
        JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
        String rawUUID = jsonObject.get("id").getAsString();
        CompletableFuture<PlayerReply> playerFuture = api.getPlayerByUuid(rawUUID);

        if (playerFuture == null) {
            reply(sender, message, "无法获取目标玩家的信息。这个玩家可能不存在？");
            return null;
        }
        PlayerReply reply;
        try {
            reply = playerFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
        if (!reply.isSuccess()) {
            reply(sender, message, "获取目标玩家的信息失败。");
            return null;
        }

        PlayerReply.Player player = reply.getPlayer();
        if (!player.exists()) {
            reply(sender, message, "玩家不存在。");
            return null;
        }
        return player;
    }
}

package snw.hypixelbot.wrapper;

import net.hypixel.api.data.type.LobbyType;
import net.hypixel.api.data.type.ServerType;

import java.util.HashMap;
import java.util.Map;

public enum LobbyTypeCN implements ServerType {

    MAIN(LobbyType.MAIN, "主大厅"),
    TOURNAMENT(LobbyType.TOURNAMENT, "竞赛殿堂"),
    ;
    private final LobbyType type;
    private final String translation;
    private static final Map<LobbyType, LobbyTypeCN> value = new HashMap<>();

    static {
        for (LobbyTypeCN lobbyTypeCN : values()) {
            value.put(lobbyTypeCN.getType(), lobbyTypeCN);
        }
    }

    LobbyTypeCN(LobbyType type, String translation) {
        this.type = type;
        this.translation = translation;
    }

    public LobbyType getType() {
        return type;
    }

    public static LobbyTypeCN value(LobbyType type) {
        return value.get(type);
    }

    @Override
    public String getName() {
        return translation;
    }
}

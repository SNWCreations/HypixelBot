package snw.hypixelbot.wrapper;

import java.util.HashMap;
import java.util.Map;

// The Rank Mapping.
public enum Rank {
    VIP("VIP", "VIP"),
    VIP_PLUS("VIP_PLUS", "VIP+"),
    MVP("MVP", "MVP"),
    MVP_PLUS("MVP_PLUS", "MVP+"),
    MVP_PP("SUPERSTAR", "MVP++"),
    ADMIN("ADMIN", "ADMIN"),
    GM("GAME_MASTER", "GM"),
    MOD("MODERATOR", "MOD"),
    YT("YOUTUBER", "YOUTUBER"),
    OWNER("OWNER", "OWNER"),
    MOJANG("MOJANG", "MOJANG");
    private static final Map<String, Rank> value = new HashMap<>();

    static {
        for (Rank rank : values()) {
            value.put(rank.getRaw(), rank);
        }
    }

    private final String raw;
    private final String translation;

    Rank(String raw, String translation) {
        this.raw = raw;
        this.translation = translation;
    }

    public String getRaw() {
        return raw;
    }

    public String getTranslation() {
        return translation;
    }

    public static Rank value(String raw) {
        return value.get(raw);
    }

}

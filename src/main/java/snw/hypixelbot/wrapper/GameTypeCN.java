package snw.hypixelbot.wrapper;

import net.hypixel.api.data.type.GameType;
import net.hypixel.api.data.type.ServerType;

import java.util.HashMap;
import java.util.Map;

// The translations are provided by Hypixel Wiki (unofficial) and the Server,
// so contact me if you found that this translation mapping is incorrect.
public enum GameTypeCN implements ServerType {
    QUAKECRAFT(GameType.QUAKECRAFT, "未来射击"),
    WALLS(GameType.WALLS, "战墙"),
    PAINTBALL(GameType.PAINTBALL, "彩弹战争"),
    SURVIVAL_GAMES(GameType.SURVIVAL_GAMES, "闪电饥饿游戏"),
    TNTGAMES(GameType.TNTGAMES, "掘战游戏"),
    VAMPIREZ(GameType.VAMPIREZ, "吸血鬼"),
    MEGA_WALLS(GameType.WALLS3, "超级战墙"),
    ARCADE(GameType.ARCADE, "街机游戏"),
    ARENA(GameType.ARENA, "竞技场乱斗"),
    MCGO(GameType.MCGO, "警匪大战"),
    UHC(GameType.UHC, "极限生存冠军"),
    WARLORDS(GameType.BATTLEGROUND, "战争领主"),
    SMASH_HEROES(GameType.SUPER_SMASH, "星碎英雄"),
    KART(GameType.GINGERBREAD, "方块赛车"),
    HOUSING(GameType.HOUSING, "家园"),
    SKYWARS(GameType.SKYWARS, "空岛战争"),
    CRAZY_WALLS(GameType.TRUE_COMBAT, "疯狂战墙"),
    SPEED_UHC(GameType.SPEED_UHC, "极速 UHC"),
    SKYCLASH(GameType.SKYCLASH, "空岛竞技场"),
    CLASSIC(GameType.LEGACY, "经典游戏"),
    PROTOTYPE(GameType.PROTOTYPE, "游戏实验室"),
    BEDWARS(GameType.BEDWARS, "起床战争"),
    MURDER_MYSTERY(GameType.MURDER_MYSTERY, "密室杀手"),
    BUILD_BATTLE(GameType.BUILD_BATTLE, "建筑大师"),
    DUELS(GameType.DUELS, "决斗"),
    SKYBLOCK(GameType.SKYBLOCK, "空岛生存"),
    PIT(GameType.PIT, "天坑乱斗"),
    REPLAY(GameType.REPLAY, "回放"),
    SMP(GameType.SMP, "SMP"),
    WOOL_WARS(GameType.WOOL_GAMES, "羊毛战争"),
    ;

    private final GameType type;
    private final String translation;
    private static final Map<GameType, GameTypeCN> value = new HashMap<>();

    static {
        for (GameTypeCN gameTypeCN : values()) {
            value.put(gameTypeCN.getType(), gameTypeCN);
        }
    }


    GameTypeCN(GameType type, String translation) {
        this.type = type;
        this.translation = translation;
    }

    public GameType getType() {
        return type;
    }

    public static GameTypeCN value(GameType gameType) {
        return value.get(gameType);
    }

    @Override
    public String getName() {
        return translation;
    }
}

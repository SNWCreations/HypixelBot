package snw.hypixelbot;

import java.util.UUID;

public class Utility {
    public static UUID noSeparatorUUID(String raw) {
        if (raw.length() < 32) {
            throw new IllegalArgumentException();
        }
        String a = raw.substring(0, 8);
        String b = raw.substring(8, 12);
        String c = raw.substring(12, 16);
        String d = raw.substring(16, 20);
        String e = raw.substring(20);
        String rawResult = String.join("-", a, b, c, d, e);
        return UUID.fromString(rawResult);
    }
}

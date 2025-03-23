package top.redstarmc.redstarprohibit.velocity.api;

import top.redstarmc.redstarprohibit.common.utils.MapBuilder;

import java.util.Map;

public class ColoredConsole {
    private static final Map<String, String> MCColorToANSI =
            MapBuilder.of(String.class, String.class)
                    .set("§0", "\u001B[30m")
                    .set("§1", "\u001B[34m")
                    .set("§2", "\u001B[32m")
                    .set("§3", "\u001B[36m")
                    .set("§4", "\u001B[31m")
                    .set("§5", "\u001B[35m")
                    .set("§6", "\u001B[33m")
                    .set("§7", "\u001B[37m")
                    .set("§8", "\u001B[90m")
                    .set("§9", "\u001B[94m")
                    .set("§a", "\u001B[92m")
                    .set("§b", "\u001B[96m")
                    .set("§c", "\u001B[91m")
                    .set("§d", "\u001B[95m")
                    .set("§e", "\u001B[93m")
                    .set("§f", "\u001B[97m")
                    .set("§k", "\u001B[5m")
                    .set("§l", "\u001B[1m")
                    .set("§m", "\u001B[9m")
                    .set("§n", "\u001B[4m")
                    .set("§o", "\u001B[3m")
                    .set("§r", "\u001B[0m")
                    .build();

    public static String toANSI(String s) {
        final String[] out = {s};
        MCColorToANSI.forEach((mc, ansi) -> out[0] = out[0].replace(mc, ansi));
        return out[0];
    }
}

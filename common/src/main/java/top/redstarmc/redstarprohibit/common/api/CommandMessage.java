package top.redstarmc.redstarprohibit.common.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class CommandMessage {
    private static final Component PluginPrefix = Component.text()
            .append(text("[", NamedTextColor.WHITE))
            .append(text("RSP",NamedTextColor.RED))
            .append(text("] ",NamedTextColor.WHITE))
            .build();

    private static final Component next = Component.text("\n");

    private static final Component LINE = Component.text("------------------------\n",NamedTextColor.BLUE);

    public static @NotNull Component Ban_end(String player_name){
        return text()
                .append(PluginPrefix,
                        text("已封禁玩家 ", NamedTextColor.RED),
                        text(player_name, NamedTextColor.GOLD),
                        text("。", NamedTextColor.RED))
                .build();
    }

    public static @NotNull Component unBan_end(String player_name){
        return text()
                .append(PluginPrefix,
                        text("已解除玩家 ", NamedTextColor.RED),
                        text(player_name, NamedTextColor.GOLD),
                        text(" 的封禁。", NamedTextColor.RED))
                .build();
    }

    public static @NotNull Component historyNull(String player_name){
        return text()
                .append(PluginPrefix,
                        text("玩家 ", NamedTextColor.RED),
                        text(player_name, NamedTextColor.GOLD),
                        text("没有封禁记录。", NamedTextColor.RED))
                .build();
    }
}

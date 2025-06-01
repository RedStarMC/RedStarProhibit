package top.redstarmc.redstarprohibit.common.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.redstarmc.redstarprohibit.common.manager.ConfigManager;

import static net.kyori.adventure.text.Component.text;

public class CommandIntroduce {

    private static final Component PluginPrefix = Component.text()
            .append(text("[",NamedTextColor.WHITE))
            .append(text("RSP",NamedTextColor.RED))
            .append(text("] ",NamedTextColor.WHITE))
            .build();

    private static final Component next = Component.text("\n");

    private static final Component LINE = Component.text("------------------------\n",NamedTextColor.BLUE);

    @Contract(" -> new")
    public static @NotNull Component getRoot(){
        return text()
                .append(PluginPrefix,
                        text("RedStarProhibit" , NamedTextColor.RED),
                        text("  运行版本 "+ ConfigManager.versioning , NamedTextColor.AQUA),next)
                .append(PluginPrefix,
                        text("使用 ",NamedTextColor.AQUA),
                        Component.keybind("/rsp help").color(NamedTextColor.GOLD).clickEvent(ClickEvent.suggestCommand("/rsp help")),
                        text(" 来查看命令列表",NamedTextColor.AQUA))
                .build();
    }

    @Contract(" -> new")
    public static @NotNull Component getHelp(){
        return text()
                .append(PluginPrefix,
                        text("RedStarProhibit" , NamedTextColor.RED),
                        text("  运行版本 "+ ConfigManager.versioning , NamedTextColor.AQUA),next)
                .append(PluginPrefix,
                        text("命 令 帮 助",NamedTextColor.AQUA),next)
                .append(getKick())
                .append(LINE)
                .append(getBan())
                .append(LINE)
                .append(getBanHistory())
                .build();
    }

    @Contract(" -> new")
    public static @NotNull Component getKick(){
        return text()
                .append(PluginPrefix,
                        text("/rsp kick",NamedTextColor.GOLD),
                        text("[玩家] [理由]",NamedTextColor.GREEN), next)
                .append(PluginPrefix,
                        text("以",NamedTextColor.AQUA),
                        text("[理由]",NamedTextColor.GREEN),
                        text("在 Velocity上 踢出",NamedTextColor.AQUA),
                        text("[玩家]",NamedTextColor.GREEN))
                .build();
    }

    @Contract(" -> new")
    public static @NotNull Component getBan(){
        return text()
                .append(PluginPrefix,
                        text("/rsp ban", NamedTextColor.GOLD),
                        text("[玩家] [理由]",NamedTextColor.GREEN),next)
                .append(PluginPrefix,
                        text("以",NamedTextColor.AQUA),
                        text("[理由]",NamedTextColor.GREEN),
                        text("在 Velocity上 封禁",NamedTextColor.AQUA),
                        text("[玩家]",NamedTextColor.GREEN),next)
                .append(PluginPrefix,
                        text("使用该指令将永久封禁该玩家",NamedTextColor.RED))
                .build();
    }

    @Contract(" -> new")
    public static @NotNull Component getBanHistory(){
        return text()
                .append(text("a", NamedTextColor.RED))
                .build();
    }

    @Contract(" -> new")
    public static @NotNull Component getUnBan(){
        return text()
                .append(text("a", NamedTextColor.RED))
                .build();
    }

    public static @NotNull Component getPluginPrefix(){
        return PluginPrefix;
    }
}

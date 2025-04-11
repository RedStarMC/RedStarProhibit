package top.redstarmc.redstarprohibit.common.api.components;

import net.kyori.adventure.text.Component;
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

    @Contract(" -> new")
    public static @NotNull Component getRoot(){
        return text()
                .append(PluginPrefix,
                        text("RedStarProhibit" , NamedTextColor.RED),
                        text("  运行版本 "+ ConfigManager.versioning , NamedTextColor.AQUA),next)
                .append(PluginPrefix,
                        text("使用",NamedTextColor.AQUA),
                        text(" /rsp help ",NamedTextColor.GOLD),
                        text("来查看命令列表",NamedTextColor.AQUA),next)
                .build();
    }

    @Contract(" -> new")
    public static @NotNull Component getHelp(){
        return text()
                .append(text("a", NamedTextColor.RED))
                .build();
    }

    @Contract(" -> new")
    public static @NotNull Component getKick(){
        return text()
                .append(text("a", NamedTextColor.RED))
                .build();
    }

}

package top.redstarmc.redstarprohibit.velocity.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import top.redstarmc.redstarprohibit.common.RedStarProhibit;

public class ReloadBuilder implements VCCommandBuilder {
    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return LiteralArgumentBuilder.<CommandSource>literal("reload")
                .requires(source -> source.hasPermission("RedStarProhibit.reload"))
                .executes(context -> {
                    context.getSource().sendMessage(Component.text("测试消息1")); //TODO 编写消息
                    RedStarProhibit.getInstance().onClose();
                    RedStarProhibit.getInstance().onStart();
                    context.getSource().sendMessage(Component.text("测试消息2")); //TODO 编写消息
                    return Command.SINGLE_SUCCESS;
                });
    }
}

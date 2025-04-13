package top.redstarmc.redstarprohibit.velocity.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.CommandSource;
import top.redstarmc.redstarprohibit.common.api.CommandIntroduce;

public class HelpBuilder implements VCCommandBuilder{
    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return LiteralArgumentBuilder.<CommandSource>literal("help")
                .requires(source -> source.hasPermission("RedStarProhibit.help"))
                .executes(context -> {
                    context.getSource().sendMessage(CommandIntroduce.getHelp());
                    return Command.SINGLE_SUCCESS;
                });
    }
}

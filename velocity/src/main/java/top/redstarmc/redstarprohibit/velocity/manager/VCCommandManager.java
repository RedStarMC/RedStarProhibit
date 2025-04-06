package top.redstarmc.redstarprohibit.velocity.manager;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import top.redstarmc.redstarprohibit.common.api.CommandBuilder;
import top.redstarmc.redstarprohibit.common.manager.CommandManger;

public class VCCommandManager implements CommandManger<CommandSource> {
    public VCCommandManager(CommandManager commandManager){
        CommandBuilder<CommandSource> builder = new CommandBuilder<>(this);
        LiteralCommandNode<CommandSource> node = builder.build();
        commandManager.register(new BrigadierCommand(node));
    }
}

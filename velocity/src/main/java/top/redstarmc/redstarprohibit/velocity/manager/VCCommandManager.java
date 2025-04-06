package top.redstarmc.redstarprohibit.velocity.manager;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import top.redstarmc.redstarprohibit.common.manager.CommandManger;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;

public class VCCommandManager implements CommandManger {

    @Override
    public void register() {
        CommandManager commandManager = RedStarProhibitVC.getInstance().getServer().getCommandManager();
        CommandMeta meta = commandManager.metaBuilder("a").plugin(RedStarProhibitVC.getInstance()).build();

        LiteralArgumentBuilder<CommandSource> builder = CommandManger.build();
        BrigadierCommand command = new BrigadierCommand(builder);
        commandManager.register(meta,command);
    }

}

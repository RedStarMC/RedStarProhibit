package top.redstarmc.redstarprohibit.velocity.manager;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import top.redstarmc.redstarprohibit.common.api.CommandIntroduce;
import top.redstarmc.redstarprohibit.common.manager.CommandManger;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;
import top.redstarmc.redstarprohibit.velocity.command.*;

public class VCCommandManager implements CommandManger{

    @Override
    public void init() {
        CommandManager commandManager = RedStarProhibitVC.getInstance().getServer().getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("RedStarProhibit")
                .plugin(RedStarProhibitVC.getInstance())
                .aliases("rsp")
                .build();
        commandManager.register(commandMeta,new BrigadierCommand(build()));
    }


    public LiteralCommandNode<CommandSource> build(){
        return LiteralArgumentBuilder.<CommandSource>literal("RedStarProhibit")
                .requires(source -> source.hasPermission("RedStarProhibit.info"))
                .executes(context -> {
                    context.getSource().sendMessage(CommandIntroduce.getRoot());
                    return Command.SINGLE_SUCCESS;
                })
                .then(new BanBuilder().build())
                .then(new TempBanBuilder().build())
                .then(new UnBanBuilder().build())
                .then(new BanHistoryBuilder().build())
                .then(new KickBuilder().build())
                .then(new HelpBuilder().build())
                .then(new ConfirmBuilder().build())
                .build();
    }

}

package top.redstarmc.redstarprohibit.common.api;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import top.redstarmc.redstarprohibit.common.manager.CommandManger;

public class CommandBuilder<S extends MessagesSender> {

    private final CommandManger<S> commandManger;

    public CommandBuilder(CommandManger<S> commandManger) {
        this.commandManger = commandManger;
    }

    public LiteralCommandNode<S> build(){

        LiteralArgumentBuilder<S> banBuilder = LiteralArgumentBuilder.<S>literal("ban");
//                .then(LiteralArgumentBuilder.<S>literal("player")
//                        .executes(context -> {
//
//                            return 1;
//                        }))
//                .then(RequiredArgumentBuilder.argument("", StringArgumentType.greedyString()));

        LiteralArgumentBuilder<S> tempBanBuilder = LiteralArgumentBuilder.<S>literal("tempBan");
        LiteralArgumentBuilder<S> unBanBuilder = LiteralArgumentBuilder.<S>literal("unBan");
        LiteralArgumentBuilder<S> banHistoryBuilder = LiteralArgumentBuilder.<S>literal("banHistory");
        LiteralArgumentBuilder<S> kickBuilder = LiteralArgumentBuilder.<S>literal("kick");

        return LiteralArgumentBuilder.<S>literal("RedStarProhibit")
                .requires(source -> source.hasPermission("RedStarProhibit.info"))
                .executes(context -> {
                    MessagesSender sender = context.getSource();
                    sender.sendMessage("啊！");
                    return Command.SINGLE_SUCCESS;
                })
                .then(banBuilder)
                .then(tempBanBuilder)
                .then(unBanBuilder)
                .then(banHistoryBuilder)
                .then(kickBuilder)
                .build();
    }

}

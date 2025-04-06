package top.redstarmc.redstarprohibit.common.api;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import top.redstarmc.redstarprohibit.common.manager.CommandManger;

public class CommandBuilder<S> {

    private final CommandManger<S> commandManger;

    public CommandBuilder(CommandManger<S> commandManger) {
        this.commandManger = commandManger;
    }

    public LiteralCommandNode<S> build(){

        LiteralArgumentBuilder<S> banCommandBuilder = LiteralArgumentBuilder.<S>literal("ban")
//                .then(LiteralArgumentBuilder.<S>literal("player")
//                        .executes(context -> {
//
//                            return 1;
//                        }))
                .then(RequiredArgumentBuilder.argument("", StringArgumentType.greedyString()));

        return LiteralArgumentBuilder.<S>literal("RedStarProhibit")
                .then(banCommandBuilder)
                .build();
    }


}

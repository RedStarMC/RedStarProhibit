package top.redstarmc.redstarprohibit.velocity.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.VelocityBrigadierMessage;
import top.redstarmc.redstarprohibit.common.api.CommandIntroduce;
import top.redstarmc.redstarprohibit.common.api.CommandMessage;
import top.redstarmc.redstarprohibit.common.datebase.HistoryResult;
import top.redstarmc.redstarprohibit.common.datebase.operates.QueryOperates;

import java.util.ArrayList;

import static net.kyori.adventure.text.Component.text;

public class BanHistoryBuilder implements VCCommandBuilder {
    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return LiteralArgumentBuilder.<CommandSource>literal("banHistory")
                .requires(source -> source.hasPermission("RedStarProhibit.banHistory"))
                .executes(refuse -> {
                    refuse.getSource().sendMessage(CommandIntroduce.getBanHistory());
                    return Command.SINGLE_SUCCESS;
                })
                .then(BrigadierCommand.requiredArgumentBuilder("player_name", StringArgumentType.string())
                        .suggests((ctx, builder) -> {
                            proxyServer.getAllPlayers().forEach(player -> builder.suggest(
                                    player.getUsername(),
                                    // 提供玩家的名字
                                    VelocityBrigadierMessage.tooltip(text(player.getUsername()))
                            ));
                            builder.suggest("all");
                            return builder.buildFuture();
                        })
                        .executes(context -> {
                            String player_name = context.getArgument("player_name", String.class);
                            String uuid = QueryOperates.UUIDs(sqlManager, player_name);

                            ArrayList<HistoryResult> historyResults = QueryOperates.BanHistory(sqlManager, uuid);

                            if (historyResults == null){
                                context.getSource().sendMessage(CommandMessage.historyNull(player_name));
                                return Command.SINGLE_SUCCESS;
                            }

                            // 展示列表内容


                            return Command.SINGLE_SUCCESS;
                        })
                );
    }
}

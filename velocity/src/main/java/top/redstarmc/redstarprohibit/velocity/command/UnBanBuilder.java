package top.redstarmc.redstarprohibit.velocity.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.VelocityBrigadierMessage;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.format.NamedTextColor;
import top.redstarmc.redstarprohibit.common.api.CommandIntroduce;
import top.redstarmc.redstarprohibit.common.api.CommandMessage;
import top.redstarmc.redstarprohibit.common.datebase.BanResult;
import top.redstarmc.redstarprohibit.common.datebase.operates.DeleteOperates;
import top.redstarmc.redstarprohibit.common.datebase.operates.InsertOperates;
import top.redstarmc.redstarprohibit.common.datebase.operates.QueryOperates;
import top.redstarmc.redstarprohibit.common.manager.H2Manager;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;
import top.redstarmc.redstarprohibit.velocity.manager.VCConfigManager;

import static net.kyori.adventure.text.Component.text;

public class UnBanBuilder implements VCCommandBuilder {
    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return LiteralArgumentBuilder.<CommandSource>literal("unBan")
                .requires(source -> source.hasPermission("RedStarProhibit.unban"))
                .executes(refuse -> {
                    refuse.getSource().sendMessage(CommandIntroduce.getUnBan());
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
                            ServerManager.getManager().debug("取消封禁指令执行");

                            String player_name = context.getArgument("player_name", String.class);
                            String lifter;

                            if(context.getSource() instanceof Player player1){
                                lifter =  player1.getUniqueId().toString();
                            }else {
                                lifter = "Console";
                            }

                            String uuid = QueryOperates.UUIDs(H2Manager.getSqlManager(), player_name);

                            // 查询是否有封禁记录
                            BanResult banResult = QueryOperates.Bans(H2Manager.getSqlManager(), uuid);
                            if (banResult == null) {
                                context.getSource().sendMessage((text("指定的玩家不存在被封禁的记录", NamedTextColor.RED)));
                                return Command.SINGLE_SUCCESS;
                            }

                            if(VCConfigManager.isIsConfirm()){
                                //TODO 命令确认
                                unBanPlayer(uuid, lifter, banResult);

                                context.getSource().sendMessage(CommandMessage.unBan_end(player_name));
                                ServerManager.getManager().info("玩家", context.getSource().toString(), "封禁了", player_name);

                                return Command.SINGLE_SUCCESS;
                            }else {
                                unBanPlayer(uuid, lifter, banResult);

                                context.getSource().sendMessage(CommandMessage.unBan_end(player_name));
                                ServerManager.getManager().info("玩家", context.getSource().toString(), "封禁了", player_name);

                                return Command.SINGLE_SUCCESS;
                            }

                        })
                );
    }

    private void unBanPlayer(String player_uuid, String lifter, BanResult banResult){
        // 添加历史记录
        InsertOperates.BanHistory(sqlManager, player_uuid, banResult.operator(), banResult.until(), banResult.reason(), "user", lifter);
        // 删除封禁记录
        DeleteOperates.Bans(sqlManager, player_uuid);
    }

}

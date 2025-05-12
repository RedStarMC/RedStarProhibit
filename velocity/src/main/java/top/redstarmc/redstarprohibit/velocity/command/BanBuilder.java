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
import top.redstarmc.redstarprohibit.common.datebase.operates.InsertOperates;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;
import top.redstarmc.redstarprohibit.velocity.Listener;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;
import top.redstarmc.redstarprohibit.velocity.manager.VCConfigManager;

import java.sql.Timestamp;

import static net.kyori.adventure.text.Component.text;

public class BanBuilder implements VCCommandBuilder {
    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return LiteralArgumentBuilder.<CommandSource>literal("ban")
                .requires(source -> source.hasPermission("RedStarProhibit.ban"))
                .executes(refuse -> {
                    refuse.getSource().sendMessage(CommandIntroduce.getBan());
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
                    context.getSource().sendMessage(text()
                            .append(CommandIntroduce.getPluginPrefix())
                            .append(text("没有输入理由！", NamedTextColor.RED)));
                    return -1;
                })
                .then(BrigadierCommand.requiredArgumentBuilder("reason", StringArgumentType.greedyString())
                        .executes(context -> {
                            String player_name = context.getArgument("player_name", String.class);
                            String reason = context.getArgument("reason", String.class);

                            if(VCConfigManager.isIsConfirm()){
                                //TODO 命令确认
                                banPlayer(player_name, reason, context.getSource());
                            }else {
                                banPlayer(player_name, reason, context.getSource());
                            }

                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }

    public void banPlayer(String player_name, String reason, CommandSource source){
        ServerManager.getManager().debug("封禁指令执行");
        Player player = RedStarProhibitVC.getInstance().getServer().getPlayer(player_name).orElse(null);

        if (player == null){
            source.sendMessage(text("指定的玩家不存在",NamedTextColor.RED));
            return;
        }

        String uuid = player.getUniqueId().toString();
        ServerManager.getManager().debug("要封禁的uuid为", uuid);
        String operator;

        if(source instanceof Player player1){
            operator =  player1.getUniqueId().toString();
        }else {
            operator = "Console";
        }

        Timestamp until = new Timestamp(Long.MAX_VALUE);
        Timestamp issuedAt = new Timestamp(System.currentTimeMillis());

        InsertOperates.Bans(sqlManager, uuid, operator, until, issuedAt, reason, true);
        
        KickBuilder.kickPlayer(source, Listener.getBanMessage(uuid,operator,until,issuedAt,reason,true), player.getUniqueId());

    }

}

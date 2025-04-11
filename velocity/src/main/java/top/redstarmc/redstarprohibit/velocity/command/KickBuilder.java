package top.redstarmc.redstarprohibit.velocity.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.VelocityBrigadierMessage;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import top.redstarmc.redstarprohibit.common.api.components.CommandIntroduce;

import java.util.Optional;
import java.util.UUID;

import static net.kyori.adventure.text.Component.text;

public class KickBuilder implements VCCommandBuilder {
    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return LiteralArgumentBuilder.<CommandSource>literal("kick")
                .requires(source -> source.hasPermission("RedStarProhibit.kick"))
                .executes(refuse -> {
                    refuse.getSource().sendMessage(CommandIntroduce.getKick());
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
                            context.getSource().sendMessage(text("没有输入参数！"));
                            return -1;
                        })
                        .then(BrigadierCommand.requiredArgumentBuilder("reason", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String player_name = context.getArgument("player_name", String.class);
                                    String reason = context.getArgument("reason", String.class);
                                    kickPlayer(proxyServer,text("测试踢出消息:"+reason),player_name);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );
    }

    public static boolean kickPlayer(ProxyServer server, Component component, UUID uuid){
        Player player = null;
        for(Player p : server.getAllPlayers()){
            if(p.getUniqueId() == uuid){
                player = p;
            }
        }
        if(player == null){
            return false;
        }
        player.disconnect(component);
        return true;
    }

    public static boolean kickPlayer(ProxyServer server, Component component, String player_name){
        Optional<Player> optionalPlayer =  server.getPlayer(player_name);
        if (optionalPlayer.isEmpty()){
            return false;
        }
        optionalPlayer.ifPresent(player -> player.disconnect(component));
        return true;
    }
}

package top.redstarmc.redstarprohibit.velocity.command;

import cc.carm.lib.easysql.api.SQLManager;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import top.redstarmc.redstarprohibit.common.manager.H2Manager;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;
import top.redstarmc.redstarprohibit.velocity.command.tool.QueueCommandManager;

/**
 * <h1>命令构建器接口</h1>
 * 提供了一些常使用的变量
 */
public interface VCCommandBuilder {

    public final QueueCommandManager QUEUE_COMMAND_MANAGER = RedStarProhibitVC.getInstance().getQueueCommandManager();
    public static final ProxyServer proxyServer = RedStarProhibitVC.getInstance().getServer();
    public static final SQLManager sqlManager = H2Manager.getSqlManager();

    public LiteralArgumentBuilder<CommandSource> build();

}

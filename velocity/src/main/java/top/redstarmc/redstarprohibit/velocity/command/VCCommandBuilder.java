package top.redstarmc.redstarprohibit.velocity.command;

import cc.carm.lib.easysql.api.SQLManager;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import top.redstarmc.redstarprohibit.common.managers.H2Manager;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;

public interface VCCommandBuilder {

    public static final ProxyServer proxyServer = RedStarProhibitVC.getInstance().getServer();
    public static final SQLManager sqlManager = H2Manager.getSqlManager();

    public LiteralArgumentBuilder<CommandSource> build();

}

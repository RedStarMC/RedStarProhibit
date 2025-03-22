package top.redstarmc.redstarprohibit.velocity.manager;

import org.jetbrains.annotations.NotNull;
import top.redstarmc.redstarprohibit.common.api.MessagesSender;
import top.redstarmc.redstarprohibit.common.api.ServerType;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;
import top.redstarmc.redstarprohibit.velocity.api.VCMessagesSender;

public class VCServerManager extends ServerManager {

    private static final VCMessagesSender CONSOLE = new VCMessagesSender(RedStarProhibitVC.getInstance().getServer().getConsoleCommandSource());

    /**
     * <h2>获得当前服务器的类型，使用一个枚举</h2>
     *
     * @return 枚举的一种类型
     */
    @Override
    public @NotNull ServerType getServerType() {
        return ServerType.Velocity;
    }

    /**
     * <h2>向服务器发送插件消息</h2>
     * <p>抽象方法，需要子类实现具体操作</p>
     *
     * @param messages 信息字符串
     */
    @Override
    public void broadcast(String... messages) {

    }

    @Override
    public @NotNull MessagesSender getConsoleSender() {
        return CONSOLE;
    }

}

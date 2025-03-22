package top.redstarmc.redstarprohibit.common.manager;

import org.jetbrains.annotations.NotNull;
import top.redstarmc.redstarprohibit.common.api.MessagesSender;
import top.redstarmc.redstarprohibit.common.api.ServerType;

/**
 * <h1>服务器管理器</h1>
 * <h2>主要作用为向 玩家 或 控制台 输出信息</h2>
 */
public abstract class ServerManager {
    public static String INFO_PREFIX = "§b§l[PluginPrefix]§r ";
    private static ServerManager manager;

    protected ServerManager() {
        manager = this;
        INFO_PREFIX = ConfigManager.getConfigManager().getString("PluginPrefix") + "§r ";
    }

    /**
     * <h2>获得当前服务器的类型，使用一个枚举</h2>
     * @return 枚举的一种类型
     */
    @NotNull
    public abstract ServerType getServerType();

    /**
     * <h2>向服务器发送插件消息</h2>
     * <p>抽象方法，需要子类实现具体操作</p>
     * @param messages 信息字符串
     */
    public abstract void broadcast(String... messages);

    /**
     * <h2>发送带前缀的插件消息</h2>
     * @param messages 信息字符串
     */
    public void broadcastPrefix(String... messages) {
        if (messages == null) return;
        for (String message : messages) {
            if (message == null) continue;
            broadcast(INFO_PREFIX + message + "§r");
        }
    }

    /**
     * <h2>发送插件普通信息</h2>
     * @param messages 字符串
     */
    public final void info(String... messages) {
        if (messages == null) return;
        for (String message : messages) {
            if (message == null) continue;
            getConsoleSender().sendMessage("§a[INFO] " + INFO_PREFIX + message + "§r");
        }
    }

    /**
     * <h2>发送插件警告信息</h2>
     * @param messages 字符串
     */
    public final void warn(String... messages) {
        if (messages == null) return;
        for (String message : messages) {
            if (message == null) continue;
            getConsoleSender().sendMessage("§e[WARN] " + INFO_PREFIX + message + "§r");
        }
    }

    /**
     * <h2>发送插件错误信息</h2>
     * @param messages 字符串
     */
    public final void error(String... messages) {
        if (messages == null) return;
        for (String message : messages) {
            if (message == null) continue;
            getConsoleSender().sendMessage("§c[ERROR] " + INFO_PREFIX + message + "§r");
        }
    }

    /**
     * <h2>发送插件debug信息</h2>
     * @param messages 字符串
     */
    public final void debug(String... messages) {
        if (messages == null) return;
        if (ConfigManager.getConfigManager().isDebugMode()) {
            for (String message : messages) {
                if (message == null) continue;
                getConsoleSender().sendMessage("§6[DEBUG] " + INFO_PREFIX + message + "§r");
            }
        }
    }

    /**
     * <h2>发送插件debug堆栈</h2>
     * @param e 堆栈
     */
    public final void debug(Throwable e) {
        if (e == null) return;
        if (ConfigManager.getConfigManager().isDebugMode())
            e.printStackTrace();
    }
    /**
     * <h2>同时发送插件debug信息和堆栈</h2>
     * @param e 堆栈
     * @param msg 字符串
     */
    public final void debug(String msg, Throwable e) {
        if (msg == null || e == null) return;
        if (ConfigManager.getConfigManager().isDebugMode()) {
            debug(msg);
            debug(e);
        }
    }
    /*
        一般方法区
     */
    @NotNull
    public abstract MessagesSender getConsoleSender();
    public static ServerManager getManager() {
        return manager;
    }
}

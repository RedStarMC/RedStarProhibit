package top.redstarmc.redstarprohibit.paper.manager;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import top.redstarmc.redstarprohibit.common.api.MessagesSender;
import top.redstarmc.redstarprohibit.common.api.ServerType;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;
import top.redstarmc.redstarprohibit.paper.RedStarProhibitPA;
import top.redstarmc.redstarprohibit.paper.api.PAMessagesSender;

public class PAServerManager extends ServerManager {
    private static final PAMessagesSender CONSOLE = new PAMessagesSender(Bukkit.getConsoleSender());

    /**
     * <h2>获得当前服务器的类型，使用一个枚举</h2>
     *
     * @return 枚举的一种类型
     */
    @Override
    @NotNull
    public ServerType getServerType() {
        return ServerType.Paper;
        //
    }

    /**
     * <h2>向服务器发送插件消息</h2>
     * <p>抽象方法，需要子类实现具体操作</p>
     *
     * @param messages 信息字符串
     */
    @Override
    public void broadcast(String... messages) {
        for (String message : messages) {
            Bukkit.broadcastMessage(message);
        }
    }

    @Override
    @NotNull
    public MessagesSender getConsoleSender() {
        return CONSOLE;
        //
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> @NotNull T runTaskTimer(Runnable r, long delaySeconds, long periodSeconds) {
        if (r == null) {
            throw new IllegalArgumentException("Runnable cannot be null");
        }
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                r.run();
            }
        };
        if (periodSeconds <= 0L) {
            if (delaySeconds <= 0L) {
                task.runTask(RedStarProhibitPA.getInstance());
            } else {
                task.runTaskLater(RedStarProhibitPA.getInstance(), delaySeconds * 20L);
            }
        } else if (delaySeconds <= 0) {
            task.runTaskTimer(RedStarProhibitPA.getInstance(), 0, periodSeconds * 20);
        } else {
            task.runTaskTimer(RedStarProhibitPA.getInstance(), delaySeconds * 20, periodSeconds * 20);
        }
        return (T) task;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TASK> @NotNull TASK runTaskTimerAsync(Runnable r, long delaySeconds, long periodSeconds) {
        if (r == null) {
            throw new IllegalArgumentException("Runnable cannot be null");
        }
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                r.run();
            }
        };
        if (periodSeconds <= 0L) {
            if (delaySeconds <= 0L) {
                task.runTaskAsynchronously(RedStarProhibitPA.getInstance());
            } else {
                task.runTaskLaterAsynchronously(RedStarProhibitPA.getInstance(), delaySeconds * 20L);
            }
        } else if (delaySeconds <= 0) {
            task.runTaskTimerAsynchronously(RedStarProhibitPA.getInstance(), 0, periodSeconds * 20);
        } else {
            task.runTaskTimerAsynchronously(RedStarProhibitPA.getInstance(), delaySeconds * 20, periodSeconds * 20);
        }
        return (TASK) task;
    }

    @Override
    public void cancelTasks() {
        Bukkit.getScheduler().cancelTasks(RedStarProhibitPA.getInstance());
        //
    }
}

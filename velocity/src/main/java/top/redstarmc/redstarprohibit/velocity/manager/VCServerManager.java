package top.redstarmc.redstarprohibit.velocity.manager;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.Scheduler;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import top.redstarmc.redstarprohibit.common.api.MessagesSender;
import top.redstarmc.redstarprohibit.common.api.ServerType;
import top.redstarmc.redstarprohibit.common.managers.ServerManager;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;
import top.redstarmc.redstarprohibit.velocity.api.VCMessagesSender;

import java.util.concurrent.TimeUnit;

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
        for (String message : messages) {
            if (message == null) continue;
            RedStarProhibitVC.getInstance().getServer().getAllPlayers().forEach(p -> {
                p.sendMessage(Component.text(message));
            });
            getConsoleSender().sendMessage(message);
        }
    }

    @Override
    public @NotNull MessagesSender getConsoleSender() {
        return CONSOLE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TASK> @NotNull TASK runTaskTimer(Runnable r, long delaySeconds, long periodSeconds) {
        Scheduler.TaskBuilder builder = RedStarProhibitVC.getInstance().getServer().getScheduler().buildTask(RedStarProhibitVC.getInstance(), r);
        if (delaySeconds > 0) {
            builder.delay(delaySeconds, TimeUnit.SECONDS);
        }
        if (periodSeconds > 0) {
            builder.repeat(periodSeconds, TimeUnit.SECONDS);
        }
        return (TASK) builder.schedule();
    }

    @Override
    public <TASK> @NotNull TASK runTaskTimerAsync(Runnable r, long delaySeconds, long periodSeconds) {
        return runTaskTimer(r, delaySeconds, periodSeconds);
        //代理端无需异步
    }

    @Override
    public void cancelTasks() {
        for (ScheduledTask task : RedStarProhibitVC.getInstance().getServer().getScheduler().tasksByPlugin(RedStarProhibitVC.getInstance())) {
            task.cancel();
        }
    }

}

package top.redstarmc.redstarprohibit.velocity.command.tool;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.format.NamedTextColor;
import top.redstarmc.redstarprohibit.common.api.CommandIntroduce;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;
import top.redstarmc.redstarprohibit.velocity.manager.VCServerManager;

import java.util.Map;
import java.util.WeakHashMap;

import static net.kyori.adventure.text.Component.text;

public class QueueCommandManager {

    //TODO 超时自动删除队列 不起作用！
    private final Map<CommandSource, QueueCommand> queueCommandMap;

    public QueueCommandManager(){
        this.queueCommandMap = new WeakHashMap<>();
    }

    public void addCommand(QueueCommand command){
        CommandSource source = command.source();

        this.removeCommand(command.source());

        VCServerManager.getManager().debug("添加了一条新命令到命令队列");
        this.queueCommandMap.put(source, command);

        command.source().sendMessage(text()
                .append(CommandIntroduce.getPluginPrefix())
                .append(text("请在30s内输入",NamedTextColor.AQUA))
                .append(text("/rsp confirm",NamedTextColor.GOLD))
                .append(text("来确认你的操作",NamedTextColor.AQUA)));

        VCServerManager.getManager().runTaskLater(expireRunnable(command),30L);
    }

    public Runnable expireRunnable(QueueCommand command){
        return null;
        // () -> {
//            QueueCommand matchingQueueCommand = this.queueCommandMap.get(command.source());
//            if(!command.equals(matchingQueueCommand)){
//                return;
//            }
////            command.source().sendMessage(text()
////                    .append(CommandIntroduce.getPluginPrefix())
////                    .append(text("命令已经过期！", NamedTextColor.RED)));
////            this.queueCommandMap.remove(command.source());
//        };
    }

    public boolean runCommand(CommandSource source){
        QueueCommand queueCommand = this.queueCommandMap.get(source);

        if (queueCommand == null) {
            ServerManager.getManager().debug("命令队列中没有任何命令");
            return false;
        }
        queueCommand.runnable().run();
        return removeCommand(queueCommand.source());
    }

    public boolean removeCommand(CommandSource source){
        QueueCommand previousCommand = this.queueCommandMap.remove(source);
        return previousCommand != null;
    }
}

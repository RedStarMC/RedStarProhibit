package top.redstarmc.redstarprohibit.paper.api;

import org.bukkit.command.CommandSender;
import top.redstarmc.redstarprohibit.common.api.MessagesSender;

public class PAMessagesSender extends MessagesSender {
    private final CommandSender sender;

    public PAMessagesSender(CommandSender sender) {
        this.sender = sender;
        //
    }

    /**
     * <h2>获得发送者名字</h2>
     *
     * @return 名称
     */
    @Override
    public String getName() {
        return sender.getName();
        //
    }

    /**
     * <h2>发送消息的具体实现，对应着ServerManger里面的发送选项</h2>
     *
     * @param msg 消息
     */
    @Override
    public void sendMessage(String... msg) {
        for (String s : msg) {
            if (s == null) continue;
            sender.sendMessage(s);
        }
    }

    /**
     * <h2>检查发送者是否有传入的权限节点</h2>
     *
     * @param s 权限节点
     * @return 是否有
     */
    @Override
    public boolean hasPermission(String s) {
        return sender.hasPermission(s);
    }

}

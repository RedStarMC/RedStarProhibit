package top.redstarmc.redstarprohibit.velocity.api;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import top.redstarmc.redstarprohibit.common.api.MessagesSender;

public class VCMessagesSender extends MessagesSender {
    private final CommandSource sender;

    public VCMessagesSender(CommandSource sender) {
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
        if (sender instanceof Player) {
            return ((Player) sender).getUsername();
        }
        return "Console";
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
            if (sender instanceof ConsoleCommandSource) {
                System.out.println(ColoredConsole.toANSI(s + "§r"));
            } else {
                sender.sendMessage(Component.text(s + "§r"));
            }
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

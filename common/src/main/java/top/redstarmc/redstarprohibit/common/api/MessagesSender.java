package top.redstarmc.redstarprohibit.common.api;

/**
 * <h1>获得信息发送者，通常和ServerManager配置使用</h1>
 */
public abstract class MessagesSender {

    /**
     * <h2>获得发送者名字</h2>
     * @return 名称
     */
    @Deprecated
    public abstract String getName();

    /**
     * <h2>发送消息的具体实现，对应着ServerManger里面的发送选项</h2>
     * @param msg 消息
     */
    public abstract void sendMessage(String... msg);

    /**
     * <h2>检查发送者是否有传入的权限节点</h2>
     * @param s 权限节点
     * @return 是否有
     */
    @Deprecated
    public abstract boolean hasPermission(String s);

}

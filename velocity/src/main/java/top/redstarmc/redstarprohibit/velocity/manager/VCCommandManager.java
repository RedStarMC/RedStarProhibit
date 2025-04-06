package top.redstarmc.redstarprohibit.velocity.manager;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import org.jetbrains.annotations.NotNull;
import top.redstarmc.redstarprohibit.common.api.CommandBuilder;
import top.redstarmc.redstarprohibit.common.manager.CommandManger;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;
import top.redstarmc.redstarprohibit.velocity.api.VCMessagesSender;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class VCCommandManager implements CommandManger<VCMessagesSender> {

    public VCCommandManager(@NotNull CommandManager commandManager) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        CommandMeta commandMeta = commandManager.metaBuilder("RedStarProhibit").plugin(RedStarProhibitVC.getInstance().getServer()).build();

        // 通过反射获取 BrigadierCommand 的构造函数
        Constructor<BrigadierCommand> constructor = BrigadierCommand.class.getConstructor(LiteralCommandNode.class);

        CommandBuilder<VCMessagesSender> builder = new CommandBuilder<>(this);
        LiteralCommandNode<VCMessagesSender> node = builder.build();

        // 使用反射创建 BrigadierCommand 实例
        BrigadierCommand command = constructor.newInstance(node);

        commandManager.register(commandMeta,command);
    }


}

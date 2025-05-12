package top.redstarmc.redstarprohibit.velocity.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.CommandSource;
import top.redstarmc.redstarprohibit.velocity.RedStarProhibitVC;
import top.redstarmc.redstarprohibit.velocity.manager.VCConfigManager;

public class ConfirmBuilder implements VCCommandBuilder{
    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        if (VCConfigManager.isIsConfirm()){
            return LiteralArgumentBuilder.<CommandSource>literal("confirm")
                    .requires(source -> source.hasPermission("RedStarProhibit.confirm"))
                    .executes(context -> {
                        RedStarProhibitVC.getInstance().getQueueCommandManager().runCommand(context.getSource());
                        return Command.SINGLE_SUCCESS;
                    });
        }else {
            return null;
        }
    }
}

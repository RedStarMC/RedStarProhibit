package top.redstarmc.redstarprohibit.velocity.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.CommandSource;

public class BanHistoryBuilder implements VCCommandBuilder {
    @Override
    public LiteralArgumentBuilder<CommandSource> build() {
        return LiteralArgumentBuilder.literal("banHistory");
    }
}

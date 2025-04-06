package top.redstarmc.redstarprohibit.common.manager;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.NotNull;

public interface CommandManger{
    public static <T> @NotNull LiteralArgumentBuilder<T> build(){
        return CommandManger.literalArgumentBuilder("a");

    }

    public static <T> LiteralArgumentBuilder<T> literalArgumentBuilder(@NotNull String name) {
        return LiteralArgumentBuilder.literal(name);
    }
    public void register();
}

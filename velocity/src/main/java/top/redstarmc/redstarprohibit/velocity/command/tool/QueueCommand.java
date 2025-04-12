package top.redstarmc.redstarprohibit.velocity.command.tool;

import com.velocitypowered.api.command.CommandSource;

public record QueueCommand(CommandSource source, Runnable runnable) {

}

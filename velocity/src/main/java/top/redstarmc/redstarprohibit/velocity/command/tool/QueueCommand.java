package top.redstarmc.redstarprohibit.velocity.command.tool;

import com.velocitypowered.api.command.CommandSource;

public record QueueCommand(CommandSource source, Runnable runnable, int validDuration, Runnable expireTask) {


    public QueueCommand(CommandSource source, Runnable runnable, Runnable expireTask) {
        this(source, runnable, 10, expireTask);
    }


    public QueueCommand(CommandSource source, Runnable runnable, int validDuration, Runnable expireTask) {
        this.source = source;
        this.runnable = runnable;
        this.validDuration = validDuration;
        this.expireTask = expireTask;
    }


}

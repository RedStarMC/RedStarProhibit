package top.redstarmc.redstarprohibit.common.datebase;

import cc.carm.lib.easysql.api.SQLAction;
import cc.carm.lib.easysql.api.SQLQuery;
import cc.carm.lib.easysql.api.function.SQLDebugHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CustomDebugHandler implements SQLDebugHandler {
    ServerManager serverManager = ServerManager.getManager();
    @Override
    public void beforeExecute(@NotNull SQLAction<?> action, @NotNull List<@Nullable Object[]> params) {
        serverManager.info("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        serverManager.info("┣# ActionUUID: {}", action.getActionUUID());
        serverManager.info("┣# ActionType: {}" + action.getClass().getSimpleName());
        if (action.getSQLContents().size() == 1) {
            serverManager.info("┣# SQLContent: {}" + action.getSQLContents().get(0));
        } else {
            serverManager.info("┣# SQLContents: ");
            int i = 0;
            for (String sqlContent : action.getSQLContents()) {
                serverManager.info("┃ - [{}] {}", ++i, sqlContent);
            }
        }
        if (params.size() == 1) {
            Object[] param = params.get(0);
            if (param != null) {
                serverManager.info("┣# SQLParam: {}" + parseParams(param));
            }
        } else if (params.size() > 1) {
            serverManager.info("┣# SQLParams: ");
            int i = 0;
            for (Object[] param : params) {
                serverManager.info("┃ - [{}] {}", ++i, parseParams(param));
            }
        }
        serverManager.info("┣# CreateTime: {}" + action.getCreateTime(TimeUnit.MILLISECONDS));
        serverManager.info("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @Override
    public void afterQuery(@NotNull SQLQuery query, long executeNanoTime, long closeNanoTime) {
        serverManager.info("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        serverManager.info("┣# ActionUUID: {}", query.getAction().getActionUUID());
        serverManager.info("┣# SQLContent: {}" + query.getSQLContent());
        serverManager.info("┣# CloseTime: {}  (cost {} ms)",
                TimeUnit.NANOSECONDS.toMillis(closeNanoTime),
                ((double) (closeNanoTime - executeNanoTime) / 1000000)
        );
        serverManager.info("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}

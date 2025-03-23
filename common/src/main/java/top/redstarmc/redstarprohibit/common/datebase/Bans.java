package top.redstarmc.redstarprohibit.common.datebase;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.SQLTable;
import cc.carm.lib.easysql.api.builder.TableCreateBuilder;
import cc.carm.lib.easysql.api.enums.NumberType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;

import java.sql.SQLException;
import java.util.function.Consumer;

public enum Bans implements SQLTable {
    USER((table) -> {
        table.addAutoIncrementColumn("id", NumberType.INT, true, true);
        table.addColumn("uuid", "VARCHAR(32) NOT NULL UNIQUE KEY");
    });

    private final Consumer<TableCreateBuilder> builder;
    private @Nullable String tablePrefix;
    private @Nullable SQLManager manager;

    Bans(Consumer<TableCreateBuilder> builder) {
        this.builder = builder;
    }

    @Override
    public @Nullable SQLManager getSQLManager() {
        return this.manager;
    }

    @Override
    public @NotNull String getTableName() {
        // 枚举的名称即为table的主名称
        return (tablePrefix != null ? tablePrefix : "") + name().toLowerCase();
    }

    @Override
    public boolean create(SQLManager sqlManager) throws SQLException {
        return create(sqlManager, null);
    }

    public boolean create(@NotNull SQLManager sqlManager, @Nullable String tablePrefix) throws SQLException {
        if (this.manager == null) this.manager = sqlManager;
        this.tablePrefix = tablePrefix;

        TableCreateBuilder tableBuilder = sqlManager.createTable(getTableName());
        if (builder != null) builder.accept(tableBuilder);
        return tableBuilder.build().executeFunction(l -> l > 0, false);
    }

    public static void initialize(@NotNull SQLManager manager, @Nullable String tablePrefix) {
        for (Bans value : values()) {
            try {
                value.create(manager, tablePrefix);
            } catch (SQLException e) {
                ServerManager.getManager().error("[数据库] 创建数据表失败");
                ServerManager.getManager().debug("SQLException",e);
            }
        }
    }
}

package top.redstarmc.redstarprohibit.common.datebase;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.SQLTable;
import cc.carm.lib.easysql.api.builder.TableCreateBuilder;
import cc.carm.lib.easysql.api.enums.IndexType;
import cc.carm.lib.easysql.api.enums.NumberType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;

import java.sql.SQLException;
import java.util.function.Consumer;

public enum DateBaseTables implements SQLTable {
    BANS((table) -> {
        //存储现有的记录，同一人只有一条
        table.addAutoIncrementColumn("id", NumberType.INT, true, true);
        table.addColumn("uuid", "VARCHAR(36) NOT NULL UNIQUE KEY");  //被执行者的UUID
        table.addColumn("operator","VARCHAR(36) NOT NULL");  //执行者的UUID
        table.addColumn("until","DATETIME");  //结束的时间
        table.addColumn("issuedAt","DATETIME NOT NULL");  //被执行时的时间
        table.addColumn("reason","TEXT NOT NULL");  //理由
//        table.addColumn("type","ENUM('ban','warn') NOT NULL"); //类型
        table.addColumn("isForever","BOOLEAN NOT NULL"); //是否永久封禁

        table.setIndex("uuid", IndexType.INDEX);
    }),
    BAN_HISTORY((table) -> {
        //存储历史记录，同一人可以有多条
        table.addAutoIncrementColumn("id", NumberType.INT, true, true);
        table.addColumn("uuid", "VARCHAR(36) NOT NULL");  //被执行者的UUID
        table.addColumn("operator","VARCHAR(36) NOT NULL");  //执行者的UUID
        table.addColumn("until","DATETIME");  //持续的时间
        table.addColumn("reason","TEXT NOT NULL");  //理由
//        table.addColumn("type","ENUM('ban','warn') NOT NULL"); //类型
        table.addColumn("liftAs","ENUM('sys','console','user') NOT NULL"); //是否永久封禁
        table.addColumn("lifter","VARCHAR(36) NOT NULL");  //执行解除者的UUID

        table.setIndex("uuid", IndexType.INDEX);
    });

    private final Consumer<TableCreateBuilder> builder;
    private @Nullable SQLManager manager;

    DateBaseTables(Consumer<TableCreateBuilder> builder) {
        this.builder = builder;
    }

    @Override
    public @Nullable SQLManager getSQLManager() {
        return this.manager;
    }

    @Override
    public @NotNull String getTableName() {
        return "ERROR_Tables";
    }


    @Override
    public boolean create(SQLManager sqlManager) throws SQLException {
        return create(sqlManager,"ERROR_Tables");
    }

    public boolean create(@NotNull SQLManager sqlManager, @NotNull String tableName) throws SQLException {
        if (this.manager == null) this.manager = sqlManager;

        TableCreateBuilder tableBuilder = sqlManager.createTable(tableName);
        if (builder != null) builder.accept(tableBuilder);
        return tableBuilder.build().executeFunction(l -> l > 0, false);
    }

    public static void initializeBANS(@NotNull SQLManager manager, @NotNull String tablePrefix) {
        try {
            BANS.create(manager, tablePrefix+"BANS");
        } catch (SQLException e) {
            ServerManager.getManager().error("[数据库] 创建数据表 BANS 失败");
            ServerManager.getManager().debug("SQLException",e);
        }
    }
    public static void initializeBAN_HISTORY(@NotNull SQLManager manager, @NotNull String tablePrefix) {
        try {
            BAN_HISTORY.create(manager, tablePrefix+"BAN_HISTORY");
        } catch (SQLException e) {
            ServerManager.getManager().error("[数据库] 创建数据表 BAN_HISTORY 失败");
            ServerManager.getManager().debug("SQLException",e);
        }
    }
}

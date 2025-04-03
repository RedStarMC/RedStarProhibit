package top.redstarmc.redstarprohibit.common.datebase;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.builder.TableMetadataBuilder;

import java.util.concurrent.CompletableFuture;

/**
 * <h1>执行数据库  查询  相关操作</h1>
 * 该类操作全为静态方法，可直接调用，调用时需使用ServerManager类辅助
 */
public class QueryOperates {

    /**
     * <h2>判断指定名称的表是否存在</h2>
     * @param sqlManager SQLManager 实例
     * @param tableName 表名
     * @return 若表存在返回 true，否则返回 false
     */
    public static CompletableFuture<Boolean> isTableExists(SQLManager sqlManager, String tableName) {
        TableMetadataBuilder metadataBuilder = sqlManager.fetchTableMetadata(tableName);
        return metadataBuilder.validateExist();
    }




}

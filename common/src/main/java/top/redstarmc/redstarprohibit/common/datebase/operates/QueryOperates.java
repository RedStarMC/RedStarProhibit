package top.redstarmc.redstarprohibit.common.datebase.operates;

import cc.carm.lib.easysql.api.SQLManager;
import top.redstarmc.redstarprohibit.common.datebase.Result;
import top.redstarmc.redstarprohibit.common.manager.H2Manager;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <h1>执行数据库  查询  相关操作</h1>
 * 该类操作全为静态方法，可直接调用，调用时需使用ServerManager类辅助
 */
public class QueryOperates {

    public static Result Bans(SQLManager sqlManager, String uuid) {
        AtomicReference<Result> result = new AtomicReference<>();
        sqlManager.createQuery()
                .inTable(H2Manager.tablePrefix+"_BANS")
                .selectColumns("uuid")
                .addCondition(uuid)
                .build().execute(
                        (query) -> {
                            long time = query.getExecuteTime();
                            ServerManager.getManager().debug("查询耗时,"+time);

                            ResultSet result_set = query.getResultSet();
                            if(!result_set.next()){
                                return 0;
                            }
                            String reason = result_set.getString("reason");
                            String operator = result_set.getString("operator");
                            boolean isForever = result_set.getBoolean("isForever");
                            Timestamp until = result_set.getTimestamp("until");
                            Timestamp issuedAt = result_set.getTimestamp("issuedAt");

                            Timestamp now = new Timestamp(System.currentTimeMillis());

                            if(until.compareTo(now) < 0){
                                return 0;
                            }


                            result.set(new Result(uuid, operator, until, issuedAt, reason, isForever));

                            return 1;
                        },
                        ((exception, sqlAction) -> {

                        })
                );
        return result.get();
    }
// TODO

//    public static ResultSet Bans(SQLManager sqlManager, String operator,boolean isOperator) {
//        AtomicReference<Result> result = new AtomicReference<>();
//        sqlManager.createQuery()
//                .inTable(H2Manager.tablePrefix+"_BANS")
//                .selectColumns("uuid")
//                .addCondition(uuid)
//                .build().execute(
//                        (query) -> {
//                            long time = query.getExecuteTime();
//                            ServerManager.getManager().debug("查询耗时,"+time);
//
//                            ResultSet result_set = query.getResultSet();
//                            if(!result_set.next()){
//                                return 0;
//                            }
//                            String reason = result_set.getString("reason");
//                            String operator = result_set.getString("operator");
//                            String type = result_set.getString("type");
//                            boolean isForever = result_set.getBoolean("isForever");
//                            Timestamp until = result_set.getTimestamp("until");
//                            Timestamp issuedAt = result_set.getTimestamp("issuedAt");
//
//                            Timestamp now = new Timestamp(System.currentTimeMillis());
//
//                            if(until.compareTo(now) < 0){
//                                return 0;
//                            }
//
//                            result.set(new Result(uuid, operator, until, issuedAt, reason, type, isForever));
//
//                            return 1;
//                        },
//                        ((exception, sqlAction) -> {
//
//                        })
//                );
//        return result.get();
//    }

}

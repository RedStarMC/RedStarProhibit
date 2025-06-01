package top.redstarmc.redstarprohibit.common.datebase;

import java.sql.Timestamp;

public record HistoryResult(int id, String uuid, String operator, Timestamp until,
                            String reason, String liftAs, String lifter) {
}

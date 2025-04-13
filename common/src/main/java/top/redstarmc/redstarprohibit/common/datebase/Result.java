package top.redstarmc.redstarprohibit.common.datebase;

import java.sql.Timestamp;

public record Result(String uuid, String operator, Timestamp until,
                     Timestamp issuedAt, String reason, boolean isForever) {
}

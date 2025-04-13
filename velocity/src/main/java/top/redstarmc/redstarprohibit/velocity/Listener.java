package top.redstarmc.redstarprohibit.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import top.redstarmc.redstarprohibit.common.datebase.Result;
import top.redstarmc.redstarprohibit.common.datebase.operates.QueryOperates;
import top.redstarmc.redstarprohibit.common.manager.H2Manager;

import java.sql.Timestamp;

import static net.kyori.adventure.text.Component.text;

public class Listener {

    @Subscribe
    public void onPreLoginEvent(@NotNull PreLoginEvent event) {
        PreLoginEvent.PreLoginComponentResult r;
        String uuid = null;
        if (event.getUniqueId() != null) {
            uuid = event.getUniqueId().toString();
        }
        if(uuid == null){
            return;
        }

        Result result = QueryOperates.Bans(H2Manager.getSqlManager(),uuid);
        if (result == null){
            return;
        }else {
            Component component = getBanMessage(uuid, result.operator(), result.until()
                    , result.issuedAt(), result.reason(), result.isForever());
            r = PreLoginEvent.PreLoginComponentResult.denied(component);
        }
        event.setResult(r);
    }

    public static Component getBanMessage(String uuid, String operator, Timestamp until,
                                   Timestamp issuedAt, String reason, boolean isForever){
        if(isForever){
            return text()
                    .append(text("你已被 永久 封禁！\n", NamedTextColor.RED))
                    .append(text("你的UUID 为：",NamedTextColor.RED),text(uuid+"\n",NamedTextColor.AQUA))
                    .append(text("操作员：",NamedTextColor.RED),text(operator+"\n",NamedTextColor.AQUA))
                    .append(text("封禁时间：",NamedTextColor.RED),text(issuedAt+"\n",NamedTextColor.AQUA))
                    .append(text("理由：",NamedTextColor.RED),text(reason,NamedTextColor.AQUA))
                    .build();
        }else {
            return text()
                    .append(text("你已被 临时 封禁！\n", NamedTextColor.RED))
                    .append(text("你的UUID 为：",NamedTextColor.RED),text(uuid+"\n",NamedTextColor.AQUA))
                    .append(text("操作员：",NamedTextColor.RED),text(operator+"\n",NamedTextColor.AQUA))
                    .append(text("封禁时间：",NamedTextColor.RED),text(issuedAt+"\n",NamedTextColor.AQUA))
                    .append(text("解封时间：",NamedTextColor.RED),text(until+"\n",NamedTextColor.GOLD))
                    .append(text("理由：",NamedTextColor.RED),text(reason,NamedTextColor.AQUA))
                    .build();
        }

    }
}

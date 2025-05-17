package top.redstarmc.redstarprohibit.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import top.redstarmc.redstarprohibit.common.datebase.Result;
import top.redstarmc.redstarprohibit.common.datebase.operates.InsertOperates;
import top.redstarmc.redstarprohibit.common.datebase.operates.QueryOperates;
import top.redstarmc.redstarprohibit.common.manager.H2Manager;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;

import java.sql.Timestamp;

import static net.kyori.adventure.text.Component.text;

public class Listener {

    @Subscribe
    public void onPreLoginEvent(@NotNull PreLoginEvent event) {
        String uuid = QueryOperates.UUIDs(H2Manager.getSqlManager(), event.getUsername());
        ServerManager.getManager().debug("处理 PreLoginEvent ，UUID: " + uuid);

        if (uuid == null) {
            return;
        }

        Result result = QueryOperates.Bans(H2Manager.getSqlManager(), uuid);
        if (result == null) {
            ServerManager.getManager().debug("玩家 " + uuid + " 未被封禁，允许登录");
        } else {
            ServerManager.getManager().info("玩家 " + uuid + " 已被封禁，拒绝登录");
            Component component = getBanMessage(uuid, result.operator(), result.until(),
                    result.issuedAt(), result.reason(), result.isForever());
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(component));
        }
    }

    @Subscribe
    public void onServerPostConnectEvent(@NotNull ServerPostConnectEvent event){
        ServerManager.getManager().debug("处理 ServerPostConnectEvent ");
        Player player_tmp = event.getPlayer();
        String name = player_tmp.getUsername();
        Player player = RedStarProhibitVC.getInstance().getServer().getPlayer(name).orElse(null);

        //TODO 是否需要每次更新uuid

        if (player == null) return;
        String temp = QueryOperates.UUIDs(H2Manager.getSqlManager(), player.getUsername());

        if (temp == null) {
            InsertOperates.User_Uuid(H2Manager.getSqlManager(), player.getUsername(), player.getUniqueId().toString());
        }
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

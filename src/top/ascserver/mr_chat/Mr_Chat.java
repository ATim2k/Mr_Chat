package top.ascserver.mr_chat;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.plugin.PluginBase;
import cn.yzq25.login.LoginMain;
import java.util.Date;
import xyz.him188.login.LoginAPI;
import xyz.mrsky.mr_love.Mr_Love;

public class Mr_Chat extends PluginBase implements Listener {
    private String QX = "管理员";
    private String GM = "生存模式";
    private static Mr_Chat plugin;

    public Mr_Chat() {
    }

    public static Mr_Chat getPlugin() {
        return plugin;
    }

    public void onEnable() {
        plugin = this;
        update update = new update();
        update.updateCheck();
        if(this.checkMr_LovePlugin()) {
            this.getLogger().info("检测到Mr_Love插件，配套功能已开启");
        } else {
            this.getLogger().info("没有检测到Mr_Love插件，配套功能已关闭");
        }

        if(this.checkechoLoginPlugin()) {
            this.getLogger().info("检测到有两个登录插件，本插件无法自适应,请删除一个后在使用本插件");
            this.getServer().getPluginManager().disablePlugin(this.getServer().getPluginManager().getPlugin("Mr_Chat"));
        } else if(this.checkLoginPlugin()) {
            this.getLogger().info("检测到Login插件，密码防泄漏功能已开启");
            this.getServer().getPluginManager().registerEvents(this, this);
            this.getLogger().info("插件正在开启,作者Mr_sky,QQ861421800,zxda-uid9886(贱哥啊哈哈)");
        } else if(this.checkZQLoginPlugin()) {
            this.getLogger().info("没有检测到Login插件，正在检测其他登录插件");
            this.getLogger().info("检测到ZQLogin插件，密码防泄漏功能已开启");
            this.getServer().getPluginManager().registerEvents(this, this);
            this.getLogger().info("插件正在开启,作者Mr_sky,QQ861421800,zxda-uid9886(贱哥啊哈哈)");
        } else {
            this.getLogger().info("没有检测到已自动配置的登录插件,如有其他登录插件，请联系本插件作者取得帮助");
            this.getServer().getPluginManager().disablePlugin(this.getServer().getPluginManager().getPlugin("Mr_Chat"));
        }

    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        String playername = player.getName();
        if(player.getGamemode() == 0) {
            this.GM = String.valueOf(this.getConfig().get("生存模式"));
        }

        if(player.getGamemode() == 1) {
            this.GM = String.valueOf(this.getConfig().get("创造模式"));
        }

        if(player.getGamemode() == 2) {
            this.GM = String.valueOf(this.getConfig().get("冒险模式"));
        }

        if(player.getGamemode() == 3) {
            this.GM = String.valueOf(this.getConfig().get("旁观模式"));
        }

        if(player.isOp()) {
            this.QX = String.valueOf(this.getConfig().get("OP"));
        }

        if(!player.isOp()) {
            this.QX = String.valueOf(this.getConfig().get("玩家"));
        }

        String msg = event.getMessage();
        String level = player.getLevel().getName();
        String geshi = this.getConfig().get("格式").toString();
        String levelname = geshi.replace("{level}", level);
        String GMname = levelname.replace("{GM}", this.GM);
        String QXname = GMname.replace("{QX}", this.QX);
        String Timename = QXname.replace("{time}", this.onCurrentTime());
        String playern = Timename.replace("{name}", playername);
        String msgname = playern.replace("{msg}", msg);
        String love;
        if(this.checkMr_LovePlugin()) {
            love = msgname.replace("{love}", Mr_Love.getPlugin().GetPlayerLoves(event.getPlayer().getName()));
        } else {
            love = msgname;
        }

        if(this.checkLoginPlugin()) {
            if(LoginAPI.getInstance().isRegistered(event.getPlayer()) && LoginAPI.getInstance().isLogined(event.getPlayer())) {
                this.getServer().broadcastMessage(love);
            }
        } else if(this.checkZQLoginPlugin() && LoginMain.getInstance().isRegistered(event.getPlayer()) && LoginMain.getInstance().isLogined(event.getPlayer())) {
            this.getServer().broadcastMessage(love);
        }

    }

    public String onCurrentTime() {
        Date time = new Date();
        int hours = time.getHours();
        int minutes = time.getMinutes();
        int seconds = time.getSeconds();
        return hours + ":" + minutes + ":" + seconds;
    }

    public boolean checkechoLoginPlugin() {
        return this.getServer().getPluginManager().getPlugins().containsKey("Login") && this.getServer().getPluginManager().getPlugins().containsKey("ZQLogin");
    }

    public boolean checkLoginPlugin() {
        return this.getServer().getPluginManager().getPlugins().containsKey("Login");
    }

    public boolean checkZQLoginPlugin() {
        return this.getServer().getPluginManager().getPlugins().containsKey("ZQLogin");
    }

    public boolean checkMr_LovePlugin() {
        return this.getServer().getPluginManager().getPlugins().containsKey("Mr_Love");
    }
}

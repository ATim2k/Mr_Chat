package top.ascserver.mr_chat;

import cn.nukkit.utils.TextFormat;
import sun.misc.BASE64Decoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by WE·JianGe on 2017/3/2.
 */
public class update {
    public void updateCheck(){
        try {
            String a;
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer("aHR0cDovL21yc2t5Lnh5ei9wbHVnaW5zL01yX0NoYXQvdXBkYXRlLmh0bWw=");
            String url = new String(b,"utf-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream(), "GB2312"));//GB2312可以根据需要替换成要读取网页的编码
            while ((a = in.readLine()) != null) {
                if (a.equals("Mr_Chat v1.1.0")){
                    Mr_Chat.getPlugin().getLogger().info(TextFormat.GREEN+"插件已是最新版本,无需更新!");
                }else{
                    Mr_Chat.getPlugin().getLogger().info(TextFormat.YELLOW+"插件有新版本");
                    updateMessage();
                }
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }
    public void updateMessage(){
        try {
            String a;
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer("aHR0cDovL21yc2t5Lnh5ei9wbHVnaW5zL01yX0NoYXQvdXBkYXRlbWVzc2FnZS5odG1s");
            String url = new String(b,"utf-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream(), "GB2312"));//GB2312可以根据需要替换成要读取网页的编码
            while ((a = in.readLine()) != null) {
                Mr_Chat.getPlugin().getLogger().info(TextFormat.YELLOW + a);
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }
}

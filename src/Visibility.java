package akogen.fabric.v6;
import java.io.*;import java.util.*;import com.google.gson.*;
/** Local viewing preference; equipped cosmetics and network announcements stay intact. */
public final class Visibility{
 public enum Mode{ENABLED("Enabled"),DISABLED("Disabled"),SELF("Show It Yourself");public final String label;Mode(String label){this.label=label;}}
 public static Mode mode=Mode.ENABLED;
 public static boolean local(){return mode!=Mode.DISABLED;}
 public static boolean others(){return mode==Mode.ENABLED;}
 static File file(){return Session.logPath==null?null:new File(new File(Session.logPath).getAbsoluteFile().getParentFile(),"visibility.json");}
 public static void load(){File file=file();if(file==null||!file.isFile())return;try(Reader r=new InputStreamReader(new FileInputStream(file),"UTF-8")){mode=Mode.valueOf(new JsonParser().parse(r).getAsJsonObject().get("mode").getAsString());}catch(Exception ignored){mode=Mode.ENABLED;}}
 public static void set(Mode next){if(next==null||next==mode)return;mode=next;World.motions.clear();for(Assets.Entry e:Session.equipped.values())e.placed=false;for(CosmeticSync.Remote remote:CosmeticSync.players())for(Assets.Entry e:remote.equipped.values())e.placed=false;File file=file();if(file!=null)try{file.getParentFile().mkdirs();try(Writer w=new OutputStreamWriter(new FileOutputStream(file),"UTF-8")){JsonObject o=new JsonObject();o.addProperty("mode",mode.name());w.write(o.toString());}}catch(Exception e){Session.error(e);}Session.log("VIEW: "+next.label);}
}

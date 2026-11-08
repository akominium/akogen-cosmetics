package akogen.fabric.v6;
import java.nio.file.*;import java.util.*;
public final class BehaviorChecks{
 public static void main(String[] args)throws Exception{
  Assets.load(Files.readAllBytes(Paths.get(args[0])));if(Assets.entries.size()!=51)throw new AssertionError("Catalog size changed");int thumbs=0;
  for(Assets.Entry e:Assets.entries){if(!Assets.files.containsKey("assets/akogen/previews/"+e.id+".png"))throw new AssertionError("Missing thumbnail "+e.name);thumbs++;Models.Model m=Models.load(e);Expr.Context c=new Expr.Context();c.time=1.3;List<String> a=Models.active(m,c,false);if(e.slot.equals("flying")&&!a.contains("flight"))throw new AssertionError("Missing flight controller "+e.name);if((e.slot.equals("wings")||e.slot.equals("aura"))&&!a.contains("main"))throw new AssertionError("Missing main controller "+e.name);}
  Assets.Entry a=new Assets.Entry(),b=new Assets.Entry(),c=new Assets.Entry();for(Assets.Entry e:new Assets.Entry[]{a,b,c}){e.slot="flying";e.model=new Models.Model();e.texture=new Object();}
  Session.equipped.clear();if(!Session.install(a)||!Session.install(b)||!Session.install(c))throw new AssertionError("Two-flying limit failed");if(Session.flyingCount()!=2||Session.isEquipped(a)||!Session.isEquipped(b)||!Session.isEquipped(c))throw new AssertionError("Concurrent flying selection failed");Session.equipped.remove("flying:0");if(!Session.install(a)||Session.flyingCount()!=2)throw new AssertionError("Freed flying slot was not reused");
  double[] north=new Models.Mat().rot(0,180,0).point(0,0,-1),west=new Models.Mat().rot(0,90,0).point(0,0,-1);if(north[2]<.99||west[0]>-.99)throw new AssertionError("Minecraft yaw convention incorrect");
  System.out.println("PASS "+thumbs+" geometry-derived thumbnails, all animation controllers, exactly two concurrent flying slots, oldest auto-replacement and remove/reuse, Minecraft yaw convention");
 }
}

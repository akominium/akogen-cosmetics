package akogen.fabric.v6;
import net.fabricmc.api.ClientModInitializer;import net.fabricmc.loader.api.FabricLoader;import java.nio.file.*;import java.io.*;
public final class ModBootstrap implements ClientModInitializer {
 static byte[] resource(String name)throws IOException{try(InputStream in=ModBootstrap.class.getResourceAsStream("/"+name)){if(in==null)throw new FileNotFoundException(name);ByteArrayOutputStream out=new ByteArrayOutputStream();byte[] buf=new byte[8192];int n;while((n=in.read(buf))!=-1)out.write(buf,0,n);return out.toByteArray();}}
 public void onInitializeClient(){try{Path config=FabricLoader.getInstance().getConfigDir().resolve("akogen");Files.createDirectories(config);ClassLoader loader=getClass().getClassLoader();Session.start(Class.forName("net.minecraft.class_310",true,loader),loader,resource("cosmetics.zip"),resource("names.json"),config.resolve("session.log").toAbsolutePath().toString());}catch(Throwable issue){throw new IllegalStateException("Unable to start Akogen Cosmetics for Minecraft 1.21.11",issue);}}
}

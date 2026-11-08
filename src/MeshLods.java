package akogen.fabric.v6;
import java.util.*;import java.io.*;import java.util.zip.*;
/** Precomputed attribute-aware LODs. No model reduction runs on the render thread. */
public final class MeshLods{
 static Map<String,byte[]> data;
 static void init()throws Exception{if(data!=null)return;Map<String,byte[]> loaded=new HashMap<String,byte[]>();StringBuilder s=new StringBuilder();for(String part:MeshLodData.CHUNKS)s.append(part);try(ZipInputStream in=new ZipInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(s.toString())))){ZipEntry e;byte[] buf=new byte[8192];while((e=in.getNextEntry())!=null){ByteArrayOutputStream bytes=new ByteArrayOutputStream();int n;while((n=in.read(buf))>0)bytes.write(buf,0,n);loaded.put(e.getName(),bytes.toByteArray());}}data=loaded;}
 @SuppressWarnings("unchecked") static void attach(Models.Bone bone,String path)throws Exception{init();if(!data.containsKey("0/"+path))return;bone.lods=(List<float[]>[])new List<?>[3];for(int level=0;level<3;level++){List<float[]> faces=new ArrayList<float[]>();try(DataInputStream in=new DataInputStream(new ByteArrayInputStream(data.get(level+"/"+path)))){if(in.readInt()!=1448237361)throw new IOException("LOD header");int count=in.readInt();if(count<1||count>bone.faces.size())throw new IOException("LOD count");for(int i=0;i<count;i++){float[] face=new float[32];for(int j=0;j<32;j++){float v=in.readFloat();if(!Float.isFinite(v))throw new IOException("Invalid LOD vertex");face[j]=v;}for(int j=0;j<4;j++)for(int k=0;k<3;k++)face[j*8+k]*=16;faces.add(face);}}bone.lods[level]=faces;}}
 static List<float[]> geometry(Models.Bone bone){if(bone.lods==null)return bone.faces;List<float[]> all=new ArrayList<float[]>(bone.faces);for(List<float[]> faces:bone.lods)all.addAll(faces);return all;}
 public static List<float[]> faces(Models.Bone bone,double distance){return bone.lods==null?bone.faces:bone.lods[distance<10?0:distance<22?1:2];}
}

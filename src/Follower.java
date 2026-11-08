package akogen.fabric.v6;
/** Ground companions follow actual owner translation, never camera/head turns. */
public final class Follower {
 static double wrap(double angle){return World.wrap(angle);}
 static void target(Assets.Entry pet,double x,double z){double radians=Math.toRadians(pet.followYaw);pet.targetX=x+Math.sin(radians)*1.85+Math.cos(radians)*.8;pet.targetZ=z-Math.cos(radians)*1.85+Math.sin(radians)*.8;}
 public static boolean update(Assets.Entry pet,double x,double y,double z,double initialYaw,double dt){
  if(!pet.placed){pet.ownerX=x;pet.ownerZ=z;pet.followYaw=initialYaw;target(pet,x,z);pet.px=pet.targetX;pet.pz=pet.targetZ;pet.py=pet.groundY=y;pet.groundAt=0;pet.yaw=initialYaw;pet.speed=0;pet.following=false;pet.running=false;pet.placed=true;}
  double ownerDx=x-pet.ownerX,ownerDz=z-pet.ownerZ,ownerStep=Math.hypot(ownerDx,ownerDz);
  if(ownerStep>.025){double desired=Math.toDegrees(Math.atan2(-ownerDx,ownerDz));double turn=1-Math.exp(-Math.max(dt,Math.min(.15,ownerStep/2.9))*8);pet.followYaw+=wrap(desired-pet.followYaw)*turn;pet.ownerX=x;pet.ownerZ=z;target(pet,x,z);}
  double dx=pet.targetX-pet.px,dz=pet.targetZ-pet.pz,distance=Math.hypot(dx,dz);
  double ownerGap=Math.hypot(x-pet.px,z-pet.pz);boolean dingiller="Dingiller".equals(pet.group);if(dingiller){if(ownerGap>3.8||distance>3)pet.running=true;else if(ownerGap<2.8&&distance<1.1)pet.running=false;}else pet.running=false;
  if(ownerGap>(dingiller?24:18)){pet.px=pet.targetX;pet.pz=pet.targetZ;pet.groundAt=0;pet.speed=0;pet.following=false;pet.running=false;return false;}
  if(distance<.22)pet.following=false;else if(distance>.58)pet.following=true;
  double step=pet.following?Math.min(Math.max(0,distance-.18),dt*(pet.running?Math.min(8,5.4+distance*.35):(distance>5?5:2.9))):0;
  if(step<dt*.08){pet.speed=0;pet.running=false;return false;}
  pet.px+=dx/distance*step;pet.pz+=dz/distance*step;pet.speed+=(step/dt-pet.speed)*(1-Math.exp(-dt*12));pet.yaw+=wrap(Math.toDegrees(Math.atan2(-dx,dz))-pet.yaw)*(1-Math.exp(-dt*8));return true;
 }
}

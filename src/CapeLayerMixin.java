package akogen.fabric.v6.mixin;
import akogen.fabric.v6.CapeBridge;import org.spongepowered.asm.mixin.Mixin;import org.spongepowered.asm.mixin.injection.*;import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(targets="net.minecraft.class_972",remap=false)
public abstract class CapeLayerMixin {
 @Inject(method="method_4177(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_10055;FF)V",at=@At("HEAD"),remap=false,require=1)
 private void akogen$cape(@Coerce Object poses,@Coerce Object submit,int light,@Coerce Object avatar,float limb,float amount,CallbackInfo ci){CapeBridge.apply(avatar);}
}

package akogen.fabric.v6.mixin;
import akogen.fabric.v6.CapeBridge;import org.spongepowered.asm.mixin.Mixin;import org.spongepowered.asm.mixin.injection.*;import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(targets="net.minecraft.class_979",remap=false)
public abstract class WingsLayerMixin {
 @Inject(method="method_64084(Lnet/minecraft/class_10034;)Lnet/minecraft/class_2960;",at=@At("HEAD"),remap=false,require=1)
 private static void akogen$elytra(@Coerce Object avatar,CallbackInfoReturnable<Object> cir){CapeBridge.apply(avatar);}
}

package me.biquaternions.benchmark.mixin;

import me.biquaternions.benchmark.area.AreaType;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftServer.class)
public class MinecraftServer_Mixin {

    @Redirect(
            method = "runServer",
            at = @At(
                    target = "Lnet/minecraft/server/MinecraftServer;processPacketsAndTick(Z)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickLoop(MinecraftServer instance, boolean sprinting) {
        final long start = System.nanoTime();
        instance.processPacketsAndTick(sprinting);
        AreaType.TICK_SERVER.getProfiler().push(System.nanoTime() - start);
    }



    @Redirect(
            method = "tickChildren",
            at = @At(
                    target = "Lnet/minecraft/server/MinecraftServer;tickConnection()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickConnection(MinecraftServer instance) {
        final long start = System.nanoTime();
        instance.tickConnection();
        AreaType.TICK_CONNECTION.getProfiler().push(System.nanoTime() - start);
    }

}

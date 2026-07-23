package me.biquaternions.benchmark.mixin;

import me.biquaternions.benchmark.util.ProfilerUtils;
import net.minecraft.server.level.ServerChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerChunkCache.class)
public class ServerChunkCache_Mixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerChunkCache;tickChunks()V"
            )
    )
    private void benchmark$beforeTickChunks(BooleanSupplier haveTime, boolean tickChunks, CallbackInfo ci) {
        ProfilerUtils.recordStartTickChunks();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerChunkCache;tickChunks()V",
                    shift = At.Shift.AFTER
            )
    )
    private void benchmark$afterTickChunks(BooleanSupplier haveTime, boolean tickChunks, CallbackInfo ci) {
        ProfilerUtils.recordStopTickChunks();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ChunkMap;tick()V"
            )
    )
    private void benchmark$beforeTickEntityTracking(BooleanSupplier haveTime, boolean tickChunks, CallbackInfo ci) {
        ProfilerUtils.recordStartTickEntityTracking();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ChunkMap;tick()V",
                    shift = At.Shift.AFTER
            )
    )
    private void benchmark$afterTickEntityTracking(BooleanSupplier haveTime, boolean tickChunks, CallbackInfo ci) {
        ProfilerUtils.recordStopTickEntityTracking();
    }

}

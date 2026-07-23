package me.biquaternions.benchmark.mixin;

import me.biquaternions.benchmark.util.ProfilerUtils;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class ServerLevel_Mixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/ticks/LevelTicks;tick(JILjava/util/function/BiConsumer;)V",
                    ordinal = 0
            )
    )
    private void benchmark$beforeTickBlocks(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStartTickBlocks();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/ticks/LevelTicks;tick(JILjava/util/function/BiConsumer;)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void benchmark$afterTickBlocks(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStopTickBlocks();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/ticks/LevelTicks;tick(JILjava/util/function/BiConsumer;)V",
                    ordinal = 1
            )
    )
    private void benchmark$beforeTickFluids(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStartTickFluids();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/ticks/LevelTicks;tick(JILjava/util/function/BiConsumer;)V",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void benchmark$afterTickFluids(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStopTickFluids();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerChunkCache;tick(Ljava/util/function/BooleanSupplier;Z)V"
            )
    )
    private void benchmark$beforeTickChunkSource(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStartTickChunkSources();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerChunkCache;tick(Ljava/util/function/BooleanSupplier;Z)V",
                    shift = At.Shift.AFTER
            )
    )
    private void benchmark$afterTickChunkSource(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStopTickChunkSources();
    }

}

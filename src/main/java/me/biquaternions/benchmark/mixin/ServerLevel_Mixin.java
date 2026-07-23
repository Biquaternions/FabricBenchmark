package me.biquaternions.benchmark.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import me.biquaternions.benchmark.util.ProfilerUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
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

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/entity/EntityTickList;forEach(Ljava/util/function/Consumer;)V"
            )
    )
    private void benchmark$beforeTickEntityList(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStartTickEntities();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/entity/EntityTickList;forEach(Ljava/util/function/Consumer;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void benchmark$afterTickEntityList(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStopTickEntities();
    }

    @Inject(
            method = "lambda$tick$0",
            at = @At(
                    value = "HEAD"
            )
    )
    private void benchmark$beforeTickEntityIfVillager(TickRateManager tickRateManager, ProfilerFiller profiler, Entity entity, CallbackInfo ci, @Share("benchmark$isVillager") LocalBooleanRef isVillager) {
        boolean villager = entity instanceof Villager;
        isVillager.set(villager);
        if (villager) {
            ProfilerUtils.recordStartTickVillagerBrains();
        }
    }

    @Inject(
            method = "lambda$tick$0",
            at = @At(
                    value = "RETURN"
            )
    )
    private void benchmark$afterTickEntityIfVillager(TickRateManager tickRateManager, ProfilerFiller profiler, Entity entity, CallbackInfo ci, @Share("benchmark$isVillager") LocalBooleanRef isVillager) {
        if (isVillager.get()) {
            ProfilerUtils.recordStopTickVillagerBrains();
        }
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;tickBlockEntities()V"
            )
    )
    private void benchmark$beforeTickBlockEntities(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStartTickBlockEntities();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;tickBlockEntities()V",
                    shift = At.Shift.AFTER
            )
    )
    private void benchmark$afterTickBlockEntities(BooleanSupplier haveTime, CallbackInfo ci) {
        ProfilerUtils.recordStopTickBlockEntities();
    }

}

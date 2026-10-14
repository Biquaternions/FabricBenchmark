package me.biquaternions.benchmark.mixin;

import me.biquaternions.benchmark.area.AreaType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Brain.class)
public class Brain_Mixin<E extends LivingEntity> {

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/world/entity/ai/Brain;forgetOutdatedMemories()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$forgetOutdatedMemories(Brain<E> instance) {
        final long start = System.nanoTime();
        instance.forgetOutdatedMemories();
        AreaType.BRAIN_FORGET_OUTDATED_MEMORIES.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/world/entity/ai/Brain;tickSensors(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickSensors(Brain<E> instance, ServerLevel level, E body) {
        final long start = System.nanoTime();
        instance.tickSensors(level, body);
        AreaType.BRAIN_TICK_SENSORS.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/world/entity/ai/Brain;startEachNonRunningBehavior(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$startEachNonRunningBehavior(Brain<E> instance, ServerLevel level, E body) {
        final long start = System.nanoTime();
        instance.startEachNonRunningBehavior(level, body);
        AreaType.BRAIN_START_NON_RUNNING_BEHAVIOR.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/world/entity/ai/Brain;tickEachRunningBehavior(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickEachRunningBehavior(Brain<E> instance, ServerLevel level, E body) {
        final long start = System.nanoTime();
        instance.tickEachRunningBehavior(level, body);
        AreaType.BRAIN_TICK_EACH_RUNNING_BEHAVIORS.getProfiler().push(System.nanoTime() - start);
    }

}

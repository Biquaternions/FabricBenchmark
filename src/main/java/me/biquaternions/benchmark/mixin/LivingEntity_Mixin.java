package me.biquaternions.benchmark.mixin;

import me.biquaternions.benchmark.area.AreaType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntity_Mixin {

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/world/entity/LivingEntity;aiStep()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$aiStep(LivingEntity instance) {
        final long start = System.nanoTime();
        instance.aiStep();
        AreaType.LIVING_AI_STEP.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "aiStep",
            at = @At(
                    target = "Lnet/minecraft/world/entity/LivingEntity;serverAiStep()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$serverAiStep(LivingEntity instance) {
        final long start = System.nanoTime();
        instance.serverAiStep();
        AreaType.LIVING_SERVER_AI_STEP.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "aiStep",
            at = @At(
                    target = "Lnet/minecraft/world/entity/LivingEntity;travel(Lnet/minecraft/world/phys/Vec3;)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$travel(LivingEntity instance, Vec3 input) {
        final long start = System.nanoTime();
        instance.travel(input);
        AreaType.LIVING_TRAVEL.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "aiStep",
            at = @At(
                    target = "Lnet/minecraft/world/entity/LivingEntity;applyEffectsFromBlocks()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$applyEffectsFromBlocks(LivingEntity instance) {
        final long start = System.nanoTime();
        instance.applyEffectsFromBlocks();
        AreaType.LIVING_APPLY_EFFECTS_FROM_BLOCKS.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "aiStep",
            at = @At(
                    target = "Lnet/minecraft/world/entity/LivingEntity;pushEntities()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$pushEntities(LivingEntity instance) {
        final long start = System.nanoTime();
        instance.pushEntities();
        AreaType.LIVING_PUSH_ENTITIES.getProfiler().push(System.nanoTime() - start);
    }

}

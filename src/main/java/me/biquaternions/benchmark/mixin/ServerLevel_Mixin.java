package me.biquaternions.benchmark.mixin;

import me.biquaternions.benchmark.area.AreaType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.ticks.LevelTicks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Mixin(ServerLevel.class)
public class ServerLevel_Mixin {

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/world/ticks/LevelTicks;tick(JILjava/util/function/BiConsumer;)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickBlocks(LevelTicks<Block> instance, long currentTick, int maxTicksToProcess, BiConsumer<BlockPos, Block> output) {
        final long start = System.nanoTime();
        instance.tick(currentTick, maxTicksToProcess, output);
        AreaType.TICK_BLOCKS.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/world/ticks/LevelTicks;tick(JILjava/util/function/BiConsumer;)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickFluids(LevelTicks<Fluid> instance, long currentTick, int maxTicksToProcess, BiConsumer<BlockPos, Fluid> output) {
        final long start = System.nanoTime();
        instance.tick(currentTick, maxTicksToProcess, output);
        AreaType.TICK_FLUIDS.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/server/level/ServerChunkCache;tick(Ljava/util/function/BooleanSupplier;Z)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickChunkSource(ServerChunkCache instance, BooleanSupplier haveTime, boolean tickChunks) {
        final long start = System.nanoTime();
        instance.tick(haveTime, tickChunks);
        AreaType.TICK_CHUNK_SOURCE_FULL.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/world/level/entity/EntityTickList;forEach(Ljava/util/function/Consumer;)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickEntities(EntityTickList instance, Consumer<Entity> output) {
        final long start = System.nanoTime();
        instance.forEach(output);
        AreaType.TICK_ENTITIES.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/server/level/ServerLevel;tickBlockEntities()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickBlockEntities(ServerLevel instance) {
        final long start = System.nanoTime();
        instance.tickBlockEntities();
        AreaType.TICK_BLOCK_ENTITIES.getProfiler().push(System.nanoTime() - start);
    }

}

package me.biquaternions.benchmark.mixin;

import me.biquaternions.benchmark.area.AreaType;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BooleanSupplier;

@Mixin(ServerChunkCache.class)
public class ServerChunkCache_Mixin {

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/server/level/ServerChunkCache;tickChunks()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickChunks(ServerChunkCache instance) {
        final long start = System.nanoTime();
        instance.tickChunks();
        AreaType.TICK_CHUNKS.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/server/level/ChunkMap;tick()V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickTracker(ChunkMap instance) {
        final long start = System.nanoTime();
        instance.tick();
        AreaType.TICK_ENTITY_TRACKING.getProfiler().push(System.nanoTime() - start);
    }

    @Redirect(
            method = "tick",
            at = @At(
                    target = "Lnet/minecraft/server/level/ChunkMap;tick(Ljava/util/function/BooleanSupplier;)V",
                    value = "INVOKE",
                    ordinal = 0
            )
    )
    private void benchmark$tickChunkUnloads(ChunkMap instance, BooleanSupplier haveTime) {
        final long start = System.nanoTime();
        instance.tick(haveTime);
        AreaType.TICK_CHUNK_UNLOADS.getProfiler().push(System.nanoTime() - start);
    }

}

package me.biquaternions.benchmark.area;

import me.biquaternions.benchmark.profiler.AbstractProfiler;
import me.biquaternions.benchmark.profiler.impl.NoopProfiler;
import org.jspecify.annotations.NullMarked;
import java.util.function.BiFunction;
import java.util.function.Function;

@NullMarked
public enum AreaType {
    TICK_CHUNK_SOURCE_FULL(NoopProfiler::new), // ServerLevel
    TICK_CHUNKS(NoopProfiler::new), // ServerChunkCache
    TICK_ENTITY_TRACKING(NoopProfiler::new), // ServerChunkCache
    TICK_CHUNK_UNLOADS(NoopProfiler::new), // ServerChunkCache

    TICK_BLOCKS(NoopProfiler::new), // ServerLevel
    TICK_FLUIDS(NoopProfiler::new), // ServerLevel
    TICK_ENTITIES(NoopProfiler::new), // ServerLevel
    TICK_BLOCK_ENTITIES(NoopProfiler::new), // ServerLevel

    TICK_CONNECTION(NoopProfiler::new), // MinecraftServer

    TICK_SERVER(NoopProfiler::new),  // MinecraftServer - measures the entire tick loop

    BRAIN_START_NON_RUNNING_BEHAVIOR(NoopProfiler::new),
    BRAIN_TICK_SENSORS(NoopProfiler::new),
    BRAIN_TICK_EACH_RUNNING_BEHAVIORS(NoopProfiler::new),
    BRAIN_FORGET_OUTDATED_MEMORIES(NoopProfiler::new),


    LIVING_AI_STEP(NoopProfiler::new),

    LIVING_SERVER_AI_STEP(NoopProfiler::new),
    LIVING_TRAVEL(NoopProfiler::new),
    LIVING_APPLY_EFFECTS_FROM_BLOCKS(NoopProfiler::new),
    LIVING_PUSH_ENTITIES(NoopProfiler::new),

    ;

    private AbstractProfiler profiler;

    AreaType(final Function<AreaType, ? extends AbstractProfiler> generator) {
        this.profiler = generator.apply(this);
    }

    public void setProfiler(final Function<AreaType, ? extends AbstractProfiler> generator) {
        this.profiler = generator.apply(this);
    }

    public void setProfiler(final BiFunction<AreaType, Integer, ? extends AbstractProfiler> function, final int window) {
        this.profiler = function.apply(this, window);
    }

    public AbstractProfiler getProfiler() {
        return this.profiler;
    }

}

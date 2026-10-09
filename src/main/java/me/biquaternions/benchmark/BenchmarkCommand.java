package me.biquaternions.benchmark;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.biquaternions.benchmark.area.AreaType;
import me.biquaternions.benchmark.profiler.impl.AreaProfiler;
import me.biquaternions.benchmark.profiler.impl.NoopProfiler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class BenchmarkCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> generateCommand() {
        return Commands.literal("benchmark")
                .then(Commands.literal("start")
                        .requires(CommandSourceStack::isPlayer) // Force player to be online to prevent someone from running the benchmark into an empty or sleeping server
                        .then(Commands.argument("profiler", StringArgumentType.word())
                                .suggests((_, builder) -> {
                                    for (AreaType type : AreaType.values()) {
                                        builder.suggest(type.name());
                                    }
                                    return builder.buildFuture();
                                })
                                .then(Commands.argument("window", IntegerArgumentType.integer(500))
                                        .executes(ctx -> {
                                            final int window = ctx.getArgument("window", int.class);
                                            final String typeString = ctx.getArgument("profiler", String.class);
                                            final AreaType areaType;
                                            try {
                                                areaType = AreaType.valueOf(typeString);
                                            } catch (IllegalArgumentException e) {
                                                ctx.getSource().sendSystemMessage(Component.literal("THIS PROFILER TYPE DOES NOT EXIST"));
                                                return Command.SINGLE_SUCCESS;
                                            }

                                            for (AreaType type : AreaType.values()) {
                                                type.setProfiler(NoopProfiler::new);
                                            }
                                            areaType.setProfiler(AreaProfiler::new, window);
                                            if (ctx.getSource().isPlayer()) {
                                                areaType.getProfiler().setCaller(Objects.requireNonNull(ctx.getSource().getPlayer()));
                                            }
                                            ctx.getSource().sendSystemMessage(Component.literal("Profiling started"));

                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                )
                .then(Commands.literal("stop")
                        .requires(CommandSourceStack::isPlayer)
                        .executes(ctx -> {
                            for (AreaType type : AreaType.values()) {
                                type.setProfiler(NoopProfiler::new);
                            }
                            ctx.getSource().sendSystemMessage(Component.literal("Profiling stopped"));

                            return Command.SINGLE_SUCCESS;
                        })
                );
    }

}

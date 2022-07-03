package io.firetamer.dragonblockbeyond.race.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.firetamer.dragonblockbeyond.init.RaceInit;
import io.firetamer.dragonblockbeyond.race.Race;
import io.firetamer.dragonblockbeyond.race.capability.RaceHolderAttacher;
import io.firetamer.dragonblockbeyond.race.util.RaceUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public class RaceCommand {
    public static final DynamicCommandExceptionType ERROR_NOT_LIVING = new DynamicCommandExceptionType(arg1 -> new TranslatableComponent("commands.dragonblockbeyond.race.failure.not_living", arg1));
    public static final Dynamic2CommandExceptionType ERROR_DISABLED_RACE = new Dynamic2CommandExceptionType((arg1, arg2) ->
            new TranslatableComponent( "commands.dragonblockbeyond.race.failure.disabled_race", arg1, arg2));
    public static final DynamicCommandExceptionType ERROR_UNKNOWN_RACE = new DynamicCommandExceptionType(arg1 -> new TranslatableComponent("argument.dragonblockbeyond.race.id.invalid", arg1));
    public static final DynamicCommandExceptionType ERROR_UNKNOWN_VARIANT = new DynamicCommandExceptionType(arg1 -> new TranslatableComponent("argument.dragonblockbeyond.variant.invalid", arg1));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> raceRootNode = dispatcher.register(Commands.literal("race")
                .requires(source -> source.hasPermission(2))
                .executes(context -> unRace(context, context.getSource().getPlayerOrException()))
                .then(Commands.argument("race", RaceArgument.race())
                        .executes(context -> race(context, context.getSource().getPlayerOrException(), RaceArgument.getRace(context, "race"), null))
                        .then(Commands.argument("variant", StringArgumentType.word())
                                .suggests(RaceCommand::suggestVariants)
                                .executes(context -> race(context, context.getSource().getPlayerOrException(),
                                        RaceArgument.getRace(context, "race"), StringArgumentType.getString(context, "variant")))
                                .then(Commands.argument("entity", EntityArgument.entity())
                                        .executes(context -> race(context, getLivingEntity(context, "entity"),
                                                RaceArgument.getRace(context, "race"), StringArgumentType.getString(context, "variant")))))));
    }

    @NotNull
    private static CompletableFuture<Suggestions> suggestVariants(CommandContext<CommandSourceStack> context, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        Race race = RaceArgument.getRace(context, "race");
        List<String> variants = race.getVariants();

        return SharedSuggestionProvider.suggest(variants, suggestionsBuilder,
                Function.identity(), variant -> new TranslatableComponent("commands.dragonblockbeyond.race.variant.suggestion", variant, race.getDescription()));
    }

    @NotNull
    public static Player getLivingEntity(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
        Player entity = EntityArgument.getPlayer(context, name);

        return entity;
    }

    public static <T extends Race> void registerAlias(String commandName, Supplier<T> raceSupplier, CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(commandName)
                .requires(source -> source.hasPermission(2))
                .executes(context -> race(context, context.getSource().getPlayerOrException(), raceSupplier.get(), null)));
    }

    private static int race(CommandContext<CommandSourceStack> context, Player entity, Race race, @Nullable String variant) throws CommandSyntaxException {
        if (!race.isEnabled(entity))
            throw ERROR_DISABLED_RACE.create(race.getDescription(), entity.getDisplayName());

        if (variant != null && !race.getVariants().contains(variant))
            throw ERROR_UNKNOWN_VARIANT.create(variant);

        RaceHolderAttacher.getRaceHolder(entity).ifPresent(raceHolder -> RaceUtil.setRace(context.getSource(), entity, raceHolder, race, variant));

        return 1;
    }

    private static int unRace(CommandContext<CommandSourceStack> context, Player entity) throws CommandSyntaxException {
        Component message = RaceUtil.getClaerRaceSuccessMessage(context, entity);
        context.getSource().sendSuccess(message, false);
        RaceHolderAttacher.getRaceHolder(entity).ifPresent(raceHolder -> RaceUtil.setRace(context.getSource(), entity, raceHolder, null, null));

        return 1;
    }

    public static class RaceArgument implements ArgumentType<Race> {
        public static RaceArgument race() {
            return new RaceArgument();
        }

        public static Race getRace(final CommandContext<?> context, final String name) {
            return context.getArgument(name, Race.class);
        }

        private RaceArgument() {}

        @Override
        public Race parse(StringReader reader) throws CommandSyntaxException {
            int cursor = reader.getCursor();
            ResourceLocation id = ResourceLocation.read(reader);
            return Optional.ofNullable(RaceInit.getRegistry().getValue(id)).orElseThrow(() -> {
                reader.setCursor(cursor);
                return ERROR_UNKNOWN_RACE.createWithContext(reader, id.toString());
            });
        }

        @Override
        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
            return context.getSource() instanceof SharedSuggestionProvider
                    ? SharedSuggestionProvider.suggestResource(RaceInit.getRegistry().getKeys(), builder)
                    : Suggestions.empty();
        }
    }
}

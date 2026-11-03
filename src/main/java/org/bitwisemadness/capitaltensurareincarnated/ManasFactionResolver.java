package org.bitwisemadness.capitaltensurareincarnated;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayer;
import org.bitwisemadness.capitalapi.RaceIntegration;
import org.bitwisemadness.capitalapi.ResolvedSpawn;
import org.bitwisemadness.capitaltensurareincarnated.data.RaceTags;

/**
 * The ManasCore half of the boundary contract: resolves a player's current
 * race into the faction its race-group tag belongs to, plus the race's own
 * default respawn dimension (the deferred-dimension design - the daemon chain
 * overrides it to Tensura's hell, every other race resolves to the overworld).
 */
public final class ManasFactionResolver implements RaceIntegration {

    @Override
    public Optional<ResolvedSpawn> resolveSpawn(ServerPlayer player) {
        Optional<ManasRaceInstance> race = currentRace(player);
        if (race.isEmpty()) {
            return Optional.empty();
        }
        ManasRaceInstance instance = race.get();
        for (var tag : RaceTags.ALL) {
            if (instance.is(tag)) {
                ResourceKey<Level> defaultDimension = instance.getRace()
                        .getRespawnDimension(instance, player).getFirst();
                return Optional.of(new ResolvedSpawn() {
                    @Override
                    public ResourceLocation factionTag() {
                        return tag.location();
                    }

                    @Override
                    public ResourceLocation defaultDimension() {
                        return defaultDimension.location();
                    }
                });
            }
        }
        return Optional.empty();
    }

    private Optional<ManasRaceInstance> currentRace(ServerPlayer player) {
        Races races = RaceAPI.getRaceFrom(player);
        if (races == null) {
            return Optional.empty();
        }
        return races.getRace();
    }
}

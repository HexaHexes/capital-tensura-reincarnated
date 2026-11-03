package org.bitwisemadness.capitaltensurareincarnated;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.bitwisemadness.capitalapi.CapitalApi;
import org.bitwisemadness.capitaltensurareincarnated.spawn.RaceSetHandler;
import org.slf4j.Logger;

/**
 * The Tensura race integration for the capital ecosystem: owns the
 * ManasCore {@code SET_RACE} hook and the race-group tags, and hands the
 * spawn engine a resolved faction through {@code capital-api} (spec §3.2).
 * Race selection itself is owned by Tensura: Reincarnated (TDR decision B).
 */
@Mod(CapitalTensuraReincarnated.MOD_ID)
public final class CapitalTensuraReincarnated {

    public static final String MOD_ID = "capital_tensura_reincarnated";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CapitalTensuraReincarnated(IEventBus modBus) {
        // ManasCore fires SET_RACE (an Architectury event) on both a first pick
        // and a reincarnation; the relocation flag it exposes is the trigger.
        RaceSetHandler.register();
        NeoForge.EVENT_BUS.addListener(RaceSetHandler::onServerTick);

        CapitalApi.registerIntegration(new ManasFactionResolver());

        LOGGER.info("Capital Tensura Reincarnated initialized");
    }
}

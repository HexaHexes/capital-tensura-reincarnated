package org.bitwisemadness.capitaltensurareincarnated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Locks the shipped race-group tag data: one file per faction, additive, and
 * non-empty after the pickability pass. Reads the packaged resources straight
 * from the test classpath - no ManasCore bootstrap.
 */
class ShippedRaceTagDataTest {

    private static final List<String> FACTIONS = List.of(
            "humans", "elves", "dwarves", "celestials", "hell", "beastfolk",
            "aquatic", "insect", "monsters", "goblins", "lizardman", "giants");

    private static InputStream tagResource(String faction) {
        return ShippedRaceTagDataTest.class.getResourceAsStream(
                "/data/tensura_faction_spawn/tags/manascore_race/races/" + faction + ".json");
    }

    @Test
    void everyFactionShipsAnAdditiveNonEmptyTag() throws IOException {
        for (String faction : FACTIONS) {
            try (InputStream in = tagResource(faction)) {
                assertTrue(in != null, "missing shipped tag for faction " + faction);
                JsonObject json = JsonParser.parseReader(
                        new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
                assertEquals(false, json.get("replace").getAsBoolean(),
                        faction + " must ship an additive tag");
                assertTrue(json.get("values").getAsJsonArray().size() > 0,
                        faction + " tag shipped empty after the pickability pass");
            }
        }
    }

    @Test
    void exactlyTwelveFactionsShip() throws IOException {
        long shipped = FACTIONS.stream().filter(f -> tagResource(f) != null).count();
        assertEquals(12, shipped);
    }
}

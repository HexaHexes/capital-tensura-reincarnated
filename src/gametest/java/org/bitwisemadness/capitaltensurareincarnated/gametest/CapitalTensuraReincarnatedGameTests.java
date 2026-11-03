package org.bitwisemadness.capitaltensurareincarnated.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import org.bitwisemadness.capitaltensurareincarnated.CapitalTensuraReincarnated;
import org.bitwisemadness.capitaltensurareincarnated.data.RaceTags;

/** The integration loads and its faction tag set matches the approved mapping. */
@GameTestHolder(CapitalTensuraReincarnated.MOD_ID)
@PrefixGameTestTemplate(false)
public final class CapitalTensuraReincarnatedGameTests {

    private CapitalTensuraReincarnatedGameTests() {
    }

    @GameTest(template = "empty")
    public static void mod_loads(GameTestHelper helper) {
        helper.succeed();
    }

    /** Twelve faction tags, all under the ecosystem's namespace, in the shipped order. */
    @GameTest(template = "empty")
    public static void faction_tags_match_the_mapping(GameTestHelper helper) {
        helper.assertTrue(RaceTags.ALL.size() == 12,
                "expected 12 faction tags, got " + RaceTags.ALL.size());
        for (var tag : RaceTags.ALL) {
            helper.assertTrue(
                    tag.location().getNamespace().equals(RaceTags.NAMESPACE),
                    "faction tag " + tag.location() + " is outside the ecosystem namespace");
        }
        helper.assertTrue(RaceTags.HUMANS.location().getPath().equals("humans")
                && RaceTags.GIANTS.location().getPath().equals("giants"),
                "the tag paths drifted from the approved mapping");
        helper.succeed();
    }
}

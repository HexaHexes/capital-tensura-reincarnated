# Capital Tensura Reincarnated

The Tensura race integration for the capital ecosystem - the boundary side
that reads Tensura's ManasCore race registry and hands the spawn engine a
resolved faction (spec 5.1 boundary, 3.2 contract).

## What it does

- Listens for ManasCore's SET_RACE signal: a first pick or a reincarnation
  queues the player for faction-capital relocation; an evolution never does
  (Tensura's own relocation flag is mirrored exactly).
- Resolves the player's current race into a faction via the race-group tags
  it ships, plus the race's own default respawn dimension (the daemon chain
  defers to Tensura's hell; every other race resolves to the overworld).
- Hands both to the spawn engine through capital-api - the engine does the
  rule selection, capital lookup, placement and respawn handling. Either side
  may be absent; everything no-ops gracefully.

## What it ships

- The race-group tags (data/tensura_faction_spawn/tags/manascore_race/races/)
  - twelve factions, populated from the config-verified pickable set
  (Tensura's six availability lists plus the Mysticism and SlimeThroneExtras
  families). They are additive ("replace": false), so a pack can extend a
  faction without editing this mod.
- Nothing else: the spawn-rule schema, the capital structure tags and the
  placement belong to the spawn engine; the generation engine lives in
  capital-core.

## Requirements

| | Mod | Why |
|---|---|---|
| Required | Minecraft | - |
| Required | NeoForge | - |
| Required | Capital API | The boundary contract this integration implements |
| Required | Tensura: Reincarnated | Provides the race system and the races |
| Required | ManasCore | Provides the race registry and the SET_RACE signal |


## Credits

- Built on [NeoForge](https://neoforged.net/).
- Uses the race tag system from ManasCore / Tensura: Reincarnated.

## License

GNU Lesser General Public License v2.1 - see [`LICENSE`](LICENSE).
Copyright (c) 2026 Bitwise Madness.

## AI disclosure

Parts of this project were written with the assistance of AI coding tools.
AI-assisted code, data and documentation ship only after human review by the
maintainers.

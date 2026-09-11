# Fixordium

Fixordium is a lightweight client-side Fabric performance mod focused on cutting unnecessary living-entity rendering work.

## Current features

- Fast pre-culling for players and mobs outside the camera frustum
- Entities still exist and keep functioning normally; Fixordium only skips unnecessary rendering
- `Fixordium: ON/OFF` toggle inside Video Settings
- Hover tooltip explaining what the optimization does
- Setting is saved locally in `config/fixordium.json`
- Fully client-side
- No Fabric API required

## Target Minecraft versions

Fixordium 1.0.0 is designed as one mapping-independent JAR for:

- 1.21.8
- 1.21.9
- 1.21.10
- 1.21.11
- 26.1
- 26.1.1
- 26.1.2
- 26.2

The JAR uses Fabric Loader's runtime mapping resolver and selects the appropriate legacy or modern mixins at launch. Build success verifies the universal package itself; each Minecraft version should still be runtime-tested before a public release is marked fully verified.

## Requirements

- Fabric Loader

## License

MIT

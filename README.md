# Fixordium

Fixordium is a lightweight client-side Fabric performance mod focused on cutting unnecessary living-entity rendering work.

## Current features

- Fast pre-culling for players and mobs that are safely outside your view
- Nearby entities are kept rendered to avoid obvious pop-in
- Works in first-person and third-person camera modes
- `Fixordium: ON/OFF` toggle inside Video Settings
- Hover tooltip explaining what the optimization does
- Setting is saved locally in `config/fixordium.json`
- Fully client-side

## Target Minecraft versions

The project is designed around one universal JAR for:

- 1.21.8
- 1.21.9
- 1.21.10
- 1.21.11
- 26.1
- 26.1.1
- 26.1.2
- 26.2

Compatibility is checked in GitHub Actions before release builds are treated as universal.

## Requirements

- Fabric Loader
- Fabric API

## License

MIT

# Fixordium

Fixordium is a lightweight client-side Fabric optimization mod for Minecraft 26.2 focused on reducing unnecessary living-entity rendering work.

## Features

- Fast pre-culling for players and mobs outside the camera frustum
- Entities are never unloaded, frozen, or changed server-side
- Native Sodium Config API integration inside Video Settings
- Dedicated Fixordium page with ON/OFF control and tooltip
- Custom Fixordium creeper icon in the Sodium/Reese's Sodium Options menu
- Persistent local config in `config/fixordium.json`
- Fabric API + Sodium required

## Compatibility design

Fixordium only adds a conservative entity-render pre-check and does not patch particle, chunk, block-model, leaf, or Sodium GUI internals. It is designed to coexist with Sodium, Lithium, Entity Culling, Particle Culling, Entity View Distance, Sodium Extra, Reese's Sodium Options, Continuity, Cull Leaves, More Culling, and similar optimization mods.

Runtime compatibility still depends on the exact versions installed, so new releases should be tested before being marked fully compatible.

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19+
- Fabric API 0.152.2+
- Sodium 0.9.1+
- Java 25

## License

MIT

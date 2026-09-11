# Fixordium

Fixordium is a lightweight client-side Fabric optimization mod for Minecraft 26.2 built around Sodium's Config API.

## Features

- Fast pre-culling for players and mobs outside the camera frustum
- Optional early culling for item frames, glow item frames, and armor stands
- Optional entity-shadow disabling
- Smart Mode with a configurable target FPS
- Smart Mode can temporarily suppress entity shadows and animated block-entity visuals when FPS drops, then restore them after performance recovers
- Container-animation controls for chests, Ender Chests, and Shulker Boxes
- Animation controls for banners, bells, enchanting-table books, skulls, spawners, beacons, conduits, vaults, and End Gateway beams
- Debug HUD with skipped-render statistics and Smart Mode status
- Optional stop-music-on-Escape behavior
- Native Sodium Config API integration inside Video Settings
- Custom Fixordium creeper icon in Sodium/Reese's Sodium Options
- Persistent local config in `config/fixordium.json`
- Fabric API + Sodium required

## Compatibility design

Fixordium keeps its optimizations focused and avoids patching particle, chunk, block-model, leaf, or Sodium GUI internals. It is designed to coexist with Sodium, Lithium, Entity Culling, Particle Culling, Entity View Distance, Sodium Extra, Reese's Sodium Options, Continuity, Cull Leaves, More Culling, and similar optimization mods.

Runtime compatibility still depends on the exact versions installed, so new releases should be tested before being marked fully compatible.

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19+
- Fabric API 0.152.2+
- Sodium 0.9.1+
- Java 25

## License

MIT

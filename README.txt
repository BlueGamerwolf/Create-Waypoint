# Waypoint Block Mod

A Forge mod for Minecraft 1.20.1 that adds a tiered **Waypoint Block** system with full RGB customization, team integration, and optional chunk loading.

---

## 📌 Tier System

### Tier 1 – Basic Waypoint Block
- **Crafting**: Mid-game (Iron + Redstone + Glass)
- **Functions**:
  - Right-click to unlock waypoint
  - Floating text, beacon beam, distance counter
  - GUI: rename, recolor, toggle visibility
  - Full **RGB color customization**
- **Limitations**:
  - ❌ No chunk loading
  - Render distance capped (~1000 blocks)
  - Configurable per-player limit (default **10**)

---

### Tier 2 – Advanced Waypoint Block
- **Crafting**: Late-game (Netherite + Ender Eye + Block of Diamond + Basic Block)
- **Functions**:
  - All **Tier 1 features**
  - Infinite render distance
  - Toggleable **chunk loading**
  - **Fuel System**: Lava only (bucket/manual or via pipes/tanks)
  - Fuel consumption: `1 bucket = ~30 min` (scales with chunk radius)
  - Configurable chunk radius (**default 1, max 3×3**)
- **GUI**:
  - Name, RGB color picker, beam toggle
  - Fuel bar + runtime timer
  - Chunk loading toggle + radius selector
- **Visuals**:
  - Lava drip particles when active
  - Core glows brighter as tank fills
  - Smoke particles when empty

---

## 🎨 Waypoint Features

### Visuals
- Floating text, beam, and distance counter
- Beam color matches **full RGB picker**
- Real-time updates while adjusting colors
- Optional pulsing outline / transparency

### Customization
- Rename waypoints
- Recolor with RGB picker
- Toggle visibility: floating text, beam, distance

---

## 🤝 Sharing System

- **Export/Import**: Waypoint JSON (coords, name, RGB, block UUID)
- **Chat Commands**:
  - `/waypoint share <player>` → sends clickable message
  - Recipient must have discovered the block to add it

---

## 👥 FTB Teams Integration

### Team Leader Rules
- Only **leader** can unlock team waypoints
- Leader edits (rename, color, chunk loading) → sends prompts to members

### Team Members
- Chat notification when leader discovers a waypoint:

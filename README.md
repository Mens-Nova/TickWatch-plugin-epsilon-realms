# TickWatch - Epsilon Realms
Is a lightweight Paper plugin for Minecraft servers (1.21.x) that provides real-time server health monitoring for operators.

It displays important metrics like TPS, player ping, CPU, RAM, and disk usage in a customizable sidebar,
with logging for TPS drops.

## Features
- **Sidebar Dashboard** (toggleable with `/toggleboard`)
    - TPS (ticks per second) with color-coded status
    - Player ping (ms)
    - System CPU usage (Total + JVM process)
    - RAM usage (system + JVM heap)
    - Disk usage (server directory)
- **/status command** - shows TPS and ping in chat
- **TPS drop logging** - records when TPS dips below a threshold to `plugins/TickWatch/tps-log.txt`
- **Operator-only toggle** - only ops can enable the sidebar
- **Auto-disable on deop** - if you lose op, the sidebar disappears automatically
- **Configurable** - update interval, TPS threshold, messages, and labels are customizable in config

## Installation
1. Download the TickWatch JAR from release.
2. Place it into your server's `plugins/` folder.
3. Start or restart your Paper server (1.21.8+).
4. The plugin will create:
    - `config.yml` - general settings (update rate, thresholds, features to show)
    - `messages.yml` - customizable messages and labels

## Configuration
`config.yml`
```
update-interval-ticks: 20   # how often scoreboard updates (20 = 1s)
tps-threshold: 18.0         # TPS value to log drops
show:
  cpu: true
  ram: true
  disk: true
```
`messages.yml`
```
sidebar:
  title: "&6&l⚡ Server Stats"
  tps: "&eTPS: &f{tps}"
  ping: "&bPing: &f{ping}ms"
  cpu_sys: "&6CPU(Σ): &f{value}%"
  cpu_proc: "&6CPU(Proc): &f{value}%"
  ram_sys: "&aRAM(Σ): &f{used}/{total}"
  ram_jvm: "&aRAM(JVM): &f{used}/{max}"
  disk: "&dDisk: &f{used}/{total}"
toggle:
  enabled: "&aStatus scoreboard enabled"
  disabled: "&cStatus scoreboard disabled"
autodisable:
  deop: "&cServer stats disabled (you are no longer an operator)."
```
Placegolders like `{tps}`,`{ping}`,`{value}`,`{used}`,`{total}` are replaced dynamically.

## Commands & Permissions

Command	Permission	Default	Description
| Command        | Permission         | Default | Description                          |
| -------------- | ------------------ | ------- | ------------------------------------ |
| `/status`      | none               | all     | Show TPS & ping in chat              |
| `/toggleboard` | `tickwatch.toggle` | op      | Toggle the sidebar with server stats |

## TPS Logging
Whenever TPS drops below configured threshold, a timestamped entry is written to:
```
plugins/TickWatch/tps-log.txt
```
Example
```
2025-08-16 04:12:33 - TPS dropped to 15.83
```

## License 
This project is MIT licensed.
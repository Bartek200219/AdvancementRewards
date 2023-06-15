# AdvancementRewards

AdvancementRewards is a simple plugin to reward players with money after  completing minecraft advancements.

### Installation

1. Place plugin jar file in plugins directory

2. Run server and wait for config to generate

### Configuration

If you want all advancement to have the same prize change `defaultPrize` in config and delete `advancements` section, it will autogenerate on server start.

If you don't want to give a reward for specific advancement, just remove it from `advancements` section.

### Dependencies

* [Vault](https://github.com/milkbowl/Vault) 

### Permissions

There is only one permission `advancementRewards.allow` and it is default. If you don't want certain players to receive reward, you need to revoke that permission.
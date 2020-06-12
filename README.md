# InfiniteBuckets
A spigot plugin that creates infinite buckets

Installation:

- Download the plugin on the [spigot page](https://www.spigotmc.org/resources/infinite-buckets.78725/)
- Stop the server
- Put the plugin .jar in the plugins folder
- Start the server
- ???
- Profit

Commands:
- /infbucket reload - reloads the config
- /infbucket help - displays a help menu
- /infbucket give (player) (water|lava) - give said player a bucket of chosen type

Configuration:

- You can use "%cost%" in the 'subtractMoney' and the lore messages
- You can use "%permission%" in the 'notEnoughPermissions' message

- If a message is empty (like this '') then the message will not be sent
- If cost is at 0 then no money will be removed and no message will be sent

Once the config is reloaded with /infbucket reload every existing bucket will automatically be updated once the player holds the item.

Permissions:

- infbucket.*: all the permissions
- infbucket.reload: permission to reload the config
- infbucket.help: permission to show the help menu
- infbucket.give: permission to give the buckets
- infbucket.use.*: allows usage of every bucket
- infbucket.use.water: allows the use of the infinite water bucket
- infbucket.use.lava: allows the use of the infinite lava bucket

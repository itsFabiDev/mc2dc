# mc2dc
## What is mc2dc?
mc2dc is a plugin that allows you to send messages from your Minecraft Server to your Discord Server and vice versa.
As well it has some additional features (see below)
## Coming soon
- [ ] Add a way to enable/disable features
- [ ] Add a way to enable/disable commands
- [ ] Add a way to change Embed Color to your needs
- [ ] Add a way to change ingame Chat Color to your needs
- [ ] Optional: Add a way to change ingame Chat Color to Players wishes
# How to use
## What applies to all builds?
This is a development build.
This means:
1. It is tested, but not as much as the release build
2. It is not stable
3. It may not work
4. It may contain bugs
5. It may contain security issues
6. It may contain other issues
7. It may not be the latest version <br>
   If you find any bugs, please report them in the issues tab.
## How to use Autobuild
1. Go to the Autobuild Folder
2. Download the jar
3. Put it in your plugins folder
4. Start or Reload your Server
5. Enjoy! <br>

$\textcolor{red}{\textsf{NOTICE:}}$ This is a automatically builded jar. This means that it is not tested and may not work. If you find any bugs, please report them in the issues tab. <br>
## What to do if Autobuild doesnt work?
Don't panic! There is an alternative build that u can use instead!
1. Navigate to /target
2. Download the mc2dc-1.0.jar or anyother jar that is not named original-*.jar
3. Put it in your plugins folder
4. Start or Reload your Server
5. Enjoy! <br>
   $\textcolor{red}{\textsf{NOTICE:}}$ This build might not be the latest version. If you want to use the latest version, please build it yourself. <br>
## How to build it yourself
1. Clone the repository
2. Setup your IDE with JDK 17
3. Build it with Maven
4. Enjoy! <br>
## What to do after you installed the plugin
1. Go to the plugins folder
2. navigate to the mc2dc folder
3. open the config.yml
4. change the values to your needs <br>
Example:
```
discord:
   token: hiiamadiscordtoken
   guildID: '123456789012345678'
   mc_chat_channel: '123456789012345678'
   developerFeatures: true
```   
   
# Features
## How to request a feature
If you want to request a feature, please use the /featureRequest command. This will send a message to the developer with your request. Otherwise you can create an issue in the issues tab.
### Discord Features
**Discord Verification** <br>
Verifies your Discord Account with your Minecraft Account
<br>**Discord Chat** <br>
Sends all messages from the Minecraft Chat to the Discord Chat and reverse
<br>**Discord Join/Quit** <br>
Sends a message to the Discord Chat when a player joins or quits the server
<br>**Discord Advancement** <br>
Sends a message to the Discord Chat when a player gets an advancement
<br>**Discord Death** <br>
Sends a message to the Discord Chat when a player dies
<br>**Discord TPS** <br>
Sends the current TPS of the server to the Discord Chat
<br>**Discord List** <br>
Sends a list of all online players to the Discord Chat
<br>**Discord Help**<br>
Sends a list of all commands to the Discord Chat (Might not be up to date)
<br>**Discord Ping** <br>
Sends a pong! to the Discord Chat
<br>**Feature Request** <br>
Sends the Developer a Mesage with the requested feature when a player uses the /featureRequest command
<br>**Discord Features**<br>
Sends a list with all Features of the Discord Bot (Might not be up to date)
#### Developer Features
**Discord Developer Features** <br>
'/developerFeatures **feature**' Sends the Return for the wanted feature
1. enable/disable the Developer Features (default: true)
2. status of the Developer Features (default: true)
3. id of the user issuing the Command
4. features sends an embed of combined developer features
### Minecraft Features
<br>**Minecraft Sit**<br>
Sits down when you right click a stair with an empty hand
<br>**TPA**<br>
Sends a request to the player you want to teleport to. The player then needs to write tpaccept to accept the request or tpdeny to deny it.


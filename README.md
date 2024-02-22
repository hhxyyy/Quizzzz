# Starting template

This README will need to contain a description of your project, how to run it, how to set up the development environment, and who worked on it.
This information can be added throughout the course, except for the names of the group members.
Add your own name (do not add the names for others!) to the section below.

## Description of project

**Quick description of the game**\
We developed a quiz game whose topic is the saving of energy and obtaining knowledge about consumption of activities while having fun.\
There are two game modes - single player and multiplayer. During The single player a user cannot use jokers and emojis, however he has 10 seconds to answer a 
question, after answering a user can see whether he responded correctly immediately. He also can compare his score to the best scores of players that played the single player game on the same server.\
During a multiplayer game a person can play with his friends in order to have more fun, jokers and emojis are enabled. However a player should wait until the timer of the question reaches zero.

**The client can do the following:**
- Change the server to which he is connected
- Play single player game
- Play multiplayer game
- Play with friends if they connect to the same server
- Respond to multiple choice and open-ended questions
- See the solo leaderboard with the highest scores of all player who played on this server
- See a halftime and final leaderboad while player multiplayer
- Use jokers during the multiplayer game
- Use emojis during the multiplayer game
- Add activities to the database 
- Delete activities from the database
- Edit existing activities on the database
- Leave a game whenever he wants to do so

**The server can do the following:**
- Execute requests from the user
- Send information to the clien
- Store activities on the server
- Add/Edit/Delete activities
- Store persistent database with solo leaderboard scores


## Group members

| Profile Picture | Name | Email |
|---|---|---|
| <img src="https://www.gravatar.com/avatar/ecb5880acfecab6b96c35bb7cd6f3200" title="Joris' picture" width="50"/> | Joris Belder | j.belder-1@student.tudelft.nl |
| <img src="https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4776/avatar.png?width=400" title="Lucian's picture" width="50"/> | Lucian Toșa | L.Toa@student.tudelft.nl |
| <img src="https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4814/avatar.png?width=400" title="Yoan's picture" width="50" /> | Yoan Naydenov | Y.Naydenov@student.tudelft.nl |
| <img src="https://eu.ui-avatars.com/api/?name=JW&length=4&size=50&color=DDD&background=777&font-size=0.325" title="Jos' picture" width="50" /> | Jocelyn Woods | j.j.woods@student.tudelft.nl |
| <img src="https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4745/avatar.png?width=400" title="Okan's picture" width="50"/> | Okan Demir Baykal | O.D.Baykal@student.tudelft.nl |
| <img src="https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4838/avatar.png?width=400" title="Xingyu's picture" width="50"/> | Xingyu Han | X.Han-5@student.tudelft.nl |

<!-- Instructions (remove once assignment has been completed -->
<!-- - Add (only!) your own name to the table above (use Markdown formatting) -->
<!-- - Mention your *student* email address -->
<!-- - Preferably add a recognizable photo, otherwise add your GitLab photo -->
<!-- - (please make sure the photos have the same size) --> 

## How to run it

- Clone the repository-template.
- Download & unzip activity-bank (make sure activity.json is next to the numbered folders).
- Open the project in IntelliJ.
- Right click on Main in Server and press run.
- Right click on Main in Client and press run.
- Click on the admin panel in the lower left corner.
- Press Add and then Import JSON and select the Json file from the activity bank.
- Press Save and wait for a few seconds (the application may stop responding, but just be patient).
- Then restart the server and the client so that the images are properly loaded.
- Now you can play the game freely !

![Run the server](docs\gifs\run_server.gif)
![Run the client](docs\gifs\run_client.gif)
![Import activities](docs\gifs\admin_view.gif)


## How to contribute to it

- To add additional activities, use the admin panel and add activities individually.
- To contribute to the project, create another branch where you can add your code
- Then make a merge request to merge into main

## Copyright / License (opt.)

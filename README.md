# cat-netty-server
Netty cat server

Project for learning Netty - CatServer that tries to handle a hungry cat horde.

On top of most important cat package it also contains some copied/pasted netty tutorials in tutorials and chat packages.

It requires cat-api library jar in local repository (see https://github.com/sidzej666/cat-api that does proto files to jar generation)

Run CatServer as a java application.
Run multiple CatClient as a java application that will mau (Mau message) to the server and then request for food (GiveFood message) in an infinite loop.
Server will greet new mauing cats and send information to other cats about new cat arrival (CatServerMessage message).
After 10 food request the food runs out and server tells that in response to the cats - they will stay hungry forever muaahahaha (or till server restart).
When a cat leave the server (becouase of disconnection), server informs other cats about this sad incident.

BossCat class delegates incomming messages to proper handlers that do the work with building and sending the messages.
CatData is some sort of data storage to add some parameters to cats (althought there are propably better ways to this).
Whole application could use some sort of dependency injections, but as a learning project wanted to keep myself away from spring to concentrate on netty.

Mau!

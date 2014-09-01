play-websocket-example
======================

Websocket example using a broadcast, built for play 1.2.5rc4

Usage:

* Start: play run
* Connect at: http://localhost:9000/example
* Hit the big red button
* 
Should see a message alerted to the screen of every browser connected to the page

### Notes:

Comments are over the top to attempt to make it clear to myself 
what is going on - hopefully someone else will find this useful too.

Can't connect from another machine? Update the WebSocket address in:
public/javascripts/example.js

It logs various actions to the web browser console

Written with the (01/06/12) latest version of play and Chrome - those 
which implement the latest revisions of the websocket RFC

Code taken from play framework chatroom example, edited and comments added

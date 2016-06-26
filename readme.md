# Distributed Texas Hold'Em

This is a peer-to-peer implementation of Texas Hold'Em poker:  
developed as an academic project for "Distributed systems" course, it could be used as skeleton for a major project, such as a smartphone version using bluetooth protocol.  

Game rules are simplified: there is not pot split and there is not possible join if session is already started.   
In addition, there is a "absolute" score surrounding the "coins" concept.  

#### Briefly:
a master node accepts subscriptions to game from other (initial slave) peers, then it becomes a peer node as other ones.   
Behind the scenes, the application uses a token ring implementation for peers turn, and an "oracle"/pre-emptive finite states machine for future peers behaviours ("correct"=the node is still in the game, or "wrong"=the node has crashed).  
Optimized service mechanisms (using parallelism and lambda/functional paradigm from Java8) help the "oracle" to handle crash happening and recovering - network communications are based on classic Java RMI, of course.

To run in demo mode in same host (with multiple address on same nic):

```sh
sudo demo/set_ips
demo/run IP
```
	
where IP is one of the ip address added from the first script, from 10.0.0.1 to 10.0.0.10.
Obviously you can change the script in order to match your needs, or use a custom `ip addr add` command.

For example:

1. "sudo demo/set_ips;": set the virtual ips on eth0 - you could change as you prefer
2. "demo/run_dev 10.0.0.1": run the server/master for subscriptions - check to act as initializer and click "Accept registration"
3. "demo/run_dev 10.0.0.2": run the client/slave - insert the address of registrar (10.0.0.1) host and click "Register"
4. if you want you could start other clients (the game accept a max of 8 total players, so you cold start up to 6 more clients)
5. click "close registrations and start game" on master/server gui to start the game

---  
##### Technical details:
class diagram [here](https://drive.google.com/file/d/0B5CxUDoGDKvkaEZXd2lJN3pxU1k/view?pref=2&pli=1)  
report (in italian) [here](https://drive.google.com/folderview?id=0B0kkcZ_2d4pGbzVvUUZ2OUZRMnM&usp=sharing): describes oracle fsm and crash/recovering mechanisms behind the scenes  

----
Enjoy it - and play responsibly ;D

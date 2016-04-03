# Distributed Texas Hold'Em

This is a peer-to-peer implementation of Texas Hold'Em poker:  
developed as an academic project for "Distributed systems" course, it could be used as skeleton for a major project, such as a smartphone version using bluetooth protocol.  

Game rules are simplified: there is not pot split and there is not possible join if session is already started.   
In addition, there is a "absolute" score surrounding the "coins" concept.  

#### Briefly:
a master node accepts subscriptions to game from other (initial slave) peers, then it becomes a peer node as other ones.   
Behind the scenes, the application uses a token ring implementation for peers turn, and an "oracle"/pre-emptive finite states machine for future peers behaviours ("correct"=the node is still in the game, or "wrong"=the node has crashed).  
Optimized service mechanisms (using parallelism and lambda/functional paradigm from Java8) help the "oracle" to handle crash happening and recovering - network communications are based on classic Java RMI, of course.

To run in dev mode in same host (with multiple address on same nic):  
	`sudo ./set_ips;  ./dev/run_dev s; ./dev/run_dev c`

	1. "sudo ./set_ips;": set the virtual ips on eth0 - you could change as you prefer
	2. "./dev/run_dev s": run the server/master for subscriptions - here you have to click "register", leaving the fields as empty
	3. "./dev/run_dev c": run the i-nth client/slave - idem as above
	4. finally, click "close registrations and start game" on master/server gui to start the game

In "lab" directory there is a similar start script: "run_lab_ws" - it does not provides fake username nor virtual ips, so you could inspire to this script for a real distributed (=different hosts) session: obviously, you have to fill fields on gui ;D  

---  
##### Technical details:
class diagram [here](https://drive.google.com/file/d/0B5CxUDoGDKvkaEZXd2lJN3pxU1k/view?pref=2&pli=1)  
report (in italian) [here](https://drive.google.com/folderview?id=0B0kkcZ_2d4pGbzVvUUZ2OUZRMnM&usp=sharing): describes oracle fsm and crash/recovering mechanisms behind the scenes  

----
Enjoy it - and play responsibly ;D
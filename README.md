# The Words GAME - MPS
This is a simple word puzzle word game in Romanian, that can be played in a multiplayer environment.

Tehnologii software:
--------------------
+ JAVA - J2EE + maven - backend 
+ HTML CSS Javascript - frotend(ptr bonus, we could use ClifJS - un framework public facut de mine ne usureaza mult munca)

Model Arhitectural
------------------
Backend
+ Dictionary - componenta de dictionare care se ocupa cu verificatul cuvintelor in dicitonar
+ Engine - componenta care se ocupa de generat litere aleator
+ Player - componenta care se ocupa de primit input de la client, cheama DictionaryClass.isValid(cuvant) si ofera punctaj + alte validari adiacenta
+ PlayerInterface - command line
+ PlayerController - WebSocketController - pentru bonus partea de multiplayer asculta pe evenimentele de emit de la client cu cuvinte si se leaga la PlayerClass dand punctaje inapoi pe canal
+ Frontend - TBDM

Roluri - TBD
------------
+ Mihai Soare - Product Owner
+ Trifan Alexandru - FrontEnd + PlayerController
+ Andrei Smeada - Docs
+ Dragos Visan - PlayerClass
+ Mihaita Ghiorghe - DictionaryClass + PlayerInterface
+ Cosmin Boaca - Engine Class + Communication Model

Version Control
---------------
+ github: https://github.com/atrifan/habarNam
+ grup FB: TBD
+ wiki (nu e neaparat necesar putem scrie documentatia in README de pe github sau sa facem un git page)


Change Log:
-----------
+ v0.1 - README and planning
+ v0.2 - Project Structure + pom.xml + discuss about dictionary(remote or static)
+ v0.3 - Functional DictionaryClass and EngineClass + eventual UT
+ v0.4 - PlayerClass + UT + integration testing
+ v0.5 - PlayerInterface - support input mechanism from user in terminal - intermediate release
+ v0.6(Bonus) - PlayerController and frontend interface(the frontend interface should use websockets - library socket.io for real-time) + testing
+ v0.7(Bonus) - Deploy on TomCat - done


Tehnical Class Description:
---------------------------
+ Dictionary:
  public boolean isValid(String word);
+ Engine:
  public void registerPlayer(String name);
  public Player getPlayer(String name);
  public ArrayList<String> getUsedWords();
  public String getLetters();
  public ArrayList<Player> getPlayers();
  public long getScore(word); 0 - duplicat -1 gresit >0 corect
+ Player:
  public long getScor();
  public String getName();
  public void play(String word);
+ PlayerInterface - command line
  static main() { -- asculta la tastatura si manipuleaza o instanta de Player() }
+ PlayerController - WS(websockets)
  on('register', name);....
  on('play', word); ... Player.play()....
  emit('scor', scor)
  emit('players', players)


  

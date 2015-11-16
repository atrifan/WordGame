#The Words GAME - MPS#

This is a simple word puzzle word game in Romanian, that can be played in a multiplayer environment.
##Tehnologii software:##
- JAVA - J2EE + maven - backend
- HTML CSS Javascript WebSockets - frotend(ClifJS)

##Model Arhitectural##

###Backend###
- Dictionary - componenta de dictionare care se ocupa cu verificatul cuvintelor in dicitonar
- Engine - componenta care se ocupa de generat litere aleator
- Player - componenta care se ocupa de primit input de la client, cheama DictionaryClass.isValid(cuvant) si ofera punctaj + alte validari adiacenta
- PlayerController - WebSocketController - pentru bonus partea de multiplayer asculta pe evenimentele de emit de la client cu cuvinte si se leaga la PlayerClass dand punctaje inapoi pe canal

###Frontend###
- WordGame Controller
- socket integration

##Roluri##
- Mihai Soare - Product Owner
- Trifan Alexandru 
    * [e-mail:trifan.alex.criss@gmail.com](mailto:trifan.alex.criss@gmail.com) 
    * [CV](ro.linkedin.com/in/trifanalexandru) 
    * Full Stack Developer + UX Designer + Technical Coordinator
- Andrei Smeada 
    * [e-mail:andreismeada@gmail.com](mailto:andreismeada@gmail.com) 
    * Technical Writter
- Dragos Visan 
    * [e-mail:v.dragos19@gmail.com](mailto:v.dragos19@gmail.com) 
    * Quality Assurance Engineer
- Mihaita Ghiorghe 
    * [e-mail:mihaita.ghiorghe@gmail.com](mailto:mihaita.giorghe@gmail.com) 
    * Team Leader + Backend Developer
- Cosmin Boaca 
    * [e-mail:cosmin.boaca1994@gmail.com](mailto:comsin.boaca1994@gmail.com) 
    * Backend Developer

##Version Control##
- github: https://github.com/atrifan/habarNam
- wiki: https://github.com/atrifan/habarNam/wiki

##Tehnical Class Description:##
* **Dictionary**:

    `public boolean isValid(String word);`

* **Engine**:

    `public void registerPlayer(String name);`

    `public Player getPlayer(String name); `

    `public ArrayList getUsedWords(); `

    `public String getLetters();`

    `public ArrayList getPlayers();`

    `public long getScore(word); 0 - duplicat -1 gresit >0 corect`

* **Player**: 

    `public long getScor(); `
    
    `public String getName(); `
    
    `public void play(String word);`

* **PlayerController - WS(websockets)**

    `on('register', name);`

    `on('play', word);`
    
    `emit('scor', scor);`

    `emit('players', players)`

##Architecture Diagrams##
* [Class Diagram](https://drive.google.com/file/d/0B1EEdPeyWeb5RzVGWU1nUlF3R3M/view)
* [Flow Diagram](https://drive.google.com/file/d/0B1EEdPeyWeb5OWRGVmlLNzllYTQ/view)



##Planning Diagram##
* [Gannt Diagram](https://drive.google.com/file/d/0B1EEdPeyWeb5QnZfS3paTFlJOVk/view)

##Usage:##
1. prerequisite: necesita apache-tomcat 7.x
2. `mvn install` in folderul WordGameServer
3. se copiaza folderul WordGameClient in <folder_tomcat>/webapps/
4. se copiaza din folderul WordGameServer/target/ fisierul .war generat in <folder_tomcat>/webapps/
5. se porneste tomcat: 
    * Windows: <folder_tomcat>/bin/catalina.bat run
    * Linux: <folder_tomcat>/bin/catalina.sh run
6. se acceseaza http://localhost:8080/WordGameClient/public/template/wordGame.html
7. jocul a fost testat pe FF latest si Chrome latest

##Change Log:##
* v0.1 - README and planning
* v0.2 - Project Structure + pom.xml + discuss about dictionary(remote or static)
* v0.3 - Functional DictionaryClass and EngineClass + eventual UT
* v0.4 - PlayerClass
* v0.6(Bonus) - PlayerController and frontend interface(the frontend interface should use websockets - library socket.io for real-time) + testing
* v0.7(Bonus) - Deploy on TomCat - done




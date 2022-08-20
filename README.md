# trustgame app developed as part of the TransSonic project 

The TrustGame is part of the PhD research of [ir. Anique Kuijpers](https://www.tudelft.nl/en/tpm/about-the-faculty/departments/multi-actor-systems/people/phd-candidates/ir-agj-anique-kuijpers). 
The game has been described in the paper [The Trust Game: The influence of Trust on Collaboration in the light of Technological Innovations](https://link.springer.com/chapter/10.1007/978-3-030-72132-9_15), and will be more extensively described in Anique's forthcoming PhD thesis. 
The game has been installed on one of the servers at TU Delft: [https://trustgame.tbm.tudelft.nl/trustgame](https://trustgame.tbm.tudelft.nl/trustgame). If you want access, or if you want to replicate the game on one of your own servers, contact Anique Kuijpers or Alexander Verbraeck. 

The trustgame has been written in Java and can be deployed in Tomcat. Currently, it has been tested and deployed with Tomcat 9. The game content itself, as well as player progress, scores, and logging are stored in a MySQL / mariadb database.

The trustgame consists of four web applications: trustgame for the game play (this project), [trustgame-admin](https://github.com/averbraeck/trustgame-admin) for the administration of the game, [trustgame-scores](https://github.com/averbraeck/trustgame-scores) for viewing the scores of any running or finished game, and [trustgame-registration](https://github.com/averbraeck/trustgame-registration) for the self-registration of players. The game has been designed in such a way that no personal data (name, email address, etc.) has to be entered to play the game.


## Installation and development

The software has been optimized to be developed in [Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/) (current settings and project files are for version 2022-06). It runs with Java version 11. We used [OpenJDK 11](https://jdk.java.net/archive/) for the development. When the project is checked out to Eclipse, a [Tomcat 9](https://tomcat.apache.org/download-90.cgi) server needs to be installed in Eclipse for testing. See, e.g., this [GeeksforGeeks page](https://www.geeksforgeeks.org/configuration-of-apache-tomcat-server-with-eclipse-ide/) for instructions how to install Tomcat in Eclipse. A MySQL or mariadb database (we worked with [mariadb version 10.5](https://mariadb.org/download/?t=mariadb&p=mariadb&r=10.5.17)) has to be configured with the game database for testing and deployment. The database tables in the Java code have been auto-generated from the database with [jOOQ](https://www.jooq.org/). 


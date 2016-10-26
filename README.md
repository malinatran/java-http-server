## Java HTTP Server

#### Objectives
* Build a simple, multi-threaded HTTP server in Java
* Gain deeper understanding of how the Internet operates
* Adhere to [SOLID principles](https://www.wikiwand.com/en/SOLID_(object-oriented_design)) and [test-driven development](https://www.wikiwand.com/en/Test-driven_development)
* Pass acceptance tests outlined by [Cob Spec](http://github.com/8thlight/cobspec)

#### Installation
- Requires Java 1.8, Maven, and IntelliJ (optional)
- `git clone https://github.com/malinatran/java-http-server`
- `cd java-http-server`
- `mvn package`

#### Run
Through the command line, run `java -cp target/java-http-server-1.0-SNAPSHOT.jar com.malinatran.Main -p [PORT] -d [DIRECTORY]`. Port and directory are optional; if not provided, default values for both will be set.

Through IntelliJ, select `Main` in dropdown menu and click on the `run` green arrow located in upper right hand corner.

#### Tests
JUnit is the unit testing framework used for this project. Through the command line, run `mvn test`. Through IntelliJ, select `All Tests` in dropdown menu and click on the `run` green arrow located in upper right hand corner. For acceptance tests via Cob Spec, follow instructions listed [here](http://github.com/8thlight/cobspec).

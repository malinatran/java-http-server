## Java HTTP Server

#### Objectives
* Build a simple, multi-threaded HTTP server in Java
* Gain deeper understanding of how the Internet operates
* Adhere to [SOLID principles](https://www.wikiwand.com/en/SOLID_(object-oriented_design)) and [test-driven development](https://www.wikiwand.com/en/Test-driven_development)
* Pass acceptance tests outlined by [Cob Spec](http://github.com/8thlight/cobspec)

#### Installation
- Requires Java 1.8 and Maven
- Instructions:
```sh
$ git clone https://github.com/malinatran/java-http-server
$ cd java-http-server 
$ mvn package
```

#### Run
Setup your environment variable: `export COB_SPEC_CREDENTIALS='Basic YWRtaW46aHVudGVyMg=='`. 

From the root directory, run `java -jar target/java-http-server-1.0-SNAPSHOT.jar`. To specify a port and/or directory, you may follow the command with the following: `-p [PORT] -d [DIRECTORY]`, `-p [PORT]` or `-d [DIRECTORY]`. Port and directory are optional; if not provided, default values for both will be set (to port 5000 and `public` directory, respectively).

#### Tests
JUnit is the unit testing framework used for this project. Through the command line, run `mvn test`. For acceptance tests via Cob Spec, follow instructions listed [here](http://github.com/8thlight/cob_spec).

#### Organization
| Package              | Purpose                                           |
|----------------------|---------------------------------------------------|
| mocks (test folder)  | Includes mock objects for testing                 |
| reader               | Reads from input stream                           |
| request              | Reads and builds request objects                  |
| resource             | Has logic for handling resources                  |
| response             | Formats and builds response objects               |
| router               | Determines action by request type                 |
| setup                | Handles command-line UI and sets up configuration |
| utility              | Includes helper objects and constants             |
| writer               | Writes to output stream                           |
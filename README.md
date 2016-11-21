## Java HTTP Server

### Objectives
* Build a simple, multi-threaded HTTP server in Java
* Gain deeper understanding of how the Internet operates
* Adhere to [SOLID principles](https://www.wikiwand.com/en/SOLID_(object-oriented_design)) and [test-driven development](https://www.wikiwand.com/en/Test-driven_development)
* Pass acceptance tests outlined by [Cob Spec](http://github.com/8thlight/cob_spec)

### Installation
- Requires Java 1.8 and Maven
- Setup environment variable: `export JAVA_SERVER_TOKEN='Basic YWRtaW46aHVudGVyMg=='`
- Instructions:
```
$ git clone https://github.com/malinatran/java-http-server
$ cd java-http-server
$ mvn package
```

### Run
From the root directory, run `java -jar target/java-http-server-1.0-SNAPSHOT.jar`. To specify a port and/or directory, you may pass in the following arguments: `-p [PORT] -d [DIRECTORY]`, `-p [PORT]`, or `-d [DIRECTORY]`. The port and directory are optional; if not provided, default values for both will be set (to port 5000 and `public` directory, respectively).

### Tests
JUnit is the unit testing framework used for this project. Through the command line, run `mvn test`. For acceptance tests via Cob Spec, run the server and follow instructions listed [here](http://github.com/8thlight/cob_spec).

### Codebase

| Package              | Purpose                                           |
|----------------------|---------------------------------------------------|
| mocks (test folder)  | Includes mock objects for testing                 |
| reader               | Reads from input stream                           |
| request              | Parses incoming requests to build request objects |
| resource             | Has logic for handling resources                  |
| response             | Formats and builds response objects               |
| routing              | Determines action by request type                 |
| setup                | Handles command-line UI and sets up configuration |
| utility              | Includes helper objects and constants             |
| writer               | Writes to output stream                           |

More details about how the code is organized [here](https://gist.github.com/malinatran/b4d01c3bfdc83dac3a58d921caecbbb4).

### Contact
:rocket:  Make a pull request, submit an issue, or contact me [@malinatran](https://twitter.com/malinatran).

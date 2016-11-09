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

From the root directory, run `java -jar target/java-http-server-1.0-SNAPSHOT.jar`. To specify a port and/or directory, you may pass in the following arguments: `-p [PORT] -d [DIRECTORY]`, `-p [PORT]`, or `-d [DIRECTORY]`. The port and directory are optional; if not provided, default values for both will be set (to port 5000 and `public` directory, respectively).

#### Tests
JUnit is the unit testing framework used for this project. Through the command line, run `mvn test`. For acceptance tests via Cob Spec, run the server and follow instructions listed [here](http://github.com/8thlight/cob_spec).

#### Organization
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

How the code works:
1. `Main` sets up sockets, logger, and router and executes runnable
2. Various `setup` classes get user input and set args for port and directory
3. `ClientHandler` is responsible for getting requests and writing responses to stream
    * `RequestListener` parses incoming requests and builds `Request` objects
    * `Router` coordinates the creation of `Response` objects and passes along to `action` objects
        - Also, `Router` checks to see whether requests should be stored and if so, `RequestLogger` logs them [1]
        - If it's a "special request", `LoggedAction` will build the response [2]
        - If it's a request with an assigned route, it will be called and build the response (`routing` package)
4. `ClientHandler` closes streams and `Main` closes sockets

[1] Request body will be stored for `PATCH` requests with an `If-Match` header that matches a SHA1 encoded body content and requests with `/parameters?` endpoint; all request status lines are stored.
[2] A "special request" refers to a request that has a valid `Authorization` header, is a `GET /form` request, or is a `GET` request to an existing file.

#### Contact
If you see something, say something. Feel free to make a pull request, submit an issue, or contact me [@malinatran](https://twitter.com/malinatran).
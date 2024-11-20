Here is an example application to demonstrate how to use Diia SDK.

To start example application
- edit `diia-client-example/src/main/resources/application.properties` to setup an Acquirer's Token in there and if you are working behind the proxy setup the proxy settings
- go to root project directory (`diia-client`)
- build project with command `mvn clean install`
- all built artifacts you can find in `assembly` directory
- go to `ua-acquirers-sdk-java\assembly\diia-client-example-1.0-SNAPSHOT` directory
- start example application with command `java -jar diia-client-example-1.0-SNAPSHOT.jar`
- application will listen 8088 port
- open URL `http://localhost:8088` in your browser
- you'll see the main page, there are instructions for possible further actions


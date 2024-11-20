Project Overview

The Diia API SDK allows developers to integrate with the Diia digital services platform for functionalities like document signing and sharing. This SDK enables:
- Authentication to securely access API.
- Document signing with multiple algorithm options.
- Branch and Offer Management.
- Obtaining deeplinks for document sharing and signing scenarios.

Project contents:
- diia-client-crypto-api - API of crypto-service used in SDK
- diia-client-crypto-iit - crypto-service implementation based on proprietary library ( see https://iit.com.ua/ )
- diia-client-example - simple web-application to demonstrate SDK usage
- diia-client-receive-documents-example - simple server application to receive documents sent from Diia and decrypt them
- diia-client-sdk - SDK library


Dependencies
This SDK requires the following Java components:

= Java version: >= 1.8
- Maven: for managing dependencies and building the project.
- Core Libraries:
- OkHttp (version 4.9.3) - for HTTP requests.
- Jackson (core: 2.13.2, databind: 2.13.2.2, annotations: 2.13.2) - for JSON data handling.
- Testing Libraries:
- JUnit Jupiter (version 5.8.2) - for unit testing.
- Mockito (core: 4.4.0, JUnit integration: 4.4.0) - for mocking dependencies during testing.
- MockWebServer (version 4.9.3) - for simulating HTTP responses in tests.
- JSONassert (version 1.5.0) - for verifying JSON structures in tests.


Installation
To install the SDK, use Maven:

Ensure that Maven is installed on your system.
Add the following dependency to your pom.xml file:
<dependency>
    <groupId>ua.gov.diia.client</groupId>
    <artifactId>diia-client-sdk</artifactId>
    <version>1.0.0</version>
</dependency>

Run the following Maven command to install the dependencies:
mvn install
This will download the SDK and its required dependencies to your project.




Testing

The SDK includes a series of test files in the `test` directory to validate the functionalities, such as `DiiaValidationApiImplTest.java` for validation and `DiiaSharingApiImplTest.java` for sharing scenario creation. To run the tests, use:
```bash
mvn test
```


Our scenarios: 
Sharing scenario
Review Technical Documentation
Please review the general technical documentation available here.
Diia Signature scenario
Review Technical Documentation
Please review the general technical documentation available here.

Obtaining a Test Token
To obtain a test token, please complete the application form here.
These steps provide initial access to the API for testing and preparing your integration with the system.

Important links
https://integration.diia.gov.ua/en/home.html - description of all available services
https://t.me/AiDiiaStartBot -reach us out to start the integration


## Purpose

### Java First Design 
#### An object oriented approach to service architecture.
To demonstrate a "java first" design for a service driven architecture. Usually the external interfaces in the architecture 
are defined by Open API Specification files rather than Java. This leads to unnecessary boiler plate code in the client applications 
consuming the external service. Assuming the clients are Java based. 

This project will demonstrate that the external service might be better implemented in Java (plain Java interface) and the marshalling logic auto-generated from that.
Note that the bit-stream implementation does not make a difference there. REST layers and message queue producer/consumer can all be generated from the defined Java interface.

## A diary of sorts
### 24-04-2022 - REST layer code generation. 
Got the custom annotation processor to work with Intellij<->Gradle<->Google auto-service.
The main time-waster here was that Intellij was configured to run gradle tasks instead of gradle itself. This meant that the default source sets were
not registered in Intellij  per default. Intellij wants generated sources to be in /src/main/generated and gradle puts
them in /build/generated/annotationProcessor/...

Next step is to create a REST layer from the @ExternalService  definition. 
The generator in the REST module should create: 

 - Rest specific POJOs (DTO classes)
 - Spring Boot MVC @RestController
 - Mapping to and from the REST layer DTOs.
 - Delegate that uses the direct method proxy to call the core service.

Triggering the code generation must be placed in the &lt;module&gt;/&lt;module&gt;-rest code itself. 
Alternatively a 3rd module (annotation processor) would have to depend on the *-rest module and the code be generated there. 

If annotation processor is the mechanism for generating source then it should be invoked from the target module itself.
The annotation processor needs input from the target *-rest module.This needs to be investigated. The simplest seems to be in the gradle script
where a environment variable can be set.

An issue with this is that an annotation processor requires a source file with an annotation on it. Ideally it would not be necessary to write 
a single line of code in order to get the REST layer for @ExternalService generated.

Instead, a special plugin generating the source should be implemented.
Different plugin settings could generate different REST library implementations.

#### update --
Code generation using a Gradle plugin seems the way to go. There is an example here: 

https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-gradle-plugin/src/main/kotlin/org/openapitools/generator/gradle/plugin/OpenApiGeneratorPlugin.kt

This link solved the "add source folder" problem: https://discuss.gradle.org/t/how-do-i-add-directories-to-the-main-resources-sourceset-in-a-gradle-plugin/5953/3

>Test command: ./gradlew -info clean :modules:test-app:assemble :modules:inventory:inventory-api:assemble

> Look at this: https://www.baeldung.com/gradle-source-sets

### 30-04-2022 - Code-gen day
Working on getting composite builds to work. The includeBuild command must be in the 
parent settings.gradle.kts file. Note: the top-level build.gradle is not needed for the build to work 
since there are no module wide rules to enforce yet. 
Might want to change that and add test dependencies that are shared later.

Tasks for the code generator
- Testing the generated code. The google testing library used for the annotation processor seems promising but adding a classpath for Spring MVC could prove complicated.
- Making a builder pattern that allows for multiple outputs when reflecting over a single Java interface.


### 1-05-2022 more code-gen 
How is a code-generator best build. The director class can easily iterate over method and parameters but what actually changes across different 
code generators.


> BarRecord getBar(String name) // api method

- return type can change - be the same or other. Can cause additional classes created or new import statements.
- annotation for methods can vary.
- Parameters might need annotations.

There was an issue with getting the correct parameter names in the generated code. Adding -parameters to the JVM compiling the 
module code should fix the issue.
>https://stackoverflow.com/questions/21455403/how-to-get-method-parameter-names-in-java-8-using-reflection/21455958#21455958

Ending for today - the results are fine as it is though the building process is cumbersome. Hopefully the efforts will prove off once 
more interfaces are defined and the amount of code generated is higher.


### 8-5-2022 - making rest interfaces

When building the REST interface the interface shold either return the same entities as 
the @ExternalService exposes or build entirely new ones. 

The best solution I would think is to use the @ExternalService contract as the POJOs are already part of the API definition. 
The whole point of generating the source code for this is to prevent boiler-plate code. 

After the API defintion is made (@ExternalService) the rest should simply be auto generated from this. 
If the core service wishes to have other, internal, data-structures these can be added over time and does not need to 
exist in the first release of the service. 

If the internal model and the external model starts to deviate then those new internal classes 
can be added slowly and simply mapped to the @ExternalService domain classes.

Java 14 records should be serializable by Spring MVC using Jackson. 
We might have to use a newer version of jackson than what is default in Spring Boot. 

> Executing the plugin in the inventory-rest is causing some problems. The inventory-api module is not available at 
> plugin execution time. Defining a custom Gradle 'configuration' seems to be the solution. 

> Documentation: https://docs.gradle.org/current/userguide/implementing_gradle_plugins.html#providing_default_dependencies_for_plugins

Note: it was not. Deploying stuff to maven local is not needed in a mono-repo model as we have now. 

The composite project model works fine with gradle, which will resolve the includBuild dependencies just fine.

Ending for today.

### 13-05-2022 (Weekend of...) Spring rest clean up and error handling. 
The build issues were solved by going completely with composite builds. The root folder no longer is a gradle project.

The generation of sources is solved by (finally) typing in the correct classname. The build script class path configuration worked
almost from the beginning, but there was a typo in  the inventory-rest build.gradle.kts file for the "restController" extension argument. 

Next up: Error handling on both service and client side when using http.

A few points:
- Code generator does not check rules for method naming or overlapping HTTP verbs and endpoints. This should be added at some point.
- Service module names should be in the generated rest output for both client and rest controller.


#### Error handling
To support automatic error handling some key features must be supported. 

- Code generator must detect which RemoteExceptions thrown by the RemoteService that is being code-generated and...
  - Generate marshall/unmarshall logic for each. 
  - Alternatively RemoteException must have a shared super class that dictates the instantiation.
- A Remote exception must be serializable in a general fashion. 
  - errorType : String
  - dataMap : Map<String, ? extends Serializable>
  - stackTrace : List<String> - Array of strings from the original exception. This should be formatted by the client.
- Serializing the RemoteException must contain the server-side stacktrace as part of the exception message.
- The client side REST proxy must unmarshall the RemoteException and throw it with all serialized data and with the same type.


An MVP implementation of this will contain the following source files:
(Not! generated!)
- RemoteException super class - defines shared constructor which all other exceptions must follow.
- RestControllerExceptionHandler (@ControllerAdvice) class to serialize RemoteException to the http stream. See : https://www.baeldung.com/exception-handling-for-rest-with-spring
- RestClientExceptionHandler which will unmarshal the exception and re-throw it on the client side.

Generated
- RestClientServiceProxy - client proxy marshalling java commands to http. 
- RestControllerServiceProxy - Spring rest controller unmarshalling http response to java service method calls.
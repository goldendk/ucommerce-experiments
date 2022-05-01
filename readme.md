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

There was an issue with getting the correct parameter names in the generated code. Adding -paramters to the JVM compiling the 
module code should fix the issue.
>https://stackoverflow.com/questions/21455403/how-to-get-method-parameter-names-in-java-8-using-reflection/21455958#21455958

Ending for today - the results are fine as it is though the building process is cumbersome. Hopefully the efforts will prove off once 
more interfaces are defined and the amount of code generated is higher.

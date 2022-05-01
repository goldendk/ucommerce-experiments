rootProject.name = "ucommerce-experiments"
includeBuild("tooling")
includeBuild("modules/shared-kernel")
include("dual-service-impl:service-a")
findProject(":dual-service-impl:service-a")?.name = "service-a"
include("dual-service-impl:service-b")
findProject(":dual-service-impl:service-b")?.name = "service-b"
include("dual-service-impl:dual-service-shared")
findProject(":dual-service-impl:dual-service-shared")?.name = "dual-service-shared"
include("dual-service-impl:dual-service-app")
findProject(":dual-service-impl:dual-service-app")?.name = "dual-service-app"
include("dual-service-impl:dual-service-app2")
findProject(":dual-service-impl:dual-service-app2")?.name = "dual-service-app2"
include("dual-service-impl:dual-service-a-rest")
findProject(":dual-service-impl:dual-service-a-rest")?.name = "dual-service-a-rest"
include("dual-service-impl:dual-service-a-api")
findProject(":dual-service-impl:dual-service-a-api")?.name = "dual-service-a-api"
include("modules")
include("modules:inventory")
findProject(":modules:inventory")?.name = "inventory"
include("modules:inventory:inventory-core")
findProject(":modules:inventory:inventory-core")?.name = "inventory-core"
include("modules:inventory:inventory-rest")
findProject(":modules:inventory:inventory-rest")?.name = "inventory-rest"
include("modules:inventory:inventory-api")
findProject(":modules:inventory:inventory-api")?.name = "inventory-api"

include("libraries")
include("libraries:annotation-processor")
findProject(":libraries:annotation-processor")?.name = "annotation-processor"
include("modules:test-app")
findProject(":modules:test-app")?.name = "test-app"
include("libraries:gradle-plugin-code-generator")
findProject(":libraries:gradle-plugin-code-generator")?.name = "gradle-plugin-code-generator"

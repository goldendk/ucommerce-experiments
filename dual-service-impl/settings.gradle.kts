rootProject.name = "dual-service-impl"
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

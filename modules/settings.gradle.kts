includeBuild("inventory/inventory-api")
includeBuild("../libraries")
includeBuild("../tooling")

include("inventory:inventory-core")
include("inventory:inventory-rest")
include("inventory:inventory-rest-client")
include("test-app")
include("inventory:inventory-test")


includeBuild("orders/orders-api-external")
includeBuild("orders/orders-api-internal")

include("orders:orders-core")

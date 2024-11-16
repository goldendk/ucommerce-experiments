REM dual-service-impl\gradlew.bat -p .\dual-service-impl assemble
apps\service-consumer-app\gradlew.bat -p .\apps\service-consumer-app assemble
apps\tooling-test-app\gradlew.bat assemble
libraries\gradlew.bat -p .\libraries assemble
tooling.gradlew.bat -p .\tooling assemble
shared-kernel\gradlew.bat -p .\shared-kernel assemble
modules\gradlew.bat -p .\modules assemble
modules\inventory-api\gradlew.bat -p .\modules\inventory assemble
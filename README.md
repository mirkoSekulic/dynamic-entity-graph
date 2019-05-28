# Dynamic entity graph
This is a basic spring boot demo project for dynamic data retrieving. Idea of this demo is to show how to use entity graph dinamicaly without using static @EntityGraph annotation. We can annotate every relation as lazy in addition to speed, and retrieve only data that we need and can create static entity graph, or use dynamic graph in runtime. This is very useful for entities with complex relations. To use methods with dynamic graph your repository only need to extend JpaSpecificationExecutorWithEntityGraph interface. Demo contains example with simple dynamic set relation retrieving and mapping at runtime.
## Installation

1. Create mysql schema with name demo
2. Run command mvn clean install -DskipTests.

## Swagger
Swagger documentation is available on endpoint */api/swagger-ui.html*.

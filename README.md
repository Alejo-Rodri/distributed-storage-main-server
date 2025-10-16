# distributed-storage-main-server

Servidor principal de un sistema de almacenamiento distribuido basado en Spring Boot. Expone servicios SOAP (Spring-WS) para operaciones sobre archivos y directorios, y usa JPA/Hibernate con H2 en memoria y migraciones con Flyway. El proyecto contempla integrar gRPC a futuro (ya existen prototipos y clientes básicos).

## Estado del proyecto

Incluye servicios SOAP iniciales para gestión de archivos (consultar, mover/renombrar, eliminar) y creación de directorios, junto con la capa de persistencia y migraciones. Existen funcionalidades por completar o implementar en siguientes iteraciones (p. ej., integración gRPC más amplia, ampliación de operaciones SOAP y consolidación del contrato XSD/DTO).

## Tecnologías

- Spring Boot 3.5.6
- Spring Web Services (SOAP) – Spring-WS
- Spring Data JPA / Hibernate
- H2 Database (memoria, modo MySQL)
- Flyway (migraciones)
- Lombok
- (En progreso) gRPC con Spring for gRPC e `io.grpc` (protos `hello.proto` y `directory.proto`, clientes base en `grpc/client/...`).

## Requisitos

- JDK compatible con Spring Boot 3 (p. ej., 17+).
- Maven Wrapper incluido (`mvnw.cmd`).
- Base de datos H2 en memoria (configurada por defecto).

## Instalación y ejecución (Windows PowerShell)

Compila y ejecuta:

```powershell
cd E:\Prototipe_2\distribuited-storage-main-server
mvn -q -DskipTests clean package
java -jar target\distributed-storage-main-server-0.0.1-SNAPSHOT.jar
```

También puedes ejecutar directamente con Spring Boot:

```powershell
cd E:\Prototipe_2\distribuited-storage-main-server
mvn -DskipTests spring-boot:run
```

## Orden recomendado de arranque con el nodo gRPC

1. Arranca primero el nodo (`grpc-storage-node`) en el puerto 9090.
2. Luego arranca este servidor principal (SOAP). Los clientes gRPC (Hello/Directory/FileNode) se conectan a `localhost:9090`.

## Verificación de conexión

- Hello: invoca el endpoint SOAP `HelloRequest` y verifica que devuelve “Hello, <name>”. Internamente usa gRPC al nodo.
- CreateDirectory: invoca el endpoint SOAP para crear un directorio; el nodo ejecuta la operación y responde con path/owner/createdAt.
- Mover/Eliminar: los endpoints SOAP `MoveRenameFile` y `DeleteFileById` están cableados a gRPC mediante `GrpcFileServices` (@Primary). Asegúrate de que el nodo esté levantado.

## Guía paso a paso (comandos PowerShell)

Sigue estos comandos en este orden para levantar servicios, ejecutar operaciones y validar resultados.

### 1) Levantar servicios

- Ejecutar el nodo (gRPC) usando el script centralizado:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File E:\Prototipe_2\scripts\start-node.ps1 -Mode mvn
```

- Ejecutar el main server (SOAP) en segundo plano:

```powershell
Start-Process -FilePath "mvn" -ArgumentList "-DskipTests","spring-boot:run" -WorkingDirectory "E:\Prototipe_2\distribuited-storage-main-server" -PassThru
```

### 2) Operaciones en el nodo (desde carpeta del nodo)

```powershell
Set-Location E:\Prototipe_2\grpc-storage-node

# Crear un directorio en el nodo (gRPC vía script)
powershell -NoProfile -ExecutionPolicy Bypass -File .\cli-scripts\node-mkdir.ps1 -Path storage\demo

# Crear un archivo de prueba dentro de ese directorio (el nodo lo verá porque opera en la misma máquina)
New-Item -Path .\storage\demo\archivo.txt -ItemType File -Force | Out-Null
Set-Content -Path .\storage\demo\archivo.txt -Value "Hola desde el nodo"

# Mover/Renombrar el archivo (gRPC vía script)
powershell -NoProfile -ExecutionPolicy Bypass -File .\cli-scripts\node-move.ps1 -Source storage\demo\archivo.txt -Destination storage\demo\archivo-renombrado.txt

# Eliminar el archivo (gRPC vía script)
powershell -NoProfile -ExecutionPolicy Bypass -File .\cli-scripts\node-delete.ps1 -Path storage\demo\archivo-renombrado.txt

# (Opcional) Eliminar el directorio si quedó vacío
powershell -NoProfile -ExecutionPolicy Bypass -File .\cli-scripts\node-delete.ps1 -Path storage\demo
```

Verificaciones rápidas en el nodo:

```powershell
# Debe ser True (destino existe)
Test-Path .\storage\demo\archivo-renombrado.txt

# Debe ser False (origen ya no existe)
Test-Path .\storage\demo\archivo.txt

# Detalles del archivo renombrado
Get-Item .\storage\demo\archivo-renombrado.txt | Format-List FullName,Length,LastWriteTime

# Contenido del archivo
Get-Content -Raw .\storage\demo\archivo-renombrado.txt

# Listado del directorio
Get-ChildItem .\storage\demo | Select-Object Name,Length,LastWriteTime

# (Opcional) Hash de integridad
Get-FileHash .\storage\demo\archivo-renombrado.txt -Algorithm SHA256
```

### 3) Llamadas SOAP desde el main server

Ejecuta estos en una terminal ubicada en `E:\Prototipe_2\distribuited-storage-main-server`.

Hello (verifica NPE resuelto y respuesta 200):

```powershell
$soap = @'
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="http://example.com/hello">
  <soapenv:Body>
    <HelloRequest>
      <name>World</name>
    </HelloRequest>
  </soapenv:Body>
  </soapenv:Envelope>
'@
Invoke-WebRequest -Uri "http://localhost:8080/ws" -Method Post -ContentType "text/xml; charset=utf-8" -Body $soap
```

Crear directorio (SOAP → gRPC DirectoryService):

```powershell
$soap = @'
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="http://example.com/directories">
  <soapenv:Body>
    <CreateDirectoryRequest>
      <path>storage/soapdir</path>
    </CreateDirectoryRequest>
  </soapenv:Body>
</soapenv:Envelope>
'@
Invoke-WebRequest -Uri "http://localhost:8080/ws" -Method Post -ContentType "text/xml; charset=utf-8" -Body $soap
```

Mover/renombrar (SOAP → gRPC FileNodeService):

```powershell
# Prepara el archivo en el almacenamiento del nodo (una sola vez si no existe)
New-Item -Path 'E:\Prototipe_2\grpc-storage-node\storage\soapfile.txt' -ItemType File -Force | Out-Null

$soap = @'
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="http://example.com/files">
  <soapenv:Body>
    <MoveRenameFileRequest>
      <sourceFile>storage/soapfile.txt</sourceFile>
      <destinationDir>storage/soapfile-renombrado.txt</destinationDir>
    </MoveRenameFileRequest>
  </soapenv:Body>
</soapenv:Envelope>
'@
Invoke-WebRequest -Uri "http://localhost:8080/ws" -Method Post -ContentType "text/xml; charset=utf-8" -Body $soap
```

## Changelog

- 2025-10-15: Se agregó `FileNodeClient` (gRPC) y `GrpcFileServices` (@Primary) para mover/renombrar y eliminar; documentación de orden de arranque y verificación.
- 2025-10-15: Se añadió guía paso a paso con comandos PowerShell para levantar servicios, ejecutar operaciones gRPC vía scripts y validar endpoints SOAP.

## Configuración

`src/main/resources/application.properties`:
- H2 console activada (`/h2-console`).
- URL H2: `jdbc:h2:mem:testdb` (usuario `sa`, pass `password`).
- Flyway activado, migraciones en `classpath:db/migration`.

## Base de datos y migraciones

- Script inicial: `src/main/resources/db/migration/V1__init_database.sql`
  - Crea tablas: `user_account`, `directory`, `file`, `shared_file` y una vista `v_space_usage`.
  - Inserta datos de ejemplo.
- Notas H2:
  - Está en modo MySQL, pero tipos como `ENUM` y cláusulas `ON UPDATE CURRENT_TIMESTAMP` pueden comportarse distinto. Si aparece un error, considera adaptar el SQL para H2 o separar por perfiles.

## SOAP (WSDL/XSD)

Endpoints y contratos principales:

- Archivos (namespace `http://example.com/files`):
  - WSDL: `http://localhost:8080/ws/files.wsdl`
  - XSD: `src/main/resources/xml/schema/files.xsd`
  - Operaciones:
    - `GetFileRequest` → `GetFileResponse`
    - `MoveRenameFileRequest` → `MoveRenameFileResponse`
    - `DeleteFileByIdRequest` → `DeleteFileByIdResponse`

- Directorios (namespace `http://example.com/directories`):
  - WSDL: `http://localhost:8080/ws/directories.wsdl`
  - XSD: `src/main/resources/xml/schema/directories.xsd`
  - Operaciones:
    - `CreateDirectoryRequest` → `CreateDirectoryResponse`

Ejemplo de request:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:fil="http://example.com/files">
  <soapenv:Header/>
  <soapenv:Body>
    <fil:GetFileRequest>
      <fil:id>1</fil:id>
    </fil:GetFileRequest>
  </soapenv:Body>
</soapenv:Envelope>
```

Nota de contrato: el XSD y los DTOs SOAP se mantienen alineados y pueden evolucionar. Revisa los XSDs para conocer la forma exacta de los mensajes.

## Estructura del proyecto (resumen)

```
src/
  main/
    java/co/edu/upb/distributed_storage_main_server/
      config/WebServiceConfig.java
      controller/
        FileEndpoints.java
        DirectoryEndpoints.java
      services/
        directory/{DirectoryServices, IDirectoryServices}.java
        {FileServices, IFileServices}.java
      repository/FileRepository.java
      domain/{FileEntity, DirectoryEntity, UserEntity}.java
      grpc/client/
        hello/{HelloClient, IHelloClient}.java
        directory/{DirectoryClient, IDirectoryClient}.java
        AbstractClient.java
      utils/LocalDateTimeAdapter.java
      DistributedStorageMainServerApplication.java
    resources/
      application.properties
      db/migration/V1__init_database.sql
      xml/schema/{files.xsd, directories.xsd}
    proto/
      hello.proto
      directory.proto
```

## Próximas iteraciones sugeridas

- Consolidar gRPC con casos de uso de archivos y directorios (definir `.proto` adicionales y servicios).
- Alinear contrato SOAP (XSD) con los DTOs para marshalling estable.
- Ampliar operaciones de negocio (creación, listado, compartición de archivos, etc.).
- Añadir pruebas de integración (SOAP y repositorios JPA).
- Perfiles de ejecución (local/dev/prod) y BD persistente según necesidad.

## Desarrollo

- Requisitos: JDK 17, VS Code/IntelliJ, soporte Lombok en el IDE.
- Estilo: Java 17+, Lombok para getters/setters, evitar lógica en entidades.
- Pruebas: `spring-boot-starter-test` y `spring-grpc-test` (este último mientras gRPC se use).

## Roadmap sugerido

- [ ] Decidir si gRPC se usará ahora. Si sí, añadir `.proto` y endpoints.
- [ ] Corregir `pom.xml` (Java 17 y plugin protobuf).
- [ ] Arreglar el método `main`.
- [ ] Alinear XSD ↔ DTO.
- [ ] Añadir tests de integración para endpoint SOAP.
- [ ] Externalizar perfiles (local/dev/prod) y considerar BD persistente.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## Seguridad y contribución

- Política de seguridad: ver `SECURITY.md`.
- Guía de contribución: ver `CONTRIBUTING.md`.
- Código de conducta: ver `CODE_OF_CONDUCT.md`.

## Contacto

No hay un canal de contacto oficial para este proyecto académico.

Si encuentras problemas no relacionados con seguridad, puedes abrir un issue descriptivo en el repositorio.

Para vulnerabilidades, revisa la sección "Seguridad" más abajo.

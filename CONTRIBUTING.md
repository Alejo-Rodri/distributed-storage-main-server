# Guía de Contribución

¡Gracias por tu interés en contribuir! Este proyecto usa Spring Boot y expone un servicio SOAP; también planea gRPC. Para mantener la calidad y la coordinación, sigue estas pautas:

## Flujo de trabajo

1. Crea un issue describiendo el cambio propuesto o bug.
2. Crea una rama desde `master` con un nombre descriptivo (ej. `feature/soap-get-file` o `fix/protobuf-plugin`).
3. Realiza cambios pequeños y con commits claros.
4. Asegúrate de que el proyecto compila y los tests pasan.
5. Abre un Pull Request (PR) enlazando el issue y describe el cambio.

## Estilo de código

- Java 17+.
- Usa Lombok para getters/setters y evita lógica en entidades JPA.
- Sigue las convenciones de Spring (nombres de paquetes en minúscula, clases en `UpperCamelCase`).
- No mezcles capas (controladores/servicios/repositorios) en un mismo paquete.

## Pruebas

- Incluye pruebas unitarias y, cuando aplique, pruebas de integración.
- Para SOAP, añade pruebas que verifiquen el marshalling conforme al XSD.
- Mantén los tests rápidos y deterministas.

## Documentación

- Actualiza `README.md` si introduces nuevas funcionalidades.
- Si cambias el contrato SOAP (XSD), actualiza ejemplos y notas de compatibilidad.

## Commit messages

- Formato sugerido: `tipo(scope): resumen` (ej. `fix(build): desactivar protobuf sin .proto`).
- Tipos comunes: `feat`, `fix`, `docs`, `test`, `refactor`, `build`, `ci`.

## Revisión de PRs

- Un PR debe ser revisado al menos por otra persona (si aplica).
- Responde a comentarios y actualiza el PR según sea necesario.

## Seguridad

- No subas secretos o credenciales.
- Reporta vulnerabilidades siguiendo `SECURITY.md`.

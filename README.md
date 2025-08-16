
<img width="1536" height="1024" alt="portada" src="https://github.com/user-attachments/assets/cb173da3-7002-4f30-9e77-63fd16cb7aa8" />

---
# 📘 BookCatalog API

**BookCatalog API** es una solución modular desarrollada con **Spring Boot** que permite **consultar**, **registrar** y **gestionar libros** de forma segura y eficiente. Diseñada para ofrecer una experiencia clara y profesional tanto en entornos CLI como web, esta API garantiza integridad en la persistencia de datos, evita duplicados y proporciona retroalimentación precisa al usuario.

---
## 🚀  Funcionalidades destacada
- 🔍 Consulta de libros desde Gutendex por título, idioma, autor o palabra clave
- 📥 Persistencia local en base de datos PostgreSQL con validación de duplicados
- 📊 Visualización en consola con tablas estilizadas y animaciones de carga
- 🧹 Interfaz CLI optimizada con limpieza de consola, menús interactivos y retroalimentación clara.
- 🛡️ Integridad de datos: evita registros duplicados y válido campo clave
- 📈 Ranking de libros más descargados
- 🔎 Filtros avanzados: por idioma, autor, año de nacimiento y título parcial
- 
---
## 🧰 Tecnologías utilizadas

- Java 21
- Spring Boot 3.5.3
- Maven
- JPA + Hibernate
- Gutendex API (gratuita)
- MapStruct
- H2 Database (memoria)
- Jackson
- Postgres (SQL)

---
## 📘 Cómo obtener una API de Gutendex

La API Gutendex es un catálogo de información de más de 70.000 libros presentes en Project Gutenberg (biblioteca en línea y gratuita).
Asegúrate de seguir cuidadosamente las instrucciones proporcionadas por la API Gutendex.

### 📝 Pasos para obtenerla:

1. Ve al sitio oficial de Gutendex:

   👉 [https://gutendex.com](https://gutendex.com)
2. Usarla de la siguiente manera:
   - guardarla en `application.properties`
     
   
   ```properties
         gutendex.api.url= https://gutendex.com/books/
   ```
   
--- 

## 🐘 Configuración de PostgreSQL (opcional)

Este proyecto también puede usar **`PostgreSQL`** como base de datos en lugar de H2 en memoria.
Si deseas persistencia real de los datos entre reinicios, puedes usar PostgreSQL.

### 🔽 Descarga e instalación

1. Ve al sitio oficial de PostgreSQL y descarga la versión más reciente para tu sistema operativo:

   👉 [https://makersuite.google.com/app/apikey](https://makersuite.google.com/app/apikey)

2. Durante la instalación, asegúrate de configurar un usuario, contraseña y puerto (por defecto es el 5432).
---

## ⚙️Clona el repositorio

```bash
git remote add origin https://github.com/AJulio569/book-catalog-springboot-postgres-h2.git
```
---
## Compila el proyecto con Maven
```bash
mvn clean install
```
---
## Ejecuta la aplicación
```bash
mvn spring-boot:run
```
---

## 🔐 Configuración de variables de entorno para PostgreSQL

Para mantener segura y flexible la conexión a la base de datos PostgreSQL, este proyecto utiliza **variables de entorno** en lugar de datos sensibles escritos directamente en el archivo de configuración.

---

## ⚙️ Configuración del perfil PostgreSQL
1. Crea un archivo de configuración:

   `src/main/resources/application-postgres.properties`
   
3. Copia y edita este contenido:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/TU_NOMBRE_DE_BASE_DE_DATOS
spring.datasource.username=TU_USUARIO_POSTGRES
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
``` 
### 🔐 Reemplaza TU_PASSWORD con la contraseña real de tu usuario PostgreSQL.
___

## 🚀 Activar perfil de PostgreSQL

Para levantar la app usando PostgreSQL, ejecuta con el siguiente perfil:

```bash
     ./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres

```

### ⚙️ Configuración en `application.properties`

Configura el perfil activo:

```propertis
     spring.profiles.active=postgres`

```
---

## ⚙️ Configuración del perfil H2 en memoria
1. Crea un archivo de configuración:

   `src/main/resources/application-dev.properties`
2. Copia y edita este contenido:

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
``` 
---

## 🚀 Activar perfil de H2 en memoria

Para levantar la app usando H2, ejecuta con el siguiente perfil:

```bash
     ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

```
### ⚙️ Configuración en `application.properties`

Configura el perfil activo:

```propertis
     spring.profiles.active=dev
```
---

## 🧭 Menú principal (CLI)

Al iniciar la aplicación, el usuario es recibido con un menú interactivo en consola que permite explorar todas las funcionalidades de la API. El diseño está pensado para ofrecer una experiencia clara, motivadora y visualmente atractiva.
---

### 📋 Vista del menú en consola

<img width="773" height="505" alt="menu" src="https://github.com/user-attachments/assets/c9dcbbdb-7aff-4c0f-abb6-06df441575b7" />

### ✨ Características del menú
- ✅ **Diseño visual con bordes decorativos y emojis** para facilitar la navegación.
- ✅ **Opciones numeradas** para una interacción rápida y precisa.
- ✅ **Mensajes motivadores y retroalimentación inmediata** tras cada acción.
- ✅ **Limpieza automática de consola** para mantener orden y profesionalismo.


---
## 📄 Licencia

**MIT License. Puedes usar este código libremente con fines educativos o personales.**

---

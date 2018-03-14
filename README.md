# file-uploader

# What is it

##### Spring boot service (built in Kotlin and on Spring Boot 2.0) used to upload files using HTTP multipart requests

## Backup storage

Service can act as a (backup) storage for any kind of data. Files are saved to local disk sotrage - base path is configurable.
(See property `files.path`)

## Configurable file age

Data is managed by age - files older than **n** days are periodically deleted. (See property `files.max-days-age`)

## Tracing

All upload requests are traced to log files. Data is logged in JSON format. (See property `trace.files.path`).

Example of trace info - saving file My-file.pdf to directory mydir:

``
  2018-03-13_20:42:25.721 - {"file":"My-file.pdf","directory":"mydir"}
``
## Rest API

Files can be uploaded using HTTP multipart requests. More specifically using API endpoint:

- **POST /api/file/upload**

Parameters:

- __file__ - file to be uploaded
- __directory__ - directory (under base path) to save file into

## Swagger API documentation

API documents via swagger UI can be accessed via **/swagger-ui.html**

# Setup

- Download (really?)
- Configure maven or use the bundled version inside a project
- Modify `application.properties` file
- Build project via Maven (`mvn install`)
- Run created jar via (for instance) `java -jar file-uploader-0.0.1-SNAPSHOT.jar`. 
You may want to run jar as a service or at least use `nohup`

## Properties configuration

All known Spring properties must/can be applied here (`server.port`, `spring.application.name`, ...) In addition to that,
service provides some additional configuration options:

- **trace.files.path** - Customize tracing log path to store tracing info ( default: trace )
- **files.path** - Customize files storage base path 
- **files.max-days-age** - Customize max age for files (last modified time is used)

# Tech used

- [Spring Boot 2.0](https://projects.spring.io/spring-boot/)
- [Kotlin](https://kotlinlang.org/)

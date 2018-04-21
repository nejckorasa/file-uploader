# file-uploader

# What is it

Service used to upload files using HTTP REST multipart requests. It's main purpose is to act as a simple solution to backup storage. It supports automatic file deletion after customizable file age. Made to work with JWT security using [JWK](https://tools.ietf.org/html/rfc7517).

## Backup storage

Service can act as a (backup) storage for any kind of data. Files are saved to local disk storage - base path is configurable.
(See property `files.path`)

## Configurable file age

Data is managed by age - files older than **n** days are periodically deleted. (See property `files.max-days-age`)

## Configurable file deletion schedule
 
Scheduled time to run deletion process can be configured using [cron expressions](http://www.cronmaker.com). 
(See property `files.deletion-cron-expression`) For example, to run deletion process every day at 2 AM:

``
files.deletion-cron-expression=0 0 2 1/1 * ?
``

Above cron expression is also set as default (if no property is defined).

## Tracing

All upload requests are traced to log files. Data is logged in JSON format. (See property `trace.files.path`).

Example of trace info - saving file My-file.pdf to directory mydir:

``
  2018-03-13_20:42:25.721 - {"file":"My-file.pdf","directory":"mydir"}
``

- Tracing can be enabled/disabled by adding `trace-enable` as active profile. (See property `spring.profiles.active`)
- Trace files are automatically rolled when file size exceeds 50MB.  (See file `logback-spring.xml`)

## Rest API

Files can be uploaded using HTTP multipart requests. More specifically using API endpoint:

- **POST /api/file/upload**

Parameters:

- __file__ - file to be uploaded
- __directory__ - directory (under base path) to save file into

## Swagger API documentation

API documents via swagger UI can be accessed via **/swagger-ui.html**

For instance: [http://localhost:8888/swagger-ui.html](http://localhost:8888/swagger-ui.html) if you run the service locally on port 8888 that is set as default.

## Security

OAuth2 security is supported using JWT (Json Web Tokens). It can be enabled/disabled via spring profiles:

- **no-auth** - No security is enabled
- **oauth2-auth** - OAuth2 Security using JWT (See property `security.oauth2.resource.jwk.key-set-uri`)


# Setup

- Download ( really? )
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
- **spring.profiles.active** - Enable/disable tracing by adding `trace-enable` as active profile

# Calling REST API ( via RestTemplate )

If by any chance you are using `RestTemplate` to call REST API and do not have an actual file but it's rather already loaded as byte array ( `byte[]` ) ...

Following method will make multipart http request.

```java
public void uploadFile(final byte[] data, final String uri, final String dir) {

    RestTemplate restTemplate = restTemplatesFactory.get();

    ByteArrayResource contentsAsResource = new ByteArrayResource(pdfDto.getData()) {

      @Override
      public String getFilename() { return "my_file_name"; }

    };

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", "my_file_name"));
    map.add("filename", "my_file_name");
    map.add("file", data);

    if (dir != null)
    {
      map.add("directory", dir);
    }

    restTemplate.postForObject(uri, map, ResponseEntity.class);
  }
```


# Tech used

- [Spring Boot 2.0](https://projects.spring.io/spring-boot/)
- [Kotlin](https://kotlinlang.org/)
- [Swagger](https://swagger.io/)
- [JWT - Json Web Tokens](https://jwt.io)


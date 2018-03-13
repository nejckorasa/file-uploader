package com.documents.saver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket


/**
 * @author Nejc Korasa
 */

@EnableScheduling
@EnableSwagger2
@SpringBootApplication
class Application {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.documents.saver"))
            .paths(PathSelectors.any())
            .build()
}

fun main(args: Array<String>) { runApplication<Application>(*args) }
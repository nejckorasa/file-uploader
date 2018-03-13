package com.documents.saver.trace

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


/**
 * @author Nejc Korasa
 */

@Component
class UploadTracer(private val objectMapper: ObjectMapper) {

    private val logger = LoggerFactory.getLogger("trace-log")

    fun trace(file: String, directory: String?) = logger.info(objectMapper.writeValueAsString(TraceInfo(file, directory)))
}
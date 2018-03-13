package com.documents.saver

import com.documents.saver.trace.UploadTracer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import javax.annotation.PostConstruct

/**
 * @author Nejc Korasa
*/

@Component
class FileSaver(
        @Value("\${files.path}") private val filesBasePath: String,
        private val uploadTracer: UploadTracer) {

    private val logger = LoggerFactory.getLogger(FileSaver::class.java)

    @PostConstruct
    fun init() = logger.info("Files base path is set to: $filesBasePath")

    fun save(file: MultipartFile, directory: String?) {

        logger.debug("Saving file: NAME=${file.originalFilename}, CONTENT_TYPE=${file.contentType}, SIZE=${file.size}")

        // create directory ( full path ) if it does not exist
        val dirName = directory?.let { "$filesBasePath/$it" } ?: filesBasePath
        File(dirName).apply { if (!exists()) mkdirs() }

        // create new file if it does not exist
        val convertedFile = File("$dirName/${file.originalFilename}")
        convertedFile.createNewFile()

        // save file
        logger.debug("Saving file to ${convertedFile.absolutePath}")
        FileOutputStream(convertedFile).apply { write(file.bytes) ; close() }

        // trace uploads
        uploadTracer.trace(file.originalFilename!!, directory)
    }
}
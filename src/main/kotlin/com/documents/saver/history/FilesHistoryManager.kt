package com.documents.saver.history

import com.documents.saver.FileSaver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


/**
 * @author Nejc Korasa
 */

@Component
class FilesHistoryManager(
        @Value("\${files.path}") private val filesBasePath: String,
        @Value("\${files.max-days-age}") private val maxDaysAge: Int) {

    private val logger = LoggerFactory.getLogger(FileSaver::class.java)


    @Scheduled(cron = "\${files.deletion-cron-expression:0 0 2 1/1 * ?}")
    fun run() {

        val cal = Calendar.getInstance().apply { add(Calendar.DATE, - 1 * maxDaysAge) }
        logger.debug("Deleting files before ${cal.time}")

        val deleteBefore: Long = cal.timeInMillis
        val dirPath = Paths.get(filesBasePath)

        deleteFromDirectory(dirPath.toFile(), deleteBefore)
    }


    private fun deleteFromDirectory(directory: File, deleteBefore: Long) {

        Files
                .walk(directory.toPath())
                .map { it.toFile() }
                .filter { !it.isDirectory }
                .filter { it.lastModified() < deleteBefore }
                .forEach { logger.debug("Deleting file ${it.name}") ; it.delete() }
    }
}
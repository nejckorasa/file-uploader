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


    @Scheduled(cron = "0 0 1 * * ?") // every day at 1 AM TODO -> make it configurable via properties
    fun run() {

        val cal = Calendar.getInstance().apply { add(Calendar.DATE, - 1 * maxDaysAge) }
        logger.debug("Deleting files after ${cal.time}")
        val deleteBefore: Long = cal.timeInMillis

        val dirPath = Paths.get(filesBasePath)

        Files
                .walk(dirPath)
                .map { it.toFile() }
                .forEach {
                    if (it.isDirectory) deleteFromDirectory(it, deleteBefore)
                    else logger.debug("Deleting file ${it.name}") ; it.delete()
                }
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
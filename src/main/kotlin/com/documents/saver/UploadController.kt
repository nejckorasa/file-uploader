package com.documents.saver

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


/**
 * @author Nejc Korasa
 */

@RestController
@RequestMapping("api/file")
class UploadController(private val fileSaver: FileSaver) {

    @PostMapping("upload")
    fun save(
            @RequestParam("file") file: MultipartFile,
            @RequestParam("directory", required = false) directory: String?) = fileSaver.save(file, directory)
}
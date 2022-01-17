package com.example.photobackend.controllers

import com.example.photobackend.models.Photo
import com.example.photobackend.repositories.PhotoRepository
import com.google.cloud.spring.vision.CloudVisionTemplate
import com.google.cloud.vision.v1.Feature
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource
import org.springframework.core.io.WritableResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
class UploadController(
    private val visionTemplate: CloudVisionTemplate,
    private val photoRepository: PhotoRepository,
    private val ctx: ApplicationContext
) {
    private val bucket = "gs://spring-cloud-337703.appspot.com/images"

    @PostMapping("/upload")
    fun upload(@RequestParam("file") file: MultipartFile): Photo {
        val id = UUID.randomUUID().toString()
        val uri = "$bucket/$id"

        val gcs = ctx.getResource(uri) as WritableResource

        file.inputStream.use { input ->
            gcs.outputStream.use { output ->
                input.copyTo(output)
            }
        }

        val response = visionTemplate.analyzeImage(file.resource, Feature.Type.LABEL_DETECTION)
        System.err.println(response)

        val labels = response.labelAnnotationsList.take(5)
            .map { it.description }
            .joinToString(",")


        return photoRepository.save(
            Photo(
                id = id,
                uri = "/image/$id",
                label = labels
            )
        )
    }

    @GetMapping("/image/{id}")
    fun get(@PathVariable id: String): ResponseEntity<Resource> {
        val resource = ctx.getResource("$bucket/$id")

        return if (resource.exists()) {
            ResponseEntity.ok(resource)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}
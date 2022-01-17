package com.example.photobackend.controllers

import com.example.photobackend.models.Photo
import com.example.photobackend.repositories.PhotoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    private val photoRepository: PhotoRepository
) {
    @GetMapping("/")
    fun hello() = "hello!"

    @PostMapping("/photo")
    fun create(@RequestBody photo: Photo) {
        photoRepository.save(photo)
    }
}
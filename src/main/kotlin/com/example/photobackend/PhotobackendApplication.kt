package com.example.photobackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhotobackendApplication

fun main(args: Array<String>) {
    runApplication<PhotobackendApplication>(*args)
}
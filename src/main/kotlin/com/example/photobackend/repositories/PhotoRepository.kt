package com.example.photobackend.repositories

import com.example.photobackend.models.Photo
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface PhotoRepository : DatastoreRepository<Photo, String>
package com.herbify.herbifyapp.data.dummies

import com.herbify.herbifyapp.data.remote.response.herbal.HerbalData
import com.herbify.herbifyapp.data.remote.response.herbal.HerbalResponse

object DummyHerbalResponse {
    val herbalResponse = HerbalResponse(
        data = listOf(
            HerbalData(
                image = "https://example.com/herbal1.jpg",
                createdAt = "2022-01-01T10:00:00",
                scientificName = "Scientific Name 1",
                name = "Herbal 1",
                description = "This is herbal 1.",
                id = 1,
                benefit = "Benefit of herbal 1",
                updatedAt = "2022-01-02T12:00:00"
            ),
            HerbalData(
                image = "https://example.com/herbal2.jpg",
                createdAt = "2022-02-01T09:00:00",
                scientificName = "Scientific Name 2",
                name = "Herbal 2",
                description = "This is herbal 2.",
                id = 2,
                benefit = "Benefit of herbal 2",
                updatedAt = "2022-02-02T11:00:00"
            ),
            HerbalData(
                image = "https://example.com/herbal3.jpg",
                createdAt = "2022-03-01T08:00:00",
                scientificName = "Scientific Name 3",
                name = "Herbal 3",
                description = "This is herbal 3.",
                id = 3,
                benefit = "Benefit of herbal 3",
                updatedAt = "2022-03-02T10:00:00"
            )
        ),
        limit = 10,
        page = 1,
        message = "Success"
    )
}
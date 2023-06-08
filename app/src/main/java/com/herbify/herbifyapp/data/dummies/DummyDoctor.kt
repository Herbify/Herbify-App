package com.herbify.herbifyapp.data.dummies

import com.herbify.herbifyapp.model.Doctor

object DummyDoctor {
    val doctor1 = Doctor(
        name = "Dr. John Doe",
        photo = "https://example.com/doctors/1/photo.jpg",
        id = 1,
        email = "johndoe@example.com",
        status = 1
    )

    val doctor2 = Doctor(
        name = "Dr. Jane Smith",
        photo = "https://example.com/doctors/2/photo.jpg",
        id = 2,
        email = "janesmith@example.com",
        status = 1
    )

    val doctor3 = Doctor(
        name = "Dr. David Johnson",
        photo = "https://example.com/doctors/3/photo.jpg",
        id = 3,
        email = "davidjohnson@example.com",
        status = 1
    )

    val doctor4 = Doctor(
        name = "Dr. Sarah Wilson",
        photo = "https://example.com/doctors/4/photo.jpg",
        id = 4,
        email = "sarahwilson@example.com",
        status = 1
    )

    val doctor5 = Doctor(
        name = "Dr. Michael Brown",
        photo = "https://example.com/doctors/5/photo.jpg",
        id = 5,
        email = "michaelbrown@example.com",
        status = 1
    )

    val listDoctor = listOf(doctor1, doctor2, doctor3, doctor4, doctor5)
}
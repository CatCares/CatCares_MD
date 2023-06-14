package com.ardianhilmip.catcares.data.local.Doctor

import com.ardianhilmip.catcares.R

object FakeDoctorDataSource {
    val dummyDoctor = listOf(
        Doctor(
            1,
            "Drh. Ardian Hilmawan",
            R.drawable.doc1,
            "Jl. Raya Cipadung No. 9, Cibiru, Bandung",
            "Spesialis Vaksinasi"
        ),
        Doctor(
            2,
            "Drh. Putri Rahayu",
            R.drawable.doc2,
            "Jl. Ahmad Yani No. 10, Cimahi, Bandung",
            "Spesialis Grooming"
        ),
        Doctor(
            3,
            "Drh. Putri Anggraini",
            R.drawable.doc3,
            "Jl. Siliwangi No. 11, Dago, Bandung",
            "Spesialis Nutrisi"
        ),
        Doctor(
            4,
            "Drh. Rizky Ramadhan",
            R.drawable.doc4,
            "Jl. Pajajaran No. 12, Cihampelas, Bandung",
            "Spesialis Perawatan"
        )
    )
}
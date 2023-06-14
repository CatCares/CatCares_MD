package com.ardianhilmip.catcares.data.local.History

import com.ardianhilmip.catcares.R

object FakeHistoryDataSource {
    val dummyHistory = listOf(
        History(
            1,
            "Drh. Rizky Ramadhan",
            R.drawable.doc4,
            "Spesialis Perawatan",
            "Pukul 09.00 WIB",
            "31 Juni 2023",
        ),
        History(
            2,
            "Drh. Ardian Hilmawan",
            R.drawable.doc1,
            "Spesialis Vaksinasi",
            "Pukul 10.00 WIB",
            "8 Juli 2023",
        ),
        History(
            3,
            "Drh. Putri Rahayu",
            R.drawable.doc2,
            "Spesialis Grooming",
            "Pukul 11.00 WIB",
            "12 Agustus 2023",
        )
    )
}
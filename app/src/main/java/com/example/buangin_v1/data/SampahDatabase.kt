package com.example.buangin_v1.data

import android.provider.BaseColumns

internal class SampahDatabase {
    internal class SampahColum : BaseColumns {
        companion object {
            const val TABLE_NAME = "sampah"
            const val _ID = "_id"
            const val NAMA = "nama"
            const val HARGA = "harga"
            const val JUMLAH = "jumlah"
        }
    }
}
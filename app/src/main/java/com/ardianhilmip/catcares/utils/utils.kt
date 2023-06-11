package com.ardianhilmip.catcares.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.net.Uri
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val MAXIMAL_SIZE = 1000000

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun getAddressName(context: Context, latitude: Double, longitude: Double): String {
    val addressName: String? = null
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null && addresses.size > 0) {
            val address = addresses[0]
            val sb = StringBuilder()
            for (i in 0 until address.maxAddressLineIndex) {
                sb.append(address.getAddressLine(i)).append("\n")
            }
            sb.append(address.locality).append("\n")
            sb.append(address.postalCode).append("\n")
            sb.append(address.countryName)
            return sb.toString()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return addressName.toString()
}

fun createCustomTempFile(context: Context): File {
    val fileName = "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()) + "_"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(fileName, ".jpg", storageDir)
}

fun uriToFile(selectImg: Uri, context: Context): File {
    val contentResolver = context.contentResolver
    val file = createCustomTempFile(context)
    val inputStream = contentResolver.openInputStream(selectImg) as InputStream
    val outputStream = FileOutputStream(file)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) {
        outputStream.write(buf, 0, len)
    }
    outputStream.close()
    inputStream.close()
    return file
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

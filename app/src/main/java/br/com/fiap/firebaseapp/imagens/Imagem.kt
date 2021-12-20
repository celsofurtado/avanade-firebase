package br.com.fiap.firebaseapp.imagens

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun converterBitmapToByteArray(bitmap: Bitmap): ByteArray {

    val bitmapArray = ByteArrayOutputStream()

    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapArray)

    return bitmapArray.toByteArray()

}
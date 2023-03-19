package com.pers.base.lib.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import com.pers.base.lib.AppConfig
import java.io.*
import java.util.*


object FileUtils {

    private val context: Context by lazy {
        AppConfig.application
    }

    fun getFileName(filePath: String?): String? {
        return filePath?.let {
            getFileName(File(it))
        }
    }

    fun getFileName(file: File?): String? {
        return file?.let {
            DocumentFile.fromFile(file).name
        }
    }

    fun getFileName(fileUri: Uri?): String? {
        return fileUri?.let {
            DocumentFile.fromSingleUri(context, fileUri)?.name
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param filePath /storage/emulated/.../7ac389230d004e2dac6a7c6bb484c017.jpg
     * @param pointFlag  是否带点： true：.jpg   false：jpg
     */
    fun getSuffixName(filePath: String?, pointFlag: Boolean = true): String? {
        return filePath?.let {
            val dot = it.lastIndexOf('.')
            if ((dot > -1) && (dot < (it.length - 1))) {
                if (pointFlag) it.substring(dot) else it.substring(dot + 1)
            } else {
                null
            }
        }
    }


    /**
     * 判断文件是否存在
     */
    fun fileIsExists(strFile: String?): Boolean {
        strFile?.let {
            return File(it).exists()
        }
        return false
    }


    /**
     * 创建文件
     */
    fun createFile(sdcardDirName: String?, fileName: String?): File? {
        if (TextUtils.isEmpty(sdcardDirName) || TextUtils.isEmpty(fileName)) {
            return null
        }
        createDir(sdcardDirName)
        val file = File(sdcardDirName + File.separator + fileName)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }

    /**
     * 创建目录
     */
    fun createDir(sdcardDirName: String?): File? {
        return sdcardDirName?.let {
            val fileDir = File(sdcardDirName)
            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }
            fileDir
        }
    }


    fun deleteFiles(path: String?) {
        path?.let {
            deleteFiles(File(it))
        }
    }

    fun deleteFiles(file: File?) {
        if (file == null || !file.exists()) {
            return
        }
        if (file.isFile) {
            file.delete()
            return
        }
        if (file.isDirectory) {
            val fileList = file.listFiles()
            fileList?.forEach {
                deleteFiles(it)
            }
        }
        file.delete()
    }


    fun copyFile(oldFile: File, outputFile: File?): Boolean {
        if (!oldFile.exists() || !oldFile.isFile || !oldFile.canRead()) {
            return false
        }
        try {
            val inputStream = FileInputStream(oldFile)
            val fileOutputStream = FileOutputStream(outputFile)
            return copyFile(inputStream, fileOutputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return false
    }


    fun copyFile(inputStream: InputStream?, fileOutputStream: FileOutputStream?): Boolean {
        if (inputStream == null || fileOutputStream == null) {
            return false
        }
        try {
            val buffer = ByteArray(1024)
            var byteRead: Int
            while (-1 != inputStream.read(buffer).also { byteRead = it }) {
                fileOutputStream.write(buffer, 0, byteRead)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        } finally {
            try {
                inputStream.close()
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return true
    }


//    /**
//     * 根据Uri获取文件绝对路径，兼容Android 10
//     */
//    fun getFileAbsolutePath(fileUri: Uri?): String? {
//        if (fileUri == null) {
//            return null
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && DocumentsContract.isDocumentUri(
//                context, fileUri
//            )
//        ) {
//            if (isExternalStorageDocument(fileUri)) {
//                val docId = DocumentsContract.getDocumentId(fileUri)
//                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                val type = split[0]
//                if ("primary".equals(type, ignoreCase = true)) {
//                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
//                }
//            } else if (isDownloadsDocument(fileUri)) {
//                val id = DocumentsContract.getDocumentId(fileUri)
//                val contentUri = ContentUris.withAppendedId(
//                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
//                )
//                return getDataColumn(
//                    context, contentUri, null, null
//                )
//            } else if (isMediaDocument(fileUri)) {
//                val docId = DocumentsContract.getDocumentId(fileUri)
//                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                val type = split[0]
//                var contentUri: Uri? = null
//                if ("image" == type) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                } else if ("video" == type) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//                } else if ("audio" == type) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//                }
//                val selection = MediaStore.Images.Media._ID + "=?"
//                val selectionArgs = arrayOf(split[1])
//                return getDataColumn(
//                    context, contentUri, selection, selectionArgs
//                )
//            }
//        } // MediaStore (and general)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            return uriToFileApiQ(context, fileUri)
//        } else if ("content".equals(fileUri.scheme, ignoreCase = true)) {
//            // Return the remote address
//            return if (isGooglePhotosUri(fileUri)) {
//                fileUri.lastPathSegment
//            } else getDataColumn(context, fileUri, null, null)
//        } else if ("file".equals(fileUri.scheme, ignoreCase = true)) {
//            return fileUri.path
//        }
//        return null
//    }
//
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is ExternalStorageProvider.
//     */
//    private fun isExternalStorageDocument(uri: Uri): Boolean {
//        return "com.android.externalstorage.documents" == uri.authority
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is DownloadsProvider.
//     */
//    private fun isDownloadsDocument(uri: Uri): Boolean {
//        return "com.android.providers.downloads.documents" == uri.authority
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is MediaProvider.
//     */
//    private fun isMediaDocument(uri: Uri): Boolean {
//        return "com.android.providers.media.documents" == uri.authority
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is Google Photos.
//     */
//    private fun isGooglePhotosUri(uri: Uri): Boolean {
//        return "com.google.android.apps.photos.content" == uri.authority
//    }
//
//
//    private fun getDataColumn(
//        context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?
//    ): String? {
//        if (uri == null) {
//            return null
//        }
//        var cursor: Cursor? = null
//        val column = MediaStore.Images.Media.DATA
//        val projection = arrayOf(column)
//        try {
//            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
//            if (cursor != null && cursor.moveToFirst()) {
//                val index = cursor.getColumnIndexOrThrow(column)
//                return cursor.getString(index)
//            }
//        } finally {
//            cursor?.close()
//        }
//        return null
//    }
//
//    /**
//     * Android 10 以上适配
//     */
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    private fun uriToFileApiQ(context: Context, uri: Uri?): String? {
//        var file: File? = null
//        if (uri == null) {
//            return null
//        }
//        //android10以上转换
//        if (uri.scheme == ContentResolver.SCHEME_FILE) {
//            file = File(Objects.requireNonNull(uri.path))
//        } else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
//            //把文件复制到沙盒目录
//            val contentResolver = context.contentResolver
//            @SuppressLint("Recycle") val cursor = contentResolver.query(uri, null, null, null, null)
//            if (cursor != null && cursor.moveToFirst()) {
//                val displayName =
//                    cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//                try {
//                    val inputStream = contentResolver.openInputStream(uri)
//                    val copyCachePath: String? = SDCardUtils.getSDCardFilesPath()
//                    val fileCache: File? = createFile(
//                        copyCachePath, StringUtils.getStringRandom(4) + displayName
//                    )
//                    val fos = FileOutputStream(fileCache)
//                    val isCopy: Boolean = copyFile(inputStream, fos)
//                    if (isCopy) {
//                        file = fileCache
//                    }
//                    fos.close()
//                    inputStream?.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//        return file?.absolutePath
//    }

}


fun String.fileName(): String {
    return FileUtils.getFileName(this) ?: ""
}

fun File.fileName(): String {
    return FileUtils.getFileName(this) ?: ""
}

fun Uri.fileName(): String {
    return FileUtils.getFileName(this) ?: ""
}

fun String.suffixName(pointFlag: Boolean = true): String {
    return FileUtils.getSuffixName(this, pointFlag) ?: ""
}

fun File.suffixName(): String {
    return FileUtils.getSuffixName(absolutePath) ?: ""
}
package com.main.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Environment
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.CropOptions
import com.jph.takephoto.model.TakePhotoOptions
import java.io.File



/**
 * Author：pengllrn
 * Time: 2019/3/23 20:02
 */
@SuppressLint("StaticFieldLeak")
object TakePhotoUtils {

    /**
     * 获取临时文件的Uri，并且以当前时间的毫秒来作为文件名称
     */
    fun createTempFile(): Uri {
        val file: File = File(
            Environment.getExternalStorageDirectory(),
            "/temp/" + System.currentTimeMillis() + ".jpg"
        )
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        return Uri.fromFile(file)
    }

    /**
     * 裁图配置：
     * 使用TakePhoto自带裁图工具
     * width：裁剪后的宽度
     * height:裁剪后的高度
     */
    fun getCropOptions(width: Int, height: Int): CropOptions {
        val builder: CropOptions.Builder = CropOptions.Builder()
        builder.setOutputX(width).setOutputY(height)
        builder.setWithOwnCrop(true)//使用TakePhoto自带裁图工具（true）,使用第三方截图工具(false)
        return builder.create()
    }

    /**
     * 压缩配置：
     * 默认使用自带的压缩算法
     * 2.鲁班压缩
     */
    fun getConfigOfCompress(maxSize:Int,width:Int,height:Int):CompressConfig{
        val config = CompressConfig.Builder().setMaxSize(maxSize)
            .setMaxPixel(if(width >= height) width else height)
            .enableReserveRaw(false)//不保留原图
            .create()
        return config
    }

    /**
     * 获取拍照的配置
     */
    fun getConfigTakePhoto():TakePhotoOptions{
        val builder = TakePhotoOptions.Builder()
        builder.setWithOwnGallery(true) // 使用自定义相册(true)或系统相册(false)
        builder.setCorrectImage(true)  //纠正拍照后的角度
        return builder.create()
    }
}
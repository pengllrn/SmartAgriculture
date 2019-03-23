package com.main.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.base.ui.fragment.BaseFragment
import com.base.utils.DateUtils
import com.base.utils.GlideUtils
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.InvokeParam
import com.jph.takephoto.model.TContextWrap
import com.jph.takephoto.model.TResult
import com.jph.takephoto.permission.InvokeListener
import com.jph.takephoto.permission.PermissionManager
import com.jph.takephoto.permission.PermissionManager.TPermissionType
import kotlinx.android.synthetic.main.fragment_discovery.*
import java.io.File



/**
 * Author：pengllrn
 * Time: 2019/3/23 9:14
 */
class DiscoveryFragment : BaseFragment(), TakePhoto.TakeResultListener,
    View.OnClickListener, InvokeListener {

    //临时文件,拍照的图片临时保存的位置
    private lateinit var mTempFile: File

    private lateinit var invokeParam: InvokeParam

    //本地图片地址
    private var mLocalFilePath: String? = null

    private lateinit var mTakePhoto: TakePhoto

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(com.main.R.layout.fragment_discovery, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhotoBt.setOnClickListener(this)
        mChoosePhotoTv.setOnClickListener(this)
    }

    override fun takeSuccess(result: TResult?) {
        Toast.makeText(context, "选择成功", Toast.LENGTH_SHORT).show()
        Log.d("TakePhoto", result?.image?.originalPath)//源地址
        Log.d("TakePhoto", result?.image?.compressPath)//压缩后的地址
        //上传头像
        mLocalFilePath = result?.image?.compressPath
        GlideUtils.loadImage(context!!,mLocalFilePath!!,mImageIv)
    }

    override fun takeCancel() {
        Toast.makeText(context, "取消选择", Toast.LENGTH_SHORT).show()
    }

    override fun takeFail(result: TResult?, msg: String?) {
        Toast.makeText(context, "选择失败", Toast.LENGTH_SHORT).show()
        Log.e("TakePhoto", msg)
    }

    override fun invoke(invokeParam: InvokeParam): TPermissionType {
        val type: TPermissionType = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod())
        if (TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam
        }
        return type
    }

    override fun onClick(view: View) {
        when (view.id) {
            com.main.R.id.mTakePhotoBt -> {
                createTempFile()
                mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))//没压缩
            }

            com.main.R.id.mChoosePhotoTv -> {
                mTakePhoto.onEnableCompress(
                    CompressConfig.ofDefaultConfig(),//这样每次拍照或者选择照片都会压缩
                    false
                )
                mTakePhoto.onPickFromGallery()
            }
        }
    }

    fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"  //文件名
        //判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //生成了临时文件对象
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }

        //filesDir是系统获取的
        this.mTempFile = File(activity?.filesDir, tempFileName)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //以下代码为处理Android6.0、7.0动态权限所需
        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.handlePermissionsResult(activity, type, invokeParam, this)
    }



}
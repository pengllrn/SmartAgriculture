package com.main.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import android.widget.Toast
import com.base.ui.fragment.BaseFragment
import com.base.ui.view.CommonPopupWindow
import com.base.utils.GlideUtils
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.model.InvokeParam
import com.jph.takephoto.model.TContextWrap
import com.jph.takephoto.model.TResult
import com.jph.takephoto.permission.InvokeListener
import com.jph.takephoto.permission.PermissionManager
import com.jph.takephoto.permission.PermissionManager.TPermissionType
import com.jph.takephoto.permission.TakePhotoInvocationHandler
import com.main.R
import com.main.utils.TakePhotoUtils
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

    private lateinit var window : CommonPopupWindow

    private lateinit var parent:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(com.main.R.layout.fragment_discovery, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //以下两行代码激活mTakePhoto
        mTakePhoto = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(this, this)) as TakePhoto
//        mTakePhoto.onCreate(savedInstanceState)
        mTakePhotoBt.setOnClickListener(this)
        mChoosePhotoTv.setOnClickListener(this)
        parent = view
    }

    /**
     * 这段代码必须加！
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    override fun takeSuccess(result: TResult?) {
        Toast.makeText(context, "选择成功", Toast.LENGTH_SHORT).show()
        Log.d("TakePhoto", result?.image?.originalPath)//源地址
        Log.d("TakePhoto", result?.image?.compressPath)//压缩后的地址
        //上传头像
        val file2 = File(result?.image?.originalPath)
        mLocalFilePath = result?.image?.compressPath
        val file = File(mLocalFilePath)
        Log.d("TakePhoto", "原图片的大小：${file2.length()/1024}KB")
        Log.d("TakePhoto", "压缩后的大小：${file.length()/1024}KB")
        GlideUtils.loadImage(context!!,mLocalFilePath!!,mImageIv)
        initPopupWindow()
        showPopupWindow(parent)
    }

    fun initPopupWindow(){
        val metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        val screenHeight = metrics.heightPixels
        window = object : CommonPopupWindow(context!!, R.layout.popupwindow_layout,ViewGroup.LayoutParams.MATCH_PARENT,
            (screenHeight * 0.7).toInt()){
            override fun initView() {
                val view = contentView
                //显示布局

            }

            override fun initEvent() {
            }

            override fun initWindow(){
                super.initWindow()
                mInstance.setOnDismissListener {
                    val lp = activity!!.window.attributes
                    lp.alpha = 1.0f
                    activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    activity!!.window.attributes = lp
                }
            }

        }
    }

    fun showPopupWindow(view:View){
        val win : PopupWindow =window.mInstance
        win.animationStyle = R.style.animTranslate
        window.showAtLocation(view,Gravity.BOTTOM,0,0)
        val lp = activity!!.window.attributes
        lp.alpha = 0.4f
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        activity!!.window.attributes = lp


    }

    override fun takeCancel() {
        Toast.makeText(context, "用户取消选择", Toast.LENGTH_SHORT).show()
    }

    override fun takeFail(result: TResult?, msg: String?) {
        Toast.makeText(context, "选择失败", Toast.LENGTH_SHORT).show()
        Log.e("TakePhoto", msg)
    }

    /**
     * 自动检查需要的权限：主要是相机以及存储权限 1
     * 自动请求相应的权限： 2
     */
    override fun invoke(invokeParam: InvokeParam): TPermissionType {
        val type: TPermissionType = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod())
        if (TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam
        }
        return type
    }

    /**
     * 需要初始化 invokeParam
     * 处理权限请求结果 3
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //以下代码为处理Android6.0、7.0动态权限所需
        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.handlePermissionsResult(activity, type, invokeParam, this)
    }



    override fun onClick(view: View) {
        mTakePhoto.onEnableCompress(//这样每次拍照或选择照片都会压缩
            TakePhotoUtils.getConfigOfCompress(400000,600,600),
            false//不显示压缩进度
        )
        when (view.id) {
            com.main.R.id.mTakePhotoBt -> {
                mTakePhoto.onPickFromCaptureWithCrop(TakePhotoUtils.createTempFile(),TakePhotoUtils.getCropOptions(400,600))
            }

            com.main.R.id.mChoosePhotoTv -> {
                mTakePhoto.setTakePhotoOptions(TakePhotoUtils.getConfigTakePhoto())
                mTakePhoto.onPickFromGalleryWithCrop(TakePhotoUtils.createTempFile(),TakePhotoUtils.getCropOptions(400,600))
            }
        }
    }

}
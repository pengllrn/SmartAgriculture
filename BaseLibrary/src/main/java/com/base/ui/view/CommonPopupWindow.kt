package com.base.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow

/**
 * Author：pengllrn
 * Time: 2019/3/25 11:58
 */
abstract class CommonPopupWindow(var context: Context, var layoutRes: Int, val w: Int, var h: Int) {

    val contentView: View
    val mInstance: PopupWindow

    init {
        contentView = LayoutInflater.from(context).inflate(layoutRes, null, false)
        mInstance = PopupWindow(contentView, w, h, true)
        initView()
        initEvent()
        initWindow()
    }

    abstract fun initView()
    abstract fun initEvent()

    open fun initWindow() {
        mInstance.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mInstance.isOutsideTouchable = true
        mInstance.isTouchable = true
    }

    /**
     * 设置popupWindow在父View上的显示位置
     * @param parent:传入父类View
     * @param gravity:popupWindow的出现方向
     * @param x y :popupWindow的出现位置
     */
    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        mInstance.showAtLocation(parent, gravity, x, y)
    }

    fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) = mInstance.showAsDropDown(anchor, xoff, yoff)


}
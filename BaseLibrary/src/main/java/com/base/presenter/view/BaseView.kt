package com.kotlin.base.presenter.view

/**
 * Author：Pengllrn
 * Date: 2019/2/23
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text:String)
}
package com.base.rx

import com.kotlin.base.presenter.view.BaseView
import rx.Subscriber

/**
 * Author：Pengllrn
 * Date: 2019/2/23
 * Contact 897198177@qq.com
 * https://github.com/pengllrn
 */
open class BaseSubscriber<T>(val baseView:BaseView):Subscriber<T>() {
    override fun onNext(t: T) {
        //在实际用的时候写
    }

    override fun onCompleted() {
        baseView.hideLoading()
    }

    override fun onError(e: Throwable?) {
        baseView.hideLoading()
        if (e is BaseException){//网络异常
            baseView.onError(e.msg)
        }
    }
}
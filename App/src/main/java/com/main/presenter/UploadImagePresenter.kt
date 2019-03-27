package com.main.presenter

import com.base.ext.excute
import com.base.rx.BaseSubscriber
import com.kotlin.base.presenter.BasePresenter
import com.main.presenter.view.UploadImageView
import com.main.service.impl.IdentifyObjectServiceImpl

class UploadImagePresenter:BasePresenter<UploadImageView>() {

    fun uploadImage(){
        var identify = IdentifyObjectServiceImpl()
        identify.uploadImage()
            .excute(object : BaseSubscriber<String>(mView){
                override fun onNext(t: String) {
                    mView.onUploadImage(t)
                }
            })
    }
}
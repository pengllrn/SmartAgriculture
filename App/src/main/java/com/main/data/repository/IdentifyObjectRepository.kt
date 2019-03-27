package com.main.data.repository

import com.base.data.net.RetrofitFactory
import com.base.data.protocal.BaseResponse
import com.main.data.api.IdentifyObjectApi
import rx.Observable

class IdentifyObjectRepository {
    fun uploadImage(): Observable<BaseResponse<String>> {
        return RetrofitFactory.instance.create(IdentifyObjectApi::class.java)
            .uploadImage()
    }
}
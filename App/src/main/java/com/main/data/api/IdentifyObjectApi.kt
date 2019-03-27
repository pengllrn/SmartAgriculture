package com.main.data.api

import com.base.data.protocal.BaseResponse
import retrofit2.http.POST
import rx.Observable

interface IdentifyObjectApi {

    @POST("commom/upload")
    fun uploadImage(): Observable<BaseResponse<String>>
}
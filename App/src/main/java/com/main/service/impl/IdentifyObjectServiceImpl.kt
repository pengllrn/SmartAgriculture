package com.main.service.impl

import com.base.ext.excute
import com.base.rx.BaseFunc
import com.main.data.repository.IdentifyObjectRepository
import com.main.service.IdentifyObjectService
import rx.Observable

class IdentifyObjectServiceImpl:IdentifyObjectService {
    override fun uploadImage(): Observable<String> {
        val repository = IdentifyObjectRepository()
        return repository.uploadImage()
            .flatMap ( BaseFunc() )
    }

}
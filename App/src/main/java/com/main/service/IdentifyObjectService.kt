package com.main.service

import rx.Observable


interface IdentifyObjectService {

    fun uploadImage(): Observable<String>
}
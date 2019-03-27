package com.base.data.protocal

class BaseResponse<out T>(val status: Int, val message: String, val data: T){

}
package com.base.rx

/**
 * Author：Pengllrn
 * Date: 2019/2/27
 * 自定义网络异常,主要用于处理网络返回的异常，即status != ResultCode.SUCCESS
 * @constructor
 */
class BaseException(val status:Int,val msg:String) :Throwable(){
}
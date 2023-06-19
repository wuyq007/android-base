package com.pers.libs.net

class ResponseBean<T> : java.io.Serializable {

    /**
     *
     */
    var errorCode: Int? = null

    /**
     *
     */
    var errorMsg: String? = null

    /**
     * 业务参数
     */
    var data: T? = null

    var succeed: Boolean = errorCode == 0

}
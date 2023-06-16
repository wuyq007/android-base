package com.pers.libs.net

class ResponseBean<T> : java.io.Serializable {

    /**
     *
     */
    var code: Int? = 0

    /**
     *
     */
    var message: String? = null

    /**
     * 业务参数
     */
    var data: T? = null

}
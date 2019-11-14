package io.github.jixiaoyong.wanandroid.utils

import org.junit.Test

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-14
 * description: todo
 */
class UtilsTest {

    @Test
    fun isNameCorrect() {
        val userName = "jixiaoyong1995@gmail.com"
        assert(Utils.isNameCorrect(userName))
    }

    /**
     * 密码6~50位且为数字、字母、-、_
     */
    @Test
    fun isPasswordCorrect() {
        val errorPwd1 = "d"
        val errorPwd2 = "123456有其他字符"
        val correctPwd = "123456"

        assert(!Utils.isPasswordCorrect(errorPwd1))
        assert(!Utils.isPasswordCorrect(errorPwd2))
        assert(Utils.isPasswordCorrect(correctPwd))
    }
}
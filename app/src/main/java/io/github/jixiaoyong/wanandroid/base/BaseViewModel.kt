package io.github.jixiaoyong.wanandroid.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
open class BaseViewModel : ViewModel(), CoroutineScope by MainScope() {

    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}
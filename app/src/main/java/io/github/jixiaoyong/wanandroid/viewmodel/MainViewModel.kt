package io.github.jixiaoyong.wanandroid.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import io.github.jixiaoyong.wanandroid.base.BaseViewModel

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-12
 * description: todo
 */
class MainViewModel : BaseViewModel() {

    val live = MutableLiveData<String>()
    val d = map(live) {
        "this is $it"
    }
    val e = switchMap(live) {
        return@switchMap when (it) {
            "a" -> MutableLiveData<String>("a")
            "b" -> MutableLiveData("b")
            else -> MutableLiveData("else")
        }
    }

    init {
        val mediatorLiveData = MediatorLiveData<String>()
        mediatorLiveData.addSource(d) {
            update(it, e.value)
        }
        mediatorLiveData.addSource(e) {
            update(d.value, it)
        }
    }

    fun update(a: String? = "", b: String? = "") {

    }
}
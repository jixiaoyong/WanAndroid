package io.github.jixiaoyong.wanandroid.utils

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import io.github.jixiaoyong.wanandroid.api.bean.DataSystemParam
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2019-11-24
 * description: todo
 */

class ParamterizedTypeImpl(val clazz: Class<*>) : ParameterizedType {
    override fun getRawType() = List::class.java

    override fun getOwnerType() = null

    override fun getActualTypeArguments(): Array<Type> = arrayOf(clazz)
}

inline fun <reified T> String.jsonToListOf(): List<T>? {
    val type = ParamterizedTypeImpl(T::class.java)
    return Gson().fromJson<List<T>>(this, type)
}

fun String.jsonToListOfDataSys(): List<DataSystemParam<DataSystemParam<Any>>>? {
    val data = this.jsonToListOf<DataSystemParam<LinkedTreeMap<String, Any>>?>()
    return data?.filterNotNull()?.map { main ->
        val children = Gson().toJson(main.children).toString().jsonToListOf<DataSystemParam<Any>>()
            ?: arrayListOf()
        val item = DataSystemParam(
            children,
            main.courseId, main.id, main.name, main.order, main.parentChapterId,
            main.userControlSetTop, main.visible
        )
        item
    }
}

fun main() {
    val preList = arrayListOf(
        DataSystemParam(
            children = arrayListOf(DataSystemParam(name = "sub1"), DataSystemParam(name = "sub2")),
            name = "main1"
        ),
        DataSystemParam(
            children = arrayListOf(DataSystemParam(name = "sub1"), DataSystemParam<Any>(name = "sub2")),
            name = "main2"
        )
    )

    val jsonStr = Gson().toJson(preList)

    val obj = jsonStr.jsonToListOfDataSys()

    obj?.forEach {
        println("tab:${it.name} ${it.children.getOrNull(0)?.name}")
    }
}

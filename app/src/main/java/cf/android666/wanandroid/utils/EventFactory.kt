package cf.android666.wanandroid.utils

import cf.android666.wanandroid.`interface`.EventInterface


/**
 * Created by jixiaoyong on 2018/3/7.
 * email:jixiaoyong1995@gmail.com
 */
data class EventFactory(private val clazz: Any) :EventInterface{

    private var event:EventInterface? = null

    fun build() :EventInterface{

        event =  when (clazz) {

            NightMode::class.java -> NightMode()

            Login::class.java -> Login()

            CollectState::class.java -> CollectState()

            else -> {
                throw CloneNotSupportedException("$clazz.class not support")
            }
        }

        return this

    }

    override fun setValue(value: Any) :EventInterface{

        event?.setValue(value)
        return if (event!=null) event!!
        else throw KotlinNullPointerException("must call build() before invoke setValue()")
    }


    inner class NightMode : EventInterface {

        override fun setValue(value: Any): EventInterface {
            isNightMode = value as Boolean
            return this
        }

        var isNightMode: Boolean = false
    }

    inner class Login : EventInterface {

        override fun setValue(value: Any): EventInterface {
            isLogin = value as Boolean
            return this
        }

        var isLogin: Boolean = false
    }

    inner class CollectState : EventInterface {

        override fun setValue(value: Any): EventInterface {
            changeNum = value as Int
            return this
        }

        var changeNum: Int = 0
    }


}

//fun main(args: Array<String>) {
//
//    var type = EventFactory(EventFactory.NightMode::class.java).build().setValue(false)
//
////    print("type is NightMode ${type is EventFactory.NightMode}")
//
//    print("type is NightMode ${(type as EventFactory.NightMode).isNightMode}")
//
//}
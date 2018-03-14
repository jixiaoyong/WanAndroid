package cf.android666.wanandroid.utils

import cf.android666.wanandroid.`interface`.EventInterface


/**
 * Created by jixiaoyong on 2018/3/7.
 * email:jixiaoyong1995@gmail.com
 */
class EventFactory :EventInterface{

    private var event:EventInterface? = null

    fun <T> build(clazz: Class<T>): EventInterface {

        event =  when (clazz) {

            NightMode::class.java -> NightMode()

            Login::class.java -> Login()

            CollectState::class.java -> CollectState()

            else -> {
                throw CloneNotSupportedException("$clazz.class not support")
            }
        }

        return event as EventInterface
    }

    override fun setValue(value: Any) {
        event?.setValue(value)
    }


    inner class NightMode : EventInterface {

        override fun setValue(value: Any) {
            isNightMode = value as Boolean
        }

        var isNightMode: Boolean = false
    }

    inner class Login : EventInterface {

        override fun setValue(value: Any) {
            isLogin = value as Boolean
        }

        var isLogin: Boolean = false
    }

    inner class CollectState : EventInterface {

        override fun setValue(value: Any) {
            changeNum = value as Int
        }

        var changeNum: Int = 0
    }


}
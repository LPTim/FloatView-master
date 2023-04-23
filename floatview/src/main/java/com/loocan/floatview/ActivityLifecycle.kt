package com.loocan.floatview

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityLifecycle(private val floatWindow: IFloatWindow) : Application.ActivityLifecycleCallbacks {


    override fun onActivityResumed(activity: Activity) {
        floatWindow.show(activity)
    }

    override fun onActivityPaused(activity: Activity) {
//        floatWindow.hide()
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
    }


    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}
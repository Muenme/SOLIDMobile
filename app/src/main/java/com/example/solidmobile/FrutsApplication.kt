package com.example.solidmobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp // без этого Hilt не запустится вообще
class FrutsApplication : Application()
package com.android.szparag.flighter.common.preferences

import com.android.szparag.myextensionsbase.invalidStringValue
import io.realm.RealmObject

open class UserSettingsModel(
    var currency: String = invalidStringValue()
) : RealmObject()
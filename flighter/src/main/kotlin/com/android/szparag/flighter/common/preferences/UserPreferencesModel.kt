package com.android.szparag.flighter.common.preferences

import com.android.szparag.myextensionsbase.UnixTimestamp
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

//vars instead of vals because Realm can't handle that stuff
open class UserPreferencesModel(
    @PrimaryKey var key: Int = 0,
    var userLoggedIn: Boolean? = null,
    var selectedAirportName: String? = null,
    var selectedAirportIataCode: String? = null,
    var departureDate: UnixTimestamp? = null,
    var arrivalDate: UnixTimestamp? = null
) : RealmObject()
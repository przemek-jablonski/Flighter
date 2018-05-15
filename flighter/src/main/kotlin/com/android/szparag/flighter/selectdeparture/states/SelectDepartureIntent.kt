package com.android.szparag.flighter.selectdeparture.states

sealed class SelectDepartureIntent {

  data class TextSearchIntent(val searchInput: String): SelectDepartureIntent()

  class GpsSearchIntent: SelectDepartureIntent()

}
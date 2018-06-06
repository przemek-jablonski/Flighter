package com.android.szparag.flighter.selectdeparture

import android.support.v7.util.DiffUtil
import com.android.szparag.flighter.selectdeparture.models.local.AirportModel
import timber.log.Timber

class AirportsDiffUtilCallback(
    private val oldAirports: List<AirportModel>,
    private val newAirports: List<AirportModel>
) : DiffUtil.Callback() {

  override fun getOldListSize() = oldAirports.size

  override fun getNewListSize() = newAirports.size

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
      oldAirports[oldItemPosition].airportIataCode == newAirports[newItemPosition].airportIataCode

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
      oldAirports[oldItemPosition] == newAirports[newItemPosition]

}
package com.android.szparag.flighter.selectdeparture

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.szparag.flighter.R
import com.android.szparag.flighter.common.GenericRecyclerViewAdapter
import com.android.szparag.flighter.selectdeparture.AirportsAdapter.AirportsViewHolder
import com.android.szparag.flighter.selectdeparture.models.local.AirportModel
import com.android.szparag.myextensionsandroid.inflate
import com.android.szparag.myextensionsbase.emptyMutableList
import java.lang.Character.toChars

class AirportsAdapter(
    itemsList: MutableList<AirportModel> = emptyMutableList()
) : GenericRecyclerViewAdapter<AirportModel, AirportsViewHolder>(itemsList) {

  class AirportsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val airportName: TextView = itemView.findViewById(R.id.view_select_departure_recycler_row_airport_name)
    val airportAddress: TextView = itemView.findViewById(R.id.view_select_departure_recycler_row_airport_address)
    val airportCode: TextView = itemView.findViewById(R.id.view_select_departure_recycler_row_airport_code)
  }

  override fun update(updatedList: List<AirportModel>) =
      update(updatedList, AirportsDiffUtilCallback(itemsList, updatedList))

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      AirportsViewHolder(parent.context.inflate(R.layout.view_select_departure_recycler_row_airport, parent, false))


  @SuppressLint("SetTextI18n")
  override fun onBind(holder: AirportsViewHolder, item: AirportModel) =
      with(holder) {
        airportName.text = item.airportName
        airportCode.text = item.airportIataCode
        airportAddress.text = "${generateCountryFlagEmoji(item.countryCode)} ${item.address}"
      }


  private fun generateCountryFlagEmoji(countryCode: String): String {
    val flagOffset = 0x1F1E6
    val asciiOffset = 0x41
    return String(toChars(Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset)) +
        String(toChars(Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset))
  }

}
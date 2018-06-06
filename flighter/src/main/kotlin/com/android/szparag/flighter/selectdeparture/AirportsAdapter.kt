package com.android.szparag.flighter.selectdeparture

import android.annotation.SuppressLint
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.szparag.flighter.R
import com.android.szparag.flighter.selectdeparture.AirportsAdapter.AirportsViewHolder
import com.android.szparag.flighter.selectdeparture.models.AirportModel
import com.android.szparag.myextensionsandroid.inflate
import com.android.szparag.myextensionsbase.emptyMutableList
import java.lang.Character.toChars

class AirportsAdapter(private var airports: MutableList<AirportModel> = emptyMutableList()) : RecyclerView.Adapter<AirportsViewHolder>() {

  class AirportsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val airportName: TextView = itemView.findViewById(R.id.view_select_departure_recycler_row_airport_name)
    val airportAddress: TextView = itemView.findViewById(R.id.view_select_departure_recycler_row_airport_address)
    val airportCode: TextView = itemView.findViewById(R.id.view_select_departure_recycler_row_airport_code)
  }

  fun update(updatedAirports: List<AirportModel>) {
    DiffUtil.calculateDiff(AirportsDiffUtilCallback(airports, updatedAirports)).also { diffResult ->
      airports.clear()
      airports.addAll(updatedAirports)
      diffResult.dispatchUpdatesTo(this)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      AirportsViewHolder(parent.context.inflate(R.layout.view_select_departure_recycler_row_airport, parent, false))

  override fun onBindViewHolder(holder: AirportsViewHolder, position: Int) = onBindViewHolder(holder, airports[position])

  @SuppressLint("SetTextI18n")
  private fun onBindViewHolder(holder: AirportsViewHolder, airport: AirportModel) =
      with(holder) {
        airportName.text = airport.airportName
        airportCode.text = airport.airportIataCode
        airportAddress.text = "${generateCountryFlagEmoji(airport.countryCode)} ${airport.address}"
      }

  override fun getItemCount() = airports.size

  private fun generateCountryFlagEmoji(countryCode: String): String {
    val flagOffset = 0x1F1E6
    val asciiOffset = 0x41
    return String(toChars(Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset)) +
        String(toChars(Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset))
  }

}
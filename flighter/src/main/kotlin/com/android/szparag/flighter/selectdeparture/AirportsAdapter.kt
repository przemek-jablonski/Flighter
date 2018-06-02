package com.android.szparag.flighter.selectdeparture

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

  private fun onBindViewHolder(holder: AirportsViewHolder, airport: AirportModel) =
      with(holder) {
        airportName.text = airport.airportName
        airportCode.text = airport.airportIataCode
        airportAddress.text = airport.address
      }

  override fun getItemCount() = airports.size

}
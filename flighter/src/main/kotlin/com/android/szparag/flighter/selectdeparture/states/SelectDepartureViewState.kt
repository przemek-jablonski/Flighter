package com.android.szparag.flighter.selectdeparture.states

import com.android.szparag.flighter.selectdeparture.models.local.AirportModel

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
sealed class SelectDepartureViewState {

  class SearchNotStartedViewState : SelectDepartureViewState()

  data class QueryingWithTextViewState(val inputText: String): SelectDepartureViewState()

  data class FetchingResultWithTextViewState(val inputText: String): SelectDepartureViewState()

  class FetchingResultWithGpsViewState: SelectDepartureViewState()

  class EmptySearchResult: SelectDepartureViewState()

  data class SearchResult(val results: List<AirportModel>): SelectDepartureViewState()

}
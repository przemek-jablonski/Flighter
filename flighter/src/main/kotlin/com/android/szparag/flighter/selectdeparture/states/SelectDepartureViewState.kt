package com.android.szparag.flighter.selectdeparture.states

import com.android.szparag.flighter.selectdeparture.models.AirportModel

/**
 * Created by Przemyslaw Jablonski (github.com/sharaquss, pszemek.me) on 01/04/2018.
 */
sealed class SelectDepartureViewState {

  class SearchNotStartedViewState : SelectDepartureViewState()

  class QueryingWithTextViewState(val inputText: String): SelectDepartureViewState()

  class FetchingResultWithTextViewState(val inputText: String): SelectDepartureViewState()

  class FetchingResultWithGpsViewState: SelectDepartureViewState()

  class EmptySearchResult: SelectDepartureViewState()

  class SearchResult(val results: List<AirportModel>): SelectDepartureViewState()

}
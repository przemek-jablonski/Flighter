package com.android.szparag.flighter.selectdeparture.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.android.szparag.flighter.R
import com.android.szparag.kotterknife.bindView
//import kotlinx.android.synthetic.main.view_select_departure_input.view.view_select_departure_input_edit_text
//import kotlinx.android.synthetic.main.view_select_departure_input.view.view_select_departure_input_gps_button

typealias TextInputChangedListener = (String) -> (Unit)
typealias GpsButtonClickedListener = () -> (Unit)

class FlighterSelectDepartureInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

////  private val editText: EditText by bindView(R.id.view_select_departure_input_edit_text)
////  private val gpsButton: ImageButton by bindView(R.id.view_select_departure_input_gps_button)
//
//  var onTextInputChangedListener: TextInputChangedListener? = null
//  var onGpsButtonClickedListener: GpsButtonClickedListener? = null
//  private lateinit var textChangedWatcher: TextWatcher
//
//  init {
//    LayoutInflater.from(context).inflate(R.layout.view_select_departure_input, this, true)
//  }
//
//  override fun onAttachedToWindow() {
//    super.onAttachedToWindow()
//
//  }
//
//  override fun onDetachedFromWindow() {
//    super.onDetachedFromWindow()
//    view_select_departure_input_gps_button.setOnClickListener(null)
//    view_select_departure_input_edit_text.removeTextChangedListener(textChangedWatcher)
//  }
//
//  override fun onFinishInflate() {
//    super.onFinishInflate()
//
//    textChangedWatcher = object: TextWatcher {
//      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
//          onTextInputChangedListener?.invoke(s.toString()) ?: Unit
//
//      override fun afterTextChanged(s: Editable?) = Unit
//      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
//    }
//    view_select_departure_input_edit_text.addTextChangedListener(textChangedWatcher)
//    view_select_departure_input_gps_button.setOnClickListener { onGpsButtonClickedListener?.invoke() }
//  }
}
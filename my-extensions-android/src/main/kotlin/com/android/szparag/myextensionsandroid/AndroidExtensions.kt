package com.android.szparag.myextensionsandroid

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.view.View
import android.widget.Toast

fun Context.createRegisteredBroadcastReceiver(vararg intentFilterActions: String, callback: (Intent) -> (Unit)): BroadcastReceiver {
  val broadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      intent?.takeIf { intentFilterActions.contains(it.action) }?.let(callback::invoke)
    }
  }
  this.registerReceiver(broadcastReceiver, IntentFilter().apply { intentFilterActions.forEach(this::addAction) })
  return broadcastReceiver
}

fun BroadcastReceiver.unregisterReceiverFromContext(context: Context) = context.unregisterReceiver(this)

fun Context.getStickyIntentFromSystem(intentFilterAction: String): Intent = registerReceiver(null, IntentFilter(intentFilterAction))

fun Intent.toPendingIntent(context: Context, requestCode: Int = 0, flags: Int = 0): PendingIntent = PendingIntent.getActivity(context,
    requestCode, this, flags)

fun View.snackbar(content: CharSequence, longDuration: Boolean = true) =
  Snackbar.make(this, content, if (longDuration) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT)

fun Context.toast(content: CharSequence, longDuration: Boolean = true) =
  Toast.makeText(this, content, if (longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)

fun View.toast(content: CharSequence, longDuration: Boolean = true) =
  this.context.toast(content, longDuration)
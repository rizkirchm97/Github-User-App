package com.rizki.githubuserapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.rizki.githubuserapp.R
import com.rizki.githubuserapp.broadcast.AlarmBroadcastReceiver

class PreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var ALARM: String

    private lateinit var isSwitchAlarm: SwitchPreference
    private lateinit var alarmBroadcastReceiver: AlarmBroadcastReceiver

    companion object {
        private const val DEFAULT_VALUE = "Get Notify"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        initAlarm()
        setSummaries()
        alarmBroadcastReceiver = AlarmBroadcastReceiver()
    }


    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isSwitchAlarm.isChecked = sh.getBoolean(ALARM, false)
    }

    private fun initAlarm() {
        ALARM = resources.getString(R.string.key_alarm_preference)

        isSwitchAlarm = findPreference<SwitchPreference>(ALARM) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen
            .sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen
            .sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == ALARM) {
            isSwitchAlarm.isChecked = sharedPreferences?.getBoolean(ALARM, false) == true
            isSwitchAlarm.isChecked.let {
                if (it) {
                    alarmBroadcastReceiver.setRepeatingAlarm(
                        context,
                        AlarmBroadcastReceiver.TYPE_REPEATING,
                        getString(R.string.alarm_message)
                    )
                } else {
                    alarmBroadcastReceiver.cancelAlarm(
                            context,
                        AlarmBroadcastReceiver.TYPE_REPEATING
                        )
                }
            }
        }
    }
}
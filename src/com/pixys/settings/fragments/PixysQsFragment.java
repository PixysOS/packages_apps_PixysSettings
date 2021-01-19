/*
 * Copyright (C) 2020 Pixys OS
 * used for PixysOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pixys.settings.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable;
import com.android.settingslib.search.SearchIndexable;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import java.util.ArrayList;
import java.util.List;

import com.pixys.settings.preferences.SystemSettingSwitchPreference;

public class PixysQsFragment extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String SHOW_QS_MEDIA_PLAYER ="quick_settings_media_player";

    private SwitchPreference mShowQSMediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pixys_settings_quicksettings);
        ContentResolver resolver = getActivity().getContentResolver();
	mShowQSMediaPlayer = (SwitchPreference) findPreference(SHOW_QS_MEDIA_PLAYER);
        mShowQSMediaPlayer.setChecked((Settings.Global.getInt(resolver,
  	      Settings.Global.SHOW_MEDIA_ON_QUICK_SETTINGS, 1) == 1));
        mShowQSMediaPlayer.setOnPreferenceChangeListener(this);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.PIXYS;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
	if (preference == mShowQSMediaPlayer){
	        final boolean isEnabled = (Boolean) newValue;
	        Settings.Global.putInt(getActivity().getContentResolver(),
			Settings.Global.SHOW_MEDIA_ON_QUICK_SETTINGS, isEnabled ? 1 : 0);
	        return true;
	}
        return true;
    }

}

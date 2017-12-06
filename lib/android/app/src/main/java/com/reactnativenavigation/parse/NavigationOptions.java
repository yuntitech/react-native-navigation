package com.reactnativenavigation.parse;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NavigationOptions implements DEFAULT_VALUES {

	public enum BooleanOptions {
		True,
		False,
		NoValue;

		static BooleanOptions parse(String value) {
			if (value != null && !value.equals("")) {
				return Boolean.valueOf(value) ? True : False;
			}
			return NoValue;
		}
	}

	@NonNull
	public static NavigationOptions parse(JSONObject json) {
		NavigationOptions result = new NavigationOptions();
		if (json == null) return result;

		result.topBarOptions = TopBarOptions.parse(json.optJSONObject("topBar"));
		result.bottomTabsOptions = BottomTabsOptions.parse(json.optJSONObject("tabBar"));

		result.leftButtons = Button.parseJsonArray(json.optJSONArray("leftButtons"));
		result.rightButtons =  Button.parseJsonArray(json.optJSONArray("rightButtons"));

		return result;
	}

	public TopBarOptions topBarOptions = new TopBarOptions();
	public BottomTabsOptions bottomTabsOptions = new BottomTabsOptions();
	public ArrayList<Button> leftButtons;
	public ArrayList<Button> rightButtons;

	public void mergeWith(final NavigationOptions other) {
		topBarOptions.mergeWith(other.topBarOptions);
		bottomTabsOptions.mergeWith(other.bottomTabsOptions);

		if(other.leftButtons != null) {
			leftButtons = other.leftButtons;
		}

		if(other.rightButtons != null) {
			rightButtons = other.rightButtons;
		}
	}
}

package com.example.pallavigupta.epoque;

import org.json.JSONException;

public interface TaskCompleted {
	void onTaskCompleted(String result, int resultType) throws JSONException;

}

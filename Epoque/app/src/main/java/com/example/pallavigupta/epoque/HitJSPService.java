package com.example.pallavigupta.epoque;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HitJSPService extends AsyncTask<Void, Void, String> {
	private static final String TAG = "FN-HITPHP";
	Context con;
	List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	String methodName = "";
	TaskCompleted taskCompleted;
	int resultType;
	ProgressDialog dialog;

	public HitJSPService(Context con, List<NameValuePair> pairs,
						 TaskCompleted taskCompleted, String methodName, int resultType) {
		// TODO Auto-generated constructor stub
		this.con = con;
		this.pairs = pairs;
        try {
            dialog = new ProgressDialog(con);
        }catch (Exception e){}
		this.methodName = methodName;
		this.taskCompleted = taskCompleted;
		this.resultType = resultType;

	}
	protected void onPreExecute() {
		// called before doInBackground() is started
        try {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }catch (Exception e){}
	}
	protected String doInBackground(Void... s) {
		// Create a new HttpClient and Post Header
		String resp = "";
		HttpClient client = new DefaultHttpClient();
		String postURL = (methodName);
		Log.i(TAG, postURL);
		HttpPost post = new HttpPost(postURL);
		try {
			if (pairs != null) {
				UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(pairs);
				post.setEntity(uefe);
			}
			HttpResponse response = client.execute(post);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				resp = EntityUtils.toString(resEntity);
				Log.i(TAG, resp);
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return resp;
	}

	protected void onPostExecute(String s) {
		// called after doInBackground() has finished
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }catch (Exception e){}
        try {
            if (taskCompleted != null) {
                taskCompleted.onTaskCompleted(s, resultType);
            }
        }catch (Exception e){}
	}
}

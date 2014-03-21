package com.common.methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.bucketdevelopers.uft.ServerService;
import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

public class assetsOperation extends Activity {
	public static String htmlfile(String fname) {
		BufferedReader buff;
		String temp = "";
		StringBuilder sb = new StringBuilder();

		// =================================================== 

		AssetManager assetManager = ServerService.assetManager;
		// ===================================================
		Log.d("thisss", assetManager.toString() + "");
		InputStreamReader is_rd;
		try {
			is_rd = new InputStreamReader(assetManager.open(fname));
			buff = new BufferedReader(is_rd);

			while ((temp = buff.readLine()) != null) {
				sb.append(temp);
			}

		} catch (IOException e) {
			Log.d("message: ", e.getMessage());
		}

		Log.d("thisss", sb.toString());
		return sb.toString();

	}
}

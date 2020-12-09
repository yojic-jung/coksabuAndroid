package com.dywlr.coksabuandroid;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;


public class HttpTask extends AsyncTask<String, Void, String> {

    String result;
    @Override
    protected String doInBackground(String... params){
        HttpsURLConnection urlConnection= null;
        java.net.URL url = null;
        String response = "";

        try {
            result=null;
            url  = new java.net.URL(params[0]);
            urlConnection=(HttpsURLConnection)url.openConnection();

            String cookieString = CookieManager.getInstance().getCookie("https://m.coksabu.com");
            if (cookieString != null) {
                urlConnection.setRequestProperty("Cookie", cookieString);
            }
            String token = params[1];
            Log.i("HTTPS토큰:", token);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();

            //
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(token.getBytes("UTF-8")); //전송할 데이터가 저장된 변수를 이곳에 입력합니다.

            outputStream.flush();
            outputStream.close();


            int resCode = urlConnection.getResponseCode();


            if (resCode == 200){
                Log.i("HTTPS통신","HTTPS통신 성공 :"+resCode);
                InputStream inStream = null;
                inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                String temp = "";
                while((temp = bReader.readLine()) != null){
                    response += temp;
                }
                bReader.close();
                inStream.close();
                result = response;
            } else {
                result = "없음";
                Log.w("HTTPS통신","HTTPS통신 오류 :"+resCode);
            }

            urlConnection.disconnect();

            Log.i(TAG,"## response :" +response);
        } catch (Exception e) {
            e.printStackTrace();
            result = "없음";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i("HTTPS통신 onPostExecute :",s);

        super.onPostExecute(s);
        //파일 없을 경우 처리
        if(result.equals("")){
            //파일 없을경우
        } else {
            //파일 있을경우
        }
    }


    public String getToken(){
        return result;
    }
}
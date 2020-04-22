package com.heyongrui.network.convert;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.heyongrui.network.core.CoreHeader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * 2020-04-15 09:36
 * heyongrui
 */
public class CustomResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = value.string();
//            Log.i("CustomGsonResponse", "convert: " + response);
            JSONObject mJSONObject = null;
            try {
                mJSONObject = new JSONObject(response);
            } catch (JSONException mE) {
                mE.printStackTrace();
            }
            int code = mJSONObject.optInt(CoreHeader.KEY_STATUS, -1);
            if (code == 200) {
                MediaType mediaType = value.contentType();
                Charset charset = mediaType != null ? mediaType.charset(Charset.forName("UTF-8")) : Charset.forName("UTF-8");
                InputStream inputStream = new ByteArrayInputStream(response.getBytes());
                JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
                T result = adapter.read(jsonReader);
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
                return result;
            } else {
                String message = mJSONObject.optString(CoreHeader.KEY_MSG);
                value.close();
                throw new CustomIOException(code, message);
            }
        } finally {
            value.close();
        }
    }
}

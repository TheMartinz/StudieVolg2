package com.example.martin.studievolg.Gson;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Brandon on 10-Jan-16.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private final Class<T> mClazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final java.lang.reflect.Type mType;
    private final String TAG = "GsonRequest<T>";

    /** Make a GET request and return a parsed object from JSON.
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers     */

    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.mClazz = clazz;          this.mType = null;
        this.headers = headers;        this.listener = listener;
    }
    // Add this constructor : (replace Class<T>  with Type ) (Zodat we een lijst met types terug kunnen krijgen i.p.v. 1 class )
    public GsonRequest(String url, Type type, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.mClazz = null;
        this.mType = type;
        this.headers = headers;
        this.listener = listener;
    }
    // Continued from previous slide

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            if (this.mClazz != null) {
                return Response.success(
                        this.gson.fromJson(new String(response.data, HttpHeaderParser.parseCharset(response.headers)), this.mClazz),
                        HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return (Response<T>) Response.success(
                        this.gson.fromJson(new String(response.data, HttpHeaderParser.parseCharset(response.headers)), this.mType),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }// end parseNetwResp
} // end class


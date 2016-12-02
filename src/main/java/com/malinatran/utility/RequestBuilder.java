package com.malinatran.utility;

import com.malinatran.request.MethodTypeReader;
import com.malinatran.request.Request;
import com.malinatran.routing.Header;

import java.util.Hashtable;
import java.util.Map;

import static com.malinatran.request.Method.PATCH;

public class RequestBuilder {

    public Map<String, String> getRequestBody(Request request) {
        Map<String, String> result = new Hashtable<String, String>();
        String method = request.getMethod();
        String path = request.getPath();

        if (method.equals(PATCH)) {
            return handlePatch(request, result);
        } else if (MethodTypeReader.isPutOrPostToForm(method, path)) {
            return handlePutOrPost(request, result);
        } else if (MethodTypeReader.isDeleteToForm(method, path)) {
            return handleDelete(result);
        }

        return new Hashtable<String, String>();
    }

    private Map<String, String> handlePatch(Request request, Map<String, String> result) {
        String eTag = request.getHeaderValue(Header.IF_MATCH);
        char[] bodyAsCharArray = request.getBody();
        String body = new String(bodyAsCharArray);

        result.put(RequestLogger.ETAG, eTag);
        result.put(RequestLogger.BODY, body);

        return result;
    }

    private Map<String, String> handlePutOrPost(Request request, Map<String, String> result) {
        char[] bodyAsCharArray = request.getBody();

        String body = new String(bodyAsCharArray);

        result.put(RequestLogger.BODY, body);

        return result;
    }

    private Map<String,String> handleDelete(Map<String, String> result) {
        result.put(RequestLogger.BODY, "");

        return result;
    }
}
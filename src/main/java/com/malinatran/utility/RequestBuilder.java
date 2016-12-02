package com.malinatran.utility;

import com.malinatran.request.MethodTypeReader;
import com.malinatran.request.Request;
import com.malinatran.routing.Header;

import java.util.Hashtable;
import java.util.Map;

import static com.malinatran.request.Method.PATCH;

public class RequestBuilder {

    public Map<String, String> getRequestBody(Request request) {
        Map<String, String> map = new Hashtable<String, String>();
        String method = request.getMethod();
        String path = request.getPath();

        if (method.equals(PATCH)) {
            return handlePatch(request, map);
        } else if (MethodTypeReader.isPutOrPostToForm(method, path)) {
            return handlePutOrPost(request, map);
        } else if (MethodTypeReader.isDeleteToForm(method, path)) {
            return handleDelete(map);
        }

        return map;
    }

    private Map<String, String> handlePatch(Request request, Map<String, String> map) {
        String eTag = request.getHeaderValue(Header.IF_MATCH);
        char[] bodyAsCharArray = request.getBody();
        String body = new String(bodyAsCharArray);
        map.put(RequestLogger.ETAG, eTag);
        map.put(RequestLogger.BODY, body);

        return map;
    }

    private Map<String, String> handlePutOrPost(Request request, Map<String, String> map) {
        char[] bodyAsCharArray = request.getBody();
        String body = new String(bodyAsCharArray);
        map.put(RequestLogger.BODY, body);

        return map;
    }

    private Map<String,String> handleDelete(Map<String, String> map) {
        map.put(RequestLogger.BODY, "");

        return map;
    }
}

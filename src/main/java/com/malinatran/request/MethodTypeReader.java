package com.malinatran.request;

import com.malinatran.resource.Directory;
import com.malinatran.resource.FileTypeReader;
import com.malinatran.utility.RequestLogger;

import static com.malinatran.request.Method.GET;
import static com.malinatran.request.Method.DELETE;
import static com.malinatran.request.Method.POST;
import static com.malinatran.request.Method.PUT;

public class MethodTypeReader {

    public static final String FORM_PATH = "/form";

    public static boolean isGetRequestWithLoggedBody(Request request, RequestLogger logger) {
        return (isGetRequestToForm(request) || isGetRequestToExistingFile(request, logger));
    }

    private static boolean isGetRequestToForm(Request request) {
        String method = request.getMethod();
        String path = request.getPath();

        return (method.equals(GET) && path.equals(FORM_PATH));
    }

    private static boolean isGetRequestToExistingFile(Request request, RequestLogger logger) {
        String method = request.getMethod();
        String filePath = request.getAbsolutePath();

        return (method.equals(GET) &&
                logger.hasBody() &&
                FileTypeReader.isTextFile(filePath) &&
                Directory.existsInDirectory(filePath));
    }

    public static boolean isPutOrPostToForm(String method, String path) {
        return (method.equals(POST) || method.equals(PUT)) && path.equals(FORM_PATH);
    }

    public static boolean isDeleteToForm(String method, String path) {
        return (method.equals(DELETE) && path.equals(FORM_PATH));
    }
}
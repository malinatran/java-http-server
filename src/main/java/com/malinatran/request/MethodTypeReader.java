package com.malinatran.request;

import com.malinatran.resource.Directory;
import com.malinatran.resource.FileTypeReader;
import com.malinatran.utility.RequestLogger;

import static com.malinatran.utility.Method.GET;
import static com.malinatran.utility.Method.DELETE;
import static com.malinatran.utility.Method.POST;
import static com.malinatran.utility.Method.PUT;

public class MethodTypeReader {

    private static final String FORM = "/form";

    public static boolean isGetRequestWithLoggedBody(Request request, RequestLogger logger) {
        return (isGetRequestToForm(request) || isGetRequestToExistingFile(request, logger));
    }

    public static boolean isGetRequestToForm(Request request) {
        String method = request.getMethod();
        String path = request.getPath();

        return (isMethod(method, GET) && path.equals(FORM));
    }

    public static boolean isGetRequestToExistingFile(Request request, RequestLogger logger) {
        String method = request.getMethod();
        String filePath = request.getAbsolutePath();

        return (isMethod(method, GET) &&
                logger.hasBody() &&
                FileTypeReader.isTextFile(filePath) &&
                Directory.existsInDirectory(filePath));
    }

    public static boolean isPutOrPostToForm(String method, String path) {
        return (isMethod(method, POST) || isMethod(method, PUT) && path.equals(FORM));
    }

    public static boolean isDeleteToForm(String method, String path) {
        return (isMethod(method, DELETE) && path.equals(FORM));
    }

    public static boolean isMethod(String text, String methodType) {
        return text.equals(methodType);
    }
}
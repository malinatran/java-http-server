package com.malinatran;

public class Response {

    public String getResponse(Request request) {
        String method = request.getMethodType();
        String uri = request.getUri();
        String body = request.getBody();

        if ((method.equals(Methods.PUT) || method.equals(Methods.POST)) && body.length() != 0) {
            return StatusCodes.OK;
        } else if ((method.equals(Methods.GET) || method.equals(Methods.HEAD)) && uri.equals("/")) {
            return StatusCodes.OK;
        } else {
            return StatusCodes.NOT_FOUND;
        }
    }
}

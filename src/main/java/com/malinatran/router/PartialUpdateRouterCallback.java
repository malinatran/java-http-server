package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.response.Response;

import java.io.IOException;

public class PartialUpdateRouterCallback implements RouterCallback {

   Directory directory = new Directory();

   public void run(Request request, Response response) throws IOException {
      String method = request.getMethod();
      String directoryPath = request.getDirectoryPath();
      String fileName = request.getPath().replace("/", "").trim();

      if (method.equals(Method.PATCH) && directory.existsInDirectory(directoryPath, fileName)) {
         response.setStatus(Status.NO_CONTENT);
      } else {
         response.setStatus(Status.NOT_MODIFIED);
      }
   }
}
package com.malinatran.router;

import com.malinatran.constants.Method;

public class Routes {
    public Routes(Router router) {
        router.addRoute(Method.OPTIONS, "/method_options", new OptionsRouterCallback());
        router.addRoute(Method.GET, "/method_options", new OptionsRouterCallback());
        router.addRoute(Method.PUT, "/method_options", new OptionsRouterCallback());
        router.addRoute(Method.POST, "/method_options", new OptionsRouterCallback());
        router.addRoute(Method.HEAD, "/method_options", new OptionsRouterCallback());
        router.addRoute(Method.OPTIONS, "/method_options2", new OptionsRouterCallback());
        router.addRoute(Method.PUT, "*", new CreateOrUpdateRouterCallback());
        router.addRoute(Method.POST, "*", new CreateOrUpdateRouterCallback());
        router.addRoute(Method.GET, "/", new IndexRouterCallback());
        router.addRoute(Method.HEAD, "/", new IndexRouterCallback());
        router.addRoute(Method.GET, "/redirect", new RedirectRouterCallback());
        router.addRoute(Method.GET, "/logs", new LogsRouterCallback());
        router.addRoute(Method.GET, "/log", new LogsRouterCallback());
        router.addRoute(Method.PUT, "/these", new LogsRouterCallback());
        router.addRoute(Method.HEAD, "/request", new LogsRouterCallback());
    }
}


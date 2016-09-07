package com.malinatran.router;

import static com.malinatran.constants.Method.*;

public class Routes {

    public Routes(Router router) {
        router.addRoute(OPTIONS, "/method_options", new OptionsRouterCallback());
        router.addRoute(GET, "/method_options", new OptionsRouterCallback());
        router.addRoute(PUT, "/method_options", new OptionsRouterCallback());
        router.addRoute(POST, "/method_options", new OptionsRouterCallback());
        router.addRoute(HEAD, "/method_options", new OptionsRouterCallback());
        router.addRoute(OPTIONS, "/method_options2", new OptionsRouterCallback());
        router.addRoute(PUT, "*", new CreateOrUpdateRouterCallback());
        router.addRoute(PUT, "/file1", new NotFoundOrAllowedRouterCallback());
        router.addRoute(POST, "*", new CreateOrUpdateRouterCallback());
        router.addRoute(POST, "/text-file.txt", new NotFoundOrAllowedRouterCallback());
        router.addRoute(GET, "/", new IndexRouterCallback());
        router.addRoute(GET, "*", new IndexRouterCallback());
        router.addRoute(HEAD, "/", new IndexRouterCallback());
        router.addRoute(HEAD, "*", new NotFoundOrAllowedRouterCallback());
        router.addRoute(GET, "/redirect", new RedirectRouterCallback());
        router.addRoute(GET, "/logs", new LogsRouterCallback());
        router.addRoute(GET, "/coffee", new EasterEggRouterCallback());
        router.addRoute(GET, "/tea", new EasterEggRouterCallback());
    }
}


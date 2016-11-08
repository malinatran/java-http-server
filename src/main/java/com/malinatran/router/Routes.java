package com.malinatran.router;

import static com.malinatran.utility.Method.*;

public class Routes {

    public Routes(Router router) {
        router.addRoute(GET, "/form", new FormRouterCallback());
        router.addRoute(POST, "/form", new FormRouterCallback());
        router.addRoute(PUT, "/form", new FormRouterCallback());
        router.addRoute(DELETE, "/form", new FormRouterCallback());
        router.addRoute(OPTIONS, "/method_options", new OptionsRouterCallback());
        router.addRoute(GET, "/method_options", new OptionsRouterCallback());
        router.addRoute(PUT, "/method_options", new OptionsRouterCallback());
        router.addRoute(POST, "/method_options", new OptionsRouterCallback());
        router.addRoute(HEAD, "/method_options", new OptionsRouterCallback());
        router.addRoute(OPTIONS, "/method_options2", new OptionsRouterCallback());
        router.addRoute(PUT, "*", new CreateOrUpdateRouterCallback());
        router.addRoute(POST, "*", new CreateOrUpdateRouterCallback());
        router.addRoute(GET, "/", new IndexRouterCallback());
        router.addRoute(GET, "*", new FileContentRouterCallback());
        router.addRoute(HEAD, "/", new IndexRouterCallback());
        router.addRoute(HEAD, "*", new NotFoundOrAllowedRouterCallback());
        router.addRoute(GET, "/redirect", new RedirectRouterCallback());
        router.addRoute(GET, "/logs", new AuthorizedRouterCallback());
        router.addRoute(GET, "/coffee", new EasterEggRouterCallback());
        router.addRoute(GET, "/tea", new EasterEggRouterCallback());
        router.addRoute(PATCH, "*", new FileContentRouterCallback());
    }
}


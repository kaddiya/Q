package org.kaddiya.Q

import groovy.util.logging.Slf4j
import org.kaddiya.Q.modules.RMIModule
import org.kaddiya.Q.routers.RootRouter
import restling.guice.modules.RestlingApplicationModule


@Slf4j
class ApplicationModule extends RestlingApplicationModule {

    Class<RootRouter> routerClass = RootRouter

    @Override
    void configureCustomBindings() {
        //install the RMI module
        this.install(new RMIModule())
    }
}

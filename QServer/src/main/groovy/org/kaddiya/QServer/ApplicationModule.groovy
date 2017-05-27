package org.kaddiya.QServer

import groovy.util.logging.Slf4j
import org.kaddiya.QServer.modules.RMIModule
import org.kaddiya.QServer.routers.RootRouter
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

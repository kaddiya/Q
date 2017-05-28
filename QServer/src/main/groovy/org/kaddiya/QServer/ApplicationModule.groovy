package org.kaddiya.QServer

import groovy.util.logging.Slf4j
import org.kaddiya.QServer.modules.ServiceModule
import org.kaddiya.QServer.routers.RootRouter
import restling.guice.modules.RestlingApplicationModule

@Slf4j
class ApplicationModule extends RestlingApplicationModule {

    Class<RootRouter> routerClass = RootRouter

    @Override
    void configureCustomBindings() {
        this.install(new ServiceModule())
    }
}

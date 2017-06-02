package org.kaddiya.QServer.modules

import com.google.inject.AbstractModule
import org.kaddiya.QServer.internal.services.TopicService
import org.kaddiya.QServer.internal.services.TopicServiceImpl


class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TopicService).to(TopicServiceImpl)
    }
}

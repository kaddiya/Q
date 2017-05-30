package org.kaddiya.QServer.modules

import com.google.inject.AbstractModule
import org.kaddiya.QServer.internal.services.TopicPutter
import org.kaddiya.QServer.internal.services.TopicPutterImpl


class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TopicPutter).to(TopicPutterImpl)
    }
}

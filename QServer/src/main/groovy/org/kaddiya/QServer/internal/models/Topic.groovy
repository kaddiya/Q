package org.kaddiya.QServer.internal.models

import groovy.transform.Immutable
import org.kaddiya.QClient.models.Message


/**
 * Created by Webonise on 27/05/17.
 */

class Topic implements Queue<Message> {

    @Override
    int size() {
        return 0
    }

    @Override
    boolean isEmpty() {
        return false
    }

    @Override
    boolean contains(Object o) {
        return false
    }

    @Override
    Iterator<Message> iterator() {
        return null
    }

    @Override
    Object[] toArray() {
        return new Object[0]
    }

    @Override
    def <T> T[] toArray(T[] a) {
        return null
    }

    @Override
    boolean add(Message message) {
        return false
    }

    @Override
    boolean remove(Object o) {
        return false
    }

    @Override
    boolean containsAll(Collection<?> c) {
        return false
    }

    @Override
    boolean addAll(Collection<? extends Message> c) {
        return false
    }

    @Override
    boolean removeAll(Collection<?> c) {
        return false
    }

    @Override
    boolean retainAll(Collection<?> c) {
        return false
    }

    @Override
    void clear() {

    }

    @Override
    boolean offer(Message message) {
        return false
    }

    @Override
    Message remove() {
        return null
    }

    @Override
    Message poll() {
        return null
    }

    @Override
    Message element() {
        return null
    }

    @Override
    Message peek() {
        return null
    }
}

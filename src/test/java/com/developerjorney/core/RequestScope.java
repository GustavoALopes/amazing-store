package com.developerjorney.core;

import org.springframework.web.context.request.RequestAttributes;

import java.util.HashMap;
import java.util.Map;

public class RequestScope implements RequestAttributes {

    private Map<String, Object> requestAttributeMap = new HashMap<>();

    @Override
    public Object getAttribute(final String name, final int scope) {
        if(scope == RequestAttributes.SCOPE_REQUEST) {
            return this.requestAttributeMap.get(name);
        }
        return null;
    }

    @Override
    public void setAttribute(final String name, final Object value, final int scope) {
        if(scope == RequestAttributes.SCOPE_REQUEST) {
            this.requestAttributeMap.put(name, value);
        }
    }

    @Override
    public void removeAttribute(final String name, final int scope) {
        if(scope == RequestAttributes.SCOPE_REQUEST) {
            this.requestAttributeMap.remove(name);
        }
    }

    @Override
    public String[] getAttributeNames(final int scope) {
        if(scope == RequestAttributes.SCOPE_REQUEST) {
            this.requestAttributeMap.keySet().toArray();
        }

        return new String[0];
    }

    @Override
    public void registerDestructionCallback(final String name, final Runnable callback, final int scope) {

    }

    @Override
    public Object resolveReference(final String key) {
        return null;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public Object getSessionMutex() {
        return null;
    }
}

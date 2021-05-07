package com.ccm.user.user.domain.interfaces;

import java.io.IOException;

public interface EventPublisherClient {

    void publish(String message) throws IOException;
}

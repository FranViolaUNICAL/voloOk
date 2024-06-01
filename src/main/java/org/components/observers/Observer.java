package org.components.observers;

import java.io.IOException;
import java.util.Observable;

public interface Observer {
    void update(Subject subject);
}

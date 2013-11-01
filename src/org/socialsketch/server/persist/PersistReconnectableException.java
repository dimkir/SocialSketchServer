package org.socialsketch.server.persist;

/**
 *
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class PersistReconnectableException extends PersistException {

    public PersistReconnectableException(String message) {
        super(message);
    }

    public PersistReconnectableException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistReconnectableException(Throwable cause) {
        super(cause);
    }
    
}

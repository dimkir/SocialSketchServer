package org.socialsketch.server.persist;


/**
 *
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class PersistFatalException extends PersistException
{

    public PersistFatalException(String message) {
        super(message);
    }

    public PersistFatalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistFatalException(Throwable cause) {
        super(cause);
    }
    
}

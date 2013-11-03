package org.socialsketch.server.persist;

/**
 *
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class PersistException extends Exception
{

    public PersistException(String message) {
        super(message);
    }

    public PersistException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistException(Throwable cause) {
        super(cause);
    }
    
}

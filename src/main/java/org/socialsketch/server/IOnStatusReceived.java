package org.socialsketch.server;

import twitter4j.Status;
import twitter4j.Twitter;

/**
 * This is interface defining callback for when new tweet arrives.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public interface IOnStatusReceived {
     void onStatusReceived(Twitter twitter, Status status);
}

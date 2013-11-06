package org.socialsketch.server;

import org.apache.log4j.Logger;
import org.socialsketch.server.persist.PersistException;
import org.socialsketch.server.persist.PersistToDb;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * This class receives events from streaming API and stores them into db through
 * the "Persister" API.
 *
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class MyStatusListener implements StatusListener {

    private final PersistToDb mPersister;
    private static final Logger logger = org.apache.log4j.Logger.getLogger(MyStatusListener.class);
    private final Twitter mTwitter;
    private final IOnStatusReceived mOnStatusReceived;

    /**
     * Initializes Listener with the given persistor.
     * @param persistor NON-null persister.
     */
    MyStatusListener(PersistToDb persistor, Twitter twitter, IOnStatusReceived callback) {
        mPersister = persistor;
        mTwitter = twitter;
        mOnStatusReceived = callback;
    }

    @Override
    public void onStatus(Status status) {
        try {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            mPersister.persistTweet(status);
            mOnStatusReceived.onStatusReceived(mTwitter, status);
        } catch (PersistException ex) {
            logger.error("Error persisting tweet with id (" + status.getId() + ")");
        } catch (TwitterException ex) {
            logger.error("Cannot favorite tweet with id (" + status.getId() + ")");
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        //ex.printStackTrace();
        logger.error("Whist listening to tweet stream, received exception:", ex);
    }
}

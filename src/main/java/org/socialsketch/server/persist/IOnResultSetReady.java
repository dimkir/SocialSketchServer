package org.socialsketch.server.persist;

import java.sql.ResultSet;

/**
 * Interface defining callback, which is used when there's avaialble result set
 * off the query.
 * 
 * This is my experiment to try to make code for sql handling in java look smaller.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public interface IOnResultSetReady {
    void onResultSet(ResultSet rs);
}

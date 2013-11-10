/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.socialsketch.server.controllers;

import org.apache.log4j.Logger;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * This is local class which encapsulates verification of the parameters.
 * 
 * 
 * For rest-list controller.
 */
class ParameterVerifier {
    
    private static final Logger logger = org.apache.log4j.Logger.getLogger(ParameterVerifier.class);
    
    private final String mPassedLimit;
    private final String mPassedOlderThan;
    
    private int mLimit;
    private long mOlderThan;
    private Exception mException;
    private boolean mIsValid = false;
    private final int C_JSON_IDENT = 4;

    /**
     * Tries to parse and verify parameters.
     *
     * @param limit should be valid limit (>0)
     * @param older_than
     */
    ParameterVerifier(String limit, String olderThan) {
        mPassedLimit = limit;
        mPassedOlderThan = olderThan;
        
        try {
            mLimit = Integer.parseInt(limit);
            if (mLimit < 1) {
                // cannot be valid as it should be 1 or more.
                mIsValid = false;
                return;
            }
            mOlderThan = Long.parseLong(olderThan);
            mIsValid = true;
        } catch (NumberFormatException nfex) {
            mException = nfex;
            mIsValid = false;
        }
        // TODO: implement ParameterVerifier
    }

    boolean isInvalid() {
        // TODO: implement isInvalid
        return false;
        //            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the exception which was the cause of error or NULL
     * if error had non-exception cause.
     *
     * @return cause Exception or NULL when there's none.
     */
    Exception getCause() {
        return mException;
    }

    /**
     * Returns error message as json object.
     * 
     * Which looks like?? (I am not sure the escaping is correct below.
     * 
     * {
     *   "error" : "This is error message \/" escaped properly \/ \"  yes.".  
     * }
     * @return 
     */
    String getJSONErrorMessage() {
        try {
            // TODO: implement getJSONErrorMessage
            JSONObject jsob = new JSONObject();
            jsob.put("error", getErrorMessage());
            return jsob.toString(C_JSON_IDENT);
        } catch (JSONException ex) {
            logger.error("Cannot actually create JSON error message, ex:", ex);
            return "{ \"error\" : \"Error creating JSON error message\" }";
        }
    }

    int getLimit() {
        // TODO: implement getLimit
        return mLimit;
    }

    /**
     * Returns error message explaining cause why parameters failed.
     * 
     * @return 
     */
    String getErrorMessage() {
        if ( !isInvalid()) {
            return "Everything is ok, parameters are valid.";
        }
        
        if ( mException != null ){
            return "Error parsing values:" + mException.getMessage();
        }
        
        return String.format("Some error parsing values limit(%s) and older_than(%s)", mPassedLimit, mPassedOlderThan);
    }
    
    
  
}

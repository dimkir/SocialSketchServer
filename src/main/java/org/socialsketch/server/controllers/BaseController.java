package org.socialsketch.server.controllers;

import org.apache.log4j.Logger;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * Base controller containing useful methods for controllers.
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class BaseController {
    
    protected final Logger logger = org.apache.log4j.Logger.getLogger(BaseController.class);
            
            
    /**
     * Creates JSON response with the error message.
     *
     * @return correct json or error about error making JSON.
     */
    protected String makeError(String errMsg, Exception ex) {
        try {
            // TODO: implement makeError
            // make error object
            JSONObject errorJson = new JSONObject();
            errorJson.put("errorCode", 444);
            errorJson.put("errorMsg", "Some error initializing persistor:" + errMsg + ". Exception: " + ex.getMessage());
            // make reply object.
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("error", errorJson);
            return jsonObject.toString(4);
        } catch (JSONException ex1) {
            logger.error("There was error making JSON", ex);
            return "There was error making JSON Object : " + ex.getMessage();
        }
    }    
    
    
}

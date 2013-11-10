package org.socialsketch.server.controllers;

import java.util.List;
import org.socialsketch.server.persist.PersistException;
import org.socialsketch.server.persist.PersistToDb;
import org.socialsketch.server.persist.TweetRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;

/**
 *
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
// this is rest controller, because we want to return value directly.
@RestController
public class RestListController extends BaseController {
    private final int C_INDENT = 4;
    
//    private final Logger logger = org.apache.log4j.Logger.getLogger(RestListController.class);
    /**
     * Handles HTTP request to get list of items. 
     * Specifies /rest_list?limit=100&older_than=1021010101 
     * the latter one is timestamp
     * 
     * @param limit limit of items per page. Valid values are from 1 to ??? (probably we need to limit it to 1000)
     * @param older_than
     * @param model ?? wtf
     * @return valid response in regards with the given status.
     */
    @RequestMapping("/rest_list")
    public String restList(
                         @RequestParam(value="limit", required=false, defaultValue="30") String limit
                        ,@RequestParam(value="older_than", required=false, defaultValue="-1") String older_than
//                      , Model model
    ){
        
        // actually here I need to chekc for the validity of the parameters.
        ParameterVerifier pv = new ParameterVerifier(limit, older_than);
        if ( pv.isInvalid() ){
            return pv.getJSONErrorMessage();
        }
        
        try {
            PersistToDb persistor = PersistToDb.getInstance();

            List<TweetRecord> latestTweets = persistor.getLatestTweets(pv.getLimit());
            // here you have to make JSON object
            return listToJson(latestTweets);
            
        } catch (PersistException ex) {
            logger.error(ex);
            return makeError("Cannot initialize persitor, check log.", ex);
        } catch (JSONException ex) {
            logger.error(ex);
            return makeError("Cannot generate json", ex);
        }
        
    }

    /**
     * Converts list of TweetRecords into JSON array of TweetRecords.
     * 
     * @param latestTweets
     * 
     * @return 
     */
    private String listToJson(List<TweetRecord> latestTweets) throws JSONException {
        // TODO: implement listToJson
        JSONArray jsonArray = new JSONArray();
        for(TweetRecord tr : latestTweets){
            jsonArray.put(tr.toMap());
        }
        
        return jsonArray.toString(C_INDENT);
    }
}

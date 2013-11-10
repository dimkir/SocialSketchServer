package org.socialsketch.server.controllers;

import java.util.List;
import org.socialsketch.server.persist.PersistException;
import org.socialsketch.server.persist.PersistToDb;
import org.socialsketch.server.persist.TweetRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This is controller for getting listing.
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */

@Controller
public class ListingController extends BaseController {
    
    /**
     * Returns view for the listing.
     * 
     * @param name
     * @param limit
     * @param older_than
     * @param model
     * @return 
     */
    @RequestMapping("/listing")
    public String listing(
                            @RequestParam(value="name", required=false, defaultValue="World") String name
                           ,@RequestParam(value="limit", required=false, defaultValue="20") String limit
                           ,@RequestParam(value="older_than", required=false, defaultValue="-1") String older_than
                           ,Model model)
    {
        try {
            model.addAttribute("name", name);
            ParameterVerifier pv = new ParameterVerifier(limit, older_than);
            
            if ( pv.isInvalid() ){
                model.addAttribute("error", pv.getErrorMessage());
                return "error_view";
            }
            PersistToDb persistor = PersistToDb.getInstance();
            
            List<TweetRecord> latestTweets = persistor.getLatestTweets(pv.getLimit());
            model.addAttribute("tweets", latestTweets);
//            model.addAttribute("books", new String[] { "War and peace", "Crimen y castigo"});
        } catch (PersistException ex) {
            logger.error("There was error initializing persistor:", ex);
        }
        return "listing"; // this is the name of the view?
        
    }
}

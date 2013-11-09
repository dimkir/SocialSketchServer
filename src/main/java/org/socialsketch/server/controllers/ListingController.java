package org.socialsketch.server.controllers;

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
public class ListingController {
    
    /**
     * Returns view for the listing.
     * 
     * @param name
     * @param model
     * @return 
     */
    @RequestMapping("/listing")
    public String listing(@RequestParam(value="name", required=false, defaultValue="World") String name,
                            Model model){
        
        model.addAttribute("name", name);
        return "listing"; // this is the name of the view?
        
    }
}

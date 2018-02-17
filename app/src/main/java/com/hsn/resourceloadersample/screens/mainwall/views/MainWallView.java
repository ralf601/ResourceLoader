package com.hsn.resourceloadersample.screens.mainwall.views;



import com.hsn.resourceloadersample.model.UserContent;
import com.hsn.resourceloadersample.screens.common.views.ViewMvc;

import java.util.List;

/**
 * This MVC view corresponds to a screen where a list containing all SMS messages is shown
 */
public interface MainWallView extends ViewMvc {


    interface MainWallViewInteractor {
        /**
         * This callback will be invoked when the user clicks on one of the shown pin image
         * @param userContent content clicked
         */
        void onItemClicked(UserContent userContent);


        void fetchData();
    }

    /**
     * Bind User contents which should be shown by this MVC view
     * @param smsMessages list of {@link UserContent} objects that need to be shown
     */
    void bindUserContent(List<UserContent> smsMessages);

    /**
     * Set a listener that will be notified by this MVC view
     * @param interactor listener that should be notified
     */
    void setInteractor(MainWallViewInteractor interactor);


    /**
     *
     */
    void stopRefreshIndicator();

}

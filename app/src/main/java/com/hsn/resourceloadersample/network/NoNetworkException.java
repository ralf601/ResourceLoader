package com.hsn.resourceloadersample.network;


/**
 * Created by hassanshakeel on 2/13/18.
 */

public class NoNetworkException extends Exception {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage(){
        return "Please check your internet connection";
    }

}

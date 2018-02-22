package com.example.bob.testlistener.util;


import com.example.bob.testlistener.data.Media;

/**
 * Created by dnld on 9/19/17.
 */
public class DeleteException extends Exception {

    Media media;

    public DeleteException(Media media) {
        super("Cannot delete Media");
        this.media = media;
    }

    public DeleteException() {
        this(null);
    }

    public Media getMedia() {
        return media;
    }
}
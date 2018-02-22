package com.example.bob.testlistener.data.filter;


import com.example.bob.testlistener.data.Media;

/**
 * Created by dnld on 4/10/17.
 */

public interface IMediaFilter {
    boolean accept(Media media);
}

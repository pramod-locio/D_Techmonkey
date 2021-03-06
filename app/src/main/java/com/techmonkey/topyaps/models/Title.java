package com.techmonkey.topyaps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by P KUMAR on 06-10-2016.
 */

public class Title implements Serializable {
    @SerializedName("rendered")
    @Expose
    private String rendered;

    /**
     *
     * @return
     * The rendered
     */
    public String getRendered() {
        return rendered;
    }

    /**
     *
     * @param rendered
     * The rendered
     */
    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}

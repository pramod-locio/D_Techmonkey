package com.dtechmonkey.d_techmonkey.models.category;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by P KUMAR on 26-10-2016.
 */

public class Collection implements Serializable {

        @SerializedName("href")
        @Expose
        private String href;

        /**
         *
         * @return
         * The href
         */
        public String getHref() {
            return href;
        }

        /**
         *
         * @param href
         * The href
         */
        public void setHref(String href) {
            this.href = href;
        }
}

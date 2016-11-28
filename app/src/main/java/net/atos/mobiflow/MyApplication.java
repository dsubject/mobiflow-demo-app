package net.atos.mobiflow;

import android.app.Application;

/**
 * Created by tmr
 */

public class MyApplication extends Application {

    public final String CDC_DATA_URL = "http://csms-demo.atos.io/rome/uploadimage";
    public final String ACF_DATA_URL = "http://mobiflow-photo-upload.apps.eu01.cf.canopy-cloud.com/jaxrs/uploadimage";;

    public String getDataUrl() {
        return CDC_DATA_URL;
    }
}

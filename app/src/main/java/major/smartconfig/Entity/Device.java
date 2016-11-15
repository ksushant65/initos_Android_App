package major.smartconfig.Entity;

import android.net.Uri;

/**
 * Created by rahul on 15/11/16.
 */
public class Device {
    String name;
    String currentConfig;
    String ipAddress;
    Uri photoUri;

    public Device(String name,String currentConfig,String ipAddress,Uri photoUri){
        this.name = name;
        this.currentConfig = currentConfig;
        this.ipAddress = ipAddress;
        this.photoUri = photoUri;
    }

    public String getCurrentConfig() {
        return currentConfig;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getName() {
        return name;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }
}

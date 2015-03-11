package com.example.yasina.llapp.Model;

import java.io.Serializable;

/**
 * Created by yasina on 11.03.15.
 */
public class Dictionary  implements Serializable {

    public static final String TAG = "Dictionary";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String name, type;

    public Dictionary() {
    }

    public Dictionary(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }
}

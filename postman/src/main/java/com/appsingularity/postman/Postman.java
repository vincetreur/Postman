package com.appsingularity.postman;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsingularity.postman.internal.BasePostman;

import java.util.HashMap;
import java.util.Map;

public class Postman {
    private static final Map<String, BasePostman> mCache = new HashMap<>();

    private Postman() {
    }



    @SuppressWarnings("unchecked")
    public static void ship(@NonNull final Class clazz, @NonNull final Parcelable source, @NonNull final android.os.Parcel dest, int flags) {
        BasePostman postman = findPostman(clazz);
        if (postman != null) {
            postman.ship(source, dest, flags);
        }
    }


    @SuppressWarnings("unchecked")
    public static void receive(@NonNull final Class clazz, @NonNull final Parcelable target, @NonNull final android.os.Parcel in) {
        BasePostman postman = findPostman(clazz);
        if (postman != null) {
            postman.receive(target, in);
        }
    }


    @Nullable
    private static BasePostman findPostman(@NonNull final Class clazz) {
        BasePostman postman = mCache.get(clazz.getName());
        if (postman == null) {
            postman = createPostman(clazz);
            if (postman != null) {
                mCache.put(clazz.getName(), postman);
            }
        }
        return postman;
    }

    @Nullable
    private static BasePostman createPostman(@NonNull final Class clazz) {
        String name = resolveName(clazz);
        Object postmanInstance;
        try {
            postmanInstance = Class.forName(name).newInstance();
            if (postmanInstance instanceof BasePostman) {
                return (BasePostman) postmanInstance;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    private static String resolveName(@NonNull final Class clazz) {
        return clazz.getName() + "$$Postman";
    }

}

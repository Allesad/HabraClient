package com.allesad.habraclient.robospice;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;

/**
 * Created by Allesad on 23.03.2014.
 */
public class BaseSpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        return new CacheManager();
    }

    @Override
    public int getThreadCount() {
        return 4;
    }
}

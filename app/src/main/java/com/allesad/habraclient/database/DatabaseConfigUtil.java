package com.allesad.habraclient.database;

import com.allesad.habraclient.database.models.posts.Post;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 9/20/2014 8:16 AM.
 *
 * DatabaseConfigUtl writes a configuration file to avoid using Annotation processing in runtime. This gains a
 * noticable performance improvement. configuration file is written to /res/raw/ by default.
 * More info at: http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_4.html#Config-Optimization
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {
            Post.class,
    };

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile(new File("ormlite_config.txt"), classes);
    }
}

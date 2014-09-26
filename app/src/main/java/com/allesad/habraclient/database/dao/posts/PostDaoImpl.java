package com.allesad.habraclient.database.dao.posts;

import com.allesad.habraclient.database.models.posts.Post;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Copyright (C) 2014 HabraClient Project
 * Created by Alexey Sakhno aka Allesad on 9/20/2014 8:34 AM.
 */
public class PostDaoImpl extends BaseDaoImpl<Post, Long> implements PostDao {

    public PostDaoImpl(Class<Post> dataClass) throws SQLException {
        super(dataClass);
    }

    public PostDaoImpl(ConnectionSource connectionSource, Class<Post> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PostDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Post> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}

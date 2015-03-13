package com.dizuo.xapp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * �ṩ���������͵����ݡ�
 * Created by peteryfren 2014-12-4
 */
public class xxProviderMetaData {


    public static final String AUTHORIY = "com.dizuo.xapp.xxContentProvider";
    /**
     * ���ݿ�����
     */
    public static final String DATABASE_NAME = "xxProvider.db";
    /**
     * ���ݿ�汾
     */
    public static final int DATABASE_VERSION = 1;
    /**
     * ����
     */
    public static final String USERS_TABLE_NAME = "users";

    /**
     * �̳���BaseColumns�������Ѿ�����_ID
     */
    public static final class UserTableMetaData implements BaseColumns {
        
    	/**
         * ����
         */
        public static final String TABLE_NAME = "users";
        
        /**
         * ���ʸ�ContentProvider��URI
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORIY + "/users");
        
        /**
         * ��ContentProvider�����ص��������Ͷ���
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/com.dizuo.user";
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/com.dizuo.user";
        
        /**
         * ����
         */
        public static final String USER_NAME = "name";
        public static final String USER_AGE = "age";
        
        /**
         * Ĭ�ϵ����򷽷�
         */
        public static final String DEFAULT_SORT_ORDER = "_id desc";
        	
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
        		+ _ID + " INTEGER PRIMARY KEY,"
        		+ USER_NAME + " VARCHAR(50),"
        		+ USER_AGE + " TEXT"
        		+ ");";
        
    }
}


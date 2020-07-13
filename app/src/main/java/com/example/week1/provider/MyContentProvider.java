package com.example.week1.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    ItemDatabase dp;

    //gives the content provider a unique name
    public static final String CONTENT_AUTHORITY = "fit2081.app.angela";
    //access the content provider
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final int MULTIPLE_ITEMS = 1;
    private static final int SINGLE_ITEM = 2;
    public static final UriMatcher myMatcher = createMyItemsMatcher();

    private static UriMatcher createMyItemsMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //content://fit2081.app.angela/items
        uriMatcher.addURI(CONTENT_AUTHORITY,item.TABLE_NAME,MULTIPLE_ITEMS);
        //content://fit2081.app.angela/45
        uriMatcher.addURI(CONTENT_AUTHORITY,item.TABLE_NAME +"/#",SINGLE_ITEM);
        return uriMatcher;
    }


    public MyContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int count;
        int matchId = myMatcher.match(uri);

        switch (matchId){
            case MULTIPLE_ITEMS:
                break;
            case SINGLE_ITEM:
                String idSr = uri.getLastPathSegment();
                break;
        }
        count = dp.getOpenHelper().getWritableDatabase().delete(item.TABLE_NAME,selection,selectionArgs);
        return count;


    }

    @Override
    public String getType(Uri uri) {
        String returnType = null;

        int uriId = myMatcher.match(uri);
        switch (uriId){
            case MULTIPLE_ITEMS:
                returnType = "vnd.android.cursor.dir/items";
                break;
            case SINGLE_ITEM:
                returnType = "vnd.android.cursor.item/items";
                break;
            default:
                throw new UnsupportedOperationException("not implemented");
        }
       return returnType;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName;
        int uriType = myMatcher.match(uri);

        if (uriType == MULTIPLE_ITEMS)
            tableName = item.TABLE_NAME;
        else
            throw new UnsupportedOperationException("Unsupported Operation");

        long newID = dp.getOpenHelper().getWritableDatabase().insert(item.TABLE_NAME,0,values);
        //unique reference to new data item
        return ContentUris.withAppendedId(CONTENT_URI,newID);
    }

    @Override
    public boolean onCreate() {
        //instance of database
        dp = ItemDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        int matchId = myMatcher.match(uri);

        switch (matchId){
            case MULTIPLE_ITEMS:
                break;
            case SINGLE_ITEM:
                break;
            default:
                throw new UnsupportedOperationException("not implemented");
        }

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(item.TABLE_NAME);
        String query = builder.buildQuery(projection,selection,null,null,sortOrder,null);
        cursor = dp.getOpenHelper().getReadableDatabase().query(query,selectionArgs);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        count = dp.getOpenHelper().getWritableDatabase().update(item.TABLE_NAME,0,values,selection,selectionArgs);
        return count;
    }
}

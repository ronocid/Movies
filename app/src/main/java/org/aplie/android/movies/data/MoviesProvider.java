package org.aplie.android.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class MoviesProvider extends ContentProvider {
    private static final String LOG_CONTENT_PROVIDER = "content_provider";
    private MoviesDbHelper dataBaseHelper;

    private static final int MOVIES = 100;
    private static final int MOVIE_TITLE = 101;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIES+"/*", MOVIE_TITLE);
    }

    @Override
    public boolean onCreate() {
        dataBaseHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIES:
            {
                SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
                cursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            case MOVIE_TITLE: {
                String where = selection;
                where = MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE+"=" + uri.getLastPathSegment();
                SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
                cursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME, projection, where, selectionArgs, null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match){
            case MOVIES:
                return MoviesContract.MoviesEntry.CONTENT_TYPE;
            case MOVIE_TITLE:
                return MoviesContract.MoviesEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 0;

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        regId = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, values);
        Uri newUri = ContentUris.withAppendedId(MoviesContract.MoviesEntry.CONTENT_URI, regId);
        Log.d(LOG_CONTENT_PROVIDER," Registros insertados "+regId);

        getContext().getContentResolver().notifyChange(uri, null);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDelete = 0;
        if(uriMatcher.match(uri) == MOVIES){
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            rowsDelete = db.delete(MoviesContract.MoviesEntry.TABLE_NAME, selection, selectionArgs);
        }
        Log.d(LOG_CONTENT_PROVIDER," Registros borrados "+rowsDelete);
        if(rowsDelete !=0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDelete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdate = 0;
        if(uriMatcher.match(uri) == MOVIES){
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            rowsUpdate = db.update(MoviesContract.MoviesEntry.TABLE_NAME, values, selection, selectionArgs);
        }
        Log.d(LOG_CONTENT_PROVIDER," Registros actualizados "+rowsUpdate);
        if(rowsUpdate !=0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdate;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                Log.d(LOG_CONTENT_PROVIDER, " Registros insertados bulkInsert " + returnCount);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}

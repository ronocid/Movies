package org.aplie.android.movies.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import org.aplie.android.movies.R;
import org.aplie.android.movies.conection.ConectionMoviePopularity;
import org.aplie.android.movies.data.MoviesContract;
import org.aplie.android.movies.utils.Movie;

import java.util.List;

public class MovieSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String LOG_TAG = MovieSyncAdapter.class.getSimpleName();
    public static final int SYNC_INTERVAL = 60*180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    public MovieSyncAdapter (Context context, boolean autoInitialize){
        super(context,autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        List<Movie> listMovies = ConectionMoviePopularity.conectionServer(getContext());
        ContentValues[] cvArray = new ContentValues[listMovies.size()];
        for(int pos = 0;pos<listMovies.size();pos++){
            Movie movie = listMovies.get(pos);
            ContentValues movieValues = new ContentValues();
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE,movie.getTitle());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH,movie.getPosterPath());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH,movie.getBackdropPath());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE,movie.getOriginaTitle());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW,movie.getOverview());
            movieValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE,movie.getVoteAverage());
            cvArray[pos] = movieValues;
        }
        getContext().getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI,null,null);
        int inserted = getContext().getContentResolver().bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI,cvArray);

        Log.d(LOG_TAG, "FetchWeatherTask Complete. " + inserted + " Inserted");
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }


    private static void onAccountCreated(Account newAccount, Context context) {
        Log.d(LOG_TAG,SYNC_INTERVAL+" , "+SYNC_FLEXTIME);
        MovieSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}

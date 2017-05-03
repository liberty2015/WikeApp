package com.liberty.wikepro.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by liberty on 2017/4/16.
 */

public class SearchRecentProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.liberty.wikepro.SearchRecentProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchRecentProvider(){
        setupSuggestions(AUTHORITY,MODE);
    }
}

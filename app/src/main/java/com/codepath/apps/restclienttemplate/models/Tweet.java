package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public long id;
    public User user;
    public String retweetCount;
    public String favoriteCount;
    public String embeddedMediaURL;

    //empty constructor needed by the Parceler library
    public Tweet(){
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = getRelativeTimeAgo(jsonObject.getString("created_at"));
        tweet.id = jsonObject.getLong("id");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.retweetCount = String.valueOf(jsonObject.getInt("retweet_count"));
        tweet.favoriteCount = String.valueOf(jsonObject.getInt("favorite_count"));
        if (jsonObject.getJSONObject("entities").has("media")){
            if (jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).has("media_url_https")){
                tweet.embeddedMediaURL = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            }
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
            relativeDate = relativeDate.replace("hr.", "h").replace("min.", "m").replace("sec.", "s").replace(" ago", "");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getRetweetCount() {
        return retweetCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public String getEmbeddedMediaURL() {
        return embeddedMediaURL;
    }
}

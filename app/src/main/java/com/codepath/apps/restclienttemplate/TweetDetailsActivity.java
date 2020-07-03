package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {

    public static final String TAG = "DetailsActivity";

    //the tweet to display
    Tweet tweet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailsBinding detailsBinding = ActivityDetailsBinding.inflate(getLayoutInflater());

        //layout of activity is stored in a special property called root
        View detailsView = detailsBinding.getRoot();
        setContentView(detailsView);

        //the view objects
        Button btnBack = detailsBinding.btnBack;
        Button btnDetailsComment = detailsBinding.btnDetailsComment;
        Button btnDetailsRetweet = detailsBinding.btnDetailsRetweet;
        Button btnDetailsFavorite = detailsBinding.btnDetailsFavorite;
        ImageView ivDetailsProfile = detailsBinding.ivDetailsProfile;
        TextView tvDetailsName = detailsBinding.tvDetailsName;
        TextView tvDetailsScreenname = detailsBinding.tvDetailsScreenname;
        TextView tvDetailsTime = detailsBinding.tvDetailsTime;
        TextView tvDetailsBody = detailsBinding.tvDetailsBody;
        TextView tvDetailsRetweets = detailsBinding.tvDetailsRetweets;
        TextView tvDetailsFavorites = detailsBinding.tvDetailsFavorites;
        ImageView ivDetailsMedia = detailsBinding.ivDetailsMedia;

        //unwrap the tweet passed in via intent using its simple name key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        Log.d(TAG, String.format("Showing tweet content '%s'", tweet.getBody()));

        //set objects
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvDetailsName.setText(tweet.getUser().name);
        tvDetailsScreenname.setText("@" + tweet.getUser().screenName);
        tvDetailsTime.setText("• " + tweet.getCreatedAt());
        tvDetailsBody.setText(tweet.getBody());
        tvDetailsRetweets.setText(tweet.getRetweetCount());
        tvDetailsFavorites.setText(tweet.getFavoriteCount());

        //set ivDetailsProfile
        String detailsProfileURL;
        String detailsMediaURL;
        detailsProfileURL = tweet.getUser().profileImageURL;
        Glide.with(this).load(detailsProfileURL).transform(new CircleCrop()).into(ivDetailsProfile);

        if (tweet.getEmbeddedMediaURL() != null){
            detailsMediaURL = tweet.getEmbeddedMediaURL();
            int imgRadius = 25; //corner radius
            ivDetailsMedia.setVisibility(View.VISIBLE);
            Glide.with(this).load(detailsMediaURL).transform(new CenterInside(),new RoundedCorners(imgRadius)).into(ivDetailsMedia);
        }
    }
}

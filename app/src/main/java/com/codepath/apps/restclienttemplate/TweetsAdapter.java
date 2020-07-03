package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.databinding.ItemTweetBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    //pass in context and list of tweets
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvName;
    TextView tvScreenName;
    TextView tvTimeAgo;
    Button btnComment;
    Button btnRetweet;
    Button btnFavorite;
    TextView tvRetweets;
    TextView tvFavorites;
    ImageView ivEmbeddedMedia;

    //for each row, inflate layout for tweet
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TweetsAdapter", "onCreateViewHolder");
        ItemTweetBinding itemTweetBinding = ItemTweetBinding.inflate(LayoutInflater.from(context));
        View tweetView = itemTweetBinding.getRoot();

        ivProfileImage = itemTweetBinding.ivProfileImage;
        tvBody = itemTweetBinding.tvBody;
        tvName = itemTweetBinding.tvName;
        tvScreenName = itemTweetBinding.tvScreenName;
        tvTimeAgo = itemTweetBinding.tvTimeAgo;
        btnComment = itemTweetBinding.btnComment;
        btnRetweet = itemTweetBinding.btnRetweet;
        btnFavorite = itemTweetBinding.btnFavorite;
        tvRetweets = itemTweetBinding.tvRetweets;
        tvFavorites = itemTweetBinding.tvFavorites;
        ivEmbeddedMedia = itemTweetBinding.ivEmbeddedMedia;
        return new ViewHolder(tweetView);
    }

    //bind values based on position of element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("TweetsAdapter", "onBindViewHolder " + position);
        //get the data at position
        Tweet tweet = tweets.get(position);
        //bind the tweet with the viewholder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items to our dataset
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        //when the user clicks on a row, show Details Activity for the selected tweet
        @Override
        public void onClick(View view) {
            //get tweet position
            int position = getAdapterPosition();
            //position must be valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION){
                //get tweet at position
                Tweet tweet = tweets.get(position);
                //create intent for new activity
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                //serialize tweet using parceler w/ short name as key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                //show the activity
                context.startActivity((intent));
            }
        }

        public void bind(Tweet tweet) {
            String mediaURl;

            tvBody.setText(tweet.body);
            tvName.setText(tweet.user.name);
            tvScreenName.setText("@" + tweet.user.screenName);
            tvTimeAgo.setText(tweet.createdAt);
            tvRetweets.setText(tweet.retweetCount);
            tvFavorites.setText(tweet.favoriteCount);
            Glide.with(context).load(tweet.user.profileImageURL).transform(new CircleCrop()).into(ivProfileImage);
            if (tweet.getEmbeddedMediaURL() != null){
                mediaURl = tweet.getEmbeddedMediaURL();
                int imgRadius = 25; //corner radius
                ivEmbeddedMedia.setVisibility(View.VISIBLE);
                Glide.with(context).load(mediaURl).transform(new CenterInside(),new RoundedCorners(imgRadius)).into(ivEmbeddedMedia);
            }

        }
    }
}
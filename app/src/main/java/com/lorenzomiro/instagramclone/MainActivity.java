package com.lorenzomiro.instagramclone;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private EditText etDescription;

    private Button btnCaptureImage;

    private ImageView ivPostImage;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etDescription = findViewById(R.id.etDescription);

        btnCaptureImage = findViewById(R.id.btnCaptureImage);

        ivPostImage = findViewById(R.id.ivPostImage);

        btnSubmit = findViewById(R.id.btnSubmit);

//        queryPosts();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description;
            }
        });

    }

    private void queryPosts() {

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.include(Post.KEY_USER);

        //get all posts

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {

                if (e != null) {

                    Log.e(TAG, "Issue with getting posts: ", e);

                    return;

                }

                for (Post post: posts) {

                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());

                }

            }
        });

    }

}

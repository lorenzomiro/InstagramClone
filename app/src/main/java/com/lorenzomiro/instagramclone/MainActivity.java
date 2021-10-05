package com.lorenzomiro.instagramclone;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 420;

    private BottomNavigationView bottom_navigation_view;

    private EditText etDescription;

    private Button btnCaptureImage;

    private ImageView ivPostImage;

    private Button btnSubmit;

    private File photoFile;

    private String photoFileName = "0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottom_navigation_view = findViewById(R.id.bottomNavigation);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_camera();
            }
        });

//        queryPosts();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = etDescription.getText().toString();

                if(description.isEmpty()) {

                    Toast.makeText(MainActivity.this, "Description can't be empty.", Toast.LENGTH_SHORT).show();

                    return;

                }

                if (photoFile == null || ivPostImage.getDrawable() == null) {

                    Toast.makeText(MainActivity.this, "No image data.", Toast.LENGTH_SHORT).show();

                    return;

                }

                ParseUser currentUser = ParseUser.getCurrentUser();

                savePost(description, currentUser, photoFile);

            }
        });

    }

    private void launch_camera() {

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            activity_launcher.launch(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File getPhotoFileUri(String photoFileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
//        File file = new File(mediaStorageDir.getPath() + File.separator + photoFileName);

        return new File(mediaStorageDir.getPath() + File.separator + photoFileName);
    }

    private void savePost(String description, ParseUser currentUser, File photoFile) {

        Post post = new Post();

        //set description, user + image

        post.setDescription(description);

        post.setImage(new ParseFile(photoFile));

        post.setUser(currentUser);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                //error check

                if (e != null) {

                    Log.e(TAG, "Error while saving: ", e);

                    Toast.makeText(MainActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();

                }

                Log.i(TAG, "Post saved successfully!");

                etDescription.setText("");

                //clear image view

                ivPostImage.setImageResource(0);

            }
        });

    }

    ActivityResultLauncher<Intent> activity_launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {

                Bitmap taken_image = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                ivPostImage.setImageBitmap(taken_image);

            } else {

                Toast.makeText(MainActivity.this, "Photo not taken", Toast.LENGTH_SHORT).show();

            }
        }
    });

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

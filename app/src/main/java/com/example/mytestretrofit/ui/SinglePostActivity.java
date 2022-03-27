package com.example.mytestretrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.db.PostViewModel;
import com.example.mytestretrofit.models.Post;

public class SinglePostActivity extends AppCompatActivity {
    TextView id, userId, title, body;
    PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        id = findViewById(R.id.tv_PostId);
        userId = findViewById(R.id.tv_PostUserId);
        title = findViewById(R.id.tv_PostTitle);
        body = findViewById(R.id.tv_PostBody);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            id.setText(arguments.get("id").toString());
            userId.setText(arguments.get("userId").toString());
            title.setText(arguments.get("title").toString());
            body.setText(arguments.get("body").toString());
        }
    }

    public void saveToDB(View view) {
        id.getText();
        Post post = new Post(Integer.parseInt((id.getText().toString())),
                Integer.parseInt(String.valueOf(userId.getText())),
                title.getText().toString(),
                body.getText().toString());
        postViewModel.insert(post);
    }
}
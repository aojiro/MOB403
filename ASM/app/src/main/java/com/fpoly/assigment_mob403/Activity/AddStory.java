package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.databinding.ActivityAddStoryBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStory extends AppCompatActivity {

    private ActivityAddStoryBinding binding;
    private EditText edName, edDescribe, edAuthor, edBackground, edPic;
    private Button btnAdd;
    private ProgressBar pgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        edName = binding.actiAddStoryEdName;
        edDescribe = binding.actiAddStoryEdDescribe;
        edAuthor = binding.actiAddStoryEdAuthor;
        edBackground = binding.actiAddStoryEdBackground;
        edPic = binding.actiAddStoryEdPic;
        btnAdd = binding.actiStoryBtnAdd;
        pgLoad = binding.actiAddStoryPgLoad;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStory();
            }
        });
    }

    private void HandleShow(boolean isShow) {
        if (isShow) {
            pgLoad.setVisibility(View.VISIBLE);
        } else {
            pgLoad.setVisibility(View.GONE);
        }
    }

    private void addStory() {
        HandleShow(true);
        String name = edName.getText().toString().trim();
        String describe = edDescribe.getText().toString().trim();
        String author = edAuthor.getText().toString().trim();
        String background = edBackground.getText().toString().trim();
        String pic = edPic.getText().toString().trim();

        if (name.isEmpty() || describe.isEmpty() || author.isEmpty() || background.isEmpty() || pic.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin truyện", Toast.LENGTH_SHORT).show();
            HandleShow(false);
            return;
        }

        long timeRelease = System.currentTimeMillis(); // Lấy thời gian hiện tại

        Story newStory = new Story();
        newStory.setName(name);
        newStory.setDescribe(describe);
        newStory.setAuthor(author);
        newStory.setBackground(background);

        // Tạo danh sách hình ảnh từ chuỗi đường dẫn
        List<String> images = new ArrayList<>();
        String[] picUrls = pic.split("\n");
        for (String imageUrl : picUrls) {
            if (!imageUrl.trim().isEmpty()) {
                images.add(imageUrl.trim());
            }
        }
        newStory.setImages(images);

        newStory.setTimeRelease((int) timeRelease);

        Call<Story> call = ContainAPI.STORY().CreateElement(newStory);
        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddStory.this, "Thêm truyện thành công", Toast.LENGTH_SHORT).show();
                    HandleShow(false);
                } else {
                    Toast.makeText(AddStory.this, "Thêm truyện thất bại", Toast.LENGTH_SHORT).show();
                    HandleShow(false);
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                Toast.makeText(AddStory.this, "Thêm truyện thất bại", Toast.LENGTH_SHORT).show();
                HandleShow(false);
            }
        });
    }
}
package com.fpoly.assigment_mob403.Activity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.assigment_mob403.Adapter.StoryContentAdapter;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Comment;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.Fragment.ReadStoryFragment;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityDetailStoryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStory extends AppCompatActivity {
    private ActivityDetailStoryBinding binding;

    private List<Comment> commentList;

    private Story story;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hiển thị các phần tử giao diện
//        HandleShow(true);

        String _id = getIntent().getStringExtra(ReadStoryFragment.KEYBUNDLE);

        // Gọi API để lấy thông tin truyện dựa trên _id
        Call<Story> call = ContainAPI.STORY().GetElement(_id);

        // Gửi yêu cầu bất đồng bộ và xử lý kết quả
        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                story = response.body();
                // Khởi tạo giao diện với thông tin truyện
//                HandleShow(true);
                Init();
                // Ẩn các phần tử giao diện

            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                // Xử lý khi gọi API thất bại
            }
        });

        // Thêm các hành động vào giao diện
        AddAction();
    }

    // Hiển thị hoặc ẩn phần tử giao diện
//    private void HandleShow(boolean isShow) {
//
//    }

    // Khởi tạo giao diện với thông tin truyện
    private void Init() {
        ValuesSave.CURRENT_ID_STORY = story.get_id();
        GeneralFunc.LoadImageFromLink(story.getBackground(), binding.actiDetailStoryImgAvatar);
        binding.actiDetailStoryTvName.setText(story.getName());
        binding.actiDetailStoryTvAuthor.setText(story.getAuthor());
        binding.actiDetailStoryTvTimeRelase.setText(GeneralFunc.ConvertToStringDate(story.getTimeRelease()));
        long timeInMillis = convertDateToMillis(GeneralFunc.ConvertToStringDate(story.getTimeRelease()));
        binding.actiDetailStoryTvTimeRelase.setText(GeneralFunc.ConvertToStringDate(timeInMillis));
        binding.actiDetailStoryTvDescribeMini.setText(story.getDescribe());
        binding.actiDetailStoryRcImages.setLayoutManager(new LinearLayoutManager(this));
        StoryContentAdapter storyContentAdapter = new StoryContentAdapter();
        storyContentAdapter.SetData(story.getImages());
        binding.actiDetailStoryRcImages.setAdapter(storyContentAdapter);
    }

    // Thêm các hành động vào giao diện
    private void AddAction() {
        binding.actiDetailStoryBtnReadStory.setOnClickListener(v -> ActionOnclickReadStory());
        binding.actiDetailStoryBtnDescribe.setOnClickListener(v -> ActionOnClickDescribe());
    }

    // Xử lý khi nhấn nút "Đọc truyện"
    private void ActionOnclickReadStory() {
        binding.actiDetailStoryRcImages.setVisibility(View.VISIBLE);
        binding.actiDetailStoryTvDescribeMini.setVisibility(View.INVISIBLE);
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnReadStory, "#D9D9D9");
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnDescribe, "#FFFFFF");
    }

    // Xử lý khi nhấn nút "Mô tả"
    private void ActionOnClickDescribe() {
        binding.actiDetailStoryTvDescribeMini.setVisibility(View.VISIBLE);
        binding.actiDetailStoryRcImages.setVisibility(View.INVISIBLE);
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnDescribe, "#D9D9D9");
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnReadStory, "#FFFFFF");
    }



    private long convertDateToMillis(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date dateObj = sdf.parse(date);
            if (dateObj != null) {
                return dateObj.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Hoặc giá trị mặc định khác nếu cần
    }
    public void showDeleteConfirmationDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xoá truyện");
        builder.setMessage("Bạn có chắc chắn muốn xoá truyện này?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Gọi hàm DeleteStory để thực hiện xoá truyện
                DeleteStory(story.get_id()); // story là biến chứa thông tin truyện
            }
        });
        builder.setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void DeleteStory(String storyId) {
        // Hiển thị loading hoặc thông báo xử lý xoá truyện (tuỳ theo nhu cầu)

        Call<Story> call = ContainAPI.STORY().DeleteElement(storyId);
        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    // Xoá truyện thành công
                    Toast.makeText(DetailStory.this, "Truyện đã được xoá", Toast.LENGTH_SHORT).show();

                    // Quay lại ReadStoryFragment
                    onBackPressed();
                } else {
                    // Xoá truyện thất bại

                    Toast.makeText(DetailStory.this, "Xoá truyện thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                Toast.makeText(DetailStory.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Thêm hàm để hiển thị giao diện chỉnh sửa thông tin truyện
    public void showEditDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa thông tin truyện");

        View editView = getLayoutInflater().inflate(R.layout.edit_story_dialog, null);
        EditText edtName = editView.findViewById(R.id.actiAddStory_edit_story_name);
        EditText edtAuthor = editView.findViewById(R.id.actiAddStory_edit_story_author);
//        EditText edtBackground = editView.findViewById(R.id.actiAddStory_edit_story_background);

        // ... Thêm các trường dữ liệu cần chỉnh sửa ...

        builder.setView(editView);

        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = edtName.getText().toString();
                String newAuthor = edtAuthor.getText().toString();
//                String newBackground = edtBackground.getText().toString();
                // ... Lấy thông tin cập nhật từ các trường khác ...

                UpdateStory(story.get_id(), newName, newAuthor);
            }
        });

        builder.setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void UpdateStory(String storyId, String newName, String newAuthor) {
        // Hiển thị loading hoặc thông báo xử lý cập nhật truyện

        // Tạo đối tượng Story mới với thông tin cập nhật
        Story updatedStory = new Story(storyId, newName, story.getDescribe(), newAuthor, story.getTimeRelease(), story.getBackground(), story.getImages(), story.getComments());

        Call<Story> call = ContainAPI.STORY().UpdateElement(storyId, updatedStory);
        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    // Cập nhật truyện thành công
                    Toast.makeText(DetailStory.this, "Thông tin truyện đã được cập nhật", Toast.LENGTH_SHORT).show();
                    story = response.body();
                    // ... Cập nhật thông tin trên giao diện nếu cần ...
                } else {
                    // Cập nhật truyện thất bại
                    Toast.makeText(DetailStory.this, "Cập nhật truyện thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                Toast.makeText(DetailStory.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ActionButtonBack(View view) {
        finish();
    }
}
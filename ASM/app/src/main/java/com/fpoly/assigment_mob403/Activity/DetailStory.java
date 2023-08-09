package com.fpoly.assigment_mob403.Activity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fpoly.assigment_mob403.Adapter.MessAdapter;
import com.fpoly.assigment_mob403.Adapter.StoryContentAdapter;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Comment;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.Fragment.ReadStoryFragment;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityDetailStoryBinding;
import com.fpoly.assigment_mob403.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStory extends AppCompatActivity {

    // Đối tượng binding để truy cập các phần tử giao diện
    private ActivityDetailStoryBinding binding;

    // Danh sách chứa các bình luận
    private List<User> commentList;

    // Adapter để hiển thị danh sách bình luận
    private MessAdapter messAdapter;

    // Đối tượng chứa thông tin câu chuyện
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Liên kết giao diện với tệp XML activity_detail_story.xml
        binding = ActivityDetailStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hiển thị thanh tiến trình tải
        HandleShow(true);

        // Lấy thông tin câu chuyện từ Intent
        String _id = getIntent().getStringExtra(ReadStoryFragment.KEYBUNDLE);

        // Gửi yêu cầu lấy thông tin câu chuyện từ API
        Call<User> call = ContainAPI.USER().GetElement(_id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // Lấy thông tin câu chuyện từ phản hồi và khởi tạo giao diện
                user = response.body();
                Init();
                // Ẩn thanh tiến trình tải
                HandleShow(false);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        // Gán các sự kiện
        AddAction();
    }

    // Phương thức xử lý hiển thị hoặc ẩn thanh tiến trình tải
    private void HandleShow(boolean isShow){
        if(isShow){
            binding.actiDetailStoryPgLoad.setVisibility(View.VISIBLE);
        } else {
            binding.actiDetailStoryPgLoad.setVisibility(View.INVISIBLE);
        }
    }

    // Phương thức khởi tạo giao diện với thông tin câu chuyện
    private void Init(){
        // Lưu ID câu chuyện hiện tại
        ValuesSave.CURRENT_ID_STORY = user.get_id();

        // Hiển thị hình ảnh nền của câu chuyện
        GeneralFunc.LoadImageFromLink(user.getAvatar(),binding.actiDetailStoryImgAvatar);

        // Hiển thị thông tin câu chuyện
        binding.actiDetailStoryTvName.setText(user.getFullName());
        binding.actiDetailStoryTvAuthor.setText(user.getEmail());
        binding.actiDetailStoryTvDescribe.setText(user.getUsername());

        // Hiển thị danh sách hình ảnh trong câu chuyện
        binding.actiDetailStoryRcImages.setLayoutManager(new LinearLayoutManager(this));
        StoryContentAdapter storyContentAdapter = new StoryContentAdapter();
        storyContentAdapter.SetData(Collections.singletonList(user.getAvatar()));
        binding.actiDetailStoryRcImages.setAdapter(storyContentAdapter);

        // Hiển thị danh sách bình luận
//        commentList = new ArrayList<>();
//        messAdapter = new MessAdapter();
//        messAdapter.SetData(commentList);
//        binding.actiDetailStoryRcMess.setLayoutManager(new LinearLayoutManager(this));
//        binding.actiDetailStoryRcMess.setAdapter(messAdapter);
    }

    // Phương thức gán các sự kiện cho các phần tử giao diện
    private void AddAction(){
        binding.actiDetaiStoryBtnBack.setOnClickListener(v -> finish());
        binding.actiDetailStoryBtnReadStory.setOnClickListener(v -> ActionOnclickReadStory());
        binding.actiDetailStoryBtnDescribe.setOnClickListener(v -> ActionOnClickDescribe());
//        binding.actiDetailStoryBtnMess.setOnClickListener(v -> ActionOnClickMess());
        binding.actiDetaiStoryBtnBackToNormal.setOnClickListener(v -> ActionOnClickBackToNormal());
//        binding.actiDetailStoryBtnSend.setOnClickListener(v -> ActionOnClickSend());
    }

    // Phương thức xử lý khi người dùng gửi bình luận
//    private void ActionOnClickSend() {
//        String text = binding.actiDetailStoryEdComment.getText().toString();
//        if(text.length() == 0) return;
//
//        // Tạo đối tượng bình luận và gửi yêu cầu tạo bình luận đến API
//        Comment comment = new Comment();
//        comment.setStoryID(ValuesSave.CURRENT_ID_STORY);
//        comment.setUserID(ValuesSave.USER.get_id());
//        comment.setContent(text);
//        Call<Comment> call = ContainAPI.COMMENT().CreateElement(comment);
//        call.enqueue(new Callback<Comment>() {
//            @Override
//            public void onResponse(Call<Comment> call, Response<Comment> response) {
//                if(response.body() == null) return;
//                binding.actiDetailStoryEdComment.setText("");
//                commentList.add(response.body());
//                messAdapter.notifyItemInserted(commentList.size() - 1);
//            }
//
//            @Override
//            public void onFailure(Call<Comment> call, Throwable t) {
//
//            }
//        });
//    }

    // Phương thức xử lý khi người dùng nhấn nút đọc câu chuyện
    private void ActionOnclickReadStory(){
        binding.actiDetailStoryRcImages.setVisibility(View.VISIBLE);
        binding.actiDetailStoryTvDescribe.setVisibility(View.INVISIBLE);
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnReadStory,"#D9D9D9");
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnDescribe,"#FFFFFF");
    }

    // Phương thức xử lý khi người dùng nhấn nút xem mô tả
    private void ActionOnClickDescribe(){
        binding.actiDetailStoryTvDescribe.setVisibility(View.VISIBLE);
        binding.actiDetailStoryRcImages.setVisibility(View.INVISIBLE);
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnDescribe,"#D9D9D9");
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnReadStory,"#FFFFFF");
    }

    // Phương thức xử lý khi người dùng nhấn nút xem bình luận
//    private void ActionOnClickMess(){
//        binding.actiDetailStoryLayoutMess.setVisibility(View.VISIBLE);
//        binding.actiDetailStoryLayoutNormal.setVisibility(View.INVISIBLE);
//        LoadMess();
//    }

    // Phương thức xử lý khi người dùng nhấn nút quay lại trạng thái bình thường
    private void ActionOnClickBackToNormal(){
        binding.actiDetailStoryLayoutMess.setVisibility(View.INVISIBLE);
        binding.actiDetailStoryLayoutNormal.setVisibility(View.VISIBLE);
    }

    // Phương thức tải danh sách bình luận từ API
//    private void LoadMess(){
//        try {
//            HandleShow(true);
//            Call<List<Comment>> call = ContainAPI.COMMENT().GetElementByStoryID(ValuesSave.CURRENT_ID_STORY);
//            call.enqueue(new Callback<List<Comment>>() {
//                @Override
//                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
//                    boolean check = false;
//
//                    if(response.body() == null){
//                        commentList = new ArrayList<>();
//                        messAdapter.SetData(commentList);
//                        return;
//                    }
//
//                    if(commentList.size() != response.body().size()) check = true;
//                    else{
//
//                    }
//
//                    if(check){
//                        commentList = response.body();
//                        messAdapter.SetData(commentList);
//                    }
//                    HandleShow(false);
//                }
//
//                @Override
//                public void onFailure(Call<List<Comment>> call, Throwable t) {
//                    HandleShow(false);
//                }
//            });
//        } catch (Exception e){
//            HandleShow(false);
//        }
//    }
}

package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityEditStoryBinding;
import com.fpoly.assigment_mob403.databinding.ActivityEditUserBinding;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditStory extends AppCompatActivity {

    private ActivityEditStoryBinding binding;
    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LoadData();
//        AddAction();
    }

    private void LoadData() {
        binding.actiEditStoryEdBackground.setText(ValuesSave.STORY.getBackground());
        binding.actiEditStoryEdName.setText(ValuesSave.STORY.getName());
        binding.actiEditStoryEdDescribe.setText(ValuesSave.STORY.getDescribe());
        binding.actiEditStoryTimeInput.setText(GeneralFunc.ConvertToStringDate(story.getTimeRelease()));
        binding.actiEditStoryEdAuthor.setText(ValuesSave.STORY.getAuthor());

    }



//    private void AddAction(){
//        binding.actiEditUserBtnBack.setOnClickListener(v -> finish());
//        binding.actiEditUserBtnEdit.setOnClickListener(v -> ActionEditButton());
//        binding.actiEditUserBtnEditPass.setOnClickListener(v -> ActionEditPassButton());
//    }
//
//
//
//    private void ActionEditPassButton() {
//
//        HandleShow(true);
//
//        try {
//            String pass = binding.actiEditUserEdPass.getText().toString().trim();
//            String newPass = binding.actiEditUserEdNewPass.getText().toString().trim();
//
//            if(pass.isEmpty() || newPass.isEmpty() ||!ValuesSave.USER.getPassword().equals(pass)){
//                Toast.makeText(this, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
//                HandleShow(false);
//                return;
//            }
//
//            User user = ValuesSave.USER;
//            user.setPassword(newPass);
//            Call<User> call = ContainAPI.USER().UpdateElement(user.get_id(),user);
//            call.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Call<User> call, Response<User> response) {
//                    ValuesSave.USER = user;
//                    binding.actiEditUserEdPass.setText("");
//                    binding.actiEditUserEdNewPass.setText("");
//                    Toast.makeText(EditUser.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                    HandleShow(false);
//                }
//
//                @Override
//                public void onFailure(Call<User> call, Throwable t) {
//                    HandleShow(false);
//                    Toast.makeText(EditUser.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }catch (Exception e){
//            HandleShow(false);
//        }
//    }
//
//

//    public static boolean isValidEmail(String email) {
//        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
//        return Pattern.matches(emailPattern, email);
//    }
//
//    private void ActionEditButton() {
//
//        try {
//            HandleShow(true);
//            String fullName = binding.actiEditUserEdFullName.getText().toString().trim();
//            String email = binding.actiEditUserEdEmail.getText().toString().trim();
//            String avatar = binding.actiEditUserEdAvatar.getText().toString().trim();
//            String userName = binding.actiEditUserEdUserName.getText().toString().trim();
//
//            if(avatar.isEmpty() || email.isEmpty() || fullName.isEmpty() || userName.isEmpty()) {
//                Toast.makeText(this, "Thông tin thiếu !", Toast.LENGTH_SHORT).show();
//                HandleShow(false);
//                return;
//            }
//
//            if(!isValidEmail(email)){
//                Toast.makeText(this, "Email sai định dạng !", Toast.LENGTH_SHORT).show();
//                HandleShow(false);
//                return;
//            }
//
//            User user = ValuesSave.USER;
//            user.setAvatar(avatar);
//            user.setEmail(email);
//            user.setFullName(fullName);
//            user.setUsername(userName);
//
//            Call<User> call = ContainAPI.USER().UpdateElement(user.get_id(),user);
//            call.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Call<User> call, Response<User> response) {
//                    ValuesSave.USER = user;
//                    Toast.makeText(EditUser.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
//                    HandleShow(false);
//                }
//
//                @Override
//                public void onFailure(Call<User> call, Throwable t) {
//                    Toast.makeText(EditUser.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
//                    HandleShow(false);
//                }
//            });
//
//        }catch (Exception e){
//            HandleShow(false);
//        }
//
//
//    }
//}
}
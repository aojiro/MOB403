package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.databinding.ActivityAddStoryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStory extends AppCompatActivity {

    private ActivityAddStoryBinding binding;
    private EditText edName, edDescribe, edAuthor, edBackground, edPic, edDate;
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
        edDate = binding.actiAddStoryTimeInput;
        pgLoad = binding.actiAddStoryPgLoad;

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        binding.actiStoryBtnAdd.setOnClickListener(new View.OnClickListener() {
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
        String date = edDate.getText().toString().trim();

        if (name.isEmpty() || describe.isEmpty() || author.isEmpty() || background.isEmpty() || pic.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin truyện", Toast.LENGTH_SHORT).show();
            HandleShow(false);
            return;
        }

        // Chuyển đổi ngày từ chuỗi sang giá trị thời gian dạng long
        long timeRelease = convertDateToMillis(date);

        Story newStory = new Story();
        newStory.setName(name);
        newStory.setDescribe(describe);
        newStory.setAuthor(author);
        newStory.setBackground(background);

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

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddStory.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format(Locale.getDefault(),
                                "%02d/%02d/%d",
                                dayOfMonth, monthOfYear + 1, year);
                        edDate.setText(selectedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
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
}
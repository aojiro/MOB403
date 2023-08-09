package com.fpoly.assigment_mob403.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fpoly.assigment_mob403.Activity.DetailStory;
import com.fpoly.assigment_mob403.Adapter.StoryAdapter;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.databinding.FragmentReadStoryBinding;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadStoryFragment extends Fragment implements StoryAdapter.EventItemStory {

    // Hằng số sử dụng để truyền dữ liệu giữa các thành phần
    public static final String KEYBUNDLE = "STORY";

    // Danh sách chứa toàn bộ câu chuyện
    private List<User> storyList;

    // Danh sách chứa câu chuyện được hiển thị hiện tại
    private List<User> curStoryList;

    // Adapter dùng để hiển thị danh sách câu chuyện
    private StoryAdapter itemStoryAdapter;

    // Đối tượng Handler để quản lý tác vụ xử lý và giao diện
    private Handler handler;

    // TAG dùng để đánh dấu cho mục đích gỡ lỗi
    private String TAG ="TAGReadStory";

    // Đối tượng binding để truy cập các phần tử giao diện
    private FragmentReadStoryBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Liên kết giao diện với tệp XML fragment_read_story.xml
        binding = FragmentReadStoryBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    // Phương thức xử lý hiển thị hoặc ẩn phần giao diện
    private void HandleShow(boolean isShow){
        if(isShow){
            // Hiển thị thanh tiến trình tải
            binding.fragReadStoryPgLoad.setVisibility(View.VISIBLE);
        } else {
            // Ẩn thanh tiến trình tải
            binding.fragReadStoryPgLoad.setVisibility(View.INVISIBLE);
        }
    }

    // Phương thức tạo mới một đối tượng ReadStoryFragment
    public static ReadStoryFragment newInstance() {
        ReadStoryFragment fragment = new ReadStoryFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Xử lý hiển thị danh sách câu chuyện và gán sự kiện
        HandleRecycleStory();
        AddAction();
    }

    // Phương thức gán sự kiện khi người dùng thay đổi nội dung tìm kiếm
    private void AddAction(){
        binding.fragReadStoryEdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Lấy nội dung tìm kiếm và chuyển về chữ thường
                String namePath = charSequence.toString().toLowerCase();
                Log.d(TAG, "namePath: " + namePath + "/ length " + namePath.length());

                // Kiểm tra nếu không có nội dung tìm kiếm, hiển thị toàn bộ danh sách câu chuyện
                if(namePath.length() == 0){
                    curStoryList = storyList;
                    itemStoryAdapter.SetData(curStoryList);
                    return;
                }

                // Tạo danh sách tạm để lưu các câu chuyện thỏa mãn điều kiện tìm kiếm
                List<User> users = new ArrayList<>();

                for(User st : storyList){
                    String name = st.getFullName().toLowerCase();
                    if(name.contains(namePath)){
                        users.add(st);
                    } else {
                        // Nếu không thỏa mãn điều kiện chính, thử so sánh không dấu
                        String normalizedText = removeDiacritics(name);
                        String normalizedZ = removeDiacritics(namePath);

                        if(normalizedText.contains(normalizedZ)){
                            users.add(st);
                        }
                    }
                }
                // Cập nhật danh sách hiển thị
                curStoryList = users;
                itemStoryAdapter.SetData(curStoryList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // Phương thức xử lý hiển thị danh sách câu chuyện
    private void HandleRecycleStory(){
        // Khởi tạo danh sách câu chuyện và Adapter
        curStoryList = new ArrayList<>();
        storyList = new ArrayList<>();
        itemStoryAdapter = new StoryAdapter(this);
        itemStoryAdapter.SetData(curStoryList);

        // Thiết lập layout cho RecyclerView và gán Adapter
        binding.fragReadStoryRcStory.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.fragReadStoryRcStory.setAdapter(itemStoryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Hiển thị thanh tiến trình tải và khởi tạo việc tải danh sách câu chuyện
        HandleShow(true);
        handler = new Handler();
        LoadList();
    }

    // Phương thức tải danh sách câu chuyện từ nguồn dữ liệu
    private void LoadList(){

        try {
            HandleShow(true);

            // Gửi yêu cầu tải danh sách câu chuyện từ API
            Call<List<User>> call = ContainAPI.USER().GetAll();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    // Lấy danh sách câu chuyện từ phản hồi và cập nhật danh sách hiển thị
                    storyList = response.body();
                    curStoryList = storyList;
                    itemStoryAdapter.SetData(curStoryList);

                    // Ẩn thanh tiến trình tải
                    HandleShow(false);
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    // Xử lý khi tải không thành công và hiển thị thông báo
                    HandleShow(false);
                    Toast.makeText(getActivity(), "Load không thành công !", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            HandleShow(false);
        }
    }

    @Override
    public void OnClickItem(String _id) {
        // Xử lý khi người dùng nhấn vào một câu chuyện, chuyển hướng đến màn hình chi tiết câu chuyện
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), DetailStory.class);
        intent.putExtra(KEYBUNDLE, _id);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Loại bỏ các callback hoặc tin nhắn chờ xử lý khi Fragment bị tạm dừng
        handler.removeCallbacksAndMessages(null);
    }

    // Phương thức loại bỏ dấu tiếng Việt từ chuỗi
    private static String removeDiacritics(String input) {
        String nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
    }
}

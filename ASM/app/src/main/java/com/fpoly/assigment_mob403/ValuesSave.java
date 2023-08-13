package com.fpoly.assigment_mob403;

import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.DTO.User;

public class ValuesSave {
    // Biến lưu trữ ID câu chuyện hiện tại
    public static String CURRENT_ID_STORY = "";

    // Đối tượng người dùng
    public static User USER = new User();

    // Đối tượng câu chuyện
    public static Story STORY = new Story();

    // Cờ đánh dấu lần mở ứng dụng đầu tiên
    public static boolean FirtOpen = true;
}

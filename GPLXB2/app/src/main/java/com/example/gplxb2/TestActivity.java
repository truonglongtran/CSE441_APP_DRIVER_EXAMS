package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestActivity extends AppCompatActivity {

    private TextView timerTextView, questionCounterTextView;
    private Button submitButton;
    private ImageButton  nextButton, backButton;
    private RecyclerView questionRecyclerView;
    private LinearLayoutManager layoutManager;
    private CountDownTimer countDownTimer;
    private int currentPosition = 0;
    private int totalQuestions = 0;
    private List<Question> questions; // List of questions
    private List<String> criticals; // List of critical questions
    private QuestionAdapter adapter; // Adapter for RecyclerView
    private ArrayList<Integer> incorrectAnswers;
    private DatabaseHelper databaseHelper;
    private String title = ""; // Biến để lưu tiêu đề

    // Exam index
    private int examIndex = -1; // Mặc định -1 để random

    // Array to hold predefined question sets for different exams
    int[][] arrays = {
            { 10011, 10021, 10029, 10031, 10044, 10046, 10094, 10099, 10100, 10102, 10117, 10132, 10134, 10223, 10241, 10267, 10298, 10324, 10356, 10361, 10362, 10373, 10375, 10382, 10385, 10400, 10401, 10410, 10424, 10435, 10441, 10450, 10482, 10532, 10535 },
            { 10038, 10045, 10065, 10091, 10116, 10130, 10138, 10189, 10203, 10205, 10230, 10234, 10235, 10237, 10259, 10263, 10295, 10302, 10306, 10312, 10316, 10340, 10341, 10348, 10378, 10379, 10392, 10507, 10535, 10536, 10577, 10580, 10589, 10591, 10595 },
            { 10007, 10049, 10061, 10067, 10074, 10091, 10099, 10145, 10153, 10156, 10183, 10201, 10212, 10229, 10277, 10288, 10305, 10337, 10340, 10381, 10388, 10391, 10398, 10426, 10433, 10444, 10446, 10454, 10463, 10469, 10508, 10548, 10559, 10572, 10574 },
            { 10026, 10033, 10047, 10049, 10068, 10082, 10117, 10127, 10138, 10176, 10186, 10201, 10212, 10217, 10219, 10227, 10231, 10237, 10262, 10264, 10272, 10285, 10308, 10351, 10358, 10391, 10398, 10415, 10435, 10446, 10452, 10460, 10486, 10522, 10550 },
            { 10010, 10017, 10085, 10115, 10119, 10141, 10164, 10178, 10191, 10212, 10217, 10234, 10255, 10262, 10268, 10271, 10285, 10301, 10325, 10337, 10349, 10366, 10410, 10427, 10475, 10505, 10511, 10516, 10532, 10534, 10538, 10562, 10563, 10567, 10575 },
            { 10033, 10054, 10065, 10103, 10128, 10145, 10155, 10169, 10205, 10209, 10251, 10297, 10333, 10341, 10342, 10344, 10358, 10362, 10400, 10404, 10412, 10426, 10427, 10431, 10441, 10446, 10451, 10477, 10478, 10487, 10522, 10547, 10588, 10596, 10597 },
            { 10009, 10010, 10024, 10055, 10060, 10072, 10075, 10077, 10135, 10174, 10178, 10181, 10190, 10202, 10212, 10220, 10246, 10257, 10261, 10287, 10323, 10395, 10396, 10400, 10408, 10418, 10466, 10474, 10501, 10507, 10508, 10539, 10574, 10582, 10586 },
            { 10035, 10037, 10053, 10056, 10061, 10071, 10074, 10096, 10143, 10152, 10180, 10189, 10201, 10274, 10283, 10291, 10316, 10326, 10329, 10336, 10342, 10347, 10360, 10371, 10381, 10388, 10399, 10452, 10462, 10463, 10470, 10512, 10516, 10522, 10590 },
            { 10007, 10016, 10029, 10043, 10053, 10054, 10068, 10080, 10090, 10138, 10173, 10179, 10198, 10199, 10221, 10241, 10250, 10252, 10265, 10286, 10308, 10309, 10358, 10377, 10380, 10416, 10423, 10435, 10470, 10490, 10492, 10504, 10509, 10569, 10570 },
            { 10034, 10052, 10071, 10076, 10079, 10083, 10127, 10158, 10163, 10171, 10178, 10208, 10254, 10258, 10265, 10271, 10279, 10290, 10351, 10357, 10403, 10416, 10421, 10436, 10465, 10500, 10501, 10531, 10543, 10544, 10546, 10563, 10574, 10579, 10592 },
            { 10027, 10082, 10132, 10140, 10143, 10152, 10160, 10167, 10176, 10180, 10208, 10238, 10267, 10271, 10288, 10313, 10322, 10336, 10354, 10371, 10376, 10387, 10389, 10397, 10428, 10456, 10459, 10465, 10478, 10501, 10547, 10563, 10569, 10585, 10590 },
            { 10030, 10048, 10075, 10076, 10078, 10094, 10105, 10157, 10171, 10184, 10227, 10234, 10235, 10239, 10250, 10264, 10269, 10306, 10307, 10312, 10314, 10317, 10344, 10347, 10373, 10391, 10419, 10492, 10500, 10522, 10531, 10541, 10548, 10575, 10594 },
            { 10011, 10023, 10030, 10109, 10133, 10147, 10149, 10154, 10228, 10234, 10239, 10243, 10275, 10282, 10284, 10285, 10299, 10353, 10386, 10394, 10396, 10402, 10438, 10456, 10469, 10472, 10490, 10502, 10545, 10554, 10555, 10563, 10569, 10572, 10575 },
            { 10025, 10033, 10060, 10067, 10070, 10089, 10101, 10129, 10132, 10147, 10155, 10180, 10197, 10203, 10226, 10240, 10245, 10266, 10290, 10298, 10300, 10324, 10333, 10347, 10377, 10379, 10382, 10387, 10398, 10419, 10460, 10475, 10496, 10501, 10571 },
            { 10013, 10028, 10045, 10094, 10113, 10144, 10155, 10175, 10187, 10208, 10231, 10266, 10293, 10316, 10322, 10325, 10346, 10350, 10376, 10385, 10388, 10398, 10410, 10416, 10426, 10433, 10437, 10486, 10500, 10507, 10517, 10528, 10535, 10547, 10574 },
            { 10023, 10037, 10042, 10054, 10073, 10108, 10130, 10133, 10134, 10146, 10166, 10195, 10205, 10220, 10223, 10231, 10235, 10265, 10311, 10321, 10340, 10342, 10377, 10389, 10399, 10416, 10452, 10461, 10519, 10526, 10533, 10571, 10578, 10598, 10599 },
            { 10002, 10020, 10031, 10048, 10059, 10084, 10094, 10105, 10134, 10138, 10147, 10210, 10217, 10222, 10263, 10315, 10317, 10324, 10344, 10348, 10353, 10360, 10381, 10383, 10388, 10413, 10445, 10457, 10458, 10460, 10463, 10475, 10487, 10547, 10588 },
            { 10001, 10020, 10030, 10053, 10094, 10117, 10122, 10174, 10187, 10204, 10213, 10235, 10244, 10245, 10250, 10253, 10259, 10276, 10278, 10295, 10302, 10317, 10318, 10364, 10386, 10390, 10403, 10468, 10472, 10483, 10531, 10546, 10574, 10586, 10595 }
    };

    private int[] khainiemQuytac ={10001, 10002, 10003, 10004, 10005, 10006, 10007, 10008, 10009, 10010, 10011, 10012, 10013, 10014, 10015, 10016, 10017, 10018, 10019, 10020, 10021, 10022, 10023, 10024, 10025, 10026, 10027, 10028, 10029, 10030, 10031, 10032, 10033, 10034, 10035, 10036, 10037, 10038, 10039, 10040, 10041, 10042, 10043, 10044, 10045, 10046, 10047, 10048, 10049, 10050, 10051, 10052, 10053, 10054, 10055, 10056, 10057, 10058, 10059, 10060, 10061, 10062, 10063, 10064, 10065, 10066, 10067, 10068, 10069, 10070, 10071, 10072, 10073, 10074, 10075, 10076, 10077, 10078, 10079, 10080, 10081, 10082, 10083, 10084, 10085, 10086, 10087, 10088, 10089, 10090, 10091, 10092, 10093, 10094, 10095, 10096, 10097, 10098, 10099, 10100, 10101, 10102, 10103, 10104, 10105, 10106, 10107, 10108, 10109, 10110, 10111, 10112, 10113, 10114, 10115, 10116, 10117, 10118, 10119, 10120, 10121, 10122, 10123, 10124, 10125, 10126, 10127, 10128, 10129, 10130, 10131, 10132, 10133, 10134, 10135, 10136, 10137, 10138, 10139, 10140, 10141, 10142, 10143, 10144, 10145, 10146, 10147, 10148, 10149, 10150, 10151, 10152, 10153, 10154, 10155, 10156, 10157, 10158, 10159, 10160, 10161, 10162, 10163, 10164, 10165, 10166};
    private int[] nghiepvuVantai = {10167, 10168, 10169, 10170, 10171, 10172, 10173, 10174, 10175, 10176, 10177, 10178, 10179, 10180, 10181, 10182, 10183, 10184, 10185, 10186, 10187, 10188, 10189, 10190, 10191, 10192};
    private int[] vanhoaGiaothong = {10193, 10194, 10195, 10196, 10197, 10198, 10199, 10200, 10201, 10202, 10203, 10204, 10205, 10206, 10207, 10208, 10209, 10210, 10211, 10212, 10213};
    private int[] kithuatLaixe = {10214, 10215, 10216, 10217, 10218, 10219, 10220, 10221, 10222, 10223, 10224, 10225, 10226, 10227, 10228, 10229, 10230, 10231, 10232, 10233, 10234, 10235, 10236, 10237, 10238, 10239, 10240, 10241, 10242, 10243, 10244, 10245, 10246, 10247, 10248, 10249, 10250, 10251, 10252, 10253, 10254, 10255, 10256, 10257, 10258, 10259, 10260, 10261, 10262, 10263, 10264, 10265, 10266, 10267, 10268, 10269};
    private int[] cautaoSuachua = {10270, 10271, 10272, 10273, 10274, 10275, 10276, 10277, 10278, 10279, 10280, 10281, 10282, 10283, 10284, 10285, 10286, 10287, 10288, 10289, 10290, 10291, 10292, 10293, 10294, 10295, 10296, 10297, 10298, 10299, 10300, 10301, 10302, 10303, 10304};
    private int[] sahinh = {10305, 10306, 10307, 10308, 10309, 10310, 10311, 10312, 10313, 10314, 10315, 10316, 10317, 10318, 10319, 10320, 10321, 10322, 10323, 10324, 10325, 10326, 10327, 10328, 10329, 10330, 10331, 10332, 10333, 10334, 10335, 10336, 10337, 10338, 10339, 10340, 10341, 10342, 10343, 10344, 10345, 10346, 10347, 10348, 10349, 10350, 10351, 10352, 10353, 10354, 10355, 10356, 10357, 10358, 10359, 10360, 10361, 10362, 10363, 10364, 10365, 10366, 10367, 10368, 10369, 10370, 10371, 10372, 10373, 10374, 10375, 10376, 10377, 10378, 10379, 10380, 10381, 10382, 10383, 10384, 10385, 10386, 10387, 10388, 10389, 10390, 10391, 10392, 10393, 10394, 10395, 10396, 10397, 10398, 10399, 10400, 10401, 10402, 10403, 10404, 10405, 10406, 10407, 10408, 10409, 10410, 10411, 10412, 10413, 10414, 10415, 10416, 10417, 10418, 10419, 10420, 10421, 10422, 10423, 10424, 10425, 10426, 10427, 10428, 10429, 10430, 10431, 10432, 10433, 10434, 10435, 10436, 10437, 10438, 10439, 10440, 10441, 10442, 10443, 10444, 10445, 10446, 10447, 10448, 10449, 10450, 10451, 10452, 10453, 10454, 10455, 10456, 10457, 10458, 10459, 10460, 10461, 10462, 10463, 10464, 10465, 10466, 10467, 10468, 10469, 10470, 10471, 10472, 10473, 10474, 10475, 10476, 10477, 10478, 10479, 10480, 10481, 10482, 10483, 10484, 10485, 10486};
    private int[] bienbao = {10305, 10306, 10307, 10308, 10309, 10310, 10311, 10312, 10313, 10314, 10315, 10316, 10317, 10318, 10319, 10320, 10321, 10322, 10323, 10324, 10325, 10326, 10327, 10328, 10329, 10330, 10331, 10332, 10333, 10334, 10335, 10336, 10337, 10338, 10339, 10340, 10341, 10342, 10343, 10344, 10345, 10346, 10347, 10348, 10349, 10350, 10351, 10352, 10353, 10354, 10355, 10356, 10357, 10358, 10359, 10360, 10361, 10362, 10363, 10364, 10365, 10366, 10367, 10368, 10369, 10370, 10371, 10372, 10373, 10374, 10375, 10376, 10377, 10378, 10379, 10380, 10381, 10382, 10383, 10384, 10385, 10386, 10387, 10388, 10389, 10390, 10391, 10392, 10393, 10394, 10395, 10396, 10397, 10398, 10399, 10400, 10401, 10402, 10403, 10404, 10405, 10406, 10407, 10408, 10409, 10410, 10411, 10412, 10413, 10414, 10415, 10416, 10417, 10418, 10419, 10420, 10421, 10422, 10423, 10424, 10425, 10426, 10427, 10428, 10429, 10430, 10431, 10432, 10433, 10434, 10435, 10436, 10437, 10438, 10439, 10440, 10441, 10442, 10443, 10444, 10445, 10446, 10447, 10448, 10449, 10450, 10451, 10452, 10453, 10454, 10455, 10456, 10457, 10458, 10459, 10460, 10461, 10462, 10463, 10464, 10465, 10466, 10467, 10468, 10469, 10470, 10471, 10472, 10473, 10474, 10475, 10476, 10477, 10478, 10479, 10480, 10481, 10482, 10483, 10484, 10485, 10486};

    private int[] arraystop50 = {10001, 10004, 10011, 10016, 10022, 10029, 10038, 10052, 10065, 10090,
            10149, 10154, 10162, 10166, 10171, 10176, 10179, 10182, 10205, 10214,
            10232, 10236, 10241, 10251, 10262, 10265, 10275, 10292, 10307, 10313,
            10339, 10343, 10375, 10380, 10397, 10402, 10403, 10411, 10419, 10438,
            10442, 10450, 10457, 10508, 10533, 10550, 10551, 10569, 10583, 10592};




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        databaseHelper = new DatabaseHelper(this);
        incorrectAnswers = databaseHelper.getIncorrectAnswers();
        // Initialize views
        timerTextView = findViewById(R.id.timer);
        questionCounterTextView = findViewById(R.id.question_counter);
        submitButton = findViewById(R.id.submit_btn);
        nextButton = findViewById(R.id.next_btn);
        backButton = findViewById(R.id.back_btn);
        questionRecyclerView = findViewById(R.id.question_list);

        // Setup RecyclerView with horizontal scrolling
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        questionRecyclerView.setLayoutManager(layoutManager);

        // Get the examIndex from intent
        examIndex = getIntent().getIntExtra("examsIndex", -1);
        Log.d("TestActivity", "examsIndex: " + examIndex);



        if (examIndex > 1000) {
            if (examIndex == 2001) {
                questions = loadCriticalQuestions();
                title = "Các câu Điểm liệt"; // Gán tiêu đề cho câu hỏi quan trọng
            } else {
                questions = loadQuestionsByExamIndex(examIndex);
                switch (examIndex) {
                    case 2002:
                        title = "Khái niệm quy tắc";
                        break;
                    case 2003:
                        title = "Nghiệp vụ vận tải";
                        break;
                    case 2004:
                        title = "Văn hóa giao thông";
                        break;
                    case 2005:
                        title = "Kỹ thuật lái xe";
                        break;
                    case 2006:
                        title = "Cấu tạo sửa chữa";
                        break;
                    case 2007:
                        title = "Biển báo";
                        break;
                    case 2008:
                        title = "Sa hình";
                        break;
                    default:
                        title = "Đề thi " + examIndex; // Đặt tiêu đề mặc định
                        break;
                }
            }
        } else if (examIndex == 60) {
            questions = loadCriticalQuestions(); // Load only critical questions
            title = "Các câu Điểm liệt"; // Tiêu đề cho câu hỏi quan trọng
        } else if (examIndex == 40) {
            questions = loadIncorrectQuestions(); // Load only top 50 questions
            title = "Các câu bị sai"; // Tiêu đề cho câu bị sai
        } else if (examIndex == 50) {
            questions = loadTop50(); // Load only top 50 questions
            title = "Top các câu hay sai"; // Tiêu đề cho top câu hỏi
        } else if (examIndex != -1) {
            questions = loadQuestionsFromArray(arrays[examIndex]); // Load specific set of questions
            int realExamIndex = examIndex + 1;
            title = "Đề thi " + realExamIndex ; // Tiêu đề cho đề thi cụ thể
        } else {
            questions = loadQuestionsFromJson(); // Load random questions
            title = "Đề ngẫu nhiên"; // Tiêu đề cho đề ngẫu nhiên
        }

        criticals = loadCriticalQuestionsFromJson();
        totalQuestions = questions.size();

        // Setup adapter and pass the callback for answer selection
        adapter = new QuestionAdapter(questions, criticals); // Pass criticals to adapter
        questionRecyclerView.setAdapter(adapter);

        // Update question counter
        updateQuestionCounter();

        // Start timer
        startTimer();

        // Set Next button click listener
        nextButton.setOnClickListener(v -> {
            if (currentPosition < totalQuestions - 1) {
                currentPosition++;
                questionRecyclerView.smoothScrollToPosition(currentPosition);
                updateQuestionCounter();
            }
        });

        // Set Back button click listener
        backButton.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                questionRecyclerView.smoothScrollToPosition(currentPosition);
                updateQuestionCounter();
            }
        });

        // Set Submit button click listener
        submitButton.setOnClickListener(v -> submitAnswers(title)); // Gửi tiêu đề khi nộp bài
    }

    // Update question counter
    private void updateQuestionCounter() {
        questionCounterTextView.setText((currentPosition + 1) + "/" + totalQuestions);
    }

    // Start the timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(10 * 60 * 1000, 1000) { // 10 minutes
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;
                String timeLeft = String.format("%02d:%02d", minutes, seconds);
                timerTextView.setText("Time: " + timeLeft);
            }

            @Override
            public void onFinish() {
                submitAnswers(title);
            }
        }.start();
    }
//    @Override
//    public void onBackPressed() {
//        // Gọi hàm submit khi nhấn nút quay lại
//        submitAnswers();
//    }
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        // Gọi hàm submit khi người dùng rời khỏi Activity
        submitAnswers(title);
    }

    // Gửi các câu trả lời và tính điểm
    private void submitAnswers(String title) {
        int score = 0; // Khởi tạo điểm số
        int incorrectCriticalCount = 0; // Số lượng câu trả lời sai cho các câu hỏi quan trọng
        Map<String, String> selectedAnswers = adapter.getSelectedAnswers(); // Lấy các câu trả lời đã chọn từ adapter

        // Lấy danh sách các câu hỏi từ cơ sở dữ liệu
        ArrayList<Integer> incorrectAnswersFromDB = databaseHelper.getIncorrectAnswers();

        // So sánh câu trả lời của người dùng với các câu trả lời đúng
        for (Question question : questions) {
            String userAnswer = selectedAnswers.get(question.getId()); // Lấy câu trả lời của người dùng

            // Kiểm tra nếu câu hỏi là câu hỏi quan trọng
            if (criticals.contains(question.getId())) {
                // Nếu câu trả lời sai hoặc không được chọn
                if (userAnswer == null || !userAnswer.equals(question.getAnswer())) {
                    incorrectCriticalCount++; // Tăng số lượng câu hỏi quan trọng trả lời sai
                }
            }

            // Kiểm tra nếu câu trả lời sai
            if (userAnswer == null || !userAnswer.equals(question.getAnswer())) {
                // Chuyển đổi ID câu hỏi sang Integer
                Integer questionId = Integer.parseInt(question.getId());

                // Kiểm tra nếu ID chưa có trong danh sách
                if (!incorrectAnswers.contains(questionId)) {
                    incorrectAnswers.add(questionId); // Thêm ID câu hỏi sai vào danh sách
                    databaseHelper.insertIncorrectAnswer(questionId); // Thêm vào cơ sở dữ liệu
                }
            } else {
                // Nếu câu trả lời đúng, kiểm tra và xóa ID nếu có trong danh sách
                Integer questionId = Integer.parseInt(question.getId());

                if (incorrectAnswers.contains(questionId)) {
                    incorrectAnswers.remove(questionId); // Xóa ID câu hỏi đúng khỏi danh sách nếu có
                    databaseHelper.deleteCorrectAnswer(questionId); // Xóa khỏi cơ sở dữ liệu
                }
                // Tăng điểm cho câu trả lời đúng
                score++; // Tăng điểm cho câu trả lời đúng
            }
        }

        // Sắp xếp danh sách các câu trả lời sai theo thứ tự tăng dần
        Collections.sort(incorrectAnswers); // Sắp xếp danh sách

        // Log ra các ID câu trả lời sai
        Log.d("TestActivity", "Các câu trả lời sai: " + incorrectAnswers);

        // Prepare to start ResultActivity
        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra("TITLE", title); // Gửi tiêu đề đề thi
        intent.putExtra("SCORE", score); // Truyền điểm số đến ResultActivity
        intent.putExtra("TOTAL_QUESTIONS", questions.size()); // Truyền tổng số câu hỏi
        intent.putExtra("INCORRECT_CRITICAL_COUNT", incorrectCriticalCount); // Truyền số lượng câu hỏi quan trọng sai
        startActivity(intent); // Chuyển đến ResultActivity
        finish(); // Kết thúc activity này
    }


    private List<Question> loadQuestionsByExamIndex(int examIndex) {
        List<Question> questionList = new ArrayList<>();
        List<Integer> questionIdList;

        // Xác định mảng câu hỏi dựa trên examIndex
        Log.d("TestAcitivity", "Giá trị của examIndex: " + examIndex);
        switch (examIndex) {
            case 2002:
                questionIdList = Arrays.stream(khainiemQuytac).boxed().collect(Collectors.toList());
                break;
            case 2003:
                questionIdList = Arrays.stream(nghiepvuVantai).boxed().collect(Collectors.toList());
                break;
            case 2004:
                questionIdList = Arrays.stream(vanhoaGiaothong).boxed().collect(Collectors.toList());
                break;
            case 2005:
                questionIdList = Arrays.stream(kithuatLaixe).boxed().collect(Collectors.toList());
                break;
            case 2006:
                questionIdList = Arrays.stream(cautaoSuachua).boxed().collect(Collectors.toList());
                break;
            case 2007:
                questionIdList = Arrays.stream(bienbao).boxed().collect(Collectors.toList());
                break;
            case 2008:
                questionIdList = Arrays.stream(sahinh).boxed().collect(Collectors.toList());
                break;
            default:
                // Trường hợp không hợp lệ
                return questionList;
        }

        try {
            InputStream is = getAssets().open("driver_data.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray questionArray = jsonObject.getJSONArray("question");

            // Phân tích câu hỏi từ JSON và kiểm tra ID trong questionIdList
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObj = questionArray.getJSONObject(i);
                String id = questionObj.getString("id");

                // Chuyển ID từ String sang Integer để so sánh
                int questionId = Integer.parseInt(id);

                // Kiểm tra nếu ID câu hỏi có nằm trong questionIdList
                if (questionIdList.contains(questionId)) {
                    String questionText = questionObj.getString("question");

                    JSONObject optionsJson = questionObj.getJSONObject("option");
                    Options options = new Options(
                            optionsJson.getString("a"),
                            optionsJson.getString("b"),
                            optionsJson.getString("c"),
                            optionsJson.optString("d", null),
                            optionsJson.optString("e", null)
                    );

                    String answer = questionObj.getString("answer");
                    String suggest = questionObj.optString("suggest", "");

                    // Load image if available
                    JSONObject imageJson = questionObj.optJSONObject("image");
                    Question.Image image = null;
                    if (imageJson != null) {
                        String img1 = imageJson.getString("img1");
                        image = new Question.Image(img1);
                    }

                    // Tạo một đối tượng Question mới và thêm vào danh sách
                    Question question = new Question(id, questionText, options, answer, suggest, image);
                    questionList.add(question);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionList;
    }

    private List<Question> loadIncorrectQuestions() {
        List<Question> incorrectQuestionList = new ArrayList<>();
        try {
            InputStream is = getAssets().open("driver_data.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray questionArray = jsonObject.getJSONArray("question");

            // Log để xem giá trị của incorrectAnswers
            Log.d("IncorrectAnswers", "List of incorrect answers: " + incorrectAnswers.toString());

            // Lọc câu hỏi dựa trên ID trong incorrectAnswers
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObj = questionArray.getJSONObject(i);
                String id = questionObj.getString("id");

                // Kiểm tra nếu ID nằm trong mảng incorrectAnswers
                if (incorrectAnswers.contains(Integer.parseInt(id))) {
                    String questionText = questionObj.getString("question");

                    JSONObject optionsJson = questionObj.getJSONObject("option");
                    Options options = new Options(
                            optionsJson.getString("a"),
                            optionsJson.getString("b"),
                            optionsJson.getString("c"),
                            optionsJson.optString("d", null),
                            optionsJson.optString("e", null)
                    );

                    String answer = questionObj.getString("answer");
                    String suggest = questionObj.optString("suggest", "");

                    // Load image if available
                    JSONObject imageJson = questionObj.optJSONObject("image");
                    Question.Image image = null;
                    if (imageJson != null) {
                        String img1 = imageJson.getString("img1");
                        image = new Question.Image(img1);
                    }

                    // Tạo đối tượng Question và thêm vào danh sách
                    Question question = new Question(id, questionText, options, answer, suggest, image);
                    incorrectQuestionList.add(question);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return incorrectQuestionList;
    }



    // Load critical questions based on IDs from the criticals list
    private List<Question> loadCriticalQuestions() {
        List<Question> criticalQuestionList = new ArrayList<>();
        criticals = loadCriticalQuestionsFromJson(); // Load critical question IDs

        try {
            InputStream is = getAssets().open("driver_data.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray questionArray = jsonObject.getJSONArray("question");

            // Parse questions from JSON and match with critical IDs
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObj = questionArray.getJSONObject(i);
                String id = questionObj.getString("id");

                // Check if the current question ID is in the criticals list
                if (criticals.contains(id)) {
                    String questionText = questionObj.getString("question");

                    JSONObject optionsJson = questionObj.getJSONObject("option");
                    Options options = new Options(
                            optionsJson.getString("a"),
                            optionsJson.getString("b"),
                            optionsJson.getString("c"),
                            optionsJson.optString("d", null),
                            optionsJson.optString("e", null)
                    );

                    String answer = questionObj.getString("answer");
                    String suggest = questionObj.optString("suggest", "");

                    // Load image if available
                    JSONObject imageJson = questionObj.optJSONObject("image");
                    Question.Image image = null;
                    if (imageJson != null) {
                        String img1 = imageJson.getString("img1");
                        image = new Question.Image(img1);
                    }

                    // Create a new Question object and add it to the list
                    Question question = new Question(id, questionText, options, answer, suggest, image);
                    criticalQuestionList.add(question);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return criticalQuestionList;
    }
    private List<Question> loadTop50() {
        List<Question> top50QuestionList = new ArrayList<>();
        // Chuyển arraystop50 thành List<Integer>
        List<Integer> arrayStop50List = Arrays.stream(arraystop50).boxed().collect(Collectors.toList());

        try {
            InputStream is = getAssets().open("driver_data.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray questionArray = jsonObject.getJSONArray("question");

            // Parse questions from JSON and match with IDs in arrayStop50
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObj = questionArray.getJSONObject(i);
                String id = questionObj.getString("id");

                // Chuyển ID từ String sang Integer để so sánh
                int questionId = Integer.parseInt(id);

                // Kiểm tra nếu ID câu hỏi có nằm trong arrayStop50
                if (arrayStop50List.contains(questionId)) {
                    String questionText = questionObj.getString("question");

                    JSONObject optionsJson = questionObj.getJSONObject("option");
                    Options options = new Options(
                            optionsJson.getString("a"),
                            optionsJson.getString("b"),
                            optionsJson.getString("c"),
                            optionsJson.optString("d", null),
                            optionsJson.optString("e", null)
                    );

                    String answer = questionObj.getString("answer");
                    String suggest = questionObj.optString("suggest", "");

                    // Load image if available
                    JSONObject imageJson = questionObj.optJSONObject("image");
                    Question.Image image = null;
                    if (imageJson != null) {
                        String img1 = imageJson.getString("img1");
                        image = new Question.Image(img1);
                    }

                    // Create a new Question object and add it to the list
                    Question question = new Question(id, questionText, options, answer, suggest, image);
                    top50QuestionList.add(question);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return top50QuestionList;
    }


    // Load critical questions from JSON
    private List<String> loadCriticalQuestionsFromJson() {
        List<String> criticalList = new ArrayList<>();
        try {
            InputStream is = getAssets().open("driver_data.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray criticalArray = jsonObject.getJSONArray("critical");

            // Parse critical IDs from JSON
            for (int i = 0; i < criticalArray.length(); i++) {
                String criticalId = criticalArray.getString(i);
                criticalList.add(criticalId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return criticalList;
    }

    // Load questions from JSON file for random selection
    private List<Question> loadQuestionsFromJson() {
        List<Question> questionList = new ArrayList<>();
        try {
            InputStream is = getAssets().open("driver_data.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray questionArray = jsonObject.getJSONArray("question");

            // Parse questions from JSON
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObj = questionArray.getJSONObject(i);
                String id = questionObj.getString("id");
                String questionText = questionObj.getString("question");

                JSONObject optionsJson = questionObj.getJSONObject("option");
                Options options = new Options(
                        optionsJson.getString("a"),
                        optionsJson.getString("b"),
                        optionsJson.getString("c"),
                        optionsJson.optString("d", null),
                        optionsJson.optString("e", null)
                );

                String answer = questionObj.getString("answer");
                String suggest = questionObj.optString("suggest", "");

                // Load image if available
                JSONObject imageJson = questionObj.optJSONObject("image");
                Question.Image image = null;
                if (imageJson != null) {
                    String img1 = imageJson.getString("img1");
                    image = new Question.Image(img1);
                }

                Question question = new Question(id, questionText, options, answer, suggest, image);
                questionList.add(question);
            }

            // Shuffle questions and limit to 35
            java.util.Collections.shuffle(questionList);
            int numberOfQuestionsToTake = Math.min(35, questionList.size());
            questionList = questionList.subList(0, numberOfQuestionsToTake);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionList;
    }

    // Load questions based on specific array for non-random selection
    private List<Question> loadQuestionsFromArray(int[] questionIds) {
        List<Question> questionList = new ArrayList<>();
        try {
            InputStream is = getAssets().open("driver_data.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray questionArray = jsonObject.getJSONArray("question");

            // Parse questions from JSON and match with questionIds
            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObj = questionArray.getJSONObject(i);
                String id = questionObj.getString("id");

                // Only add questions that match the given array of IDs
                for (int questionId : questionIds) {
                    if (id.equals(String.valueOf(questionId))) {
                        String questionText = questionObj.getString("question");

                        JSONObject optionsJson = questionObj.getJSONObject("option");
                        Options options = new Options(
                                optionsJson.getString("a"),
                                optionsJson.getString("b"),
                                optionsJson.getString("c"),
                                optionsJson.optString("d", null),
                                optionsJson.optString("e", null)
                        );

                        String answer = questionObj.getString("answer");
                        String suggest = questionObj.optString("suggest", "");

                        // Load image if available
                        JSONObject imageJson = questionObj.optJSONObject("image");
                        Question.Image image = null;
                        if (imageJson != null) {
                            String img1 = imageJson.getString("img1");
                            image = new Question.Image(img1);
                        }

                        Question question = new Question(id, questionText, options, answer, suggest, image);
                        questionList.add(question);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionList;
    }
}

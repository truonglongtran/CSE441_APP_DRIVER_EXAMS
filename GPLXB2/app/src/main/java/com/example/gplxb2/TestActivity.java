package com.example.gplxb2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestActivity extends AppCompatActivity {

    private TextView timerTextView, questionCounterTextView;
    private Button submitButton;
    private ImageButton nextButton, backButton;
    private RecyclerView questionRecyclerView;
    private CountDownTimer countDownTimer;
    private int currentPosition = 0;
    private int totalQuestions = 0;
    private List<Question> questions; // List of questions
    private List<String> criticals; // List of critical questions
    private QuestionAdapter adapter; // Adapter for RecyclerView
    private ArrayList<Integer> incorrectAnswers;
    private DatabaseHelper databaseHelper;
    private String title = ""; // Biến để lưu tiêu đề
    private boolean isLeavingActivity = false;


    // Exam index
    private int examIndex = -1; // Mặc định -1 để random

    // Array to hold predefined question sets for different exams
    int[][] arrays = {
                        {10031, 10089, 10114, 10121, 10134, 10151, 10158, 10160, 10163, 10186, 10193, 10254, 10258, 10260, 10294, 10342, 10343, 10348, 10366, 10393, 10415, 10423, 10466, 10479, 10482, 10489, 10498, 10508, 10516, 10521, 10528, 10532, 10541, 10542, 10558},
                        {10001, 10002, 10061, 10065, 10128, 10144, 10150, 10154, 10156, 10189, 10208, 10210, 10223, 10242, 10271, 10308, 10324, 10326, 10343, 10350, 10361, 10375, 10378, 10400, 10429, 10503, 10504, 10517, 10522, 10544, 10553, 10563, 10567, 10592, 10593},
                        {10019, 10030, 10112, 10113, 10117, 10129, 10140, 10143, 10158, 10162, 10180, 10208, 10217, 10262, 10273, 10361, 10388, 10404, 10420, 10449, 10454, 10464, 10465, 10482, 10485, 10489, 10498, 10503, 10525, 10532, 10537, 10554, 10555, 10575, 10581},
                        {10043, 10060, 10079, 10107, 10130, 10143, 10153, 10155, 10161, 10162, 10168, 10199, 10240, 10247, 10290, 10306, 10345, 10350, 10358, 10373, 10395, 10413, 10431, 10462, 10470, 10493, 10494, 10535, 10537, 10545, 10553, 10556, 10574, 10576, 10590},
                        {10019, 10063, 10068, 10084, 10089, 10133, 10144, 10147, 10148, 10153, 10192, 10209, 10232, 10244, 10278, 10316, 10350, 10383, 10392, 10412, 10417, 10427, 10431, 10467, 10484, 10495, 10532, 10533, 10542, 10543, 10550, 10572, 10576, 10577, 10593},
                        {10030, 10068, 10097, 10116, 10126, 10149, 10153, 10158, 10164, 10172, 10193, 10224, 10248, 10250, 10294, 10339, 10357, 10392, 10393, 10412, 10425, 10431, 10441, 10463, 10465, 10501, 10506, 10514, 10519, 10552, 10554, 10562, 10565, 10568, 10596},
                        {10054, 10066, 10072, 10077, 10127, 10141, 10142, 10148, 10150, 10152, 10180, 10212, 10238, 10256, 10304, 10323, 10358, 10379, 10392, 10400, 10405, 10411, 10449, 10454, 10476, 10505, 10516, 10524, 10529, 10536, 10573, 10578, 10580, 10582, 10595},
                        {10047, 10058, 10080, 10109, 10112, 10130, 10140, 10141, 10143, 10147, 10175, 10200, 10220, 10221, 10299, 10329, 10342, 10397, 10404, 10407, 10413, 10423, 10428, 10470, 10476, 10488, 10492, 10499, 10506, 10514, 10531, 10537, 10540, 10575, 10577},
                        {10009, 10011, 10093, 10114, 10116, 10130, 10142, 10146, 10150, 10166, 10170, 10199, 10256, 10258, 10291, 10315, 10340, 10343, 10346, 10368, 10397, 10404, 10427, 10440, 10480, 10488, 10494, 10512, 10531, 10539, 10541, 10543, 10553, 10557, 10595},
                        {10044, 10062, 10066, 10114, 10119, 10134, 10144, 10151, 10154, 10161, 10188, 10200, 10220, 10250, 10295, 10313, 10323, 10338, 10364, 10373, 10421, 10431, 10435, 10445, 10482, 10487, 10506, 10507, 10535, 10536, 10547, 10563, 10566, 10576, 10577},
                        {10028, 10056, 10073, 10107, 10126, 10140, 10153, 10155, 10156, 10166, 10187, 10199, 10221, 10238, 10288, 10314, 10318, 10323, 10360, 10379, 10401, 10450, 10452, 10461, 10473, 10491, 10494, 10507, 10518, 10523, 10524, 10537, 10546, 10586, 10596},
                        {10019, 10046, 10066, 10086, 10100, 10131, 10151, 10153, 10156, 10161, 10184, 10205, 10246, 10251, 10275, 10345, 10362, 10389, 10398, 10435, 10441, 10445, 10446, 10453, 10467, 10488, 10513, 10536, 10539, 10560, 10573, 10586, 10587, 10595, 10600},
                        {10001, 10010, 10021, 10028, 10043, 10131, 10140, 10150, 10151, 10162, 10189, 10196, 10251, 10263, 10273, 10319, 10325, 10332, 10368, 10388, 10408, 10410, 10440, 10451, 10465, 10495, 10501, 10535, 10536, 10539, 10547, 10561, 10562, 10587, 10589},
                        {10003, 10032, 10051, 10079, 10109, 10126, 10141, 10145, 10156, 10159, 10186, 10202, 10229, 10266, 10290, 10324, 10335, 10345, 10371, 10384, 10390, 10434, 10444, 10456, 10457, 10506, 10511, 10512, 10522, 10542, 10558, 10560, 10574, 10580, 10591},
                        {10033, 10041, 10056, 10057, 10138, 10144, 10146, 10156, 10161, 10188, 10201, 10215, 10227, 10241, 10274, 10309, 10311, 10326, 10360, 10366, 10372, 10377, 10471, 10473, 10482, 10504, 10519, 10521, 10532, 10533, 10537, 10556, 10564, 10582, 10591},
                        {10004, 10037, 10045, 10114, 10121, 10129, 10151, 10156, 10163, 10165, 10175, 10209, 10236, 10245, 10280, 10345, 10348, 10358, 10368, 10396, 10421, 10427, 10431, 10466, 10472, 10499, 10517, 10542, 10545, 10566, 10574, 10578, 10585, 10597, 10600},
                        {10052, 10067, 10078, 10080, 10081, 10137, 10144, 10149, 10153, 10161, 10190, 10211, 10254, 10264, 10302, 10352, 10404, 10406, 10411, 10437, 10439, 10462, 10476, 10480, 10482, 10511, 10515, 10516, 10520, 10551, 10562, 10564, 10567, 10582, 10587},
                        {10019, 10031, 10054, 10111, 10132, 10141, 10152, 10153, 10161, 10165, 10176, 10209, 10224, 10236, 10274, 10349, 10351, 10415, 10416, 10418, 10452, 10470, 10473, 10476, 10485, 10494, 10499, 10507, 10519, 10566, 10571, 10578, 10590, 10594, 10600} };

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        databaseHelper = new DatabaseHelper(this);
        incorrectAnswers = databaseHelper.getIncorrectAnswers();
        timerTextView = findViewById(R.id.timer);
        questionCounterTextView = findViewById(R.id.question_counter);
        submitButton = findViewById(R.id.submit_btn);
        nextButton = findViewById(R.id.next_btn);
        backButton = findViewById(R.id.back_btn);
        questionRecyclerView = findViewById(R.id.question_list);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        questionRecyclerView.setOnTouchListener((v, event) -> true);

        examIndex = getIntent().getIntExtra("examsIndex", -1);
        Log.d("TestActivity", "examsIndex: " + examIndex);

        if (examIndex > 1000) {
            if (examIndex == 2001) {
                questions = loadCriticalQuestions();
                title = "Các câu Điểm liệt";
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
                        title = "Đề thi " + examIndex;
                        break;
                }
            }
        } else if (examIndex == 60) {
            questions = loadCriticalQuestions();
            title = "Các câu Điểm liệt";
        } else if (examIndex == 40) {
            questions = loadIncorrectQuestions();
            title = "Các câu bị sai";
        } else if (examIndex == 50) {
            questions = loadTop50();
            title = "Top các câu hay sai";
        } else if (examIndex != -1) {
            questions = loadQuestionsFromArray(arrays[examIndex]);
            title = "Đề thi " + (examIndex + 1);
        } else {
            questions = loadQuestionsFromJson();
            title = "Đề ngẫu nhiên";
        }

        criticals = loadCriticalQuestionsFromJson();
        totalQuestions = questions.size();
        adapter = new QuestionAdapter(questions, criticals);
        questionRecyclerView.setAdapter(adapter);
        updateQuestionCounter();
        startTimer();

        nextButton.setOnClickListener(v -> {
            if (currentPosition < totalQuestions - 1) {
                currentPosition++;
                questionRecyclerView.smoothScrollToPosition(currentPosition);
                updateQuestionCounter();
            }
        });

        backButton.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                questionRecyclerView.smoothScrollToPosition(currentPosition);
                updateQuestionCounter();
            }
        });

        submitButton.setOnClickListener(v -> showConfirmationDialog(title));
    }

    private void updateQuestionCounter() {
        questionCounterTextView.setText((currentPosition + 1) + "/" + totalQuestions);
    }

    private void startTimer() {
        if (examIndex < 40) {
            new Handler().postDelayed(() -> {
                countDownTimer = new CountDownTimer(22 * 60 * 1000, 1000) { // 22 minutes
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
            }, 2000);
        } else {
            // Hiển thị title nếu examIndex >= 40, không có bộ đếm thời gian
            timerTextView.setText(title);
        }
    }

    private boolean isSubmitted = false; // Cờ để kiểm tra nếu đã gọi submitAnswers

    @Override
    protected void onPause() {
        super.onPause();
        if (isLeavingActivity && !isSubmitted) {
            submitAnswers(title); // Nộp bài khi người dùng rời Activity
            isSubmitted = true; // Đánh dấu rằng đã nộp bài
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isLeavingActivity && !isSubmitted) {
            submitAnswers(title); // Nộp bài khi Activity ngừng hoạt động hoàn toàn
            isSubmitted = true; // Đánh dấu rằng đã nộp bài
        }
        isLeavingActivity = false; // Reset lại cờ khi quay trở lại Activity
    }

    @Override
    public void onBackPressed() {
        if (!isSubmitted) {
            submitAnswers(title); // Nộp bài khi người dùng nhấn nút Back
            isSubmitted = true; // Đánh dấu rằng đã nộp bài
        }
        super.onBackPressed();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        isLeavingActivity = true; // Đặt cờ khi Activity chuyển vào nền
    }

    //hộp thoại xác nhận trước khi nộp bài
    private void showConfirmationDialog(final String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận nộp bài");
        builder.setMessage("Bạn có chắc chắn muốn nộp bài?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Gọi hàm submitAnswers khi người dùng xác nhận
                submitAnswers(title);
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng hộp thoại nếu người dùng chọn không
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void submitAnswers(String title) {
        int score = 0, incorrectCriticalCount = 0;
        Map<String, String> selectedAnswers = adapter.getSelectedAnswers();
        ArrayList<Integer> incorrectAnswersFromDB = databaseHelper.getIncorrectAnswers();

        for (Question question : questions) {
            String userAnswer = selectedAnswers.get(question.getId());
            Integer questionId = Integer.parseInt(question.getId());

            if (criticals.contains(question.getId()) && (userAnswer == null || !userAnswer.equals(question.getAnswer()))) {
                incorrectCriticalCount++;
            }

            if (userAnswer == null || !userAnswer.equals(question.getAnswer())) {
                if (!incorrectAnswers.contains(questionId)) {
                    incorrectAnswers.add(questionId);
                    databaseHelper.insertIncorrectAnswer(questionId);
                }
            } else {
                if (incorrectAnswers.contains(questionId)) {
                    incorrectAnswers.remove(questionId);
                    databaseHelper.deleteCorrectAnswer(questionId);
                }
                score++;
            }
        }

        Collections.sort(incorrectAnswers);
        Log.d("TestActivity", "Các câu trả lời sai: " + incorrectAnswers);

        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", questions.size());
        intent.putExtra("INCORRECT_CRITICAL_COUNT", incorrectCriticalCount);
        intent.putExtra("EXAMS_INDEX", examIndex);
        startActivity(intent);
        finish();
    }

    private List<Question> loadQuestionsByExamIndex(int examIndex) {
        int[] questionIds;
        switch (examIndex) {
            case 2002: questionIds = khainiemQuytac; break;
            case 2003: questionIds = nghiepvuVantai; break;
            case 2004: questionIds = vanhoaGiaothong; break;
            case 2005: questionIds = kithuatLaixe; break;
            case 2006: questionIds = cautaoSuachua; break;
            case 2007: questionIds = bienbao; break;
            case 2008: questionIds = sahinh; break;
            default: return new ArrayList<>();
        }
        return filterQuestionsByIds(questionIds);
    }


    private List<Question> loadIncorrectQuestions() {
        return filterQuestionsByIds(incorrectAnswers.stream().mapToInt(i -> i).toArray());
    }

    private List<Question> loadCriticalQuestions() {
        List<String> criticalIds = loadCriticalQuestionsFromJson();
        return filterQuestions(question -> criticalIds.contains(question.getId()));
    }

    private List<Question> loadTop50() {
        return filterQuestionsByIds(Arrays.stream(arraystop50).boxed().mapToInt(i -> i).toArray());
    }

    private List<Question> loadQuestionsFromJson() {
        List<Question> questionList = loadQuestionsFromFile();
        Collections.shuffle(questionList);
        return questionList.subList(0, Math.min(35, questionList.size()));
    }

    private List<Question> filterQuestionsByIds(int[] questionIds) {
        List<Integer> idList = Arrays.stream(questionIds).boxed().collect(Collectors.toList());
        return filterQuestions(question -> idList.contains(Integer.parseInt(question.getId())));
    }

    private List<Question> filterQuestions(Predicate<Question> filter) {
        return loadQuestionsFromFile().stream().filter(filter).collect(Collectors.toList());
    }

    private List<Question> loadQuestionsFromFile() {
        List<Question> questionList = new ArrayList<>();
        try (InputStream is = getAssets().open("driver_data.json")) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray questionArray = new JSONObject(json).getJSONArray("question");

            for (int i = 0; i < questionArray.length(); i++) {
                Question question = parseQuestion(questionArray.getJSONObject(i));
                if (question != null) questionList.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionList;
    }

    private List<String> loadCriticalQuestionsFromJson() {
        List<String> criticalList = new ArrayList<>();
        try (InputStream is = getAssets().open("driver_data.json")) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            JSONArray criticalArray = new JSONObject(new String(buffer, StandardCharsets.UTF_8)).getJSONArray("critical");

            for (int i = 0; i < criticalArray.length(); i++) {
                criticalList.add(criticalArray.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return criticalList;
    }

    private Question parseQuestion(JSONObject questionObj) {
        try {
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

            JSONObject imageJson = questionObj.optJSONObject("image");
            Question.Image image = null;
            if (imageJson != null) {
                image = new Question.Image(imageJson.getString("img1"));
            }

            return new Question(id, questionText, options, answer, suggest, image);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<Question> loadQuestionsFromArray(int[] questionIds) {
        List<Question> questionList = new ArrayList<>();
        Set<String> questionIdSet = Arrays.stream(questionIds)
                .mapToObj(String::valueOf)
                .collect(Collectors.toSet());

        try (InputStream is = getAssets().open("driver_data.json")) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray questionArray = new JSONObject(json).getJSONArray("question");

            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObj = questionArray.getJSONObject(i);
                String id = questionObj.getString("id");

                if (questionIdSet.contains(id)) {
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
                    JSONObject imageJson = questionObj.optJSONObject("image");
                    Question.Image image = imageJson != null ? new Question.Image(imageJson.getString("img1")) : null;

                    questionList.add(new Question(id, questionText, options, answer, suggest, image));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionList;
    }

}

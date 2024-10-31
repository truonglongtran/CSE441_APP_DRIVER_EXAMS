//public class TestActivity extends AppCompatActivity {
//    private TextView timerTextView, questionCounterTextView;
//    private Button submitButton;
//    private ImageButton nextButton, backButton;
//    private RecyclerView questionRecyclerView;
//    private CountDownTimer countDownTimer;
//    private int currentPosition = 0;
//    private int totalQuestions = 0;
//    private List<Question> questions; // List of questions
//    private List<String> criticals; // List of critical questions
//    private QuestionAdapter adapter; // Adapter for RecyclerView
//    private ArrayList<Integer> incorrectAnswers;
//    private DatabaseHelper databaseHelper;
//    private String title = ""; // Biến để lưu tiêu đề
//    private boolean isLeavingActivity = false;
//    private int examIndex = -1; // Mặc định -1 để random
//    int[][] arrays = {{10019, 10031, 10054, 10111, 10132, 10141, 10152, 10153, 10161, 10165, 10176, 10209, 10224, 10236, 10274, 10349, 10351, 10415, 10416, 10418, 10452, 10470, 10473, 10476, 10485, 10494, 10499, 10507, 10519, 10566, 10571, 10578, 10590, 10594, 10600} };
//    private int[] khainiemQuytac ={10001, 10166};
//    private int[] nghiepvuVantai = {10192};
//    private int[] vanhoaGiaothong = { 10213};
//    private int[] kithuatLaixe = {10269};
//    private int[] cautaoSuachua = {10304};
//    private int[] sahinh = {10486};
//    private int[] bienbao = {10486};
//    private int[] arraystop50 = {10550, 10551, 10569, 10583, 10592};
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//        databaseHelper = new DatabaseHelper(this);
//        incorrectAnswers = databaseHelper.getIncorrectAnswers();
//        timerTextView = findViewById(R.id.timer);
//        questionCounterTextView = findViewById(R.id.question_counter);
//        submitButton = findViewById(R.id.submit_btn);
//        nextButton = findViewById(R.id.next_btn);
//        backButton = findViewById(R.id.back_btn);
//        questionRecyclerView = findViewById(R.id.question_list);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        questionRecyclerView.setOnTouchListener((v, event) -> true);
//        questionRecyclerView.setLayoutManager(layoutManager);
//        examIndex = getIntent().getIntExtra("examsIndex", -1);
//        Log.d("TestActivity", "examsIndex: " + examIndex);
//        if (examIndex > 1000) {
//            if (examIndex == 2001) {
//                questions = loadCriticalQuestions();
//                title = "Các câu Điểm liệt"; // Gán tiêu đề cho câu hỏi quan trọng
//            } else {
//                questions = loadQuestionsByExamIndex(examIndex);
//                switch (examIndex) {
//                    case 2002:
//                        title = "Khái niệm quy tắc";
//                        break;
//                    case 2003:
//                        title = "Nghiệp vụ vận tải";
//                        break;
//                    case 2004:
//                        title = "Văn hóa giao thông";
//                        break;
//                    case 2005:
//                        title = "Kỹ thuật lái xe";
//                        break;
//                    case 2006:
//                        title = "Cấu tạo sửa chữa";
//                        break;
//                    case 2007:
//                        title = "Biển báo";
//                        break;
//                    case 2008:
//                        title = "Sa hình";
//                        break;
//                    default:
//                        title = "Đề thi " + examIndex; // Đặt tiêu đề mặc định
//                        break;
//                }
//            }
//        } else if (examIndex == 60) {
//            questions = loadCriticalQuestions(); // Load only critical questions
//            title = "Các câu Điểm liệt"; // Tiêu đề cho câu hỏi quan trọng
//        } else if (examIndex == 40) {
//            questions = loadIncorrectQuestions(); // Load only top 50 questions
//            title = "Các câu bị sai"; // Tiêu đề cho câu bị sai
//        } else if (examIndex == 50) {
//            questions = loadTop50(); // Load only top 50 questions
//            title = "Top các câu hay sai"; // Tiêu đề cho top câu hỏi
//        } else if (examIndex != -1) {
//            questions = loadQuestionsFromArray(arrays[examIndex]); // Load specific set of questions
//            int realExamIndex = examIndex + 1;
//            title = "Đề thi " + realExamIndex ; // Tiêu đề cho đề thi cụ thể
//        } else {
//            questions = loadQuestionsFromJson(); // Load random questions
//            title = "Đề ngẫu nhiên"; // Tiêu đề cho đề ngẫu nhiên
//        }
//        criticals = loadCriticalQuestionsFromJson();
//        totalQuestions = questions.size();
//        adapter = new QuestionAdapter(questions, criticals); // Pass criticals to adapter
//        questionRecyclerView.setAdapter(adapter);
//        updateQuestionCounter();
//        startTimer();
//        nextButton.setOnClickListener(v -> {
//            if (currentPosition < totalQuestions - 1) {
//                currentPosition++;
//                questionRecyclerView.smoothScrollToPosition(currentPosition);
//                updateQuestionCounter();
//            }
//        });
//        backButton.setOnClickListener(v -> {
//            if (currentPosition > 0) {
//                currentPosition--;
//                questionRecyclerView.smoothScrollToPosition(currentPosition);
//                updateQuestionCounter();
//            }
//        });
//        submitButton.setOnClickListener(v -> showConfirmationDialog(title)); // Gửi tiêu đề khi nộp bài
//    }
//    private void updateQuestionCounter() {
//        questionCounterTextView.setText((currentPosition + 1) + "/" + totalQuestions);
//    }
//    private void startTimer() {
//        if (examIndex < 40) {
//            new Handler().postDelayed(() -> {
//                countDownTimer = new CountDownTimer(22 * 60 * 1000, 1000) { // 22 minutes
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        long minutes = millisUntilFinished / 60000;
//                        long seconds = (millisUntilFinished % 60000) / 1000;
//                        String timeLeft = String.format("%02d:%02d", minutes, seconds);
//                        timerTextView.setText("Time: " + timeLeft); // Hiển thị thời gian còn lại
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        submitAnswers(title); // Nộp bài khi hết giờ
//                    }
//                }.start();
//            }, 2000); // 2000 milliseconds = 2 seconds
//        } else {
//            timerTextView.setText(title);
//        }
//    }
//    private boolean isSubmitted = false; // Cờ để kiểm tra nếu đã gọi submitAnswers
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (isLeavingActivity && !isSubmitted) {
//            submitAnswers(title); // Nộp bài khi người dùng rời Activity
//            isSubmitted = true; // Đánh dấu rằng đã nộp bài
//        }
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (isLeavingActivity && !isSubmitted) {
//            submitAnswers(title); // Nộp bài khi Activity ngừng hoạt động hoàn toàn
//            isSubmitted = true; // Đánh dấu rằng đã nộp bài
//        }
//        isLeavingActivity = false; // Reset lại cờ khi quay trở lại Activity
//    }
//    @Override
//    public void onBackPressed() {
//        if (!isSubmitted) {
//            submitAnswers(title); // Nộp bài khi người dùng nhấn nút Back
//            isSubmitted = true; // Đánh dấu rằng đã nộp bài
//        }
//        super.onBackPressed();
//    }
//    @Override
//    protected void onUserLeaveHint() {
//        super.onUserLeaveHint();
//        isLeavingActivity = true; // Đặt cờ khi Activity chuyển vào nền
//    }
//    private void showConfirmationDialog(final String title) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Xác nhận nộp bài");
//        builder.setMessage("Bạn có chắc chắn muốn nộp bài?");
//        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                submitAnswers(title);
//            }
//        });
//        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss(); // Đóng hộp thoại nếu người dùng chọn không
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//    private void submitAnswers(String title) {
//        int score = 0; // Khởi tạo điểm số
//        int incorrectCriticalCount = 0;
//        Map<String, String> selectedAnswers = adapter.getSelectedAnswers();
//        ArrayList<Integer> incorrectAnswersFromDB = databaseHelper.getIncorrectAnswers();
//        for (Question question : questions) {
//            String userAnswer = selectedAnswers.get(question.getId());
//            if (userAnswer == null || !userAnswer.equals(question.getAnswer())) {
//                incorrectCriticalCount++; // Tăng số lượng câu hỏi quan trọng trả lời sai
//            }
//        }
//        if (userAnswer == null || !userAnswer.equals(question.getAnswer())) {
//            Integer questionId = Integer.parseInt(question.getId());
//            if (!incorrectAnswers.contains(questionId)) {
//                incorrectAnswers.add(questionId); // Thêm ID câu hỏi sai vào danh sách
//                databaseHelper.insertIncorrectAnswer(questionId); // Thêm vào cơ sở dữ liệu
//            }
//        } else {
//            Integer questionId = Integer.parseInt(question.getId());
//            if (incorrectAnswers.contains(questionId)) {
//                incorrectAnswers.remove(questionId); // Xóa ID câu hỏi đúng khỏi danh sách nếu có
//                databaseHelper.deleteCorrectAnswer(questionId); // Xóa khỏi cơ sở dữ liệu
//            }
//            score++; // Tăng điểm cho câu trả lời đúng
//        }
//    }
//        Collections.sort(incorrectAnswers); // Sắp xếp danh sách
//        Log.d("TestActivity", "Các câu trả lời sai: " + incorrectAnswers);
//    Intent intent = new Intent(TestActivity.this, ResultActivity.class);
//        intent.putExtra("TITLE", title); // Gửi tiêu đề đề thi
//        intent.putExtra("SCORE", score); // Truyền điểm số đến ResultActivity
//        intent.putExtra("TOTAL_QUESTIONS", questions.size()); // Truyền tổng số câu hỏi
//        intent.putExtra("INCORRECT_CRITICAL_COUNT", incorrectCriticalCount); // Truyền số lượng câu hỏi quan trọng sai
//        intent.putExtra("EXAMS_INDEX", examIndex); // Truyền examsIndex đến ResultActivity
//    startActivity(intent); // Chuyển đến ResultActivity
//    finish(); // Tùy chọn để kết thúc activity này
//}
//private List<Question> loadQuestionsByExamIndex(int examIndex) {
//    List<Question> questionList = new ArrayList<>();
//    List<Integer> questionIdList;
//    switch (examIndex) {
//        case 2002:
//            questionIdList = Arrays.stream(khainiemQuytac).boxed().collect(Collectors.toList());
//            break;
//        case 2003:
//            questionIdList = Arrays.stream(nghiepvuVantai).boxed().collect(Collectors.toList());
//            break;
//        case 2004:
//            questionIdList = Arrays.stream(vanhoaGiaothong).boxed().collect(Collectors.toList());
//            break;
//        case 2005:
//            questionIdList = Arrays.stream(kithuatLaixe).boxed().collect(Collectors.toList());
//            break;
//        case 2006:
//            questionIdList = Arrays.stream(cautaoSuachua).boxed().collect(Collectors.toList());
//            break;
//        case 2007:
//            questionIdList = Arrays.stream(bienbao).boxed().collect(Collectors.toList());
//            break;
//        case 2008:
//            questionIdList = Arrays.stream(sahinh).boxed().collect(Collectors.toList());
//            break;
//        default:
//            return questionList;
//    }
//
//    try {
//        InputStream is = getAssets().open("driver_data.json");
//        byte[] buffer = new byte[is.available()];
//        is.read(buffer);
//        is.close();
//        String json = new String(buffer, StandardCharsets.UTF_8);
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray questionArray = jsonObject.getJSONArray("question");
//        for (int i = 0; i < questionArray.length(); i++) {
//            JSONObject questionObj = questionArray.getJSONObject(i);
//            String id = questionObj.getString("id");
//            int questionId = Integer.parseInt(id);
//            if (questionIdList.contains(questionId)) {
//                String questionText = questionObj.getString("question");
//                JSONObject optionsJson = questionObj.getJSONObject("option");
//                Options options = new Options(
//                        optionsJson.getString("a"),
//                        optionsJson.getString("b"),
//                        optionsJson.getString("c"),
//                        optionsJson.optString("d", null),
//                        optionsJson.optString("e", null)
//                );
//                String answer = questionObj.getString("answer");
//                String suggest = questionObj.optString("suggest", "");
//                JSONObject imageJson = questionObj.optJSONObject("image");
//                Question.Image image = null;
//                if (imageJson != null) {
//                    String img1 = imageJson.getString("img1");
//                    image = new Question.Image(img1);
//                }
//                Question question = new Question(id, questionText, options, answer, suggest, image);
//                questionList.add(question);
//            }
//        }
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return questionList;
//}
//private List<Question> loadIncorrectQuestions() {
//    List<Question> incorrectQuestionList = new ArrayList<>();
//    try {
//        InputStream is = getAssets().open("driver_data.json");
//        byte[] buffer = new byte[is.available()];
//        is.read(buffer);
//        is.close();
//        String json = new String(buffer, StandardCharsets.UTF_8);
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray questionArray = jsonObject.getJSONArray("question");
//        Log.d("IncorrectAnswers", "List of incorrect answers: " + incorrectAnswers.toString());
//        for (int i = 0; i < questionArray.length(); i++) {
//            JSONObject questionObj = questionArray.getJSONObject(i);
//            String id = questionObj.getString("id");
//            if (incorrectAnswers.contains(Integer.parseInt(id))) {
//                String questionText = questionObj.getString("question");
//                JSONObject optionsJson = questionObj.getJSONObject("option");
//                Options options = new Options(
//                        optionsJson.getString("a"),
//                        optionsJson.getString("b"),
//                        optionsJson.getString("c"),
//                        optionsJson.optString("d", null),
//                        optionsJson.optString("e", null)
//                );
//                String answer = questionObj.getString("answer");
//                String suggest = questionObj.optString("suggest", "");
//                JSONObject imageJson = questionObj.optJSONObject("image");
//                Question.Image image = null;
//                if (imageJson != null) {
//                    String img1 = imageJson.getString("img1");
//                    image = new Question.Image(img1);
//                }
//                Question question = new Question(id, questionText, options, answer, suggest, image);
//                incorrectQuestionList.add(question);
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return incorrectQuestionList;
//}
//private List<Question> loadCriticalQuestions() {
//    List<Question> criticalQuestionList = new ArrayList<>();
//    criticals = loadCriticalQuestionsFromJson(); // Load critical question IDs
//    try {
//        InputStream is = getAssets().open("driver_data.json");
//        byte[] buffer = new byte[is.available()];
//        is.read(buffer);
//        is.close();
//        String json = new String(buffer, StandardCharsets.UTF_8);
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray questionArray = jsonObject.getJSONArray("question");
//        for (int i = 0; i < questionArray.length(); i++) {
//            JSONObject questionObj = questionArray.getJSONObject(i);
//            String id = questionObj.getString("id");
//            if (criticals.contains(id)) {
//                String questionText = questionObj.getString("question");
//                JSONObject optionsJson = questionObj.getJSONObject("option");
//                Options options = new Options(
//                        optionsJson.getString("a"),
//                        optionsJson.getString("b"),
//                        optionsJson.getString("c"),
//                        optionsJson.optString("d", null),
//                        optionsJson.optString("e", null)
//                );
//                String answer = questionObj.getString("answer");
//                String suggest = questionObj.optString("suggest", "");
//                JSONObject imageJson = questionObj.optJSONObject("image");
//                Question.Image image = null;
//                if (imageJson != null) {
//                    String img1 = imageJson.getString("img1");
//                    image = new Question.Image(img1);
//                }
//                Question question = new Question(id, questionText, options, answer, suggest, image);
//                criticalQuestionList.add(question);
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return criticalQuestionList;
//}
//private List<Question> loadTop50() {
//    List<Question> top50QuestionList = new ArrayList<>();
//    List<Integer> arrayStop50List = Arrays.stream(arraystop50).boxed().collect(Collectors.toList());
//    try {
//        InputStream is = getAssets().open("driver_data.json");
//        byte[] buffer = new byte[is.available()];
//        is.read(buffer);
//        is.close();
//        String json = new String(buffer, StandardCharsets.UTF_8);
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray questionArray = jsonObject.getJSONArray("question");
//        for (int i = 0; i < questionArray.length(); i++) {
//            JSONObject questionObj = questionArray.getJSONObject(i);
//            String id = questionObj.getString("id");
//            int questionId = Integer.parseInt(id);
//            if (arrayStop50List.contains(questionId)) {
//                String questionText = questionObj.getString("question");
//                JSONObject optionsJson = questionObj.getJSONObject("option");
//                Options options = new Options(
//                        optionsJson.getString("a"),
//                        optionsJson.getString("b"),
//                        optionsJson.getString("c"),
//                        optionsJson.optString("d", null),
//                        optionsJson.optString("e", null)
//                );
//                String answer = questionObj.getString("answer");
//                String suggest = questionObj.optString("suggest", "");
//                JSONObject imageJson = questionObj.optJSONObject("image");
//                Question.Image image = null;
//                if (imageJson != null) {
//                    String img1 = imageJson.getString("img1");
//                    image = new Question.Image(img1);
//                }
//                Question question = new Question(id, questionText, options, answer, suggest, image);
//                top50QuestionList.add(question);
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return top50QuestionList;
//}
//private List<String> loadCriticalQuestionsFromJson() {
//    List<String> criticalList = new ArrayList<>();
//    try {
//        InputStream is = getAssets().open("driver_data.json");
//        byte[] buffer = new byte[is.available()];
//        is.read(buffer);
//        is.close();
//        String json = new String(buffer, StandardCharsets.UTF_8);
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray criticalArray = jsonObject.getJSONArray("critical");
//        for (int i = 0; i < criticalArray.length(); i++) {
//            String criticalId = criticalArray.getString(i);
//            criticalList.add(criticalId);
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return criticalList;
//}
//private List<Question> loadQuestionsFromJson() {
//    List<Question> questionList = new ArrayList<>();
//    try {
//        InputStream is = getAssets().open("driver_data.json");
//        byte[] buffer = new byte[is.available()];
//        is.read(buffer);
//        is.close();
//        String json = new String(buffer, StandardCharsets.UTF_8);
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray questionArray = jsonObject.getJSONArray("question");
//        for (int i = 0; i < questionArray.length(); i++) {
//            JSONObject questionObj = questionArray.getJSONObject(i);
//            String id = questionObj.getString("id");
//            String questionText = questionObj.getString("question");
//            JSONObject optionsJson = questionObj.getJSONObject("option");
//            Options options = new Options(
//                    optionsJson.getString("a"),
//                    optionsJson.getString("b"),
//                    optionsJson.getString("c"),
//                    optionsJson.optString("d", null),
//                    optionsJson.optString("e", null)
//            );
//            String answer = questionObj.getString("answer");
//            String suggest = questionObj.optString("suggest", "");
//            JSONObject imageJson = questionObj.optJSONObject("image");
//            Question.Image image = null;
//            if (imageJson != null) {
//                String img1 = imageJson.getString("img1");
//                image = new Question.Image(img1);
//            }
//            Question question = new Question(id, questionText, options, answer, suggest, image);
//            questionList.add(question);
//        }
//        java.util.Collections.shuffle(questionList);
//        int numberOfQuestionsToTake = Math.min(35, questionList.size());
//        questionList = questionList.subList(0, numberOfQuestionsToTake);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return questionList;
//}
//private List<Question> loadQuestionsFromArray(int[] questionIds) {
//    List<Question> questionList = new ArrayList<>();
//    try {
//        InputStream is = getAssets().open("driver_data.json");
//        byte[] buffer = new byte[is.available()];
//        is.read(buffer);
//        is.close();
//        String json = new String(buffer, StandardCharsets.UTF_8);
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray questionArray = jsonObject.getJSONArray("question");
//        for (int i = 0; i < questionArray.length(); i++) {
//            JSONObject questionObj = questionArray.getJSONObject(i);
//            String id = questionObj.getString("id");
//            for (int questionId : questionIds) {
//                if (id.equals(String.valueOf(questionId))) {
//                    String questionText = questionObj.getString("question");
//                    JSONObject optionsJson = questionObj.getJSONObject("option");
//                    Options options = new Options(
//                            optionsJson.getString("a"),
//                            optionsJson.getString("b"),
//                            optionsJson.getString("c"),
//                            optionsJson.optString("d", null),
//                            optionsJson.optString("e", null)
//                    );
//                    String answer = questionObj.getString("answer");
//                    String suggest = questionObj.optString("suggest", "");
//                    JSONObject imageJson = questionObj.optJSONObject("image");
//                    Question.Image image = null;
//                    if (imageJson != null) {
//                        String img1 = imageJson.getString("img1");
//                        image = new Question.Image(img1);
//                    }
//                    Question question = new Question(id, questionText, options, answer, suggest, image);
//                    questionList.add(question);
//                    break;
//                }
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return questionList;
//}
//}

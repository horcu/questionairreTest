package com.example.hcummings.questionairretest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String QUESTION_GET = "QUESTION GET: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jservice.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final QuestionApi questionsApi = retrofit.create(QuestionApi.class);

        final TextView questionsTv = (TextView)findViewById(R.id.questions);
        final TextView answerTv = (TextView)findViewById(R.id.answers);
        final TextView loading = (TextView)findViewById(R.id.loading);

        final Button questionsGet = (Button) findViewById(R.id.get_q);

        if (questionsGet != null) {
            questionsGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loading.setVisibility(View.VISIBLE);
                    Map<String, String> qStringOptions = new HashMap<String, String>();
                    qStringOptions.put("count","20");
                    String path = "random";

                    Call<List<Question>> questions = questionsApi.getRandomQuestions(path, qStringOptions);

                    questions.enqueue(new Callback<List<Question>>() {
                        @Override
                        public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                            if (questionsTv != null) {

                                if(response.isSuccessful())
                                {
                                    try {
                                        loading.setVisibility(View.INVISIBLE);
                                        List<Question> questions = response.body();

                                        String question = questions.get(0).getQuestion();
                                        String answer = questions.get(0).getAnswer();
                                        questionsTv.setText(question);
                                        answerTv.setText(answer);

                                    } catch (JsonSyntaxException e) {
                                        e.printStackTrace();
                                        loading.setVisibility(View.INVISIBLE);
                                        Log.d(QUESTION_GET, e.getMessage() + " : " + Arrays.toString(e.getStackTrace()));
                                    }
                                }
                                Log.d(QUESTION_GET, response.message() + ": " + response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Question>> call, Throwable t) {
                            if (questionsTv != null) {
                                questionsTv.setText(t.getMessage());
                                Log.d("QUESTION GET FAILED: ", t.getMessage());
                                Log.d("QUESTION GET FAILED: ", t.getStackTrace().toString());
                                loading.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            });
        }
    }

    private Question GetQuestionsAndTheirAnswers(JsonObject jsonObject) throws JSONException {
        Question newQuestion = new Question();

            newQuestion.setQuestion(jsonObject.get("question").toString());
            newQuestion.setAnswer(jsonObject.get("answer").toString());

        return newQuestion;
    }


}

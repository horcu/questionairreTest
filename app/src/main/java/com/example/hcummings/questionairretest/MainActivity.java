package com.example.hcummings.questionairretest;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String QUESTION_GET = "QUESTION GET: ";
    private TextView questionsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jservice.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final QuestionApi questionsApi = retrofit.create(QuestionApi.class);

        questionsTv = (TextView)findViewById(R.id.questions);
        final TextView answerTv = (TextView)findViewById(R.id.answers);
        final AutoFitGridLayout answerGrid = (AutoFitGridLayout)findViewById(R.id.answer_tiles);

        final AutoFitGridLayout clueGrid = (AutoFitGridLayout)findViewById(R.id.clue_tiles);



        final ImageView questionsGet = (ImageView) findViewById(R.id.get_q);
        final ImageView answerGet = (ImageView) findViewById(R.id.get_a);

        answerGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerTv.getVisibility() == View.INVISIBLE)
                answerTv.setVisibility(View.VISIBLE);
            }
        });

        if (questionsGet != null) {
            questionsGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answerTv.setVisibility(View.INVISIBLE);
                    questionsTv.setText("fetching question.....");
                    Map<String, String> qStringOptions = new HashMap<String, String>();
                    qStringOptions.put("count","1");
                    String path = "random";

                    Call<List<Question>> questions = questionsApi.getRandomQuestions(path, qStringOptions);

                    questions.enqueue(new Callback<List<Question>>() {
                        @Override
                        public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                            if (questionsTv != null) {

                                if(response.isSuccessful())
                                {
                                    try {

                                        List<Question> questions = response.body();

                                        String question = questions.get(0).getQuestion();
                                        String answer = questions.get(0).getAnswer();
                                        String category = questions.get(0).getCategory().getTitle();
                                        int value = questions.get(0).getValue();

                                        answer = SanitizeAnswer(answer);

                                        //set actionbar title and sub title
                                        if(getSupportActionBar() !=null) {
                                            getSupportActionBar().setTitle(category);
                                            getSupportActionBar().setSubtitle(String.valueOf(value));
                                        }

                                        //get the random spaces to amitt in the answer
                                        ArrayList<Integer> spaceIndexes = GetRandomSpaceIndexes(answer);

                                        //purge views from grid
                                        if (answerGrid != null) {
                                            answerGrid.removeAllViews();
                                            answerGrid.setColumnCount(answer.length());
                                            answerGrid.requestLayout();
                                        }

                                        //todo make the column count for the clue grid be static or based on the game level ??
                                        if (clueGrid != null) {
                                            clueGrid.setColumnCount(8);
                                        }

                                        //add the letters to the grid
                                        for(int i = 0 ; i < answer.length(); i ++) {

                                            char letter = answer.charAt(i);
                                            String lt = String.valueOf(letter);
                                            MaterialLetterIcon icon;

                                            if(lt.equals(""))
                                                icon = getMaterialLetterIcon(null);
                                            else
                                            icon = getMaterialLetterIcon(String.valueOf(letter));

                                            if (spaceIndexes.contains(i))
                                                letter = '_';

                                            icon.setLetter(String.valueOf(letter));

                                            if(answerGrid != null)
                                               answerGrid.addView(icon, i);
                                        }

                                        String alphabet = getString(R.string.alphabet);
                                        char[] remaindingLetters = GetRandomLetters(10 - spaceIndexes.size(), alphabet);

                                        char[] clueLetters = GetRandomLetters(10 - remaindingLetters.length, answer);

                                        char[] gameString = ArrayUtils.addAll(remaindingLetters, clueLetters);

                                        //scramble letters
                                        String  gameReadyString = ScrambleLetters(gameString);

                                        //set up the clue gridlayout
                                        for(int i = 0 ; i < gameReadyString.length(); i ++ )
                                        {

                                            ImageView icon = null;
                                            if (clueGrid != null) {
                                                icon = (ImageView) clueGrid.getChildAt(i);
                                            }



                                            //todo play sound as they get added..
                                        }

                                        for(int i =0; i < clueGrid.getChildCount(); i++ ){
                                            char l = gameReadyString.charAt(i);

                                            //get all the clue letters from the server
                                            String url = getLetterUrl(i, l);
                                            if(!url.equals(""))
                                                Picasso.with(getApplicationContext()).load(url).into((ImageView) clueGrid.getChildAt(i));
                                        }


                                        questionsTv.setText(question);
                                        if (answerTv != null) {
                                            answerTv.setText(answer);
                                        }

                                    } catch (JsonSyntaxException e) {
                                        e.printStackTrace();
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
                            }
                        }
                    });
                }
            });
        }
    }

    private String SanitizeAnswer(String answer) {
        String result1 = answer.replace("<i>","");
        String result2 = result1.replace("(", "");
        String result3 = result2.replace(")", "");
        String result4 = result3.replace("</i>", "");
        String result5 = result4.replace(".", "");
        String result6 = result5.replace(".", "");

        return result6;
    }

    private String getLetterUrl(int index, char letter) {
      String pre = "https://storage.googleapis.com/ballrz/icons/";
      String mid =  String.valueOf(letter).toUpperCase() ;
      String postLower = "%20Lowercase-52.png";
      String postUpper = "-52.png";

        String url = new StringBuilder()
                        .append(pre)
                        .append(mid)
                        .append(index == 0 ? postUpper : postLower)
                        .toString();
        return url;
    }

    @NonNull
    private MaterialLetterIcon getMaterialLetterIcon(String letter) {
        MaterialLetterIcon icon = new MaterialLetterIcon(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = 150;
        icon.setMinimumHeight(150);
        icon.setMinimumWidth(150);

        if(letter == null)
        {
            icon.setBackgroundColor(Color.TRANSPARENT);
            icon.setLayoutParams(params);
            icon.setShapeColor(Color.WHITE);
            icon.setLetterColor(Color.TRANSPARENT);
            icon.setLetter("");
        }
        else {
            icon.setBackgroundColor(Color.TRANSPARENT);
            icon.setLayoutParams(params);
            icon.setLetterTypeface(Typeface.SANS_SERIF);
            icon.setShapeColor(Color.parseColor("#ffd3d3d3"));
            icon.setLetterColor(Color.BLACK);
            icon.setLetter(letter);
        }
        return icon;
    }

    private String ScrambleLetters(char[] result) {

        Random random = new Random(0);
        // Scramble the letters using the standard Fisher-Yates shuffle,
        for( int i=0 ; i<result.length-1 ; i++ )
        {
            int j = random.nextInt(result.length-1);
            // Swap letters
            char temp = result[i]; result[i] = result[j];  result[j] = temp;
        }

        return new String( result );
    }

    private char[] GetRandomLetters(int letterCount, String text) {
        if(letterCount < 0)
            letterCount = 3;

        char[] chars = new char[letterCount];

        Random r = new Random(0);

        for(int i=0; i < letterCount; i ++){
          char c =   text.charAt(r.nextInt(text.length()));
            chars[i] = c;
        }
        return chars;
    }

    private ArrayList<Integer> GetRandomSpaceIndexes(String answer) {

        Random rand= new Random(0) ;
        int limit = answer.length() -1;

        if(answer.length() <= 5) { // hide 2 letters
            return GetResultIndexes(rand, limit, 2);
        }
        else if(answer.length() > 5 && answer.length() < 10){ // hide 4 letters
           return GetResultIndexes(rand, limit, 4);
        }
        else if (answer.length() >= 10 && answer.length() <= 20){ // hide 6 letters
           return GetResultIndexes(rand, limit, 6);
        }
        else //hide 12 letters
        {
           return  GetResultIndexes(rand, limit, 12);
        }
    }

    private ArrayList<Integer> GetResultIndexes(Random rand, int limit, int letterOmitCount) {
        ArrayList<Integer> results;
        results = new ArrayList<>();
        while(letterOmitCount > 0 ){
            int r = rand.nextInt(limit);
            results.add(r);
            letterOmitCount --;
        }
        return results;
    }

    private Question GetQuestionsAndTheirAnswers(JsonObject jsonObject) throws JSONException {
        Question newQuestion = new Question();

            newQuestion.setQuestion(jsonObject.get("question").toString());
            newQuestion.setAnswer(jsonObject.get("answer").toString());

        return newQuestion;
    }


}

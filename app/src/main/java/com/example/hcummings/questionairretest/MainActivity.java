package com.example.hcummings.questionairretest;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;
import org.apache.commons.lang3.ArrayUtils;

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
    private AutoFitGridLayout answerGrid;
    private AutoFitGridLayout answerGrid2;
    private AutoFitGridLayout answerGrid3;
    private String question;
    private String answer;
    private String category;
    private TextView timer;
    private ImageView questionsGet;
    private TextView myScoreText;
    private TextView answerTv;
    private QuestionApi questionsApi;
    private AutoFitGridLayout clueGrid;
    private RelativeLayout questionAndAnswer;
    private CountDownTimer cdTimer;
    private int value;
    // private CardView questionCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jservice.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         questionsApi = retrofit.create(QuestionApi.class);

      //  questionCard = (CardView)findViewById(R.id.question_card);
        questionsTv = (TextView)findViewById(R.id.questions);
        answerTv = (TextView)findViewById(R.id.answers);
        answerGrid = (AutoFitGridLayout)findViewById(R.id.answer_tiles);
        answerGrid2 = (AutoFitGridLayout)findViewById(R.id.answer_tiles2);
        answerGrid3 = (AutoFitGridLayout)findViewById(R.id.answer_tiles3);
        questionAndAnswer = (RelativeLayout)findViewById(R.id.q_and_a);
        clueGrid = (AutoFitGridLayout)findViewById(R.id.clue_tiles);

         timer = (TextView) findViewById(R.id.timer);

        questionsGet = (ImageView) findViewById(R.id.get_q);
        myScoreText = (TextView) findViewById(R.id.my_score);

        if (questionsGet != null) {
            questionsGet.setOnClickListener(NewQuestionListener(questionsApi, answerTv, clueGrid));
        }
    }

    private void ShowAnswer() {
        if(answerTv.getVisibility() == View.INVISIBLE)
        answerTv.setVisibility(View.VISIBLE);
    }

    @NonNull
    private View.OnClickListener NewQuestionListener(final QuestionApi questionsApi, final TextView answerTv, final AutoFitGridLayout clueGrid) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateNewQuestion();
            }
        };
    }

    private void GenerateNewQuestion() {
        ResetAnswerGrids();
        ShowQuestionSection();
        // questionsGet.setVisibility(View.INVISIBLE);
        answerTv.setVisibility(View.INVISIBLE);

        questionsTv.setText("loading.....");
        Map<String, String> qStringOptions = new HashMap<>();
        qStringOptions.put("count","1");
        String path = "random";

        Call<List<Question>> questions = questionsApi.getRandomQuestions(path, qStringOptions);

        questions.enqueue(new Callback<List<Question>>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (questionsTv != null) {

                    if(response.isSuccessful())
                    {
                        StartTimer();
                        try {

                            List<Question> questions = response.body();

                             question = questions.get(0).getQuestion();
                             answer = questions.get(0).getAnswer();
                             category = questions.get(0).getCategory().getTitle();
                             value = questions.get(0).getValue();

                            answer = SanitizeAnswer(answer);

                            //set actionbar title and sub title
                            if(getSupportActionBar() !=null) {
                                getSupportActionBar().setTitle(category);
                                getSupportActionBar().setSubtitle(String.valueOf(value));
                            }

                            ArrayList<Integer> spaceIndexes = GetRandomSpaceIndexes(answer);

                            ShowAnswerGrids(answer.length());

                            String alphabet = getString(R.string.alphabet);

                            char[] clueLetters = GetLettersToOmit(answer, spaceIndexes);

                            int remSize = 10 - spaceIndexes.size();
                            char[] remLetters = GetRandomLetters(remSize, alphabet);

                            char[] gameString = ArrayUtils.addAll(remLetters, clueLetters);

                            //scramble letters
                            String  gameReadyString = ScrambleLetters(gameString);

                           // clueGrid.set();
                            for(int i =0; i < clueGrid.getChildCount(); i++ ){
                                char l = gameReadyString.charAt(i);

                                //get all the clue letters from the server
                                //and add them to the clues grid
                                CardView card =  (CardView)clueGrid.getChildAt(i);
                                card.setCardElevation(0);
                                TileView icon;

                                if(card.getChildCount() < 1)
                               continue;

                                icon =   (TileView)card.getChildAt(0);

                               card.setOnDragListener(new LetterDragListener());
                                icon.setOnTouchListener(new GridTouchListener());

                                String url = getLetterUrl(false, l, false);
                                if(!url.equals(""))
                                    Picasso
                                    .with(getApplicationContext())
                                    .load(url)
                                    .placeholder(R.drawable.ic_more_48)
                                    .into(icon);
                            }

                            //add the letters to the grid
                            for(int i = 0 ; i < answer.length(); i ++) {
                                TileView icon;
                                char letter = answer.charAt(i);

                                icon = getImageViewForLetter(i);

                                CardView card = (CardView) icon.getParent();

                                card.setOnDragListener(new LetterDragListener());
                                card.setCardElevation(0);
                                icon.setOnTouchListener(new GridTouchListener());



                                    if(answer.charAt(i) == ' ') {
                                       // icon.setBackground(new ColorDrawable(Color.parseColor("#efefef")));
                                    }
                                    else if(spaceIndexes.contains(i)){
                                        String url = getLetterUrl(false,'-', true);
                                        icon.setTag('-');
                                        if (!url.equals(""))
                                            Picasso.with(getApplicationContext())
                                                    .load(url)
                                                    .into(icon);
                                    }
                                    else {
                                        String url = getLetterUrl(false, letter, false);
                                        icon.setTag(letter);

                                        if (!url.equals(""))
                                            Picasso.with(getApplicationContext())
                                                    .load(url)
                                                    .into(icon);
                                    }
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
                    cdTimer = null;
                }
            }
        });
    }

    private final class GridTouchListener implements View.OnTouchListener {
        final int[] location = new int[2];

        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    private void ShowQuestionSection() {
      //  YoYo.with(Techniques.FadeIn).duration(700).playOn(questionAndAnswer);
    }

    private void StartTimer() {
       cdTimer =  new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("secs remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("time's up!");
                //StopRound();
                questionsGet.setVisibility(View.VISIBLE);
                ShowAnswer();
            }
        };
        cdTimer.start();
    }

    private TileView getImageViewForLetter(int i) {
        TileView icon;
        if(i < 10)               // first row
            icon = (TileView)((CardView)answerGrid.getChildAt(i)).getChildAt(0);
        else if(i > 9 && i < 20) // second row
            icon = (TileView)((CardView)answerGrid2.getChildAt(i - 10)).getChildAt(0);
        else                     // third row
            icon = (TileView)((CardView) answerGrid3.getChildAt(i - 20)).getChildAt((0));
        return icon;
    }

    private char[] GetLettersToOmit(String answer, ArrayList<Integer> spaceIndexes) {
       char[] returnList = new char[spaceIndexes.size()];

        int index = 0;
        while(index < spaceIndexes.size()){
            int spot = spaceIndexes.get(index);
            char spotChar = answer.charAt(spot);

                returnList[index] = spotChar;
                index ++;
        }

        return returnList;
    }

    private void ResetAnswerGrids() {
        answerGrid2.setVisibility(View.GONE);
        answerGrid3.setVisibility(View.GONE);

        for(int i = 0; i < answerGrid.getChildCount(); i ++){
            CardView container = (CardView)answerGrid.getChildAt(i);
            ((TileView)container.getChildAt(0)).setImageDrawable(null);
        }

        for(int i = 0; i < answerGrid2.getChildCount(); i ++){
            CardView container = (CardView)answerGrid2.getChildAt(i);
            ((TileView)container.getChildAt(0)).setImageDrawable(null);
        }

        for(int i = 0; i < answerGrid3.getChildCount(); i ++){
            CardView container = (CardView)answerGrid3.getChildAt(i);
            ((TileView)container.getChildAt(0)).setImageDrawable(null);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ShowAnswerGrids(int length) {
        answerGrid.setVisibility(View.VISIBLE);

        for(int i = 0; i < answerGrid.getChildCount(); i ++){
            CardView container = (CardView)answerGrid.getChildAt(i);
            container.setCardElevation(0);
            container.setOnDragListener(new LetterDragListener());
            container.getChildAt(0).setOnTouchListener(new GridTouchListener());
        }

        if(length > 10) {
            answerGrid2.setVisibility(View.VISIBLE);
            for(int i = 0; i < answerGrid2.getChildCount(); i ++){
                CardView container = (CardView)answerGrid2.getChildAt(i);
                container.setCardElevation(0);
               container.setOnDragListener(new LetterDragListener());
                container.getChildAt(0).setOnTouchListener(new GridTouchListener());
            }
        }

        if(length > 20) {
            answerGrid3.setVisibility(View.VISIBLE);
            for(int i = 0; i < answerGrid3.getChildCount(); i ++){
                CardView container = (CardView)answerGrid3.getChildAt(i);
                container.setCardElevation(0);
                container.setOnDragListener(new LetterDragListener());
                container.getChildAt(0).setOnTouchListener(new GridTouchListener());
            }
        }

    }

    private String SanitizeAnswer(String answer) {
        String result1 = answer.replace("<i>","");
        String result2 = result1.replace("(", "");
        String result3 = result2.replace(")", "");
        String result4 = result3.replace("</i>", "");
        String result5 = result4.replace(".", "");
        String result6 = result5.replace(",", "");
        String result7 = result6.replace("\\'", "");
        String result8 = result7.replace('"', ' ');

        return result8.trim();
    }

    private String getLetterUrl(boolean firstLetter, char letter, boolean blocked) {
      String pre = "https://storage.googleapis.com/ballrz/icons/";
      String mid = String.valueOf(letter).toUpperCase() ;
      String postLower = "%20Lowercase-52.png";
      String postUpper = "-52.png";
      String blank = "Minus-48.png";
        if(letter == 'u')
        {

        }

        if(blocked)
        {
            return new StringBuilder()
                    .append(pre)
                    .append(blank)
                    .toString();
        }
        else {
           return new StringBuilder()
                    .append(pre)
                    .append(mid)
                    .append(!firstLetter ? postUpper : postLower)
                    .toString();
        }
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
        int len = answer.length();
        int limit = len -1;

        if(len <= 5) { // hide 2 letters
            return GetResultIndexes(answer, rand, limit, 2);
        }
        else if(len > 5 && len < 10){ // hide 4 letters
           return GetResultIndexes(answer, rand, limit, 4);
        }
        else if (len >= 10 && len <= 20){ // hide 6 letters
           return GetResultIndexes(answer, rand, limit, 6);
        }
        else //hide 9 letters
        {
           return  GetResultIndexes(answer,rand, limit, 9);
        }
    }

    private ArrayList<Integer> GetResultIndexes(String answer, Random rand, int limit, int letterOmitCount) {
        ArrayList<Integer> results;
        results = new ArrayList<>();
        while(letterOmitCount > 0 ){
            int r = rand.nextInt(limit);

            //don't add if it's a space or if the index already occurs in the index list
            if(!results.contains(r) &&  answer.charAt(r) != ' ' &&  answer.charAt(r) != '-'){
                results.add(r);
                letterOmitCount --;
            }
        }
        return results;
    }

    class LetterDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            CardView newCard = (CardView) v;
            View view = (View) event.getLocalState();
            CardView oldCard = (CardView) view.getParent();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    try {
                        SwapLetterWithDash(newCard, oldCard);
                        ValidatePlay();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                default:
                    break;
            }
            return true;
        }
    }

    private void SwapLetterWithDash(CardView newCard, CardView oldCard) {
        ImageView movingIcon = (ImageView) oldCard.getChildAt(0);
        oldCard.removeView(movingIcon);
        ImageView containerIcon = (ImageView) newCard.getChildAt(0);
        newCard.removeView(containerIcon);
        newCard.addView(movingIcon);
        oldCard.addView(containerIcon);
//        YoYo.with(Techniques.Pulse).duration(700).playOn(containerIcon);
//        YoYo.with(Techniques.Pulse).duration(700).playOn(movingIcon);
    }

    private void ValidatePlay() { // todo needs to be async
        StringBuilder builder = new StringBuilder();

        List<AutoFitGridLayout> playedAnswer = new ArrayList<>();
        playedAnswer.add(answerGrid);

        if(answer.length() > 10)
            playedAnswer.add(answerGrid2);

        if(answer.length() > 20)
            playedAnswer.add(answerGrid3);

        for(int i=0; i < playedAnswer.size(); i++){

            AutoFitGridLayout grid = playedAnswer.get(i);
             for(int g =0; g < grid.getChildCount(); g++)
             {
                 CardView card = (CardView) grid.getChildAt(g);
                 TileView tile = (TileView) card.getChildAt(0);

                 if(tile == null || tile.getTag() == null)
                     continue;

                 char tagLetter = (char) tile.getTag();
                 builder.append(tagLetter);
             }
        }
        String saneAnswer = answer.replaceAll("\\s+","");
        String saneBuilder = builder.toString().replaceAll("\\s+","");

            if(saneAnswer.equals(saneBuilder)){
            Toast.makeText(getApplicationContext(), "correct!", Toast.LENGTH_LONG).show();
                CalculateScores();
                ResetTimer();
                GenerateNewQuestion(); //todo call this async
        }
    }

    private void CalculateScores() {

        int currentScore = Integer.parseInt(myScoreText.getText().toString());
        int result = currentScore + value;
        myScoreText.setText(result);
    }

    private void ResetTimer() {
        cdTimer = null;
        timer.setText("get ready..");
        StartTimer();
    }

}

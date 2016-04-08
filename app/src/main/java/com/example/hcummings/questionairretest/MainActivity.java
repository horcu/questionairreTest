package com.example.hcummings.questionairretest;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.LinearLayout;
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
    private AutoFitGridLayout answerGrid4;
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
    private static int runningTotal = 0;
    private LinearLayout answerGrids;
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
        answerGrid4 = (AutoFitGridLayout)findViewById(R.id.answer_tiles4);
        questionAndAnswer = (RelativeLayout)findViewById(R.id.q_and_a);
        clueGrid = (AutoFitGridLayout)findViewById(R.id.clue_tiles);
        answerGrids = (LinearLayout)findViewById(R.id.answer_grids);

         timer = (TextView) findViewById(R.id.timer);

        questionsGet = (ImageView) findViewById(R.id.get_q);
        myScoreText = (TextView) findViewById(R.id.my_score);

        if (questionsGet != null) {
            questionsGet.setOnClickListener(NewQuestionListener());
        }
    }

    private void ShowAnswer() {
        if(answerTv.getVisibility() == View.INVISIBLE)
        answerTv.setVisibility(View.VISIBLE);
    }

    @NonNull
    private View.OnClickListener NewQuestionListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateNewQuestion();
            }
        };
    }

    private void GenerateNewQuestion() {
        ResetAnswerGrids();
        answerTv.setVisibility(View.INVISIBLE);
        answerGrids.setVisibility(View.VISIBLE);
        clueGrid.setVisibility(View.VISIBLE);
        questionsTv.setText("loading.....");
        Map<String, String> qStringOptions = new HashMap<>();
        qStringOptions.put("count","20");
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
                            answer = makeUpper(answer);

                            //set actionbar title and sub title
                            if(getSupportActionBar() !=null) {
                                getSupportActionBar().setTitle(category);
                                getSupportActionBar().setSubtitle(String.valueOf(value));
                            }

                            ArrayList<Integer> spaceIndexes = GetRandomSpaceIndexes(answer);

                            String alphabet = getString(R.string.alphabet);

                            char[] clueLetters = GetLettersToOmit(answer, spaceIndexes);

                            int remSize = 10 - spaceIndexes.size();
                            char[] remLetters = GetRandomLetters(remSize, alphabet.toUpperCase());

                            char[] gameString = ArrayUtils.addAll(remLetters, clueLetters);

                            //scramble letters
                            String  gameReadyString = ScrambleLetters(gameString);

                           // clueGrid.set();
                            for(int i =0; i < clueGrid.getChildCount(); i++ ){
                                char l = gameReadyString.charAt(i);

                                //get all the clue letters from the server
                                //and add them to the clues grid
                                CardView card =  (CardView)clueGrid.getChildAt(i);
                                card.setCardElevation(1);
                                TileView icon;

                                if(card.getChildCount() < 1)
                               continue;

                                icon =   (TileView)card.getChildAt(0);
                                icon.setLetter(l);
                                card.setOnDragListener(new LetterDragListener());
                                icon.setOnTouchListener(new GridTouchListener());

                                icon.setLetter(l);
//                                String url = getLetterUrl(false, l, false);
//                                if(!url.equals(""))
//                                    Picasso
//                                    .with(getApplicationContext())
//                                    .load(url)
//                                    .placeholder(R.drawable.ic_more_48)
//                                    .into(icon);
                            }

                            //add the letters to the grid
                            for(int i = 0 ; i < answer.length(); i ++) {
                                TileView icon;
                                char letter = answer.charAt(i);

                                icon = getImageViewForLetter(i);
                              //  icon.setBackgroundColor(Color.parseColor("#efefef"));
                                CardView card = (CardView) icon.getParent();
                                //card.setBackground(new ColorDrawable(Color.TRANSPARENT));
                                card.setOnDragListener(new LetterDragListener());
                                card.setCardElevation(1);
                                icon.setOnTouchListener(new GridTouchListener());

                                    if(answer.charAt(i) == ' ') {
                                        icon.setLetter(' ');
                                    }
                                    else if(spaceIndexes.contains(i)){
                                        String url = getLetterUrl(false,'-', true);
                                        icon.setLetter('_');
//                                        if (!url.equals(""))
//                                            Picasso.with(getApplicationContext())
//                                                    .load(url)
//                                                    .into(icon);
                                    }
                                    else {
                                        String url = getLetterUrl(false, letter, false);
                                        icon.setLetter(letter);

//                                        if (!url.equals(""))
//                                            Picasso.with(getApplicationContext())
//                                                    .load(url)
//                                                    .into(icon);
                                    }
                            }

                            ShowAnswerGrids(answer.length());
                            questionsTv.setText(question);
                            if (answerTv != null) {
                                answerTv.setText(answer);
                            }

                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            Log.d(QUESTION_GET, e.getMessage() + " : " + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    Log.d(QUESTION_GET, response.message());
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

    private String makeUpper(String answer) {
        return answer.toUpperCase();
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
        YoYo.with(Techniques.FadeIn).duration(700).playOn(questionAndAnswer);
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
        if(i >= 0 && i < 10)               // first row
            icon = (TileView)((CardView)answerGrid.getChildAt(i)).getChildAt(0);
        else if(i > 9 && i < 20) // second row
            icon = (TileView)((CardView)answerGrid2.getChildAt(i - 10)).getChildAt(0);
        else if(i > 19 && i < 30) // third row
            icon = (TileView)((CardView) answerGrid3.getChildAt(i - 20)).getChildAt((0));
        else
            icon = (TileView)((CardView) answerGrid4.getChildAt(i - 30)).getChildAt((0));

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

        for(int i = 0; i < answerGrid.getChildCount(); i ++){
            CardView container = (CardView)answerGrid.getChildAt(i);
            container.setBackgroundColor(Color.LTGRAY);
            ((TileView)container.getChildAt(0)).setImageDrawable(null);
            ((TileView)container.getChildAt(0)).setLetter(' ');
        }

        for(int i = 0; i < answerGrid2.getChildCount(); i ++){
            CardView container = (CardView)answerGrid2.getChildAt(i);
            container.setBackgroundColor(Color.LTGRAY);
            ((TileView)container.getChildAt(0)).setImageDrawable(null);
            ((TileView)container.getChildAt(0)).setLetter(' ');
        }

        for(int i = 0; i < answerGrid3.getChildCount(); i ++){
            CardView container = (CardView)answerGrid3.getChildAt(i);
            container.setBackgroundColor(Color.LTGRAY);
            ((TileView)container.getChildAt(0)).setImageDrawable(null);
            ((TileView)container.getChildAt(0)).setLetter(' ');
        }

        for(int i = 0; i < answerGrid4.getChildCount(); i ++){
            CardView container = (CardView)answerGrid4.getChildAt(i);
            container.setBackgroundColor(Color.LTGRAY);
            ((TileView)container.getChildAt(0)).setImageDrawable(null);
            ((TileView)container.getChildAt(0)).setLetter(' ');
        }
         answerGrid.setVisibility(View.GONE);
        answerGrid3.setVisibility(View.GONE);
        answerGrid3.setVisibility(View.GONE);
        answerGrid4.setVisibility(View.GONE);
    }


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

        if(length > 30) {
            answerGrid4.setVisibility(View.VISIBLE);
            for(int i = 0; i < answerGrid4.getChildCount(); i ++){
                CardView container = (CardView)answerGrid4.getChildAt(i);
                container.setCardElevation(0);
                container.setOnDragListener(new LetterDragListener());
                container.getChildAt(0).setOnTouchListener(new GridTouchListener());
            }
        }

    }

    private String SanitizeAnswer(String answer) {
        return answer
                .replace("<i>","")
                .replace("(", "")
                .replace(")", "")
                .replace("</i>", "")
                .replace(".", "")
                .replace(",", "")
                .replace("\\'", "")
                .replace('"', ' ')
                .replace("\\[", "")
                .replace("\\]", "");
    }

    private String getLetterUrl(boolean firstLetter, char letter, boolean blocked) {
      String pre = "https://storage.googleapis.com/ballrz/icons/";
      String mid = String.valueOf(letter).toUpperCase() ;
      String postLower = "%20Lowercase-52.png";
      String postUpper = "-52.png";
      String blank = "Minus-48.png";

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
        newCard.addView(movingIcon,0);
        oldCard.addView(containerIcon,0);
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

        if(answer.length() > 30)
            playedAnswer.add(answerGrid4);

        for(int i=0; i < playedAnswer.size(); i++){

            Log.d("ValidatePlay:", "checking all answer grids to get the full answer char list ");
            AutoFitGridLayout grid = playedAnswer.get(i);

            Log.d("ValidatePlay:", "iterating grids children");
             for(int g = 0; g < grid.getChildCount(); g++)
             {
                 CardView card = (CardView) grid.getChildAt(g);
                 TileView tile = (TileView) card.getChildAt(0);

//                 if(tile.getLetter() == ' ')
//                     continue;

                 char tagLetter = tile.getLetter();

                 Log.d("ValidatePlay:", "tag letter is " + tagLetter + " it's the char at index "+ g);
                 builder.append(tagLetter);

             }
        }
        Log.d("ValidatePlay:", "done iterating grids children");
        Log.d("ValidatePlay:", "raw answer value:" + answer);

        String guessedString = builder.toString().replaceAll("[^\\p{L}\\p{Nd}]+", "").trim();
        String answerParsed = answer.replaceAll("[^\\p{L}\\p{Nd}]+", "").trim();
        Log.d("ValidatePlay: builder", guessedString);

            if(answerParsed.equalsIgnoreCase(guessedString) || guessedString.contentEquals(answerParsed) || guessedString.contains(answerParsed)){
            Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_LONG).show();
                CalculateScores();//todo call this async maybe from the postExecute function in the async to be called for the validate method
                ResetTimer();
                ShowAnswer();
                GenerateNewQuestion();
        }
    }

    private void CalculateScores() {

        try {
            int currentScore = runningTotal;
            int result = currentScore + value;
            myScoreText.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.d("calculating score: ", e.getMessage());
        }
    }

    private void ResetTimer() {
        cdTimer = null;
        timer.setText("get ready..");
        StartTimer();
    }

}

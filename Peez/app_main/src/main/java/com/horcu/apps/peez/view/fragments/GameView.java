package com.horcu.apps.peez.view.fragments;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.SpannableStringBuilder;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.backend_gameboard.gameApi.model.Game;
import com.horcu.apps.peez.chat.LeBubbleTitleTextView;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.AutoFitGridLayout;
import com.horcu.apps.peez.Dtos.MMDto;
import com.horcu.apps.peez.custom.CircleTransform;
import com.horcu.apps.peez.custom.GameBuilder;
import com.horcu.apps.peez.custom.Gameboard.GameDialogHelper;
import com.horcu.apps.peez.custom.Gameboard.TileMover;
import com.horcu.apps.peez.custom.MaterialLetterIcon;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.custom.Gameboard.TileView;
import com.horcu.apps.peez.custom.UserImageView;
import com.horcu.apps.peez.databinding.FragmentGameViewBinding;
import com.horcu.apps.peez.enums.MoveType;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.model.MoveEntry;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.service.QuestionService;
import com.horcu.apps.peez.utils.gameViewUtil;
import com.horcu.apps.peez.view.activities.ChallengeView;
import com.horcu.apps.peez.view.activities.MainView;
import com.squareup.picasso.Picasso;
import org.apache.commons.lang3.ArrayUtils;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import at.markushi.ui.CircleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected FragmentGameViewBinding binding;

    private OnFragmentInteractionListener mListener;
    private LoggingService.Logger mLogger;
    private SenderCollection mSenders;
    private String myToken;
    private String currentSpot;
    private SharedPreferences settings;
    private Player opponent;
    private String gameKey;

    private String playerTurn;

    private Boolean isGameMenuOpen;

    int[] rangeA = new int[]{0,1,6,7,12,13, 18,19, 24,25, 30,31};
    int[] rangeB = new int[]{2,3,8,9,14,15, 20,21, 26,27, 32,33};
    int[] rangeC = new int[]{4,5,10,11,16,17, 22,23, 28,29, 34,35};

    int[] rangeTop = new int[]{2,3,8,9,14,15};
    int[] rangeBottom = new int[]{20,21, 26,27, 32,33};

    int[] colorArray ;

    GameBuilder gameBuilder = null;

    int chosenColorIndex = 0;
    private Retrofit retrofit;
    private QuestionService qService;
    private boolean mQuestionServiceIsBusy = false;
    private GameDialogHelper dialogHelper;
    private CardView CurrentOldCard;
    private CardView CurrentNewCard;
    private View CurrentMovingView;


    public GameView() {
        // Required empty public constructor
    }

    public Boolean UpdateGameInfo(String gameKey, String playerName, String token, String playerImageUrl){
        try {
            opponent.setCanBeMessaged(true);
            opponent.setCurrentlyOnLine(true);
            opponent.setCurrentlyPlaying(true);
            opponent.setImageUri(playerImageUrl);
            opponent.setUserName(playerName);
            opponent.setToken(token);
            this.gameKey = gameKey;

            View layout = binding.userInfoLayout.findViewById(R.id.user_info_layout);

            UpdateIcon(layout);

            int chosenColor = gameViewUtil.GetFavoriteColor(getContext());
            UpdateOpponent(chosenColor, layout);
           // UpdateUserInfoSectionBGColor();

            return true;

        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    private void UpdateUserInfoSectionBGColor() {
        ((ViewGroup)binding.userInfoLayout).getChildAt(0).setBackgroundColor(Color.WHITE);
    }

    private void UpdateOpponent(int col, View layout) {
        TextView tv = (TextView) layout.findViewById(R.id.opponent_info_text);
        tv.setText(opponent.getUserName() == null || opponent.getUserName().equals("") ? "choose your opponent.." : opponent.getUserName());
        tv.setTextColor(col);


    }

    private void UpdateIcon(View layout) {
        UserImageView userImage = (UserImageView)layout.findViewById(R.id.opponent_img);
        Picasso.with(getContext())
                .load(opponent.getImageUri())
                .transform(new CircleTransform())
                .into(userImage);
    }

    // TODO: Rename and change types and number of parameters
    public static GameView newInstance() {
        GameView fragment = new GameView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        settings =getActivity().getSharedPreferences(consts.PEEZ, 0);
        mLogger = new LoggingService.Logger(getContext());
        mSenders = SenderCollection.getInstance(getActivity());
        opponent = new Player();
        gameBuilder = new GameBuilder(getContext());
        chosenColorIndex = settings.getInt(consts.FAV_COLOR, 0);

        retrofit = new Retrofit.Builder()
                .baseUrl(consts.QUESTIONS_BASE_URL)
                .build();

        qService = retrofit.create(QuestionService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dialogHelper = new GameDialogHelper(getContext());

        binding.gameboardGridOpponent.setVisibility(View.VISIBLE);
        binding.gameboardGridPlayer.setVisibility(View.VISIBLE);

        binding.tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewSwitcher.showNext();
            }
        });




        Game game = GameBuilder.CreateOrGetGameboard(gameKey, false);
        //TODO then set the binding for the viewmodel for the game board

        colorArray =  getActivity().getResources().getIntArray(R.array.Colors);
        myToken = settings.getString(consts.REG_ID,"");

        //first move
        if(currentSpot == null)
            setCurrentSpot("0");

        int halfWayMark = (consts.TOTAL_TILES / 2);

        Random rand = new Random(0);
        int min = 0;
        int max = colorArray.length - 1;
        int randIndex =  GetRandomColorndex(min, max);

        while (randIndex == chosenColorIndex)
            randIndex = GetRandomColorndex(min, max);

        for(int i = 0; i < consts.TOTAL_TILES; i ++)
        {
            final CardView card = (CardView) binding.gameboardGrid.getChildAt(i);
            card.getChildAt(0).setTag(consts.GAMEVIEW_ORIGINAL_ICON_INDEX, i);
            card.setTag(consts.GAMEVIEW_CARD_INDEX, i);
            SetCardTagsAndListeners(i, card, consts.CARD_TYPE_GAMEBOARD);
            SetCardIconTagsAndListeners(i, card);

            if((i + 1) > halfWayMark)
                ((MaterialLetterIcon)card.getChildAt(0)).setShapeColor(colorArray[chosenColorIndex]);
            else
                ((MaterialLetterIcon) card.getChildAt(0)).setShapeColor(colorArray[randIndex]);
        }

        for (int o = 0; o < consts.TOTAL_PLAYERS_TILES; o ++)
        {
            final CardView opponentCard = (CardView) binding.gameboardGridOpponent.getChildAt(o);
            SetCardTagsAndListeners(o,opponentCard,consts.CARD_TYPE_OPPONENT_HOME);

            final CardView playerCard = (CardView) binding.gameboardGridPlayer.getChildAt(o);
            SetCardTagsAndListeners(o,playerCard,consts.CARD_TYPE_PLAYER_HOME);
        }

        for(int i = 0 ; i < binding.tapBarMenu.getChildCount(); i++)
        {
            final LinearLayout layout = (LinearLayout) binding.tapBarMenu.getChildAt(i);
            layout.setTag(consts.GAMEVIEW_MENU_INDEX, i);
            final MoveType[] mt = {MoveType.REG};
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(!getGameMenuIsOpen())
                            return;

                        int position = (int) v.getTag(consts.GAMEVIEW_MENU_INDEX);
                        switch(position)
                        {
                            case 0:
                                TileMover.SetSnare(CurrentOldCard, CurrentNewCard);
                                PlaySoundEffect(MainView.SoundEffects.EFFECT_SNARE);
                                mt[0] = MoveType.TRAP;
                                enableSwiping();
                                closeMenu();
                                break;
                            case 1:
                                TileMover.EatTile(CurrentOldCard, CurrentNewCard);
                                PlaySoundEffect(MainView.SoundEffects.EFFECT_EAT);
                                mt[0] = MoveType.EAT;
                                enableSwiping();
                                closeMenu();
                                break;
                            case 2:
                                PlaySoundEffect(MainView.SoundEffects.EFFECT_SWAP);
                                mt[0] = MoveType.SWAP;
                                enableSwiping();
                                closeMenu();
                                break;
                            default:
                                TileMover.ReverseLastPlay();
                                PlaySoundEffect(MainView.SoundEffects.EFFECT_BACK);
                                enableSwiping();
                                closeMenu();
                                break;
                        }
                        MoveEntry move = BuildMoveEntryRecord(CurrentOldCard, CurrentNewCard,mt[0], false);
                        addMoveToDBAsync(move);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return root;
    }

    private void addMoveToDBAsync(MoveEntry moveEntry) {
        //TODO to be implemented as soon s the realm issues are all resolved
    }

    private MoveEntry BuildMoveEntryRecord(CardView currentOldCard, CardView currentNewCard, MoveType type, Boolean moveAlreadyMade) {
        String email = settings.getString(consts.PREF_ACCOUNT_NAME, "");
        int moveFrom = (int)currentOldCard.getTag(consts.GAMEVIEW_CARD_INDEX);
        int moveTo = (int)currentNewCard.getTag(consts.GAMEVIEW_CARD_INDEX);
        return new MoveEntry(email,opponent.getEmail(), new Date().toString(), type, moveFrom, moveTo, moveAlreadyMade);
    }


    private void PlaySoundEffect(MainView.SoundEffects effect) {
        mListener.onPlaySoundEffects(effect);
    }

    private void closeMenu() {
        YoYo.with(Techniques.FadeOutDown).duration(250).playOn(binding.tapBarMenu);
        isGameMenuOpen = false;
    }


    private boolean getGameMenuIsOpen() {
        return isGameMenuOpen != null ? isGameMenuOpen : false;
    }

    private void setGameMenuIsOpen(){
        isGameMenuOpen = true;
    }

    private void setGameMenuIsClosed(){
        isGameMenuOpen = false;
    }


    private int GetRandomColorndex(int min, int max) {
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((max - min) + 1) + min;
    }

    private void SetCardIconTagsAndListeners(int i, CardView card) {
        MaterialLetterIcon icon = (MaterialLetterIcon) card.getChildAt(0);
        icon.setTag(i);

        icon.setOnTouchListener(new TileTouchListener());
    }

    private void SetCardTagsAndListeners(int i, CardView card, String type) {
        card.setTag(i);
        card.setTag(R.string.gameboard_card_type, type);
        //card.setOnClickListener(HandleTileClick(i));
        card.setOnDragListener(new playerPieceDragListener());
    }

    private void SetBubbleArrowLocation(int i, LeBubbleTitleTextView infoBox) {
        int direction = GetBubbleArrowDirectionForCurrentTile(i);
        infoBox.setCurDirection(direction);
    }

    private int GetBubbleArrowDirectionForCurrentTile(int i) {

        if(ArrayUtils.contains(rangeA, i)) // left arrow
        {
            return 1;
        }
             else if (ArrayUtils.contains(rangeB, i)) // bottom or top arrow
             {
                 if(ArrayUtils.contains(rangeTop, i)) // top
                 {
                 return 2;
                 }
                 else if(ArrayUtils.contains(rangeBottom, i)) // bottom
                 {
                     return 4;
                 }
             }
             else if (ArrayUtils.contains(rangeC, i)) // right arrow
             {
                 return 3;
             }
        return 4;
    }

    private void SetLayoutParams(CircleButton playerPiece) {
        AutoFitGridLayout.LayoutParams params = new AutoFitGridLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.width = 50;
        params.height = 50;
        playerPiece.setLayoutParams(params);
    }

    private void SetPlayerPieceBgColor(CircleButton playerPiece) {
        playerPiece.setBackground(new ColorDrawable(Color.LTGRAY));
    }

    @NonNull
    private View.OnClickListener HandleTileClick(final int finalI) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!MyTurn())
                {
                    Toast.makeText(getContext(),"Not your turn",Toast.LENGTH_SHORT).show();
                return;
                }
                String transitionName = getString(R.string.tileTransition);
              //  ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, transitionName);
                Intent intent = new Intent(getContext(), ChallengeView.class);
                //put info to pass in here
                intent.putExtra(consts.EXTRAS_MOVE_TO, finalI);
             //   startActivityForResult(intent, consts.INTENT_TO_CHALLENGE, transitionActivityOptions.toBundle());
            }
        };
    }

    private final class TileTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(getGameMenuIsOpen()) {
                Toast.makeText(getContext(), "one move at a time", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!MyTile(view))
            {
                Toast.makeText(getContext(), "play your own tiles", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);

                //view.setVisibility(View.INVISIBLE);
                //SuperSizeView(view);
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean MyTile(View view) {
        int index = (int)view.getTag(consts.GAMEVIEW_ORIGINAL_ICON_INDEX);
        int myFirstSpot = consts.TOTAL_TILES/2;
        return (index)  >= myFirstSpot;
    }

    private void superSize(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = params.width*2;
        params.height = params.height*2;
        view.setLayoutParams(params);
    }

    private void unSuperSize(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = params.width/2;
        params.height = params.height/2;
        view.setLayoutParams(params);
    }

    private void SuperSizeView(View view) {
        view.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = view.getMeasuredWidth()*2;
        params.height = view.getMeasuredHeight()*2;
        view.setLayoutParams(params);
    }

    private void UnSuperSizeView(View view) {
        view.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = view.getMeasuredWidth()/2;
        params.height = view.getMeasuredHeight()/2;
        view.setLayoutParams(params);
    }

    class playerPieceDragListener implements View.OnDragListener {
        int colorIndex = settings.getInt(consts.FAV_COLOR,0);

        int[] colorArray = getResources().getIntArray(R.array.Colors);
        int favColor = colorArray[colorIndex];
        int userFavColor = R.color.grey_mid;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            CardView container = (CardView)v;
            View view = (View) event.getLocalState();
            CardView owner = (CardView) view.getParent();

            CurrentOldCard = owner;
            CurrentNewCard = container;
            CurrentMovingView = view;

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    try {
                        //check if its my tile thats the destination
                        //if yes = > then dont allow it
                        //if no = >
                        //check if its the opponents tile
                             //if yes =>   then do a swap or eat
                             //if no  =>   check if its an empty tile
                                               //if yes => regular move or a trap
                                               //if no  => its a homeBasedTile ... yeah!!!!

                        TileMover.SwapTiles(CurrentOldCard, CurrentNewCard);
                        ShowBottomMenu();
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

    private void ShowBottomMenu() {
        DisableSwiping();
        YoYo.with(Techniques.SlideInUp).duration(250).playOn(binding.tapBarMenu);
        binding.tapBarMenu.setVisibility(View.VISIBLE);
        setGameMenuIsOpen();
    }

    private void DisableSwiping() {
        ((MainView)getActivity()).mViewPager.setPagingEnabled(false);
    }

    private void enableSwiping() {
        ((MainView)getActivity()).mViewPager.setPagingEnabled(true);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    GetQuestions("");
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

    private View CloneLetterIconView(MaterialLetterIcon swap) {
        Random rand = new Random(1);
        MaterialLetterIcon icon = new MaterialLetterIcon(getContext());
        icon.setLetterColor(swap.getLetterColor());
        icon.setLetter(String.valueOf(rand.nextInt()));
        icon.setShapeColor(swap.getShapeColor());

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.width = 30;
        params.height = 30;
        icon.setLayoutParams(params);
        return icon;
    }



    private boolean MyTurn() {
       // return playerTurn != null &&  playerTurn.equals(myToken);
        return true;
    }

    public void setCurrentSpot(String moveTo) {
        currentSpot = moveTo;
    }

    public String getCurrentSpot() {
       return currentSpot;
    }

    public void setCurrentPlayer(Player player) {
        opponent = player;
    }

    public Player getCurrentPLayer() {
        return opponent;
    }

    public void setPlayerTurn(String playerToken) {
        playerTurn = playerToken;
    }

    private String getPlayerTurn() {
        return playerTurn;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case consts.INTENT_TO_CHALLENGE:
            {
                if(data != null) {
                    Bundle extras = data.getExtras();
                    SendMoveResultsToOpponent(extras.getString(consts.EXTRAS_MOVE_TO));
                }
            }
        }
    }

    private void SendMoveResultsToOpponent(String moveTo) {

                Date d = new Date();
                long time = d.getTime();
                int color = gameViewUtil.GetFavoriteColor(getContext()); // getResources().getColor(settings.getInt(consts.FAV_COLOR, Color.parseColor("#551A8B")));

                String collapseKey = String.valueOf(UUID.randomUUID());

                MMDto dto = new MMDto(currentSpot, moveTo, "move made!", String.valueOf(time), myToken, opponent.getToken(), opponent.getImageUri(), color, collapseKey);
                String message = MessageSender.JsonifyMoveDto(dto);

                Message moveMessage = MessageSender.BuildMoveMessage(dto, message);

                MessageSender sender = new MessageSender(getActivity(), mLogger, mSenders);

                if(opponent.getToken() == null || myToken == null){
                    Toast.makeText(getActivity(), "no recipient chosen", Toast.LENGTH_LONG).show();
                }
                else if(opponent.getToken().equals("")){
                    Toast.makeText(getActivity(), "no recipient chosen", Toast.LENGTH_LONG).show();
                }
                else if (sender.SendMove(moveMessage)) {
                    ShowMoveOnBoard(moveMessage);
                    setCurrentSpot(moveMessage.getTo());
                    Toast.makeText(getActivity(), "sent!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "failed ;/", Toast.LENGTH_LONG).show();
                }
    }

    public void ShowMoveOnBoard(Message message) {
        try {
            int position = Integer.parseInt(message.getTo());
            CardView moveTo = (CardView) binding.gameboardGrid.getChildAt(position);
            CardView from = (CardView) binding.gameboardGrid.getChildAt(Integer.parseInt(message.getFrom()));

            from.setBackground(new ColorDrawable(Color.parseColor("#ffefefef")));
            from.setAlpha(.7f);
            from.setBackgroundResource(R.drawable.ic_mt);

            //create the new player dot
            TileView tv = AddButtonToCard(moveTo);

            moveTo.setBackground(new ColorDrawable(Color.LTGRAY));
            //move the player from the old spot
            if(currentSpot != "0")
            MovePlayerFromOldLocation(from);

            //move player to new spot
            MovePlayerToNewLocation(moveTo);

            //set up click and drag listeners
            SetPlayerDotListeners(tv,moveTo,from);

            //animate the player in the tile
            SetPlayerDotBackground(tv);

            //highlight the neighbours
            //String[] neighbours = tv.getNeighbours();
           // GlowNeighbours(neighbours);

            ShowSnack(binding.gameboardGrid.getRootView(), message);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void GlowNeighbours(String[] neighbours) {

    }

    private void SetPlayerDotListeners(TileView tv, View to, View from) {

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "clicked: " + v.getTag().toString(), Toast.LENGTH_LONG).show();
            }
        });

        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                YoYo.with(Techniques.Pulse).duration(3000).playOn(v);
                return true;
            }
        });

        tv.setOnDragListener(new View.OnDragListener() {

            @Override
            public boolean onDrag(View v, DragEvent event) {
                Toast.makeText(getContext(), "Attempting player dot dragged", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //TODO remove old listeners and update new listeners
    }

    private void MovePlayerToNewLocation(View v) {
        try {
            View playerDot = ((CardView)v).getChildAt(0);// TODO this is assuming that the first child will always be the player dot... this is not ideal. Maybe add the cards and circle dots using naming conventions so they can be found by id.. eg card_03 > cb_03 etc..
            YoYo.with(Techniques.FadeIn).duration(1000).playOn(playerDot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void MovePlayerFromOldLocation(View from) {
        try {
            View playerDot = ((CardView)from).getChildAt(0);// TODO this is assuming that the first child will always be the player dot... this is not ideal. Maybe add the cards and circle dots using naming conventions so they can be found by id.. eg card_03 > cb_03 etc..
            YoYo.with(Techniques.FadeOut).duration(500).playOn(playerDot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TileView AddButtonToCard(CardView card) {

        try {
            TileView tv = new TileView(getContext(), null);
            tv.setBackground(getResources().getDrawable(R.drawable.ic_mt));
            //CircleButton cb = new CircleButton(getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.height = 45;
            params.width = 45;
            tv.setLayoutParams(params);
            tv.setOval(true);
            tv.setPadding(1,1,1,1);
            tv.setTextColor(Color.WHITE);
            card.addView(tv);
            return tv;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void ShowSnack(View parent, Message message) {
        try {
            SpannableStringBuilder builder = new SpannableStringBuilder();
          //  builder.setSpan(new ImageSpan(getActivity(), R.mipmap.ic_launcher), builder.length() - 1, builder.length(), 0); //TODO user image here instead of default iconm
            builder.append("from ").append(message.getFrom());
            builder.append(" ");
            builder.append("to ").append(message.getTo());
            Snackbar.make(parent, builder, Snackbar.LENGTH_INDEFINITE).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SetPlayerDotBackground(TileView tileView) {
        int favColor = gameViewUtil.GetFavoriteColor(getContext());
        tileView.setBackground(new ColorDrawable(favColor));
        tileView.setTextColor(Color.WHITE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onPlaySoundEffects(MainView.SoundEffects effect);
    }


    private void GetQuestions(String url) {

            Call<List<String>> questions = qService.GetQuestions(!url.equals("") ? url : consts.QUESTIONS_API_RANDOM);
            mQuestionServiceIsBusy = true;
            questions.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    mQuestionServiceIsBusy = false;
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Toast.makeText(getContext(), "failed to get questions", Toast.LENGTH_SHORT).show();
                    mQuestionServiceIsBusy = false;
                }
            });
    }

    private boolean questionServiceIsBusy() {

        return mQuestionServiceIsBusy;
    }
}

package com.horcu.apps.peez.view.fragments;

import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.identity.intents.AddressConstants;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.backend_gameboard.gameApi.model.Game;
import com.horcu.apps.peez.chat.LeBubbleTitleTextView;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.AutoFitGridLayout;
import com.horcu.apps.peez.Dtos.MMDto;
import com.horcu.apps.peez.custom.GameBuilder;
import com.horcu.apps.peez.custom.Gameboard.TileCard;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.custom.Gameboard.TileView;
import com.horcu.apps.peez.custom.UserImageView;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.view.activities.ChallengeView;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Date;
import java.util.UUID;

import at.markushi.ui.CircleButton;

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

    private OnFragmentInteractionListener mListener;
    private LoggingService.Logger mLogger;
    private SenderCollection mSenders;
    private String myToken;
    private String currentSpot;
    private SharedPreferences settings;
    private Player opponent;
    private String gameKey;

    AutoFitGridLayout grid = null;
    private String playerTurn;

    int[] rangeA = new int[]{0,1,6,7,12,13, 18,19, 24,25, 30,31};
    int[] rangeB = new int[]{2,3,8,9,14,15, 20,21, 26,27, 32,33};
    int[] rangeC = new int[]{4,5,10,11,16,17, 22,23, 28,29, 34,35};

    int[] rangeTop = new int[]{2,3,8,9,14,15};
    int[] rangeBottom = new int[]{20,21, 26,27, 32,33};

    GameBuilder gameBuilder = null;

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
            return true;
            //     getActivity().getActionBar().setTitle(playerName);
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    // TODO: Rename and change types and number of parameters
    public static GameView newInstance() {
        GameView fragment = new GameView();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mLogger = new LoggingService.Logger(getContext());
        mSenders = SenderCollection.getInstance(getActivity());
        opponent = new Player();
        gameBuilder = new GameBuilder(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game_view, container, false);
         grid = (AutoFitGridLayout)root.findViewById(R.id.gameboard_grid);
        Game game = null;

        if(gameKey != null)
        game = GameBuilder.CreateOrGetGameboard(gameKey, false);
        else
        game = GameBuilder.CreateOrGetGameboard("", true);

        settings =getActivity().getSharedPreferences("Peez", 0);
        myToken = settings.getString(consts.REG_ID,"");

        //first move
        if(currentSpot == null)
            setCurrentSpot("0");

        for(int i = 0; i < consts.TOTAL_TILES; i++)
        {
            grid.getChildAt(i).setTag(i);
            grid.getChildAt(i).setOnClickListener(HandleTileClick(i));
        }
        return root;
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

                Intent intent = new Intent(getContext(), ChallengeView.class);
                //put info to pass in here
                intent.putExtra(consts.EXTRAS_MOVE_TO, finalI);
                startActivityForResult(intent, consts.INTENT_TO_CHALLENGE);
            }
        };
    }


    @NonNull
    private int GetFavoriteColor() {
        if(settings == null)
            settings = getActivity().getSharedPreferences("Peez", 0);

        int savedColorIndex = settings.getInt(consts.FAV_COLOR, 0) != 0
                ? settings.getInt(consts.FAV_COLOR, 0)
                : Color.parseColor("#a5a5c9");

        return getResources().getIntArray(R.array.Colors)[savedColorIndex];
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
                int color = GetFavoriteColor(); // getResources().getColor(settings.getInt(consts.FAV_COLOR, Color.parseColor("#551A8B")));

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
            CardView moveTo = (CardView) grid.getChildAt(position);
            CardView from = (CardView) grid.getChildAt(Integer.parseInt(message.getFrom()));

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

            ShowSnack(grid.getRootView(), message);

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
        int favColor = GetFavoriteColor();
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
    }
}

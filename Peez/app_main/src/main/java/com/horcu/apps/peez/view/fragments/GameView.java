package com.horcu.apps.peez.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.AutoFitGridLayout;
import com.horcu.apps.peez.Dtos.MMDto;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.service.LoggingService;
import com.lighters.cubegridlibrary.callback.ICubeGridAnimCallback;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game_view, container, false);
         grid = (AutoFitGridLayout)root.findViewById(R.id.gameboard_grid);

        settings =getActivity().getSharedPreferences("Peez", 0);
        myToken = settings.getString(consts.REG_ID,"");

        for(int i = 0; i < grid.getChildCount(); i++)
        {
            final int finalI = i;

            if(currentSpot == null)
                setCurrentSpot("0");

            CardView child = (CardView) grid.getChildAt(i);
            child.setCardElevation(0);

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!MyTurn())
                    {
                        Toast.makeText(getContext(),"Not your turn",Toast.LENGTH_SHORT).show();
                    return;
                    }

                    Date d = new Date();
                    long time = d.getTime();
                    int color = settings.getInt(consts.FAV_COLOR, Color.parseColor("#111111"));

                    String collapseKey = String.valueOf(UUID.randomUUID());


                    MMDto dto = new MMDto(currentSpot, String.valueOf(finalI), "move made!", String.valueOf(time), myToken, opponent.getToken(), opponent.getImageUri(), color, collapseKey);
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
            });
        }

        return root;
    }

    private boolean MyTurn() {
       // return playerTurn != null &&  playerTurn.equals(myToken);
        return false;
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

    public void ShowMoveOnBoard(Message message) {
        try {
            int position = Integer.parseInt(message.getTo());
            View v = grid.getChildAt(position);
            v.setBackground(new ColorDrawable(message.getColor()));
            View from = grid.getChildAt(Integer.parseInt(message.getFrom()));

            from.setBackground(new ColorDrawable(message.getColor()));
            from.setAlpha(.7f);
            from.setBackgroundResource(R.drawable.ic_mt);

            //create the new player dot
            CircleButton cb = AddButtonToCard(v);

            //move the player from the old spot
            if(currentSpot != "0")
            MovePlayerFromOldLocation(from);

            //move player to new spot
            MovePlayerToNewLocation(v);

            //set up click and drag listeners
            SetPlayerDotListeners(cb,v,from);

            //animate the player in the tile
            SetPlayerDotBackground(cb);
            ShowSnack(grid, message);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void SetPlayerDotListeners(CircleButton cb, View to, View from) {

        cb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                YoYo.with(Techniques.Pulse).duration(3000).playOn(v);
                return true;
            }
        });

        cb.setOnDragListener(new View.OnDragListener() {

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
        View playerDot = ((CardView)v).getChildAt(0);// TODO this is assuming that the first child will always be the player dot... this is not ideal. Maybe add the cards and circle dots using naming conventions so they can be found by id.. eg card_03 > cb_03 etc..
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(playerDot);
        YoYo.with(Techniques.Pulse).duration(1000).playOn(playerDot);
    }

    private void MovePlayerFromOldLocation(View from) {
        View playerDot = ((CardView)from).getChildAt(0);// TODO this is assuming that the first child will always be the player dot... this is not ideal. Maybe add the cards and circle dots using naming conventions so they can be found by id.. eg card_03 > cb_03 etc..
        YoYo.with(Techniques.FadeOut).duration(1000).playOn(playerDot);
    }

    private CircleButton AddButtonToCard(View v) {

        CircleButton cb = new CircleButton(getContext());
        cb.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Toast.makeText(getContext(),"moving",Toast.LENGTH_SHORT).show();
                v.setBackground(new ColorDrawable(Color.LTGRAY));
                return true;
            }
        });

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = 40;
        params.width = 40;
        cb.setLayoutParams(params);
        ((CardView)v).addView(cb);
        return cb;
    }

    private void ShowSnack(View parent, Message message) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_launcher), builder.length() - 1, builder.length(), 0); //TODO user image here instead of default iconm
        builder.append("from ").append(message.getFrom());
        builder.append(" ");
        builder.append("to ").append(message.getTo());
        Snackbar.make(parent, builder, Snackbar.LENGTH_INDEFINITE).show();

    }

    private void SetPlayerDotBackground(CircleButton cb) {
        int favColor = settings.getInt(consts.FAV_COLOR, 000000);
        cb.setBackground(new ColorDrawable(favColor));
        cb.setColor(favColor);
    }

    private ICubeGridAnimCallback mCubeGridAnimCallback = new ICubeGridAnimCallback() {
        @Override
        public void onAnimStart() {
            Toast.makeText(getActivity(), "your move!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAnimEnd() {
       //     Toast.makeText(getActivity(), "ok now its your move!", Toast.LENGTH_LONG).show();
        }
    };

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

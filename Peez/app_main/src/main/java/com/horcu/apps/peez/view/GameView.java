package com.horcu.apps.peez.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.JsonObject;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.AutoFitGridLayout;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.gcm.MoveMessage;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.service.LoggingService;
import com.lighters.cubegridlibrary.callback.ICubeGridAnimCallback;

import java.util.Date;

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
    private String message_recipient;
    private String myToken;
    private String playerUsername;
    private String currentSpot;
    private String playerImageUri;
    private SharedPreferences settings;

    AutoFitGridLayout grid = null;

    public GameView() {
        // Required empty public constructor
    }

    public Boolean UpdateGamePlayer(String userName, String token, String imgUrl){
        try {
            message_recipient = token;
            playerUsername = userName;
            playerImageUri  = imgUrl;
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
            if(currentSpot == null) currentSpot = "0";
            grid.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date d = new Date();
                    long time = d.getTime();
                    int color = settings.getInt(consts.FAV_COLOR, Color.parseColor("#111111"));

                    MoveMessage moveMessage = MessageSender.BuildMoveMessage(currentSpot, String.valueOf(finalI), "move made!", String.valueOf(time),myToken ,message_recipient,playerImageUri, color);

                    String json = ConvertToJson(moveMessage);
                    MessageSender sender = new MessageSender(getActivity(), mLogger, mSenders);

                    if(message_recipient == null || myToken == null){
                        Toast.makeText(getActivity(), "no recipient chosen", Toast.LENGTH_LONG).show();
                    }
                    else if(message_recipient.equals("")){
                        Toast.makeText(getActivity(), "no recipient chosen", Toast.LENGTH_LONG).show();
                    }
                    else if (sender.SendMove(message_recipient, consts.TEST_MSG_ID, json, consts.TEST_TINE_TO_LIVE, false, color)) {
                        Toast.makeText(getActivity(), "sent!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "failed ;/", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return root;
    }

    private String ConvertToJson(MoveMessage moveMessage) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("moveTo", moveMessage.getMoveTo());
        jsonObject.addProperty("moveFrom", moveMessage.getMoveFrom());
        jsonObject.addProperty("message", moveMessage.getMessage());
        jsonObject.addProperty("type", moveMessage.getType());
        jsonObject.addProperty("dateTime", moveMessage.getDateTime());
        jsonObject.addProperty("senderToken", moveMessage.getSenderToken());
        jsonObject.addProperty("receiverToken", moveMessage.getReceiverToken());
        jsonObject.addProperty("senderUrl", moveMessage.getSenderUrl());

        return jsonObject.toString();
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

    public void ShowMoveOnBoard(MoveMessage move) {
        int position = Integer.parseInt(move.getMoveTo());
        View v = grid.getChildAt(position);
        v.setBackground(new ColorDrawable(getResources().getColor(R.color.light_grey)));
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

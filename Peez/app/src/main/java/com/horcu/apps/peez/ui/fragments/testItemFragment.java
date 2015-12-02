package com.horcu.apps.peez.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.adapters.BetAdapter;
import com.horcu.apps.peez.backend.models.betApi.BetApi;
import com.horcu.apps.peez.backend.models.betApi.model.Bet;
import com.horcu.apps.peez.backend.models.betApi.model.CollectionResponseBet;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.custom.Api;
import com.horcu.apps.peez.dummy.DummyContent;
import com.horcu.apps.peez.listener.RecyclerItemClickListener;
import com.horcu.apps.peez.ui.activities.BetActivity;
import com.horcu.apps.peez.ui.activities.GridActivity;

import java.io.IOException;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class testItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "user";

    private BetAdapter mBetAdapter;
    private RecyclerView betList;
    // TODO: Rename and change types of parameters
    private User user;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    private String[] testResults = new String[4];

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static testItemFragment newInstance(User user) {
        testItemFragment fragment = new testItemFragment();

//        Bundle args = new Bundle();
//        args.putString("userName", user.getUserName());
//        args.putString("email", user.getEmail());
//        args.putString("regId", user.getRegistrationId());
//        args.putLong("rank", user.getRank());
//
//        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public testItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {

            testResults[0] = "regId:" + args.getString("regId");
            testResults[1] = "userName:" + args.getString("userName");
            testResults[2] = "email:" + args.getString("email");
            testResults[3] = "rank:" + args.getLong("rank");

        }

        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<>(getActivity(),
//                R.layout.fragment_testitem_list,R.id.bet_card, testResults);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_testitem, container, false);

        Button newBet = (Button)view.findViewById(R.id.new_bet_btn);
        Button newGrid = (Button)view.findViewById(R.id.new_grid_btn);

        newBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartNewBet();
            }
        });

        newGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              StartNewGrid();
            }
        });

        betList = (RecyclerView)view.findViewById(R.id.bets_list);
        getAllBets(); //pass email address

        betList.setLayoutManager(new LinearLayoutManager(getContext()));
        betList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, float x, float y) {
                StartNewBet();
            }
        }));
        return view;
    }

    private void StartNewGrid() {
        Intent intent = new Intent(getContext(), GridActivity.class);
        startActivity(intent);
    }

    private void StartNewBet() {
        Intent intent = new Intent(getContext(), BetActivity.class);
        startActivity(intent);
    }


    private void getAllBets() {


        new AsyncTask<Void, Void, Void>() {
            public List<Bet> mBets;

            @Override
            protected Void doInBackground(Void... params) {
                BetApi betService = Api.BuildBetApiService();
                try {
                    CollectionResponseBet collection = betService.list().execute();
                    if(collection !=null)
                        mBets = collection.getItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                if (mBetAdapter == null) {
                    mBetAdapter = new BetAdapter(mBets);
                    betList.setAdapter(mBetAdapter);
                } else {
                    mBetAdapter = new BetAdapter(mBets);
                    betList.setAdapter(mBetAdapter);
                    mBetAdapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onFragmentInteraction(String id);
    }

}

package com.horcu.apps.peez.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableArrayList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.BR;
import com.horcu.apps.peez.backend.models.playerApi.PlayerApi;
import com.horcu.apps.peez.backend.models.playerApi.model.CollectionResponsePlayer;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.binder.PlayerBinder;
import com.horcu.apps.peez.binder.SuperPlayerBinder;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.ApiServicesBuilber;
import com.horcu.apps.peez.custom.UserImageView;
import com.horcu.apps.peez.databinding.FragmentFeedBinding;
import com.horcu.apps.peez.view.activities.MainView;
import com.horcu.apps.peez.viewmodel.PlayerViewModel;
import com.horcu.apps.peez.viewmodel.PlayersViewModel;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import net.droidlabs.mvvm.recyclerview.adapter.ClickHandler;
import net.droidlabs.mvvm.recyclerview.adapter.binder.CompositeItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;
import java.io.IOException;

import io.realm.Realm;

public class FeedView extends Fragment {
        FragmentFeedBinding binding;

    private OnFragmentInteractionListener mListener;
    private SharedPreferences settings;
    private PlayersViewModel playersViewModel;
    ObservableArrayList<PlayerViewModel> vms = null;

    private boolean gameinProgressWithPlayerTest;

    public FeedView() {
        // Required empty public constructor
    }

    public static FeedView newInstance() {
        FeedView fragment = new FeedView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        settings = getActivity().getSharedPreferences(consts.PEEZ,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFeedBinding.inflate(inflater, container, false);
        playersViewModel = new PlayersViewModel();

        gameinProgressWithPlayerTest = false;

        GetPlayersFromServer(getActivity());

        binding.setPlayersVM(playersViewModel);
        binding.setView(this);
        binding.playersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TapBarMenu)v).toggle();
            }
        });
        binding.refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
               @Override
               public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                 ClearItems();
                 GetPlayersFromServer(getContext());
               }

//               @Override
//               public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//
//                   binding.refresh.finishRefreshLoadMore();
//               }
           });

        ChangeBackgroundColor();
        return binding.getRoot();
    }

    private Realm getRealm() {
        return ((MainView)getActivity()).getRealm();
    }

    private void ChangeBackgroundColor() {

        View layout = binding.feedsMain.getChildAt(0);

        int chosenColor = UpdateIcon(layout);
        UpdateOpponent(chosenColor, layout);
        //UpdateUserInfoSectionBGColor(layout);
    }

    private void UpdateUserInfoSectionBGColor(View layout) {
        layout.setBackground(new ColorDrawable(Color.WHITE));
    }

    private void UpdateOpponent(int col, View layout) {
        TextView tv = (TextView) layout.findViewById(R.id.opponent_info_text);
        tv.setText("Peez");
        tv.setTextColor(col);
    }

    private int UpdateIcon(View layout) {
        UserImageView icon = (UserImageView) layout.findViewById(R.id.opponent_img);
        int chosenColorIndex = settings.getInt(consts.FAV_COLOR, 0);
        int col = getResources().getIntArray(R.array.Colors)[chosenColorIndex];
        icon.setBackground(new ColorDrawable(col));
        return col;
    }

    private void ClearItems() {
        playersViewModel.playersVMs.clear();
    }

    private void GetPlayersFromServer(Context context) {

            vms =  new ObservableArrayList<>();

            new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                PlayerApi api = ApiServicesBuilber.BuildPlayerApiService();
                try {
                    CollectionResponsePlayer list = api.list().execute();

                    if (list != null && list.size() <= 3) return null;

                    String un = settings.getString(consts.PREF_ACCOUNT_NAME,"");

                    for (Player player : list.getItems()){
                        if(player.getEmail().equals(un))
                        continue;

                       Player p = new Player();
                        p.setUserName(player.getUserName());
                        p.setCanBeMessaged(true);
                        p.setRank(player.getRank());
                        p.setToken(player.getToken());
                        p.setImageUri(player.getImageUri());
                        PlayerViewModel pvm = new PlayerViewModel(p);
                        vms.add(pvm);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {

                if(playersViewModel.playersVMs == null)
                    playersViewModel.playersVMs = new ObservableArrayList<>();

                playersViewModel.playersVMs.addAll(vms);

                if(binding.refresh != null)
                binding.refresh.finishRefresh();
            }

       }.execute();
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
        void onInitiateNewGame(PlayerViewModel playerViewModel);

        void onSwitchCurrentGame(String gamekey, PlayerViewModel playerViewModel );

        void onSetPlayerTurn(PlayerViewModel playerVm);
    }

    public ItemBinder<PlayerViewModel> itemViewBinder()
    {
        return new CompositeItemBinder<>(
                new SuperPlayerBinder(BR.playerVm, R.layout.item_winning_player),
                new PlayerBinder(BR.playerVm, R.layout.item_player)
        );
    }

    public ClickHandler<PlayerViewModel> playerClicked()
    {
        return new ClickHandler<PlayerViewModel>()
        {
            @Override
            public void onClick(PlayerViewModel playerVm)
            {

               if(GameAlreadyInprogressWithPlayer()) {
                    String gameId = GetCurrentGameWithPlayer();// TODO this value should not be hardcoded in the called method
                    mListener.onSwitchCurrentGame(gameId, playerVm);
             }
                else
                {
                    mListener.onInitiateNewGame(playerVm);
                    gameinProgressWithPlayerTest = true;
                    mListener.onSetPlayerTurn(playerVm);
                }
            }
        };
    }

    private String GetCurrentGameWithPlayer() {
        return "1234567890";

    }

    private boolean GameAlreadyInprogressWithPlayer() {
        return gameinProgressWithPlayerTest;
    }
}

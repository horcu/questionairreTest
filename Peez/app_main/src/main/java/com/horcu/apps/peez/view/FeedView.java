package com.horcu.apps.peez.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ActionProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.BR;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.binder.PlayerBinder;
import com.horcu.apps.peez.binder.SuperPlayerBinder;
import com.horcu.apps.peez.binder.SuperUserBinder;
import com.horcu.apps.peez.binder.UserBinder;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.Api;
import com.horcu.apps.peez.custom.PlayerView;
import com.horcu.apps.peez.databinding.FragmentFeedBinding;
import com.horcu.apps.peez.gcm.GcmServerSideSender;
import com.horcu.apps.peez.gcm.Message;
import com.horcu.apps.peez.model.Player;
import com.horcu.apps.peez.viewmodel.MessageViewModel;
import com.horcu.apps.peez.viewmodel.PlayerViewModel;
import com.horcu.apps.peez.viewmodel.PlayersViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.ClickHandler;
import net.droidlabs.mvvm.recyclerview.adapter.binder.CompositeItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

        FragmentFeedBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SharedPreferences settings;
    private PlayersViewModel playersViewModel;
    ObservableArrayList<PlayerViewModel> vms = null;

    public FeedView() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FeedView newInstance() {
        FeedView fragment = new FeedView();
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
        settings = getActivity().getSharedPreferences("Peez",0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFeedBinding.inflate(inflater, container, false);
        playersViewModel = new PlayersViewModel();

        binding.feedLoader.setVisibility(View.GONE);
        getFeedFromDb(getActivity());

        getFeedFromServer(getActivity());

        binding.setPlayersVM(playersViewModel);
        binding.setView(this);
        binding.playersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        return binding.getRoot();
    }

    private void getFeedFromServer(Context context) {


            vms =  new ObservableArrayList<>();

            new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                UserApi api = Api.BuildUserApiService();
                try {
                    CollectionResponseUser list = api.list().execute();

                    if(list == null || list.getItems().size() < 1)
                        return null;

                    String un = settings.getString(consts.PREF_ACCOUNT_NAME,"");

                    for (User user : list.getItems()){
                        if(user.getEmail().equals(un))
                        continue;

                       Player p = new Player();
                        p.setName(user.getUserName());
                        p.setCanBeMessaged(true);
                        p.setRank(String.valueOf(user.getRank()));
                        p.setToken(user.getToken());
                        p.setImageUrl(user.getImageUri());

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
                binding.feedLoader.setVisibility(View.GONE);
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
        void onFragmentInteraction(String name, String imageUrl, String token);
    }

    private void getFeedFromDb(Context ctx) {

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(ctx).build();
        // Realm.deleteRealm(realmConfig);
        Realm realm = Realm.getInstance(realmConfig);
        final ObservableArrayList<PlayerViewModel> vms = new ObservableArrayList<>();

        // Query and update the result asynchronously in another thread
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    RealmResults<Player> players = realm.allObjects(Player.class); // where(Message.class).equalTo("sender", sender).findAll();

                    if(players.size() < 1)
                        return;

                    for (Player player : players)
                    {
                            PlayerViewModel mod = new PlayerViewModel(player);
                            vms.add(mod);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onSuccess() {
                if(playersViewModel.playersVMs == null)
                    playersViewModel.playersVMs = new ObservableArrayList<>();

                playersViewModel.playersVMs.addAll(vms);
            }
        });
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
                String name = playerVm.getModel().getName();
                String token = playerVm.getModel().getToken();
                String imgUrl = playerVm.getModel().getImageUrl();

              mListener.onFragmentInteraction(name, imgUrl,token);
            }
        };
    }
}

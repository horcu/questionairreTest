//package com.horcu.apps.peez.ui.fragments;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.databinding.ObservableArrayList;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.ListAdapter;
//import android.widget.TextView;
//
//import com.horcu.apps.peez.adapters.RegistrationAdapter;
//import com.horcu.apps.peez.backend.models.betApi.BetApi;
//import com.horcu.apps.peez.backend.models.userApi.UserApi;
//import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
//
//
//import com.horcu.apps.peez.backend.registration.Registration;
//import com.horcu.apps.peez.backend.registration.model.CollectionResponseRegistrationRecord;
//
//import com.horcu.apps.peez.common.models.User;
//import com.horcu.apps.peez.custom.Api;
//import com.horcu.apps.peez.databinding.FragmentMainBinding;
//import com.horcu.apps.peez.dummy.DummyContent;
//import com.horcu.apps.peez.model.app.Friend;
//import com.horcu.apps.peez.model.app.obs.FriendViewModel;
//import com.horcu.apps.peez.ui.activities.BetActivity;
//import com.horcu.apps.peez.ui.activities.GCMActivity;
//import com.horcu.apps.peez.ui.activities.GridActivity;
//import java.io.IOException;
//import java.util.List;
//
//import me.tatarka.bindingcollectionadapter.ItemView;
//
//
///**
// * A fragment representing a list of Items.
// * <p/>
// * Large screen devices (such as tablets) are supported by replacing the ListView
// * with a GridView.
// * <p/>
// * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
// * interface.
// */
//public class testItemFragment extends Fragment implements AbsListView.OnItemClickListener {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "user";
//
//    private RegistrationAdapter mRegAdapter;
//    private RecyclerView betList;
//
//    private FriendViewModel model;
//
//    // TODO: Rename and change types of parameters
//    private User user;
//
//    private OnFragmentInteractionListener mListener;
//
//    /**
//     * The fragment's ListView/GridView.
//     */
//    private AbsListView mListView;
//
//    private String[] testResults = new String[4];
//
//    /**
//     * The Adapter which will be used to populate the ListView/GridView with
//     * Views.
//     */
//    private ListAdapter mAdapter;
//    private CollectionResponseRegistrationRecord registrations; //TODO remove this for production
//    private FragmentMainBinding binding;
//
//    // TODO: Rename and change types of parameters
//    public static testItemFragment newInstance(com.horcu.apps.peez.backend.models.userApi.model.User user) {
//        testItemFragment fragment = new testItemFragment();
//
////        Bundle args = new Bundle();
////        args.putString("userName", user.getUserName());
////        args.putString("email", user.getEmail());
////        args.putString("regId", user.getRegistrationId());
////        args.putLong("rank", user.getRank());
////
////        fragment.setArguments(args);
//        return fragment;
//    }
//
//    /**
//     * Mandatory empty constructor for the fragment manager to instantiate the
//     * fragment (e.g. upon screen orientation changes).
//     */
//    public testItemFragment() {
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
//        if (args != null) {
//
//            testResults[0] = "regId:" + args.getString("regId");
//            testResults[1] = "userName:" + args.getString("userName");
//            testResults[2] = "email:" + args.getString("email");
//            testResults[3] = "rank:" + args.getLong("rank");
//
//        }
//        model = new FriendViewModel();
//        model.friends = new ObservableArrayList<>();
//        model.itemView = new ItemView();
//
//        for(int i =0; i < 10; i ++ )
//        {
//            com.horcu.apps.peez.backend.models.userApi.model.User u = new com.horcu.apps.peez.backend.models.userApi.model.User();
//            u.setEmail("dsfdsfdsf");
//            u.setUserName("user_" + String.valueOf(i));
//            model.friends.add(new Friend(u,"",""));
//        }
//
//        // TODO: Change Adapter to display your content
////        mAdapter = new ArrayAdapter<>(getActivity(),
////                R.layout.fragment_testitem_list,R.id.bet_card, testResults);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
////
////        View view = inflater.inflate(R.layout.fragment_testitem, container, false);
////        Button newBet = (Button)view.findViewById(R.id.new_bet_btn);
////        Button newGrid = (Button)view.findViewById(R.id.new_grid_btn);
////        Button txt = (Button)view.findViewById(R.id.new_grid_txt);
////
////        txt.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startMessenger();
////            }
////        });
////
////        newBet.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                StartNewBet();
////            }
////        });
////
////        newGrid.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                StartNewGrid();
////            }
////        });
////
////        betList = (RecyclerView)view.findViewById(R.id.bets_list);
////       // GetAllFriends();
////
////        betList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
////
////            @Override
////            public void onItemClick(View view, int position, float x, float y) {
////                StartNewBet();
////            }
////        }));
//
//         binding = FragmentMainBinding.inflate(inflater, container, false);
//        binding.setViewModel(model);
//        //binding.setListeners(new Listeners(model));
////        binding.executePendingBindings();
//        return binding.getRoot();
//
//    }
//
//    private void startMessenger() {
//        Intent intent = new Intent(getActivity(), GCMActivity.class);
//        startActivity(intent);
//    }
//
//    private void StartNewGrid() {
//        Intent intent = new Intent(getContext(), GridActivity.class);
//        startActivity(intent);
//    }
//
//    private void StartNewBet() {
//        Intent intent = new Intent(getContext(), BetActivity.class);
//        startActivity(intent);
//    }
//
//
//    private FriendViewModel GetAllFriends() {
//
//        new AsyncTask<Void, Void,  List<com.horcu.apps.peez.backend.models.userApi.model.User>>() {
//            public List<com.horcu.apps.peez.backend.registration.model.RegistrationRecord> mRegs;
//
//            @Override
//            protected  List<com.horcu.apps.peez.backend.models.userApi.model.User> doInBackground(Void... params) {
//                BetApi betService = Api.BuildBetApiService();
//                Registration regService = Api.BuildRegistrationApiService();
//                UserApi userService = Api.BuildUserApiService();
//                try {
//
//                    //here check memcache first (if this is even how you use memcache)
//
//                    //TODO - this cannot make production code. these lists could be really large.. for testing only
//                    CollectionResponseUser usersCollection = userService.list().execute();
//                    registrations = regService.listDevices(100).execute();
//                    //TODO -- end remove.. be sure to remove!!!!
//
//                    if(usersCollection !=null) {
//                     //   mRegs = collection.getItems();
//                        List<com.horcu.apps.peez.backend.models.userApi.model.User> mfriends = usersCollection.getItems();
//                       return mfriends;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute( List<com.horcu.apps.peez.backend.models.userApi.model.User> users) {
//
////                if (mRegAdapter == null) {
////                    mRegAdapter = new RegistrationAdapter(mRegs);
////                    betList.setAdapter(mRegAdapter);
////                } else {
////                    mRegAdapter = new RegistrationAdapter(mRegs);
////                    betList.setAdapter(mRegAdapter);
////                    mRegAdapter.notifyDataSetChanged();
////                }
//        model = MorphToFriendObjects(users);
//        binding.setViewModel(model);
//      //  binding.setListeners(new Listeners(model));
//        binding.executePendingBindings();
//            }
//        }.execute();
//        return null;
//    }
//
//    private FriendViewModel MorphToFriendObjects(List<com.horcu.apps.peez.backend.models.userApi.model.User> users) {
//
//        if(model == null)
//        model = new FriendViewModel();
//
//        if(model.friends == null)
//        model.friends = new ObservableArrayList<>();
//
//        try {
//            for(com.horcu.apps.peez.backend.models.userApi.model.User user : users)
//            {
//               Friend friend = new Friend(user,user.getJoined(), user.getPhone());
//                friend.setUsername(user.getUserName());
//                friend.setEmail(user.getUserName());
//                friend.setRegistrationId(user.getRegistrationId());
//                model.friends.add(friend);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return model;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
//    }
//
//    /**
//     * The default content for this Fragment has a TextView that is shown when
//     * the list is empty. If you would like to change the text, call this method
//     * to supply the text it should use.
//     */
//    public void setEmptyText(CharSequence emptyText) {
//        View emptyView = mListView.getEmptyView();
//
//        if (emptyView instanceof TextView) {
//            ((TextView) emptyView).setText(emptyText);
//        }
//    }
//
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(String id);
//    }
//
//}

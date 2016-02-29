package com.horcu.apps.peez.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.BR;
import com.horcu.apps.peez.binder.SuperUserBinder;
import com.horcu.apps.peez.binder.UserBinder;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.databinding.FragmentChatViewBinding;
import com.horcu.apps.peez.gcm.PubSubHelper;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.model.Player;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.viewmodel.SuperUserViewModel;
import com.horcu.apps.peez.viewmodel.UserViewModel;
import com.horcu.apps.peez.viewmodel.UsersViewModel;

import net.droidlabs.mvvm.recyclerview.adapter.ClickHandler;
import net.droidlabs.mvvm.recyclerview.adapter.LongClickHandler;
import net.droidlabs.mvvm.recyclerview.adapter.binder.CompositeItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private UsersViewModel usersViewModel;
    private FragmentChatViewBinding binding;

    //messaging
    private SharedPreferences settings;
    PubSubHelper pubsub = null;
    private LoggingService.Logger mLogger;
    private SenderCollection mSenders;

    private boolean itsMe;
    private Spinner users;
    private BroadcastReceiver mLoggerCallback;

    public ChatView() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChatView newInstance() {
        ChatView fragment = new ChatView();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pubsub = new PubSubHelper(getContext());
        final Bundle bundle = new Bundle();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mLogger = new LoggingService.Logger(getActivity());
        mSenders = SenderCollection.getInstance(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settings = getActivity().getSharedPreferences("Peez", 0);
        itsMe = false;


        binding = FragmentChatViewBinding.inflate(inflater, container, false);
        usersViewModel = new UsersViewModel();
        usersViewModel.users.add(new SuperUserViewModel(new Player(String.valueOf(new Date()), "Good luck !!")));
        binding.setUsersViewModel(usersViewModel);
        binding.setView(this);
        binding.getView();

        mLoggerCallback = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {

                    case LoggingService.ACTION_LOG:
                        String newLog = intent.getStringExtra(LoggingService.EXTRA_LOG_MESSAGE);

                        usersViewModel.users.add(new UserViewModel(new Player(String.valueOf(new Date()), newLog))); //TODO the date should come with the message

//                        Snackbar snack =  Snackbar.make(findViewById(R.id.drawer_layout), Html.fromHtml(stringBuilder.toString()), Snackbar.LENGTH_LONG);
//                        snack.setActionTextColor(Color.WHITE);
//                        snack.show();
                        break;
                }
            }
        };


        binding.activityUsersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot(); //inflater.inflate(R.layout.fragment_chat_view, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction("");
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
        void onFragmentInteraction(String newMessage);
    }

    @Nullable
    private static String getStringFromEditText(EditText editText)
    {
        Editable editable = editText.getText();
        return editable == null ? null : editable.toString();
    }


    public View.OnClickListener onButtonClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Date d = new Date();
                long time = d.getTime();

                usersViewModel.addUser(String.valueOf(time), getStringFromEditText(binding.usersViewLastname),false);

                String senderId = settings.getString(consts.SENDER_ID, "");
                if("" != senderId) {
                    MessageSender sender = new MessageSender(getActivity(), mLogger, mSenders);
                  if(!sender.sendMessage(consts.TEST_GAME_TOPIC,consts.TEST_MSG_ID,getStringFromEditText(binding.usersViewLastname),consts.TEST_TINE_TO_LIVE, false))
                  {
                    //message not sent something went wrong
                      Snackbar.make(v, ":/ bummer", Snackbar.LENGTH_LONG).show();
                  }
                }
                else
                {
                   Snackbar.make(v, ":) sent..werd!", Snackbar.LENGTH_LONG).show();
                }
                ((EditText)binding.getRoot().findViewById(R.id.users_view_lastname)).setText("");
                ((RecyclerView)binding.getRoot().findViewById(R.id.activity_users_recycler)).smoothScrollToPosition(usersViewModel.users.size() - 1);
            }
        };
    }


    public ClickHandler<UserViewModel> clickHandler()
    {
        return new ClickHandler<UserViewModel>()
        {
            @Override
            public void onClick(UserViewModel user)
            {
                Toast.makeText(getActivity(), user.getFirstName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public LongClickHandler<UserViewModel> longClickHandler()
    {
        return new LongClickHandler<UserViewModel>()
        {
            @Override
            public void onLongClick(UserViewModel user)
            {
                Toast.makeText(getActivity(), "LONG CLICK: " + user.getFirstName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public ItemBinder<UserViewModel> itemViewBinder()
    {
        return new CompositeItemBinder<UserViewModel>(
                new SuperUserBinder(BR.user, R.layout.item_super_user),
                new UserBinder(BR.user, R.layout.item_user)
        );
    }
}

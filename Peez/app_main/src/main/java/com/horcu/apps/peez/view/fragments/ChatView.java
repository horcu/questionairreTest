package com.horcu.apps.peez.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.horcu.apps.peez.Dtos.SmsDto;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.BR;
import com.horcu.apps.peez.binder.SuperMessageBinder;
import com.horcu.apps.peez.binder.MessageBinder;
import com.horcu.apps.peez.chat.Actions.EmojIconActions;
import com.horcu.apps.peez.chat.Helper.EmojiconEditText;
import com.horcu.apps.peez.chat.Helper.EmojiconTextView;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.MessageSender;
//import com.horcu.apps.peez.databinding.FragmentChatViewBinding;

import com.horcu.apps.peez.databinding.FragmentChatViewBinding;
import com.horcu.apps.peez.gcm.core.PubSubHelper;
import com.horcu.apps.peez.gcm.message.Message;
import com.horcu.apps.peez.misc.SenderCollection;
import com.horcu.apps.peez.model.MessageEntry;
import com.horcu.apps.peez.service.LoggingService;
import com.horcu.apps.peez.viewmodel.SuperMessageViewModel;
import com.horcu.apps.peez.viewmodel.MessageViewModel;
import com.horcu.apps.peez.viewmodel.MessagesViewModel;


import net.droidlabs.mvvm.recyclerview.adapter.ClickHandler;
import net.droidlabs.mvvm.recyclerview.adapter.LongClickHandler;
import net.droidlabs.mvvm.recyclerview.adapter.binder.CompositeItemBinder;
import net.droidlabs.mvvm.recyclerview.adapter.binder.ItemBinder;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


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
    private static final String MESSAGE_RECIPIENT = "recip";
    private static final String PLAYER_IMGURL = "img_url";
    private static final String PLAYER_USERNAME = "p_user_name";

    // TODO: Rename and change types of parameters
    public String message_recipient;
    public String playerImageUri;
    public String playerName;
    public String myToken;
    public String gameId;

    private String myEmail;
    private String theirEmail;

    private OnFragmentInteractionListener mListener;
    private MessagesViewModel messagesViewModel;
    protected FragmentChatViewBinding binding;

    //messaging
    private SharedPreferences settings;
    PubSubHelper pubsub = null;
    private LoggingService.Logger mLogger;
    private SenderCollection mSenders;

    private Realm realm;
    private RealmConfiguration realmConfig;

    private View rootView;
    private EmojIconActions emojIcon;
    private ImageView emojiButton;
    private ImageView submitButton;
    //private EmojiconTextView resultsTextView;
    private EmojiconEditText emojiconEditText;
    private CheckBox mCheckBox;

    public ChatView() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChatView newInstance(String recipient, String imgUrl, String uName) {
        ChatView fragment = new ChatView();
        Bundle args = new Bundle();
          args.putString(MESSAGE_RECIPIENT, recipient);
          args.putString(PLAYER_IMGURL, imgUrl);
          args.putString(PLAYER_USERNAME, uName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pubsub = new PubSubHelper(getContext());
        settings = getActivity().getSharedPreferences("Peez", 0);

        if (getArguments() != null) {
            message_recipient = getArguments().getString(MESSAGE_RECIPIENT,"");
            playerImageUri = getArguments().getString(PLAYER_IMGURL,"");
            playerName = getArguments().getString(PLAYER_USERNAME,"");
        }
        mLogger = new LoggingService.Logger(getActivity());
        mSenders = SenderCollection.getInstance(getActivity());
        myToken = settings.getString(consts.REG_ID,"");

        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
       realmConfig = new RealmConfiguration.Builder(getContext()).build();
        // Get a Realm instance for this thread
       realm = Realm.getInstance(realmConfig);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

}

    public Boolean upDateChatPlayer(String gameId, String userName, String token, String imgUrl){
        try {
            message_recipient = token;
            playerName = userName;
            playerImageUri  = imgUrl;
            this.gameId = gameId;
            return true;

       //     getActivity().getActionBar().setTitle(playerName);
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //final View rootView = inflater.inflate(R.layout.fragment_chat_view,container, false);
        binding = FragmentChatViewBinding.inflate(inflater, container, false);
        messagesViewModel = new MessagesViewModel();
        binding.setMsgViewModel(messagesViewModel);
        binding.setView(this);
        binding.activityUsersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshMessagesFromDb(gameId, getActivity());

        rootView = binding.getRoot();
        emojiButton = (ImageView) rootView.findViewById(R.id.emoji_btn);
        submitButton = (ImageView) rootView.findViewById(R.id.submit_btn);
        emojiconEditText = (EmojiconEditText) rootView.findViewById(R.id.emojicon_edit_text);

        emojIcon=new EmojIconActions(getContext(),rootView,emojiconEditText,emojiButton);

        emojIcon.ShowEmojIcon();

               emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard","open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard","close");
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String message = emojiconEditText.getText().toString();
                    //resultsTextView.setText(message);
                    Date d = new Date();
                    long time = d.getTime();

                    // build up the message oject to send to opponent
                    SmsDto dto = new SmsDto(myToken,message_recipient, message, String.valueOf(time), playerImageUri);
                    String json = MessageSender.JsonifySmsDto(dto);
                    Message sms = MessageSender.BuildSmsMessage(dto, json);
                    String guid = UUID.randomUUID().toString();

                    MessageEntry me = new MessageEntry(String.valueOf(time), message, sms.getMessageId(),playerImageUri,myToken, message_recipient);

                    MessageSender sender = new MessageSender(getActivity(), mLogger, mSenders);
                    if (sender.SendSMS(sms)) {
                        try {
                            // save to db

                            realm.beginTransaction();
                            realm.copyToRealm(me);
                            realm.commitTransaction();

                            Toast.makeText(getActivity(), "added to db!", Toast.LENGTH_SHORT).show();

                            // Create an entry for showing the text
                            messagesViewModel.messageViewModels.add(new SuperMessageViewModel(me));
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "not added to db! " + e.getMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            return;
                        }

                    } else {
                        Toast.makeText(getActivity(), "failed ;/", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ((EmojiconEditText) rootView.findViewById(R.id.emojicon_edit_text)).setText("");
                    ((RecyclerView) rootView.findViewById(R.id.activity_users_recycler)).smoothScrollToPosition(messagesViewModel.messageViewModels.size() - 1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView; //inflater.inflate(R.layout.fragment_chat_view, container, false);
    }

    public void refreshMessagesFromDb(final String gameId, Context ctx) {

        final ObservableArrayList<MessageViewModel> vms = new ObservableArrayList<>();

        // Query and update the result asynchronously in another thread
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    RealmResults<MessageEntry> messages = realm.where(MessageEntry.class)
                            .equalTo("gameId", gameId)
                            .equalTo("to", myEmail)
                            .or()
                            .equalTo("from", myEmail)
                            .or()
                            .equalTo("from", theirEmail)
                            .or()
                            .equalTo("to", theirEmail)

                            .findAll(); // where(Message.class).equalTo("sender", sender).findAll();

                    if(messages.size() < 1)
                        return;

                    for (MessageEntry m : messages)
                    {
                        if(MessageAlreadyAdded(m.getId()))
                            continue;

                        String message = m.getMessage();
                        MessageEntry messageEntry = new MessageEntry(String.valueOf(new Date()), message, m.getId(), m.getImgUrl(), m.getFrom(), m.getTo());
                        String from = m.getFrom();
                        SuperMessageViewModel su;
                        MessageViewModel su2;

                        if(from.equals(myToken))
                        {
                         su = new SuperMessageViewModel(messageEntry);
                          vms.add(su);
                        }
                        else
                        {
                         su2 = new MessageViewModel(messageEntry);
                          vms.add(su2);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onSuccess() {
                if(messagesViewModel.messageViewModels == null)
                messagesViewModel.messageViewModels = new ObservableArrayList<>();

                messagesViewModel.messageViewModels.addAll(vms);
//                RecyclerView.Adapter adapter = binding.activityUsersRecycler.getAdapter();
//
//                if(adapter == null)
//                    return;
//
//                int msgCount = adapter.getItemCount();
//
//                if(msgCount > 0)
//                {
//                    binding.activityUsersRecycler.smoothScrollToPosition(msgCount -1);
//                    YoYo.with(Techniques.BounceIn).duration(1000).playOn(binding.activityUsersRecycler.getChildAt(msgCount -1));
//                }
            }
        });
    }

    public Boolean MessageAlreadyAdded(String messageId){
        for(int i=0; i < messagesViewModel.messageViewModels.size(); i ++)
        {
            MessageViewModel mvm = messagesViewModel.messageViewModels.get(i);
           MessageEntry me = mvm.getModel();
            String meId = me.getId();
            if(meId == null || messageId == null || meId.equals("") || messageId.equals(""))
                continue;

            if(me.getId().equals(messageId))
                return true;
        }
        return false;
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


   public ClickHandler<MessageViewModel> clickHandler()
    {
        return new ClickHandler<MessageViewModel>()
        {
            @Override
            public void onClick(MessageViewModel user)
            {
                Toast.makeText(getActivity(), user.getFirstName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public LongClickHandler<MessageViewModel> longClickHandler()
    {
        return new LongClickHandler<MessageViewModel>()
        {
            @Override
            public void onLongClick(MessageViewModel user)
            {
                Toast.makeText(getActivity(), "LONG CLICK: " + user.getFirstName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public ItemBinder<MessageViewModel> itemViewBinder()
    {
        return new CompositeItemBinder<MessageViewModel>(
                new SuperMessageBinder(BR.msgVM, R.layout.item_my_message),
                new MessageBinder(BR.msgVM, R.layout.item_message)
        );
    }

    private void changeEmojiKeyboardIcon(ImageView iconToBeChanged, int drawableResourceId){
        iconToBeChanged.setImageResource(drawableResourceId);
    }
}

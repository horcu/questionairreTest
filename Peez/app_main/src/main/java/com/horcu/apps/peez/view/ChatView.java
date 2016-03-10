package com.horcu.apps.peez.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.BR;
import com.horcu.apps.peez.binder.SuperUserBinder;
import com.horcu.apps.peez.binder.UserBinder;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.MessageSender;
import com.horcu.apps.peez.databinding.FragmentChatViewBinding;
import com.horcu.apps.peez.gcm.SmsMessage;

import com.horcu.apps.peez.gcm.PubSubHelper;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

import github.ankushsachdeva.emojicon.EmojiconGridView;
import github.ankushsachdeva.emojicon.EmojiconsPopup;
import github.ankushsachdeva.emojicon.emoji.Emojicon;
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

}

    public Boolean upDateChatPlayer(String userName, String token, String imgUrl){
        try {
            message_recipient = token;
            playerName = userName;
            playerImageUri  = imgUrl;
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

       // if(!playerName.equals(""))
       //     getActivity().getActionBar().setTitle(playerName); //TODO add a custom actionbar so that you can add the player image there and pass in the url here

        binding = FragmentChatViewBinding.inflate(inflater, container, false);
        binding.chatLoader.setVisibility(View.VISIBLE);

        messagesViewModel = new MessagesViewModel();

        binding.setMsgViewModel(messagesViewModel);
        binding.setView(this);

        // Give the topmost view of your activity layout hierarchy. This will be used to measure soft keyboard height
        final EmojiconsPopup popup = new EmojiconsPopup(binding.getRoot(), getActivity());

        //Will automatically set size according to the soft keyboard size
        popup.setSizeForSoftKeyboard();

        //If the emoji popup is dismissed, change emojiButton to smiley icon
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                changeEmojiKeyboardIcon(binding.getEmojis, R.drawable.smiley);
            }
        });

        //If the text keyboard closes, also dismiss the emoji popup
        popup.setOnSoftKeyboardOpenCloseListener(new EmojiconsPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {

            }

            @Override
            public void onKeyboardClose() {
                if(popup.isShowing())
                    popup.dismiss();
            }
        });

        //On emoji clicked, add it to edittext
        popup.setOnEmojiconClickedListener(new EmojiconGridView.OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emojicon emojicon) {
                if (binding.usersViewLastname == null || emojicon == null) {
                    return;
                }

                int start = binding.usersViewLastname.getSelectionStart();
                int end = binding.usersViewLastname.getSelectionEnd();
                if (start < 0) {
                    binding.usersViewLastname.append(emojicon.getEmoji());
                } else {
                    binding.usersViewLastname.getText().replace(Math.min(start, end),
                            Math.max(start, end), emojicon.getEmoji(), 0,
                            emojicon.getEmoji().length());
                }
            }
        });

        //On backspace clicked, emulate the KEYCODE_DEL key event
        popup.setOnEmojiconBackspaceClickedListener(new EmojiconsPopup.OnEmojiconBackspaceClickedListener() {

            @Override
            public void onEmojiconBackspaceClicked(View v) {
                KeyEvent event = new KeyEvent(
                        0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                binding.usersViewLastname.dispatchKeyEvent(event);
            }
        });

        // To toggle between text keyboard and emoji keyboard keyboard(Popup)
        binding.getEmojis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //If popup is not showing => emoji keyboard is not visible, we need to show it
                if(!popup.isShowing()){

                    //If keyboard is visible, simply show the emoji popup
                    if(popup.isKeyBoardOpen()){
                        popup.showAtBottom();
                        changeEmojiKeyboardIcon(binding.getEmojis, R.drawable.ic_action_keyboard);
                    }

                    //else, open the text keyboard first and immediately after that show the emoji popup
                    else{
                        binding.usersViewLastname.setFocusableInTouchMode(true);
                        binding.usersViewLastname.requestFocus();
                        popup.showAtBottomPending();
                        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(binding.usersViewLastname, InputMethodManager.SHOW_IMPLICIT);
                        changeEmojiKeyboardIcon(binding.getEmojis, R.drawable.ic_action_keyboard);
                    }
                }

                //If popup is showing, simply dismiss it to show the undelying text keyboard
                else{
                    popup.dismiss();
                }
            }
        });

        binding.activityUsersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshMessagesFromDb(myToken, getActivity());

        return binding.getRoot(); //inflater.inflate(R.layout.fragment_chat_view, container, false);
    }

    public void refreshMessagesFromDb(final String sender, Context ctx) {
        binding.chatLoader.setVisibility(View.VISIBLE);

        realmConfig = new RealmConfiguration.Builder(ctx).build();
        // Realm.deleteRealm(realmConfig);
        realm = Realm.getInstance(realmConfig);
        final ObservableArrayList<MessageViewModel> vms = new ObservableArrayList<>();

        // Query and update the result asynchronously in another thread
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    String theirToken = !message_recipient.equals("") ? message_recipient : "123w";
                    RealmResults<SmsMessage> messages = realm.where(SmsMessage.class)
                            .equalTo("to", myToken)
                            .or()
                            .equalTo("from", myToken)
                            .or()
                            .equalTo("from", theirToken)
                            .or()
                            .equalTo("to", theirToken)
                            .findAll(); // where(Message.class).equalTo("sender", sender).findAll();

                    if(messages.size() < 1)
                        return;

                    for (SmsMessage m : messages)
                    {
                     ///   if(MessageAlreadyAdded(m))
                    //        continue;

                        String message = m.getMessage();
                        MessageEntry messageEntry = new MessageEntry(String.valueOf(new Date()), message, m.getMessageId(), m.getSenderUrl());
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
                RecyclerView.Adapter adapter = binding.activityUsersRecycler.getAdapter();

                if(adapter == null)
                    return;

                int msgCount = adapter.getItemCount();

                if(msgCount > 0)
                {
                    binding.activityUsersRecycler.smoothScrollToPosition(msgCount -1);
                }
                binding.chatLoader.setVisibility(View.GONE);
            }
        });
    }

    public Boolean MessageAlreadyAdded(SmsMessage message){
        for(int i=0; i < messagesViewModel.messageViewModels.size(); i ++)
        {
            MessageViewModel mvm = messagesViewModel.messageViewModels.get(i);
           MessageEntry me = mvm.getModel();
            String mId = message.getMessageId();
            String meId = me.getId();
            if(meId == null || mId == null || meId.equals("") || mId.equals(""))
                continue;

            if(me.getId().equals(mId))
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

    public View.OnClickListener onButtonClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try {
                    Date d = new Date();
                    long time = d.getTime();
                    String message = getStringFromEditText(binding.usersViewLastname);

                    binding.chatLoader.setVisibility(View.VISIBLE);
                    SmsMessage sms = MessageSender.BuildMessage(message_recipient, myToken, message, String.valueOf(time), playerImageUri);
                    UUID uuid = UUID.randomUUID();

                    String guid = uuid.toString();
                    sms.setMessageId(guid);

                    if (sms.getFrom().equals("") || sms.getTo().equals(""))
                        return;

                    messagesViewModel.messageViewModels.add(new SuperMessageViewModel(new MessageEntry(String.valueOf(time), message, guid, playerImageUri)));

                    String senderId = consts.SENDER_ID;
                    if ("" != senderId) {

                        String json = ConvertToJson(sms);
                        // String json = new Gson().toJson(sms);

                        MessageSender sender = new MessageSender(getActivity(), mLogger, mSenders);
                        if (sender.SendSMS(message_recipient, consts.TEST_MSG_ID, json, consts.TEST_TINE_TO_LIVE, false)) {
                            //save to db
                            realm.beginTransaction();
                            sms.setFrom(myToken);
                            realm.copyToRealm(sms);
                            realm.commitTransaction();

                            Toast.makeText(getActivity(), "sent!", Toast.LENGTH_LONG).show();
                            binding.chatLoader.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getActivity(), "failed ;/", Toast.LENGTH_LONG).show();
                        }
                        ((EditText) binding.getRoot().findViewById(R.id.users_view_lastname)).setText("");
                        ((RecyclerView) binding.getRoot().findViewById(R.id.activity_users_recycler)).smoothScrollToPosition(messagesViewModel.messageViewModels.size() - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private String ConvertToJson(SmsMessage message) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("to", message.getTo());
        jsonObject.addProperty("from", message.getFrom());
        jsonObject.addProperty("message", message.getMessage());
        jsonObject.addProperty("type", message.getType());
        jsonObject.addProperty("dateTime", message.getDateTime());
        jsonObject.addProperty("senderUrl", message.getSenderUrl());

        return jsonObject.toString();
    }

//    public View.OnClickListener onEmojiButtonClick()
//    {
//        return new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//            }
//        };
//    }

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
                new SuperUserBinder(BR.msgVM, R.layout.item_my_message),
                new UserBinder(BR.msgVM, R.layout.item_message)
        );
    }

    private void changeEmojiKeyboardIcon(ImageView iconToBeChanged, int drawableResourceId){
        iconToBeChanged.setImageResource(drawableResourceId);
    }
}

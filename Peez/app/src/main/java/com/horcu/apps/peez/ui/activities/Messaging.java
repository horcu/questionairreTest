//package com.horcu.apps.peez.ui.activities;
//
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.NotificationManager;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.ServiceConnection;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.support.v7.app.AppCompatActivity;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnKeyListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.example.dpt.bubbletextview.widget.LeBubbleTextView;
//import com.horcu.apps.peez.R;
//import com.horcu.apps.peez.chat.interfaces.IAppManager;
//import com.horcu.apps.peez.chat.services.IMService;
//import com.horcu.apps.peez.chat.tools.FriendController;
//import com.horcu.apps.peez.chat.types.FriendInfo;
//import com.horcu.apps.peez.custom.AutoFitGridLayout;
//
//import java.text.AttributedCharacterIterator;
//
//public class Messaging extends AppCompatActivity {
//
//	private static final int MESSAGE_CANNOT_BE_SENT = 0;
//	private EditText messageText;
//	private LinearLayout messageHistoryLayout;
//	private Button sendMessageButton;
//	private IAppManager imService;
//	private FriendInfo friend = new FriendInfo();
//
//	private ServiceConnection mConnection = new ServiceConnection() {
//
//		public void onServiceConnected(ComponentName className, IBinder service) {
//            imService = ((IMService.IMBinder)service).getService();
//        }
//        public void onServiceDisconnected(ComponentName className) {
//        	imService = null;
//            Toast.makeText(Messaging.this, R.string.local_service_stopped,
//                    Toast.LENGTH_SHORT).show();
//        }
//    };
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.messaging_screen); //messaging_screen);
//
//		messageHistoryLayout = (LinearLayout) findViewById(R.id.messageHistory);
//
//		messageText = (EditText) findViewById(R.id.message);
//
//		messageText.requestFocus();
//
//		sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
//
////		Bundle extras = this.getIntent().getExtras();
////
////		friend.userName = extras.getString(FriendInfo.USERNAME);
////		friend.ip = extras.getString(FriendInfo.IP);
////		friend.port = extras.getString(FriendInfo.PORT);
////		String msg = extras.getString(FriendInfo.MESSAGE);
//
//		friend.userName = "Remy";
//		friend.userKey = "qweaasqwq312";
//		friend.port = "12345";
//		String msg = "Hey daddy dute. can you take me to school?";
//
//		setTitle(friend.userName);
//
//
////		EditText friendUserName = (EditText) findViewById(R.id.friendUserName);
////		friendUserName.setText(friend.userName);
//
//		if (msg != null)
//		{
//			this.appendToMessageHistory(friend.userName , msg, false);
//			((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancel((friend.userName+msg).hashCode());
//		}
//
//		sendMessageButton.setOnClickListener(new OnClickListener(){
//			CharSequence message;
//			Handler handler = new Handler();
//			public void onClick(View arg0) {
//				message = messageText.getText();
//				if (message.length()>0)
//				{
//					appendToMessageHistory(imService.getUsername(), message.toString(), true);
//
//					messageText.setText("");
//					Thread thread = new Thread(){
//						public void run() {
//							if (!imService.sendMessage(friend.userName, message.toString()))
//							{
//								handler.post(new Runnable(){
//
//									public void run() {
//										showDialog(MESSAGE_CANNOT_BE_SENT);
//									}
//
//								});
//							}
//						}
//					};
//					thread.start();
//
//				}
//
//			}});
//
//		messageText.setOnKeyListener(new OnKeyListener(){
//			public boolean onKey(View v, int keyCode, KeyEvent event)
//			{
//				if (keyCode == 66){
//					sendMessageButton.performClick();
//					return true;
//				}
//				return false;
//			}
//
//
//		});
//
//	}
//
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		int message = -1;
//		switch (id)
//		{
//		case MESSAGE_CANNOT_BE_SENT:
//			message = R.string.message_cannot_be_sent;
//		break;
//		}
//
//		if (message == -1)
//		{
//			return null;
//		}
//		else
//		{
//			return new AlertDialog.Builder(Messaging.this)
//			.setMessage(message)
//			.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int whichButton) {
//					/* User clicked OK so do some stuff */
//				}
//			})
//			.create();
//		}
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		unregisterReceiver(messageReceiver);
//		unbindService(mConnection);
//
//		FriendController.setActiveFriend(null);
//
//	}
//
//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		bindService(new Intent(Messaging.this, IMService.class), mConnection , Context.BIND_AUTO_CREATE);
//
//		IntentFilter i = new IntentFilter();
//		i.addAction(IMService.TAKE_MESSAGE);
//
//		registerReceiver(messageReceiver, i);
//
//		FriendController.setActiveFriend(friend.userName);
//	}
//
//
//	public class  MessageReceiver extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, Intent intent)
//		{
//			Bundle extra = intent.getExtras();
//			String username = extra.getString(FriendInfo.USERNAME);
//			String message = extra.getString(FriendInfo.MESSAGE);
//
//			if (username != null && message != null)
//			{
//				if (friend.userName.equals(username)) {
//					appendToMessageHistory(username, message, false);
//				}
//				else {
//					if (message.length() > 15) {
//						message = message.substring(0, 15);
//					}
//					Toast.makeText(Messaging.this,  username + " says '"+
//													message + "'",
//													Toast.LENGTH_SHORT).show();
//				}
//			}
//		}
//
//	};
//	private MessageReceiver messageReceiver = new MessageReceiver();
//
//	private void appendToMessageHistory(String username, String message, boolean me) {
//		if (username != null && message != null) {
////			messageHistoryLayout.append(username + ":\n");
////			messageHistoryLayout.append(message + "\n");
//
//
//			LeBubbleTextView btv = new LeBubbleTextView(this);
//			btv.getContentTextView().setText(username + ":\n" + message);
//
//			if(!me) {
//				btv.getContentTextView().setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//				btv.setBackgroundColor(Color.parseColor("#5c6ead"));
//				btv.setGravity(Gravity.RIGHT);
//				btv.layout(5,10,5,20);
//			}
//			else
//			{
//				btv.getContentTextView().setTextColor(Color.parseColor("#5c6ead"));
//				btv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//				btv.setGravity(Gravity.LEFT);
//				btv.layout(5,20,5,10);
//			}
//
//			messageHistoryLayout.addView(btv);
//
//
//		}
//	}
//
//
//
//
//
//}

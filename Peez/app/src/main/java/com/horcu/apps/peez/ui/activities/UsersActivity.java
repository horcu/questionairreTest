package com.horcu.apps.peez.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.adapters.UserAdapter;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.CollectionResponseUser;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.custom.Api;
import com.horcu.apps.peez.custom.ViewController;
import com.horcu.apps.peez.listener.RecyclerItemClickListener;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements
            View.OnClickListener{

    private UserApi userApi;
    private CollectionResponseUser allFriends;
    private ArrayList<User> allLocalFriends;
    private SharedPreferences settings;
    private String me;
    private RecyclerView friendsList;
    ArrayList<User> selectedUsers = new ArrayList<>();
    ArrayList<User> DisplayedUsersList = new ArrayList<>();
    ViewController viewController = new ViewController();
    private Button done;

    private ArrayList<String> returnedSelectedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        friendsList = (RecyclerView)findViewById(R.id.users_list);
        friendsList.setLayoutManager(new LinearLayoutManager(this));

        done = (Button)findViewById(R.id.done);
        done.setOnClickListener(this);
        userApi = Api.BuildUserApiService();
        settings = getSharedPreferences("Peez", 0);

        showContacts();

        //eventually save the last set of users in the
        //userPreference and show those intially in the list of contacts
    }

    protected void returnDataToBetpage(Intent intent){
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    protected void showContacts()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    allFriends = userApi.list().execute();
                    allLocalFriends = getLocalContacts();

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                try {
                    allFriends
                            .getItems()
                            .addAll(allLocalFriends);

                    DisplayedUsersList = new ArrayList<>();
                    me = settings.getString(consts.PREF_ACCOUNT_NAME, "");
                    for (User user : allFriends.getItems()) {
                        String email = user.getEmail();
                        if (email != null && !email.equals(me)) {
                            DisplayedUsersList.add(user);
                        }
                    }

                    UserAdapter userAdapter = new UserAdapter(DisplayedUsersList, getApplicationContext());// new ArrayAdapter<>(getApplicationContext(), R.layout.user_item, R.id.friend, friends);
                    friendsList.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private ArrayList<User> getLocalContacts(){
        ArrayList<User> users = null;
        try {
            users = new ArrayList<User>();
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            assert phones != null;
            while (phones.moveToNext())
            {
                User user = new User();
                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String email = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
                String imageUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));
                user.setPhone(phoneNumber);
                user.setUserName(name);
                user.setEmail(email);
                user.setImageUri(imageUri);

                users.add(user);

            }
            phones.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.done) {
            Intent intent = new Intent();
            intent.putExtra(consts.SELECTED_FRIENDS, returnedSelectedUsers);
            returnDataToBetpage(intent);
        }
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        User selected = DisplayedUsersList.get(position);
//        //ImageView checkBtn =
//
//        if(returnedSelectedUsers.contains(selected.getUserName()))
//        {
//            returnedSelectedUsers.remove(selected.getUserName());
//            selectedUsers.remove(selected);
//        }
//        else
//        {
//            returnedSelectedUsers.add(selected.getUserName());
//            selectedUsers.add(selected);}
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}

//package com.horcu.apps.peez.model.app.obs;
//import android.databinding.ObservableArrayList;
//import android.databinding.ObservableList;
//
//import com.horcu.apps.peez.R;
//
//import com.horcu.apps.peez.backend.models.userApi.model.User;
//import com.horcu.apps.peez.model.app.Friend;
//
//
//
///**
// * Created by Horatio on 2/16/2016.
// */
//public class FriendViewModel {
//
//    public  ObservableList<Friend> friends = new ObservableArrayList<>() ;
//    public  ItemView itemView = ItemView.of(BR.friend, R.layout.friend_listitem);
//
//    public void addItem(User user, String friendSince, String telephone) {
//        friends.add(new Friend(user, friendSince, telephone));
//    }
//
//    public void removeItem(Friend friend) {
//        if (friends.size() > 1) {
//            friends.remove(friend);
//        }
//    }
//}

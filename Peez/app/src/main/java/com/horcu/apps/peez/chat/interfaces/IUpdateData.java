package com.horcu.apps.peez.chat.interfaces;


import com.horcu.apps.peez.chat.types.FriendInfo;

public interface IUpdateData {
	void updateData(FriendInfo[] friends, FriendInfo[] unApprovedFriends, String userKey);

}

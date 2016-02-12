package com.horcu.apps.peez.chat.interfaces;


public interface IAppManager {
	
	String getUsername();
	boolean sendMessage(String username, String message);

	String authenticateUser(String usernameText, String passwordText);
	void messageReceived(String message);
//	public void setUserKey(String value);
boolean isNetworkConnected();
	boolean isUserAuthenticated();
	String getLastRawFriendList();
	void exit();
	String signUpUser(String usernameText, String passwordText, String email);
	String addNewFriendRequest(String friendUsername);
	String sendFriendsReqsResponse(String approvedFriendNames,
								   String discardedFriendNames);

	
}

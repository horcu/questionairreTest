///*
//Copyright 2015 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
// */
//package com.horcu.apps.peez.ui;
//
//import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.view.MenuItem;
//
//import com.horcu.apps.peez.R;
//import com.horcu.apps.peez.logic.quicktest.DownstreamHttpJsonQuickTest;
//import com.horcu.apps.peez.logic.quicktest.GetTokenQuickTest;
//import com.horcu.apps.peez.logic.quicktest.QuickTest;
//import com.horcu.apps.peez.service.LoggingService;
//import com.horcu.apps.peez.ui.activities.GCMActivity;
//import com.horcu.apps.peez.ui.fragments.DownstreamFragment;
//import com.horcu.apps.peez.ui.fragments.GroupsFragment;
//import com.horcu.apps.peez.ui.fragments.HomeFragment;
//import com.horcu.apps.peez.ui.fragments.InstanceIdFragment;
//import com.horcu.apps.peez.ui.fragments.NetworkSchedulerFragment;
//import com.horcu.apps.peez.ui.fragments.NotificationFragment;
//import com.horcu.apps.peez.ui.fragments.TopicsFragment;
//import com.horcu.apps.peez.ui.fragments.UpstreamFragment;
//
//import java.util.LinkedHashMap;
//
///**
// * Drawer menu for the app
// */
//public class MainMenu {
//    private final GCMActivity mActivity;
//    private LinkedHashMap<CharSequence, Class<? extends Fragment>> mMenu = new LinkedHashMap<>();
//
//    public MainMenu(GCMActivity mActivity) {
//        this.mActivity = mActivity;
//        addMenuEntry(R.string.main_menu_home, HomeFragment.class);
//        addMenuEntry(R.string.main_menu_instanceid, InstanceIdFragment.class);
//        addMenuEntry(R.string.main_menu_downstream, DownstreamFragment.class);
//        addMenuEntry(R.string.main_menu_upstream, UpstreamFragment.class);
//        addMenuEntry(R.string.main_menu_notification, NotificationFragment.class);
//        addMenuEntry(R.string.main_menu_groups, GroupsFragment.class);
//        addMenuEntry(R.string.main_menu_topics, TopicsFragment.class);
//        addMenuEntry(R.string.main_menu_network_manager, NetworkSchedulerFragment.class);
//    }
//
//    public CharSequence[] getEntries() {
//        return mMenu.keySet().toArray(new CharSequence[mMenu.size()]);
//    }
//
//    public Fragment createFragment(int position)
//            throws InstantiationException, IllegalAccessException {
//        return mMenu.get(getEntries()[position]).newInstance();
//    }
//
//    public boolean onOverflowMenuItemSelected(MenuItem item) {
//   //     switch (item.getItemId()) {
////            case R.id.toggle_logs: {
////                CharSequence showLogs = mActivity.getString(R.string.show_logs);
////                if (showLogs.equals(item.getTitle())) {
////                    // mActivity.toggleLogsView(true);
////                    item.setTitle(R.string.hide_logs);
////                    item.setIcon(R.drawable.visibility_off_white);
////                } else {
////                    //   mActivity.toggleLogsView(false);
////                    item.setTitle(R.string.show_logs);
////                    item.setIcon(R.drawable.visibility_white);
////                }
////                return true;
////            }
////            case R.id.clear_logs: {
////                (new LoggingService.Logger(mActivity)).clearLogs();
////                return true;
////            }
////            default:
////                return false;
//      //  }
//    return true;
//    }
//
//    public static LinkedHashMap<String, QuickTest> getTests(Context context) {
//        LinkedHashMap<String, QuickTest> tests = new LinkedHashMap<>();
//        addTest(context, tests, new GetTokenQuickTest());
//        addTest(context, tests, new DownstreamHttpJsonQuickTest());
//        return tests;
//    }
//
//    private void addMenuEntry(int title, Class<? extends Fragment> fragment) {
//        mMenu.put(mActivity.getText(title), fragment);
//    }
//
//    private static void addTest(Context context, LinkedHashMap<String, QuickTest> arrayMap,
//                                QuickTest test) {
//        arrayMap.put(context.getText(test.getName()).toString(), test);
//    }
//}

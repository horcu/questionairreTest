package com.horcu.apps.peez;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.tatarka.bindingcollectionadapter.sample.databinding.ViewpagerViewBinding;

/**
 * Created by evan on 5/31/15.
 */
public class FragmentViewPagerView extends Fragment {
    private static final String TAG = "BindingViewPager";
    private ViewModel viewModel;
    private ViewpagerViewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        viewModel = new ViewModel(true, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerViewBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setListeners(new PagerListeners(viewModel));
        binding.executePendingBindings();

        PagerAdapter adapter = binding.pager.getAdapter();
        binding.tabs.setTabsFromPagerAdapter(adapter);
        binding.tabs.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.pager));
        binding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        return binding.getRoot();
    }

    private class PagerListeners extends Listeners {
        public PagerListeners(ViewModel viewModel) {
            super(viewModel);
        }

        @Override
        public void onAddItem() {
            super.onAddItem();
            PagerAdapter adapter = binding.pager.getAdapter();
            binding.tabs.setTabsFromPagerAdapter(adapter);
        }

        @Override
        public void onRemoveItem() {
            super.onRemoveItem();
            PagerAdapter adapter = binding.pager.getAdapter();
            binding.tabs.setTabsFromPagerAdapter(adapter);
        }
    }
}

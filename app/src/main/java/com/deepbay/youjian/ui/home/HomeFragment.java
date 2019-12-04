package com.deepbay.youjian.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deepbay.youjian.R;
import com.deepbay.youjian.adapter.SimpleBaseBindingAdapter;
import com.deepbay.youjian.databinding.FragmentHomeBinding;
import com.deepbay.youjian.databinding.ItemCourseBinding;
import com.deepbay.youjian.entity.Course;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ClickProxy mClickProxy;
    private SimpleBaseBindingAdapter<Course, ItemCourseBinding> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.bind(root);
        binding.setVm(homeViewModel);
        binding.setClick(mClickProxy = new ClickProxy());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SimpleBaseBindingAdapter<Course, ItemCourseBinding>(getContext(), R.layout.item_course) {

            @Override
            protected void onSimpleBindItem(ItemCourseBinding binding, Course item, RecyclerView.ViewHolder holder) {
                binding.courseTitle.setText(item.getTitle());
                binding.textDuration.setText(String.format("已练习\n%s", item.getDruation()));
                Glide.with(binding.courseIcon.getContext()).load(item.getIcon()).into(binding.courseIcon);
            }
        };
        binding.rv.setAdapter(adapter);
    }

    public class ClickProxy {
        public void showMessage() {
            Toast.makeText(getContext(), "test", Toast.LENGTH_LONG).show();
        }
    }
}
package com.example.medicationreminderapplication;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ByDay extends Fragment {

    public ByDay() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_by_day, container, false);
        ViewPager2 viewPagerByDay = (ViewPager2) root.findViewById(R.id.viewPagerByDays);
        viewPagerByDay.setAdapter(new AdapterByDay(this.getActivity()));
        TabLayout tabLayout1 = (TabLayout) root.findViewById(R.id.tabsDays);

        new TabLayoutMediator(tabLayout1, viewPagerByDay, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Every 'X' Days");
                }
                else{
                    tab.setText("Specific Days");
                }
            }
        }).attach();
        return root;
    }

    class AdapterByDay extends FragmentStateAdapter {

        public AdapterByDay(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public AdapterByDay(@NonNull Fragment fragment) {
            super(fragment);
        }

        public AdapterByDay(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0){
                return new ByXDays();
            }
            else {
                return new BySpecificDay();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
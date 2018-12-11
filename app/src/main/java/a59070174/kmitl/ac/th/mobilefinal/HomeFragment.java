package a59070174.kmitl.ac.th.mobilefinal;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button profileSetup, myFriends, signout;
    private TextView homeHello, homeHello2;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SharedPreferences preferences = getActivity().getSharedPreferences("final", Context.MODE_PRIVATE);

        profileSetup = getActivity().findViewById(R.id.profile);
        myFriends =getActivity().findViewById(R.id.myfriends);
        signout = getActivity().findViewById(R.id.singout);
        homeHello = getActivity().findViewById(R.id.home_text);
        homeHello2 = getActivity().findViewById(R.id.home_text2);

        myFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new FriendFragment());
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().remove("userid").commit();
                preferences.edit().remove("name").commit();
                changeFragment(new LoginFragment());
            }
        });


        String name = preferences.getString("name","null");

        homeHello.setText(String.format("Hello %s", name));

    }

    private void changeFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, fragment)
                .commit();
    }

    private void addFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, fragment)
                .addToBackStack(null)
                .commit();
    }
}

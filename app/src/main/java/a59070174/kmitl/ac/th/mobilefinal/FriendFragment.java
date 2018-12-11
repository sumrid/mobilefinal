package a59070174.kmitl.ac.th.mobilefinal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.tls.OkHostnameVerifier;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {
    private ListView firendLists;
    private Button back;
    private List<Friend> friends = new ArrayList<>();

    OkHttpClient client;


    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firendLists = getActivity().findViewById(R.id.friend_list);
        back = getActivity().findViewById(R.id.friend_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

//        getFriend();
    }

    private void getFriend() {
        client = new OkHttpClient();

        String url = "https://jsonplaceholder.typicode.com/users";

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String jsonData = response.body().string();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Load", jsonData);
//                            jsonToObject(jsonData);
                        }
                    });
                }
            }
        });
    }

    private void jsonToObject(String json) {
        Type type = new TypeToken<ArrayList<Friend>>(){}.getType();
        ArrayList<Friend> dataSet = new Gson().fromJson(json, type);

        Log.d("Load", json);
//        posts.clear();
//        posts.addAll(dataSet);
//        adapter.notifyDataSetChanged();
    }
}

package a59070174.kmitl.ac.th.mobilefinal;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    private EditText userid;
    private EditText password;
    private Button loginButton;
    private TextView registerButton;

    SQLiteDatabase database;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        database = getActivity().openOrCreateDatabase("final.db", Context.MODE_PRIVATE, null);
        database.execSQL("create table if not exists user (userid varchar(255) primary key, name varchar(255), age int, password varchar(255))");

        userid = getActivity().findViewById(R.id.login_userid);
        password = getActivity().findViewById(R.id.login_password);
        loginButton = getActivity().findViewById(R.id.login_button);
        registerButton = getActivity().findViewById(R.id.register_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Check userid and password
                checkInput();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void checkInput() {
        if (userid.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            showToast("Please fill out this form");
        } else {
            login(userid.getText().toString(), password.getText().toString());
        }
    }

    private void login(String userid, String password) {
        String sql = String.format("select * from user where userid = '%s' and password = '%s'", userid, password);
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.getCount() < 1) {
            Log.d("Login", "don't have userid");
            showToast("Invalid user or password");
        } else {
            Log.d("Login", "have userid");
            showToast("login");

            // store in preference
            SharedPreferences preferences = getContext().getSharedPreferences("final", Context.MODE_PRIVATE);

            //get name
            String name = "";
            cursor.moveToPosition(0);
            name = cursor.getString(1);

            // save to preference
            preferences.edit().putString("userid", userid).commit();
            preferences.edit().putString("name", name).commit();

            // change fragment
            changeFragment(new HomeFragment());
        }
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void changeFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, fragment)
                .commit();
    }
}

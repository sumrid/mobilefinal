package a59070174.kmitl.ac.th.mobilefinal;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterFragment extends Fragment {
    private EditText userid, name, age, password;
    private Button regisButton;

    private SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userid = getActivity().findViewById(R.id.regis_userid);
        name = getActivity().findViewById(R.id.regis_name);
        age = getActivity().findViewById(R.id.regis_age);
        password = getActivity().findViewById(R.id.regis_password);
        regisButton = getActivity().findViewById(R.id.regis_button);

        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEditText();
            }
        });

    }

    private void checkEditText(){
        String useridStr = userid.getText().toString();
        String nameStr = name.getText().toString();
        String ageStr = age.getText().toString();
        String passwordStr = password.getText().toString();

        if(useridStr.isEmpty() || nameStr.isEmpty() || ageStr.isEmpty() || passwordStr.isEmpty()) {
            Toast.makeText(getContext(), "กรุณากรอกข้อมูล", Toast.LENGTH_SHORT).show();
        } else {
            int age = Integer.parseInt(ageStr);

            String[] text = {""};
            text = nameStr.trim().split(" ");

            if(useridStr.length() <6 || useridStr.length()>12) {
                userid.setError("6 ถึง 12 ตัว");
            } else if (text.length<2){
                name.setError("");
            } else if (age < 10 || age > 80) {
                this.age.setError("10 - 80");
            }
            else {
                regist(useridStr, nameStr, ageStr, passwordStr);
            }
        }
    }

    private void regist(String id, String name, String age, String password){
        ContentValues row = new ContentValues();
        row.put("userid", id);
        row.put("name", name);
        row.put("age", Integer.parseInt(age));
        row.put("password", password);

        database = getActivity().openOrCreateDatabase("final.db", Context.MODE_PRIVATE, null);

        // save database
        database.insert("user", null, row);
        Toast.makeText(getContext(), "เรียบร้อย", Toast.LENGTH_SHORT).show();

        getFragmentManager().popBackStack();
    }
}

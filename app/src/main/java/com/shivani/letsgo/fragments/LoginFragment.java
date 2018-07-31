package com.shivani.letsgo.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shivani.letsgo.R;
import com.shivani.letsgo.activities.DashboardActivity;
import com.shivani.letsgo.activities.SignupLoginActivity;
import com.shivani.letsgo.jsonparser.JsonParser;
import com.shivani.letsgo.utils.SessionMaintain;
import com.shivani.letsgo.utils.URLendpoint;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    TextView newusertextv;
    EditText username, password;
    Button loginbt;
    ProgressDialog progressDialog;

    public LoginFragment() {

    }

    SessionMaintain sessionMaintain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        newusertextv = (TextView) view.findViewById(R.id.clickhere);
        username = (EditText) view.findViewById(R.id.Username);
        password = (EditText) view.findViewById(R.id.password);
        loginbt = (Button) view.findViewById(R.id.loginbt);
        sessionMaintain = new SessionMaintain(getContext());
        newusertextv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignupLoginActivity) getActivity()).showTab(0);
            }
        });

        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = validation(username.getText().toString(), password.getText().toString());
                if (message.equals("")) {
                    new Logintask(username.getText().toString(),
                            password.getText().toString()).execute();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(message).setCancelable(false).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });


        return view;

    }

    public String validation(String username, String password) {

        String message = "";
        if (username.equals("")) {
            message = message + "Username is mandatory";
        }
        if (password.equals("")) {
            message = message + "Password is mandatory";

        }
        return message;
    }


    class Logintask extends AsyncTask<Void, Void, String> {
        public static final int MAX_EXECUTION = 5;
        String username, password;

        public Logintask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loding......");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            Map<String, String> checkusername = new HashMap<>();
            checkusername.put("username", username);
            checkusername.put("password", password);
            int i = 1;
            while (i <= MAX_EXECUTION) {
                try {
                    i=6;
                    return JsonParser.post(URLendpoint.LOGIN_URL, checkusername);
                } catch (Exception e) {
                    e.printStackTrace();
                    i++;
                }
            }

            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                progressDialog.dismiss();
                Log.d("fffffffffff", s);
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getInt("success") == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    String firstName = jsonObject1.getString("firstName");
                    String lastName = jsonObject1.getString("lastName");
                    String email = jsonObject1.getString("email");
                    String phone = jsonObject1.getString("phone");
                    String password = jsonObject1.getString("password");
                    String gcm_reqid = jsonObject1.getString("gcm_reqid");

                    sessionMaintain.createUserLoginSession(firstName + "" + lastName, email, phone);
                    startActivity(new Intent(LoginFragment.this.getContext(), DashboardActivity.class));
                    ((SignupLoginActivity) getActivity()).finish();
                }
            } catch (Exception e) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("Intenet problem").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Logintask(LoginFragment.this.username.getText().toString(), LoginFragment.this.password.getText().toString()).execute();
                    }

                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                dialog.show();
                e.printStackTrace();
            }
        }
    }
}

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
import com.shivani.letsgo.activities.Dashboard1;
import com.shivani.letsgo.activities.DashboardActivity;
import com.shivani.letsgo.activities.SignupLoginActivity;
import com.shivani.letsgo.jsonparser.JsonParser;
import com.shivani.letsgo.utils.SessionMaintain;
import com.shivani.letsgo.utils.URLendpoint;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {



    EditText firstname,lastname,e_mail,phoneno,password,cfrmpassword;
    Button signupbt;
    ProgressDialog progressDialog;
    public SignupFragment() {

    }

    TextView alreayusertextv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_signup, container, false);
        sessionMaintain=new SessionMaintain(this.getContext());
        alreayusertextv=(TextView)view.findViewById(R.id.alreadyuser2);
        firstname =(EditText)view.findViewById(R.id.firstname);
        lastname=(EditText)view.findViewById(R.id.lastname);
        e_mail=(EditText)view.findViewById(R.id.email);
        phoneno=(EditText)view.findViewById(R.id.phoneno);
        password=(EditText)view.findViewById(R.id.password);
        cfrmpassword=(EditText)view.findViewById(R.id.confirmpassword);
        signupbt=(Button)view.findViewById(R.id.signupbt);

        alreayusertextv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignupLoginActivity)getActivity()).showTab(1);


            }
        });

        signupbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String msg=validation(firstname.getText().toString()
                        ,lastname.getText().toString(),
                        e_mail.getText().toString(),
                        phoneno.getText().toString(),
                        password.getText().toString(),
                        cfrmpassword.getText().toString());

                if(msg.equals(""))
                {
                    new SignUpTask(firstname.getText().toString()
                            ,lastname.getText().toString(),
                            e_mail.getText().toString(),
                            phoneno.getText().toString(),
                            password.getText().toString(),
                            cfrmpassword.getText().toString()).execute();
                }

                else
                {
                    final AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                    builder.setMessage(msg).setCancelable(false).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }

                    });

                    AlertDialog alert= builder.create();
                    alert.show();
                }
            }
        });



        return view;




    }

    public String validation(String fname,String lname,String email,String phone,String password,String cpassword)
    {
        String message="";
        if(fname.equals(""))
        {
            message+="First Name  is mandatory";
        }
            if(lname.equals(""))
            {
                message+="Last Name  is mandatory";
            }
            if(email.equals(""))
            {
                message+="Email  is mandatory";
            }
            if(phone.equals(""))
            {
                message+="Phone  is mandatory";
            }
            if(password.equals(""))
            {
                message+="Password  is mandatory";
            }

            if(cpassword.equals(""))
            {
                message+="Confirm Password  is mandatory";
            }


        return message;
    }



    class SignUpTask extends AsyncTask<Void,Void,String>{
        String fname,lname,email,phone,password,cpassword;
        SignUpTask(String fname,String lname,String email,String phone,String password,String cpassword)
        {
            this.fname=fname;
            this.lname=lname;
            this.email=email;
            this.phone=phone;
            this.password=password;
            this.cpassword=cpassword;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Loding......");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Map<String,String> stringStringMap=new HashMap<>();
            stringStringMap.put("fname",fname);
            stringStringMap.put("lname",lname);
            stringStringMap.put("email",email);
            stringStringMap.put("phone",phone);
            stringStringMap.put("password",password);
            try {
                return JsonParser.post(URLendpoint.REG_URL,stringStringMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("fffffffffff",s);
            try {
              JSONObject jsonObject=  new JSONObject(s);
                if (jsonObject.getInt("success")==1)
                {
                    startActivity(new Intent(SignupFragment.this.getContext(), DashboardActivity.class));
                    if (getActivity() instanceof SignupLoginActivity){
                        sessionMaintain.createUserLoginSession(fname+" "+lname,email,phone);
                        ((SignupLoginActivity)getActivity()).finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    SessionMaintain sessionMaintain;
}

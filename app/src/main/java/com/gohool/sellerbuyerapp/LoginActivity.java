package com.gohool.sellerbuyerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gohool.sellerbuyerapp.Model.Users;
import com.gohool.sellerbuyerapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public
class LoginActivity extends AppCompatActivity {

    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;

    private String parentDbName ="Users";
    private CheckBox chkBoxRememberMe;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        LoginButton=(Button)findViewById ( R.id.login_btn );
        InputNumber=(EditText)findViewById ( R.id.login_number_input );
        InputPassword=(EditText)findViewById ( R.id.login_password_input );
        AdminLink=(TextView )findViewById ( R.id.admin_panel_link );
        NotAdminLink =(TextView )findViewById ( R.id.not_admin_panel_link );
        loadingBar= new ProgressDialog ( this );

        chkBoxRememberMe = (CheckBox )findViewById ( R.id.remember_me_chkb );
        Paper.init ( this );

        LoginButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                loginUser();
            }
        } );

        AdminLink.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {

                LoginButton.setText("Login Admin");
                AdminLink.setVisibility ( View.INVISIBLE );
                NotAdminLink.setVisibility ( View.VISIBLE );
                parentDbName ="Admins";
            }
        } );

        NotAdminLink.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {

                LoginButton.setText ( "Login" );
                AdminLink.setVisibility ( View.VISIBLE );
                NotAdminLink.setVisibility ( View.INVISIBLE );
                parentDbName ="Users";


            }
        } );
    }

    private
    void loginUser() {
         String phone = InputNumber.getText ().toString ();
         String password = InputPassword.getText ().toString ();

         if (TextUtils.isEmpty ( phone )){
             Toast.makeText ( this,"Please write your phone number...",Toast.LENGTH_LONG ).show ();
         }else if (TextUtils.isEmpty ( password )){
             Toast.makeText ( this,"Please write your password...",Toast.LENGTH_LONG ).show ();
         }
         else {
             loadingBar.setTitle ( "Login Account" );
             loadingBar.setMessage ( "Please wait while we are checking the credentials" );
             loadingBar.setCanceledOnTouchOutside ( false );
             loadingBar.show ();


             AllowAccessAccount(phone, password);
         }
    }

    private void AllowAccessAccount(final String phone, final String password) {

        if (chkBoxRememberMe.isChecked ())
        {
            Paper.book ().write ( Prevalent.userPhoneKey,phone );
            Paper.book ().write ( Prevalent.userPasswordKey,password );
        }



        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance ().getReference ();

        RootRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public
            void onDataChange( DataSnapshot dataSnapshot) {

                if (dataSnapshot.child ( parentDbName ).child ( phone ).exists () )
                {
                    Users usersData = dataSnapshot.child ( parentDbName ).child ( phone ).getValue (Users.class  );
                   // Log.e("***", "onDataChange -> " + usersData.toString());

                    if (usersData.getPhone ().equals ( phone ))
                    {
                        if (usersData.getPassword ().equals ( password ))
                        {
                           if (parentDbName.equals ( "Admins" ))
                           {
                               Toast.makeText ( LoginActivity.this," Welcome Admin, you are logged in Successfully...",Toast.LENGTH_SHORT ).show ();
                               loadingBar.dismiss ();

                              Intent intent = new Intent ( LoginActivity.this,AdminCategoryActivity.class );
                             startActivity ( intent );

                           }
                           else if (parentDbName.equals ( "Users" ))
                           {
                               Toast.makeText ( LoginActivity.this,"logged in Successfully...",Toast.LENGTH_SHORT ).show ();
                               loadingBar.dismiss ();

                               Intent intent = new Intent ( LoginActivity.this,HomeActivity.class );
                               Prevalent.currentOnlineUser = usersData;
                               startActivity ( intent );

                           }
                        }
                        else
                        {
                            Toast.makeText ( LoginActivity.this,"Password is incorrect.",Toast.LENGTH_SHORT ).show ();
                            loadingBar.dismiss ();
                        }

                    }
                }
                else
                {
                    Toast.makeText ( LoginActivity.this,"Account with this " + phone +" number doesn't exists",Toast.LENGTH_SHORT ).show ();
                    loadingBar.dismiss ();
                   // Toast.makeText ( LoginActivity.this,"you need to create a new account ",Toast.LENGTH_SHORT ).show ();
                }

            }

            @Override
            public
            void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }
}

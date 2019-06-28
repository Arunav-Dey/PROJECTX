package util.softs.projectx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    Button signupbtn;
    EditText passwordinp, emailinp;
    TextView loginbtn,loginstatus;
    ProgressDialog progressDialog;
    String email, password;
    ConstraintLayout clt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clt = (ConstraintLayout) findViewById(R.id.clt);
        firebaseAuth = FirebaseAuth.getInstance();
        signupbtn = (Button) findViewById(R.id.signupbtn);
        passwordinp = (EditText) findViewById(R.id.passwordinp);
        emailinp = (EditText) findViewById(R.id.emailinp);
        loginbtn = (TextView) findViewById(R.id.loginbtn);
        loginstatus = (TextView) findViewById(R.id.loginstatus);
        progressDialog = new ProgressDialog(this);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });


    }

    private void registerUser() {

        //getting email and password from edit texts
        String email = emailinp.getText().toString().trim();
        String password = passwordinp.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                        } else {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

    private void userLogin() {
        String email = emailinp.getText().toString().trim();
        String password = passwordinp.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Signing in Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            //startActivity(new Intent(getApplicationContext(),Home.class));
                            Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_LONG).show();
                        }
                       /* else
                        {
                            Toast.makeText(MainActivity.this,"Wrong Credentials",Toast.LENGTH_LONG);

                        }*/
                    }
                });

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnFailureListener(MainActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

               // Toast.makeText(MainActivity.this,"Wrong Credentials",Toast.LENGTH_LONG);
               //loginstatus.setText("LOGIN FAILURE");
                //Toast.makeText(MainActivity.this,"Wrong Credentials",Toast.LENGTH_LONG);



            }
        });


    }
}


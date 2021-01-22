package my.application.mytestapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);
        Info = findViewById(R.id.tvInfo);
        Login = findViewById(R.id.btnLogin);
        TextView userRegistration = findViewById(R.id.tvRegister);
        TextView forgotPassword = findViewById(R.id.tvForgotPassword);

        Info.setText("No of attempts remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(Login.this, MainActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserLogin();
                handleValidation();
                validate(Email.getText().toString(), Password.getText().toString());


            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, PasswordActivity.class));
            }
        });
    }



    private void handleValidation() {
        final String userEmail = Email.getText().toString();
        final String userPassword = Password.getText().toString();
        validateEmailPass(userEmail, userPassword);
    }

    private void validate(String userEmail, String userPassword) {
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).
                addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    //Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                }else{
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if(counter == 0){
                        Login.setEnabled(false);
                    }
                }
            }
        });


    }

    public void checkUserLogin(){
        if(firebaseAuth.getCurrentUser() != null) {
            Intent profileIntent = new Intent(this, MainActivity.class);
            this.startActivity(profileIntent);
        }
            else
                {
                    Toast.makeText(Login.this,
                            "Please Login or Register",
                            Toast.LENGTH_SHORT).show();
                }

    }

    private void validateEmailPass(String userEmail, String userPassword) {

        if (TextUtils.isEmpty(userEmail)) {
            Email.setError("Required.");
        } else if (!userEmail.contains("@")) {
            Email.setError("Not an email id.");
        } else {
            Email.setError(null);
        }

        if (TextUtils.isEmpty(userPassword)) {
            Password.setError("Required.");
        } else if (userPassword.length() < 6) {
            Password.setError("Min 6 chars.");
        }
        else{
            Password.setError(null);
        }

    }


    private void checkEmailVerification(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        boolean emailflag = firebaseUser.isEmailVerified();

       if(emailflag){
            finish();
            startActivity(new Intent(Login.this, Login.class));
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
       }


    }


}

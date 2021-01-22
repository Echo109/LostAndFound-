package my.application.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private Button update;
    private EditText newPassword;
    String pass1;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        update = findViewById(R.id.btnUpdatePassword);
        newPassword = findViewById(R.id.etNewPassword);

        pass1 = newPassword.getText().toString();

        if (pass1.isEmpty()) {
            Toast.makeText(UpdatePassword.this, "Enter password", Toast.LENGTH_SHORT).show();

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userPasswordNew = newPassword.getText().toString();
                firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdatePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpdatePassword.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdatePassword.this,UploadActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.HomeMenu) {

            startActivity(new Intent(this, MainActivity.class));

            return true;
        }
        if (id == R.id.profileMenu) {

            startActivity(new Intent(this, ProfileActivity.class));

            return true;
        }

        if (id == R.id.feedbackMenu) {

            Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
            feedbackEmail.setType("text/email");
            feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"josephkimani240@gmail.com"});
            startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
            return true;

        }
        if (id == R.id.About) {
            startActivity(new Intent(this, About.class));
            return true;

        }

        if (id == R.id.logoutMenu) {

            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}

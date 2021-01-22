package my.application.mytestapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;

public class About extends AppCompatActivity {
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(About.this,UploadActivity.class));

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        readFromTextFile();


    }


    public void readFromTextFile() {
        try {
            InputStream input = getAssets().open("about.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();//closing input stream to avoid resource leaks
            String text = new String(buffer);//convert the read buffer to sting
            TextView tvabout = findViewById(R.id.myAbout);
            tvabout.setText(text);

        } catch (IOException ioe) {
            Toast.makeText(getApplicationContext(), "Sorry, app has encountered an error", Toast.LENGTH_LONG).show();
        }
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

            startActivity(new Intent(this,MainActivity.class));

            return true;
        }
        if (id == R.id.profileMenu) {

            startActivity(new Intent(this,ProfileActivity.class));

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
            Toast.makeText(this, "You are here!", Toast.LENGTH_SHORT).show();

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

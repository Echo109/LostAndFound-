package my.application.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;



public class activity_detail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imgView;
    TextView dateTxt,ItemnameTxt,pnumTxt,locTxt,statusTxt,descTxt;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);


            imgView = findViewById(R.id.itemimageView);
            dateTxt = findViewById(R.id.dateDetailTxt);
            ItemnameTxt = findViewById(R.id.nameDetailTxt);
            //usernmTxt = findViewById(R.id.nameDetailTxt2);
            pnumTxt = findViewById(R.id.phoneDetailTxt);
            pnumTxt.setMovementMethod(LinkMovementMethod.getInstance());
            locTxt = findViewById(R.id.locDetailTxt);
            statusTxt = findViewById(R.id.statusDetailTxt);
            descTxt = findViewById(R.id.descDetailTxt);


        //GET INTENT

        Intent i=this.getIntent();

        //RECEIVE DATA
        String image=i.getExtras().getString("IMAGE_KEY");
        String datetxt =i.getExtras().getString("DATE_KEY");
        String Itemname =i.getExtras().getString("NAME_KEY");
        String pnum =i.getExtras().getString("PNUM_KEY");
        String loc =i.getExtras().getString("LOC_KEY");
        String status =i.getExtras().getString("STATUS_KEY");
        String desc=i.getExtras().getString("DESC_KEY");

        //BIND DATA
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.add_image)
                .fit()
                .centerCrop()
                .into(imgView);

        dateTxt.setText("Date posted: "+datetxt);
        ItemnameTxt.setText("Item name: "+Itemname);
        pnumTxt.setText("Phone Number: "+pnum);
        locTxt.setText("Location: "+loc);
        statusTxt.setText("Status: "+status);
        descTxt.setText("Description: "+desc);


        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(activity_detail.this,UploadActivity.class));
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //TextView uname = navigationView.findViewById(R.id.nav_title);
        //TextView uemail = navigationView.findViewById(R.id.nav_header);
        //final FirebaseUser user = firebaseAuth.getCurrentUser();
        //uname.setText("Hi " + user.getDisplayName());
        //uemail.setText( user.getEmail());


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(activity_detail.this, MainActivity.class));


        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(activity_detail.this, ProfileActivity.class));

        }

        else if (id == R.id.nav_about) {

            startActivity(new Intent(activity_detail.this, About.class));

        }

        else if (id == R.id.nav_share) {
            String text = "Check out this app Lost or Found ";
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
            sharingIntent.setType("text/plain");
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
            return true;

        }

        else if (id == R.id.nav_feedback) {
            Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
            feedbackEmail.setType("text/email");
            feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"josephkimani240@gmail.com"});
            startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));

        }


        else if (id == R.id.nav_logout) {

            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

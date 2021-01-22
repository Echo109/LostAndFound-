package my.application.mytestapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import my.application.mytestapplication.Adapter.RecyclerAdapter;
import my.application.mytestapplication.Model.AddItem;

/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
*/
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        RecyclerAdapter.OnItemClickListener {

    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    FirebaseAuth firebaseAuth;
    private FirebaseStorage mStorage;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private List<AddItem> myAddedItems;
    SearchView sv;


    private void openDetailActivity(String[] data){

        Intent intent = new Intent(this, activity_detail.class);
        intent.putExtra("IMAGE_KEY",data[0]);
        intent.putExtra("DATE_KEY",data[1]);
        intent.putExtra("NAME_KEY",data[2]);
        intent.putExtra("PNUM_KEY",data[3]);
        intent.putExtra("LOC_KEY",data[4]);
        intent.putExtra("STATUS_KEY",data[5]);
        intent.putExtra("DESC_KEY",data[6]);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        sv = findViewById(R.id.searchProjectTxt);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        myAddedItems = new ArrayList<>();
        mAdapter = new RecyclerAdapter (MainActivity.this, myAddedItems);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(MainActivity.this);

        firebaseAuth = FirebaseAuth.getInstance();

        //SEARCH
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        //INITIALIZE FIREBASE DB

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Item");


        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myAddedItems.clear();

                for (DataSnapshot ItemSnapshot : dataSnapshot.getChildren()) {
                    AddItem upload = ItemSnapshot.getValue(AddItem.class);
                    upload.setKey(ItemSnapshot.getKey());
                    myAddedItems.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);

            }


                @Override
                public void onCancelled (DatabaseError databaseError){
                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.INVISIBLE);

                }
        });

        //ADAPTER

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UploadActivity.class));
            }
        });

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {
                Toast.makeText(MainActivity.this,
                        "You are home!",
                        Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));

        }

        else if (id == R.id.nav_feedback) {
            Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
            feedbackEmail.setType("text/email");
            feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"josephkimani240@gmail.com"});
            startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));

        }


        else if (id == R.id.nav_share) {
            String text = "Check out this app Lost or Found ";
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
            sharingIntent.setType("text/plain");
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
            return true;

        }      else if (id == R.id.nav_logout) {

                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(int position) {
        AddItem clickedItem= myAddedItems.get(position);
        String[] ItemData={clickedItem.getImage(),clickedItem.getDate(),clickedItem.getItemname(),clickedItem.getPnum(),clickedItem.getLocation()
                ,clickedItem.getStatus(),clickedItem.getDescription()};
        openDetailActivity(ItemData);

    }

    @Override
    public void onShowItemClick(int position) {
        AddItem clickedItem= myAddedItems.get(position);
        String[] ItemData={clickedItem.getImage(),clickedItem.getDate(),clickedItem.getItemname(),clickedItem.getPnum(),clickedItem.getLocation()
                ,clickedItem.getStatus(),clickedItem.getDescription()};
        openDetailActivity(ItemData);

    }

    @Override
    public void onDeleteItemClick(int position) {
        AddItem selectedItem = myAddedItems.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImage());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }



}

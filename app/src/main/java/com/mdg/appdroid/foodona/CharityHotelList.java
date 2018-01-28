package com.mdg.appdroid.foodona;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CharityHotelList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DatabaseReference databaseReference;
    public List<CharityHotelListItem> list;
    public HotelListTextAdapter textAdapter;
    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_hotel_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        list = new ArrayList<>();


        textAdapter = new HotelListTextAdapter(CharityHotelList.this, list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();


        DatabaseReference upload = databaseReference.child("hotel").child("upload");
        upload.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    uid = ds.getKey();
                    DatabaseReference food = databaseReference.child("hotel").child("user").child(uid);
                    food.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("HotelName").getValue(String.class);
                            String address = dataSnapshot.child("Address").getValue(String.class);
                            String phone = dataSnapshot.child("Phone").getValue(String.class);
                            final CharityHotelListItem m = new CharityHotelListItem();
                            m.setHotel_name(name);
                            m.setHotel_address(address);
                            m.setHotel_phone(phone);
                            list.add(m);
                            textAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RecyclerView.LayoutManager recycler = new LinearLayoutManager(CharityHotelList.this);
        recyclerView.setLayoutManager(recycler);
        recyclerView.setAdapter(textAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.charity_hotel_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class HotelListTextAdapter extends RecyclerView.Adapter<com.mdg.appdroid.foodona.CharityHotelList.HotelListTextAdapter.HotelListTextHolder> {
        Context context;
        List<CharityHotelListItem> list;
        CharityHotelListItem mylist;

        public HotelListTextAdapter(Context context, List<CharityHotelListItem> list) {

            this.context = context;
            this.list = list;
        }

        @Override
        public com.mdg.appdroid.foodona.CharityHotelList.HotelListTextAdapter.HotelListTextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hotel, parent, false);
            com.mdg.appdroid.foodona.CharityHotelList.HotelListTextAdapter.HotelListTextHolder textHolder = new com.mdg.appdroid.foodona.CharityHotelList.HotelListTextAdapter.HotelListTextHolder(view);
            return textHolder;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final com.mdg.appdroid.foodona.CharityHotelList.HotelListTextAdapter.HotelListTextHolder holder, int position) {

            mylist = list.get(position);
            holder.hotel_name.setText(mylist.getHotel_name());
            holder.hotel_name.setTag(position);
            holder.hotel_address.setText(mylist.getHotel_address());
            holder.hotel_address.setTag(position);
            holder.hotel_phone.setText(mylist.getHotel_phone());
            holder.hotel_phone.setTag(position);
            holder.hotel_pic.setImageAlpha(mylist.getHotel_pic_url());
            holder.hotel_pic.setTag(position);
        }

        @Override
        public int getItemCount() {
            int arr = 0;
            try {
                if (list.size() == 0) {
                    arr = 0;
                } else {
                    arr = list.size();
                }
            } catch (Exception e) {

            }

            Log.d(TAG, "getItemCount: " + String.valueOf(arr));
            return arr;

        }


        public class HotelListTextHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView hotel_name, hotel_address, hotel_phone;
            public ImageView hotel_pic;

            public HotelListTextHolder(View itemView) {
                super(itemView);
                hotel_name = itemView.findViewById(R.id.hotel_name);
                hotel_name.setOnClickListener(this);
                hotel_address = itemView.findViewById(R.id.hotel_address);
                hotel_address.setOnClickListener(this);
                hotel_phone = itemView.findViewById(R.id.hotel_phone);
                hotel_phone.setOnClickListener(this);
                hotel_pic = itemView.findViewById(R.id.hotel_pic);
                hotel_pic.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == hotel_name.getId() || v.getId() == hotel_address.getId() || v.getId() == hotel_phone.getId() || v.getId() == hotel_pic.getId()) {
                    Intent intent = new Intent(v.getContext(), CharityHotelDescription.class);
                    intent.putExtra("passuid", uid);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
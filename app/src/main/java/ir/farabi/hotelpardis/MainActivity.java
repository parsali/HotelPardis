package ir.farabi.hotelpardis;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,EmptyRoom.OnFragmentInteractionListener,
                                                                contactUs.OnFragmentInteractionListener,aboutUs.OnFragmentInteractionListener,reserve.OnFragmentInteractionListener {
    databaseHandler DB;
    ActionBar ab;
    public static int navItemIndex = 0;
    private ListView listView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Handler mHandler;
    private static final String TAG_HOME = "home";
    private static final String TAG_reserve = "reserve";
    private static final String TAG_hesab = "hesab";
    private static final String TAG_aboutUs = "aboutUs";
    private static final String TAG_contactUs = "contactUs";
    public static String CURRENT_TAG = TAG_HOME;
    private String[] activityTitles;
    ActionBarDrawerToggle actionBarDrawerToggle;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        session = new SessionManager(getApplicationContext());



        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        DB= new databaseHandler(getApplicationContext());



    }
    private void loadNavHeader() {
        databaseHandler db = new databaseHandler(this);
        HashMap<String,String> userHash = session.getUserDetails();
        int userId = Integer.parseInt(userHash.get(SessionManager.USER_ID));
        User user = db.getUser(Integer.parseInt(userHash.get(SessionManager.USER_ID)));

        // name, website
        txtName.setText(user.getName());
        //txtWebsite.setText("www.androidhive.info");
        txtWebsite.setVisibility(View.GONE);

        // loading header background image
        Glide.with(this).load(R.drawable.untitled)


                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);



        // Loading profile image


        // showing dot next to notifications label

    }
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button

            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                EmptyRoom EmptyRoom = new EmptyRoom();
                return EmptyRoom;
            case 2:
                // movies fragment
                reserve reserve = new reserve();
                return reserve;
            case 3:
                Log.d("does it?","it does");
                aboutUs aboutUs = new aboutUs();
                return aboutUs;
            case 4:
                // notifications fragment
                contactUs contactUs = new contactUs();
                return contactUs;

//            case 4:
//                // settings fragment
//                SettingsFragment settingsFragment = new SettingsFragment();
//                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                if(menuItem.getGroupId()==R.id.menu_top) {
                    switch (menuItem.getItemId()) {
                        //Replacing the main content with ContentFragment Which is our Inbox View;

                        case R.id.nav_home:
                            navItemIndex = 0;
                            CURRENT_TAG = TAG_HOME;
                            break;
                        case R.id.virayesh:
                            navItemIndex = 1;
                            CURRENT_TAG = TAG_hesab;
                            break;
                        case R.id.reserve:
                            navItemIndex = 2;
                            CURRENT_TAG = TAG_reserve;
                            break;
                    }
                }
                    else{
                        switch (menuItem.getItemId()) {
                            case R.id.aboutUs:
                                Log.d("does it?","it does");

                                // launch new intent instead of loading fragment
                                navItemIndex = 3;
                                CURRENT_TAG = TAG_aboutUs;
                                break;
                            case R.id.contactUS:
                                // launch new intent instead of loading fragment
                                navItemIndex = 4;
                                CURRENT_TAG = TAG_contactUs;
                                break;
                            default:
                                navItemIndex = 0;
                        }
                    }


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
         actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
             @Override
             public boolean onOptionsItemSelected(MenuItem item) {
                 if (item != null && item.getItemId() == android.R.id.home) {
                     if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                         drawer.closeDrawer(Gravity.RIGHT);
                     }
                     else {
                         drawer.openDrawer(Gravity.RIGHT);
                     }
                 }
                 return false;
             }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        Log.d("comes", "yes");
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home

            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // show or hide the fab


    public void onClickLux(View v){
        Intent intent = new Intent(getApplicationContext(),moreInfo.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }
    public void onClickPrimium(View v){
        Intent intent = new Intent(getApplicationContext(),moreInfo.class);
        intent.putExtra("type",2);
        startActivity(intent);
    }
    public void onClickEconomy(View v){
        Intent intent = new Intent(getApplicationContext(),moreInfo.class);
        intent.putExtra("type",3);
        startActivity(intent);
    }
    public void cancel(View v){

    }
}

package com.example.cheonyujung.accidentofworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheonyujung.accidentofworld.fragment.ListActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by cheonyujung on 2016. 5. 19..
 */


public class Base extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    public DrawerLayout drawer;
    public Button drawerWorldMap_btn;
    public Button drawerWorldList_btn;
    public Button drawerBoard_btn;
    public Button drawerBookmark_btn;
    public RelativeLayout actionbar;

    public SearchView searchview;
    public Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = (DrawerLayout) findViewById(R.id.main_activity);
        drawerWorldMap_btn = (Button) findViewById(R.id.worldMapButton);
        drawerWorldList_btn = (Button) findViewById(R.id.CountryListButton);
        drawerBoard_btn = (Button) findViewById(R.id.BoardButton);
        drawerBookmark_btn = (Button)findViewById(R.id.BoardButton);

        BtnListener listener = new BtnListener();

        drawerWorldMap_btn.setOnClickListener(listener);
        drawerWorldList_btn.setOnClickListener(listener);
        drawerBoard_btn.setOnClickListener(listener);
        drawerBookmark_btn.setOnClickListener(listener);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setText("로그인 해주세요");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                        .enableAutoManage(Base.this /* FragmentActivity */, Base.this /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
                signIn(mGoogleApiClient);
            }
        });
    }

    private void signIn(GoogleApiClient mGoogleApiClient) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 1);
        System.out.println("!");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            System.out.println(resultCode);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        System.out.println("@");

        if (result.isSuccess()) {
            System.out.println("#");
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            loginBtn.setText( acct.getDisplayName());
            Toast.makeText(getApplicationContext(), "login 성공", Toast.LENGTH_SHORT);
            //updateUI(true);
        } else {
            System.out.println("$");
            Toast.makeText(getApplicationContext(), "login 실패", Toast.LENGTH_SHORT);
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.drawer_btn:
                drawer.openDrawer(Gravity.RIGHT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("onConnecttionFailed", "onConnectionFailed:" + connectionResult);

    }

    private class BtnListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            finish();
            drawer.closeDrawer(Gravity.RIGHT);
            switch(view.getId()) {
                case R.id.worldMapButton:
                    startActivity(new Intent(Base.this, MainActivity.class));
                    break;
                case R.id.CountryListButton:
                    startActivity(new Intent(Base.this, ListActivity.class));
                    break;
                case R.id.BoardButton:
                    startActivity(new Intent(Base.this, Board.class));
                    break;
                case R.id.BookmarkButton:
                    startActivity(new Intent(Base.this, BookMark.class));
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_layout,menu);
        return true;
    }
    public void setTitle(String title){
        TextView Title = (TextView)findViewById(R.id.toolbar_title);
        Title.setText(title);
    }
    public void hiddenItem(){
        MenuItem search = (MenuItem)findViewById(R.id.search_btn);
        Log.d(search+"","is null?");
//        search.setVisible(false);
    }
}
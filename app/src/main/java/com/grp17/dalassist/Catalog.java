package com.grp17.dalassist;



import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.grp17.dalassist.Assist.googlesignin;
import com.grp17.dalassist.MyAccount.LoginFrontActivity;
import com.grp17.dalassist.catalog.Banking;
import com.grp17.dalassist.catalog.Employment;
import com.grp17.dalassist.catalog.GettingStarted;
import com.grp17.dalassist.catalog.HealthCare;
import com.grp17.dalassist.catalog.Housing;
import com.grp17.dalassist.catalog.Telecommunication;
import com.grp17.dalassist.catalog.Transportation;


@SuppressWarnings("ALL")
public class Catalog extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridLayout mainGrid;

    private static ImageView imgView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //setSingleEvent (mainGrid);
        setToggleEvent(mainGrid);
    }

    private void setSingleEvent(GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount( ); i++) {
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {

                    Toast.makeText(Catalog.this, "Clicked" + finalI, Toast.LENGTH_SHORT).show( );
                }
//[24][25][26][27]

            });
        }
    }

    public void setToggleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount( ); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    if (cardView.getCardBackgroundColor( ).getDefaultColor( ) == -1) {
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                       // Toast.makeText(Catalog.this, "State : True", Toast.LENGTH_SHORT).show( );


                    } else {
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        //Toast.makeText(Catalog.this, "State : False", Toast.LENGTH_SHORT).show( );
                    }

                }

//[24][25][26][27]
            });
        }
        imgView = findViewById(R.id.imageview1);

        imgView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent second = new Intent(getApplicationContext( ), GettingStarted.class);
                startActivity(second);
            }
        });
//[28][29]
        imgView =findViewById(R.id.imageview2);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent link=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dal.ca/"));
                startActivity(link);
            }
        });
//[28][29]
        imgView = findViewById(R.id.imageview3);
        imgView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Catalog.this, Banking.class);
                startActivity(intent);
            }
        });
//[28][29]
        imgView = findViewById(R.id.ImageView4);
        imgView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Catalog.this, Employment.class);
                startActivity(intent);
            }
        });
//[28][29]
        imgView = findViewById(R.id.ImageView5);
        imgView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Catalog.this, HealthCare.class);
                startActivity(intent);
            }
        });
//[28][29]
        imgView = findViewById(R.id.ImageView6);
        imgView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Catalog.this, Housing.class);
                startActivity(intent);
            }
        });
//[28][29]
        imgView = findViewById(R.id.ImageView7);
        imgView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Catalog.this, Telecommunication.class);
                startActivity(intent);
            }
        });
//[28][29]
        imgView = findViewById(R.id.ImageView8);
        imgView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Catalog.this, Transportation.class);
                startActivity(intent);
            }
        });
//[28][29]
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener( ) {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId( )) {
                            case R.id.nav_assist:
                                Intent first = new Intent(getApplicationContext(), googlesignin.class);
                                startActivity(first);
                                break;
                            case R.id.nav_catalog:
                                Intent second = new Intent(getApplicationContext( ), Catalog.class);
                                startActivity(second);
                                break;
                            case R.id.nav_myaccount:
                                Intent third = new Intent(getApplicationContext(), LoginFrontActivity.class);
                                startActivity(third);
                                break;
                        }
                        return true;
                    }
                });
    }


//[18][17]

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}


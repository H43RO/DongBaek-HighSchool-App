
package dongbaek.hs.kr.dbapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Timer;

public class MainActivity extends Activity {

    private int contentView;
    private Button button;
    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private ImageView imageView;
    private ImageView[] imageViews;
    private ViewGroup viewPics;
    private ViewGroup viewPoints;
    private TextView recentNotice;


    private android.support.v7.widget.CardView card0;

    Toolbar toolbar;
    Timer timer;
    int page = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseInstanceId.getInstance().getToken();




        card0 = (CardView) findViewById(R.id.card_1);
        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Notice.class));
            }
        });


        card0 = (CardView) findViewById(R.id.card_3);
        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Meal.class));
            }
        });


        card0 = (CardView) findViewById(R.id.card_2);
        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Home.class));
            }
        });

        card0 = (CardView) findViewById(R.id.card_4);
        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "업데이트를 기다려주세요", Toast.LENGTH_LONG);
                toast.show();

            }
        });

        card0 = (CardView) findViewById(R.id.card_5);
        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Info.class));
            }
        });

        card0 = (CardView) findViewById(R.id.card_6);
        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Promotion.class));


            }
        });


        card0 = (CardView) findViewById(R.id.card_4);
        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Schedule.class));
            }
        });
    }



}

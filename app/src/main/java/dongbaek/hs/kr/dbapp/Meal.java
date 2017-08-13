package dongbaek.hs.kr.dbapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import toast.library.meal.MealLibrary;


public class Meal extends ActionBarActivity {

    Toolbar toolbar;

    private String CountryCode = "goe.go.kr";
    private String schulCode = "J100005488";
    private String KNDLUNCH = "2";
    private String KNDDINNER = "3";
    private ListView meallist;
    private String allergie = "";
    private ImageButton sharebtn;
    private Switch mealswitch;
    private ProgressBar kcalbar;
    private MealListAdapter MealAdapter = null;
    private AlertDialog.Builder builder;
    ArrayList<ListData> mListData;
    private String schulCrseScCode = "4";
    private String schulKndScCode = "04";
    private String[] meal = new String[7];
    private String[] kcal = new String[7];
    private String[] date = new String[7];
    private TextView title_day;
    private TextView kcalview;
    private ConnectivityManager cManager;
    private NetworkInfo mobile;
    private NetworkInfo wifi;
    private AlertDialog.Builder errordialog;
    private String[] itemlist;
    private String wh = "";
    private String sharelist = "";
    private int PARSEDAY;

    List<String> mealname = new ArrayList<String>();

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        internetCheck();

        sharebtn = (ImageButton) findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent msg = new Intent(Intent.ACTION_SEND);
                msg.addCategory(Intent.CATEGORY_DEFAULT);

                if (mealswitch.isChecked()) {
                    wh = "석식";
                } else {
                    wh = "중식";
                }

                msg.putExtra(Intent.EXTRA_SUBJECT, "[" + date[PARSEDAY] + " " + wh + " 급식정보]");

                for (int sm = 0; sm < meal.length; sm++) {


                }

                msg.putExtra(Intent.EXTRA_TEXT, "\n" + meal[PARSEDAY]);
                msg.putExtra(Intent.EXTRA_TITLE, "동백고등학교 급식");
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "급식 메뉴 공유"));
            }
        });

        mealswitch = (Switch) findViewById(R.id.mealswitch);
        mealswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetMeal().execute();
            }
        });

        kcalbar = (ProgressBar) findViewById(R.id.KcalProgress);
        kcalview = (TextView) findViewById(R.id.kcalviewer);

        meallist = (ListView) findViewById(R.id.meallist);
        meallist.setOnItemClickListener( //리스트 클릭시 실행될 로직 선언
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://m.terms.naver.com/search.nhn?query=" + URLEncoder.encode(mealname.get(position), "utf-8") + "&searchType=text&dicType=&subject=")));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });


        Calendar m = Calendar.getInstance();
        PARSEDAY = m.get(Calendar.DAY_OF_WEEK) - 1;


        new GetMeal().execute();

        final String[] dayofweek = getResources().getStringArray(R.array.dayofweek);

        title_day = (TextView) findViewById(R.id.title_day);

        title_day.setText(getdayofweek(PARSEDAY));
        title_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(Meal.this);
                builder.setItems(dayofweek,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PARSEDAY = which;
                                title_day.setText(getdayofweek(PARSEDAY));
                                new GetMeal().execute();
                            }
                        });
                builder.show();
            }
        });






       /* int year = m.get(Calendar.YEAR);
        int month = m.get(Calendar.MONTH) + 1;
        int day = m.get(Calendar.DATE);
        String nowDate = String.format("%04d", year) + "." + String.format("%02d", month) + "." + String.format("%02d", day);*/

        Log.d("LOGLOG", PARSEDAY + "");


    }

    private boolean isInternetCon() {
        cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return !mobile.isConnected() && !wifi.isConnected();
    }

    private void internetCheck() {
        if (isInternetCon()) {
            makeCustomToast(0xff2196F3, 0xffffffff, "데이터를 불러올 수 없습니다\n인터넷 연결을 확인해주세요.", Toast.LENGTH_LONG);
            finish();
        }
    }


    private void makeCustomToast(int backgroundColor, int textColor, String msg, int showLength) {
        int paddingValue = px2dp(7);

        TextView view = new TextView(this);
        view.setText(msg);
        view.setBackgroundColor(backgroundColor);
        view.setTextColor(textColor);
        view.setGravity(Gravity.CENTER);
        view.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);

        Toast toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(showLength);
        toast.show();
    }

    private int px2dp(int dpValue) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dpValue * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public String getdayofweek(int daynum) {

        String dayofweek = "";
        switch (daynum) {
            case 0:
                dayofweek = "일요일";
                break;
            case 1:
                dayofweek = "월요일";
                break;
            case 2:
                dayofweek = "화요일";
                break;
            case 3:
                dayofweek = "수요일";
                break;
            case 4:
                dayofweek = "목요일";
                break;
            case 5:
                dayofweek = "금요일";
                break;
            case 6:
                dayofweek = "토요일";
                break;
        }
        return dayofweek;
    }

    public void listing() {

        MealAdapter = new MealListAdapter(this);
        meallist.setAdapter(MealAdapter); //리스트에 어댑터를 먹여준다.
        MealAdapter.notifyDataSetChanged();
    }

    public class GetMeal extends AsyncTask<URL, Integer, Long> { //엔진리스트 로드

        protected Long doInBackground(URL... urls) {


            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog = ProgressDialog.show(Meal.this, "",
                            "급식을 로드중입니다", true);
                }
            }, 0);


            mListData = new ArrayList<>();

            if (mealswitch.isChecked() == false) {
                meal = MealLibrary.getMealNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, KNDLUNCH);
                kcal = MealLibrary.getKcalNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, KNDLUNCH);
                date = MealLibrary.getDateNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, KNDLUNCH);
            } else {
                meal = MealLibrary.getMealNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, KNDDINNER);
                kcal = MealLibrary.getKcalNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, KNDDINNER);
                date = MealLibrary.getDateNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, KNDDINNER);
            }

            if (meal[PARSEDAY] == null || meal[PARSEDAY].trim().length() == 0) meal[PARSEDAY] = "급식 정보가 없습니다.";

            Log.d("LOGLOG", meal[PARSEDAY]);

            String[] meallist = meal[PARSEDAY].split("\n");


            for (int i = 0; i < meallist.length; i++) {

                allergie = "알레르기정보 ";
                itemlist = meallist[i].split("-");

                if (itemlist.length > 1) {

//                    allergie = itemlist[1];

                    for (int m = 0; m < itemlist[1].length(); m++) {
                        if (itemlist[1].charAt(m) == '①') {
                            allergie = allergie + "|난류";
                        }
                        if (itemlist[1].charAt(m) == '②') {
                            allergie = allergie + "|우유";
                        }
                        if (itemlist[1].charAt(m) == '③') {
                            allergie = allergie + "|메밀";
                        }
                        if (itemlist[1].charAt(m) == '④') {
                            allergie = allergie + "|땅콩";
                        }
                        if (itemlist[1].charAt(m) == '⑤') {
                            allergie = allergie + "|대두";
                        }
                        if (itemlist[1].charAt(m) == '⑥') {
                            allergie = allergie + "|밀";
                        }
                        if (itemlist[1].charAt(m) == '⑦') {
                            allergie = allergie + "|고등어";
                        }
                        if (itemlist[1].charAt(m) == '⑧') {
                            allergie = allergie + "|게";
                        }
                        if (itemlist[1].charAt(m) == '⑨') {
                            allergie = allergie + "|새우";
                        }
                        if (itemlist[1].charAt(m) == '⑩') {
                            allergie = allergie + "|돼지고기";
                        }
                        if (itemlist[1].charAt(m) == '⑪') {
                            allergie = allergie + "|복숭아";
                        }
                        if (itemlist[1].charAt(m) == '⑫') {
                            allergie = allergie + "|토마토";
                        }
                        if (itemlist[1].charAt(m) == '⑬') {
                            allergie = allergie + "|황산아염";
                        }
                    }

                }

                if (meal[PARSEDAY].replaceAll("\\s", "").equals("")) {

                } else {
                    mListData.add(new ListData(itemlist[0], allergie));
                    mealname.add(itemlist[0]);

                }
            }

            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        listing();
                        if(kcal[PARSEDAY] != null) {
                            float i = Float.valueOf(kcal[PARSEDAY]);
                            kcalbar.setProgress((int) i);
                            kcalview.setText(kcal[PARSEDAY] + "/" + kcalbar.getMax() + "kcal");
                        }
                        mProgressDialog.dismiss();
                    } catch (Exception e) {
                        Log.d("mealError", e+"");
                        mProgressDialog.dismiss();
                        errordialog = new AlertDialog.Builder(Meal.this);
                        errordialog.setMessage("급식정보를 불러올 수 없습니다.\n*재량휴업일,공휴일에 발생");
                        errordialog.show();
                        kcalbar.setProgress(0);
                        kcalview.setText("ERROR/" + kcalbar.getMax() + "kcal");
                    }

                }
            }, 0);
            return 0L;


        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }

    // <리스트 적용부분
    class ViewHolder {

        public TextView mMeal;
        public TextView mMealtype;
    }


    public class MealListAdapter extends BaseAdapter {
        private Context mContext = null;

        public MealListAdapter(Context mContext) {
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.mealstyle, null);

                holder.mMeal = (TextView) convertView.findViewById(R.id.meal);
                holder.mMealtype = (TextView) convertView.findViewById(R.id.meal_type);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);
            holder.mMeal.setText(mData.mMeal);
            holder.mMealtype.setText(mData.mMealtype);

            return convertView;

        }

    }

    public class ListData { // 데이터를 받는 클래스

        public String mMeal;
        public String mMealtype;


        public ListData() {


        }

        public ListData(String mMeal, String mMealtype) { //데이터를 받는 클래스 메서드
            this.mMeal = mMeal;
            this.mMealtype = mMealtype;

        }

    }

}
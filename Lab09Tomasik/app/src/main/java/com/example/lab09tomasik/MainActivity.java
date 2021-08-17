package com.example.lab09tomasik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText ed,edh,edm,eds;
    ProgressBar bar;
    TextView tv,tvcolor;
    Timer timer;
    Button btn,btnl,btnr,btnll,btnrr,cha;
    ListView lst;
    ArrayList<Theme> listatheme=new ArrayList<>();
    ConstraintLayout cl;
    ImageView iv;
    int hasaudio=0;
    StringBuilder sb = new StringBuilder();

    int liczymsko = 5;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String LICZ = "0";
    private String text;
    private int change = 0;
    int trzymcolor=0;
    int trzymczas=0;
    int trzymbackg=0;
    String trzymtytul="";
    MediaPlayer mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        btnll = findViewById(R.id.button9);
        btnrr = findViewById(R.id.button10);
        cha = findViewById(R.id.button5);
        iv = findViewById(R.id.imageButton);
        cl = findViewById(R.id.lay);
        btnl = findViewById(R.id.button7);
        btnr = findViewById(R.id.button8);
        edh = findViewById(R.id.editTextTextPersonName2);
        edm = findViewById(R.id.editTextTextPersonName3);
        eds = findViewById(R.id.editTextTextPersonName4);
        btn = findViewById(R.id.button6);
        tvcolor = findViewById(R.id.textView2);
        ed = findViewById(R.id.editTextTextPersonName);
        lst = findViewById(R.id.lv);
        bar = findViewById(R.id.progress_bar);

        loadData();
        odkoduj(text);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Wybrano" + listatheme.get(i).tittle, Toast.LENGTH_SHORT).show();
                liczymsko=listatheme.get(i).czas;
                if(listatheme.get(i).fcolor==0)
                {
                    tv.setTextColor(Color.rgb(255,255,255));
                }
                else if(listatheme.get(i).fcolor==1)
                {
                    tv.setTextColor(Color.rgb(3,215,61));
                }
                else if(listatheme.get(i).fcolor==2)
                {
                    tv.setTextColor(Color.rgb(161, 63, 251));
                }
                else if(listatheme.get(i).fcolor==3)
                {
                    tv.setTextColor(Color.rgb(80, 89, 251));
                }

                cl.setBackgroundResource(listatheme.get(i).background);


            }
        });


    }
    public void pokazliste(View view)
    {
        lst.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return listatheme.size();
            }

            @Override
            public Object getItem(int i) {
                return listatheme.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup viewGroup) {
                if(convertView == null) {

                    convertView = getLayoutInflater().inflate(R.layout.pp, null);



                    TextView tt = convertView.findViewById(R.id.textView);
                    tt.setText(listatheme.get(i).tittle);


                }
                return convertView;
            }
        });

    }

    public void btStartClick(View view) {
        timer = new Timer(liczymsko*1000, 100);
        timer.start();
        bar.setMax(liczymsko*10);
        bar.incrementProgressBy(-liczymsko*10);


    }

    public void btStopClick(View view) {
        timer.cancel();
    }



    public void save(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT,sb.toString());
        editor.putInt(LICZ,change);
        editor.apply();

    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        change = sharedPreferences.getInt(LICZ,change);
    }
    int click = 0;

    public void customizetimer(View view) {
        if (click==0)
        {

            click++;
            ed.setVisibility(View.VISIBLE);
            btn.setText("NEXT");
        }
        else if (click==1)
        {

            trzymtytul=ed.getText().toString();
            ed.setText("");
            click++;
            ed.setVisibility(View.GONE);
            edh.setVisibility(View.VISIBLE);
            edm.setVisibility(View.VISIBLE);
            eds.setVisibility(View.VISIBLE);
        }
        else if(click==2)
        {
            click++;
            int k=0;
            if(edh.getText().length()>0)
            {
                k+=Integer.parseInt(edh.getText().toString())*60*60;
            }
            if(edm.getText().length()>0)
            {
                k+=Integer.parseInt(edm.getText().toString())*60;
            }
            if(eds.getText().length()>0)
            {
                k+=Integer.parseInt(eds.getText().toString());
            }
            trzymczas=k;
            edh.setVisibility(View.GONE);
            edm.setVisibility(View.GONE);
            eds.setVisibility(View.GONE);
            edh.setText("");
            edm.setText("");
            eds.setText("");
            btnl.setVisibility(View.VISIBLE);btnr.setVisibility(View.VISIBLE);
            tvcolor.setVisibility(View.VISIBLE);
            colo = 0;
            zm();

        }
        else if(click==3)
        {
            trzymcolor=colo;
            click++;
            btnl.setVisibility(View.GONE);btnr.setVisibility(View.GONE);
            tvcolor.setVisibility(View.GONE);

            btn.setText("SAVE");

            iv.setVisibility(View.VISIBLE);
            btnll.setVisibility(View.VISIBLE);
            btnrr.setVisibility(View.VISIBLE);




        }
        else if(click==4)
        {
            click=0;
            Theme nowe = new Theme(trzymcolor,trzymczas,trzymbackg,trzymtytul);
            listatheme.add(nowe);
            iv.setVisibility(View.GONE);
            btnll.setVisibility(View.GONE);
            btnrr.setVisibility(View.GONE);
            backg=0;
            zb();
            Toast.makeText(this, "THEME SAVED !!!", Toast.LENGTH_SHORT).show();
            btn.setText("Customize timer");
            cha.setVisibility(View.VISIBLE);
            zakoduj(nowe);

        }
    }
    int colo = 0;
    int backg = 0;
    public void zm(){
        if(colo==0)
        {
            tvcolor.setText("WHITE");
            tvcolor.setTextColor(Color.rgb(255,255,255));
        }
        else if(colo==1)
        {
            tvcolor.setText("GREEN");
            tvcolor.setTextColor(Color.rgb(3,215,61));
        }
        else if(colo==2)
        {
            tvcolor.setText("PURPLE");
            tvcolor.setTextColor(Color.rgb(161, 63, 251));
        }
        else if(colo==3)
        {
            tvcolor.setText("BLUE");
            tvcolor.setTextColor(Color.rgb(80, 89, 251));
        }
    }
    public void zb(){
        if(backg==0)
        {
            trzymbackg=R.drawable.jj;
            iv.setImageResource(trzymbackg);

        }
        else if(backg==1)
        {
            trzymbackg=R.drawable.aa;
            iv.setImageResource(trzymbackg);
        }
        else if(backg==2)
        {
            trzymbackg=R.drawable.nn;
            iv.setImageResource(trzymbackg);
        }
        else if(backg==3)
        {
            trzymbackg=R.drawable.ff;
            iv.setImageResource(trzymbackg);
        }
    }
    public void r(View view)
    {
        if(colo==3)
            colo=0;
        else
            colo++;
        zm();

    }

    public void l(View view)
    {
        if(colo==0)
            colo=3;
        else
        colo--;
        zm();
    }

    public void ri(View view) {
        if(backg==3)
            backg=0;
        else
            backg++;
        zb();
    }

    public void le(View view) {
        if(backg==0)
            backg=3;
        else
            backg--;
        zb();
    }

    public void ustawtune(View view)
    {
        Intent pp = new Intent(Intent.ACTION_GET_CONTENT);
        pp.setType("audio/*");
        startActivityForResult(pp,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK)
        {
            switch (requestCode) {
                case 2:

                    MediaPlayer mp = MediaPlayer.create(this, data.getData());
                    mm = mp;
                    hasaudio=1;

            }



        }
    }

    class Timer extends CountDownTimer {

        final long sek = 1000;
        final long min = sek * 60;
        final long hour = min * 60;
        final long day = hour * 24;

        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long mls = millisUntilFinished;
            long h = mls / hour;
            mls = mls % hour;
            long m = mls / min;
            mls = mls % min;
            long s = mls / sek;
            mls = mls % sek;
            long ss = mls / 100;
            tv.setText(String.format("%02d:%02d:%02d.%1d", h, m, s, ss));
            bar.incrementProgressBy(1);


        }


        @Override
        public void onFinish() {
            tv.setText("Koniec");
            bar.incrementProgressBy(bar.getMax());
            /*int resID=getResources().getIdentifier("raw", "raw", getPackageName());
            mm=MediaPlayer.create(MainActivity.this,resID); NIE DZIALA NIE WIEM CZEMU ;c*/
            if(hasaudio==1)
                mm.start();




        }

    }
    public void zakoduj(Theme kod){

        sb.append("X"+kod.tittle);
        sb.append("X"+kod.czas);
        sb.append("X"+kod.fcolor);
        sb.append("X"+kod.background+"XY");

    }
    public void odkoduj(String txt)
    {   int xx = 0;
        String tytulowo = "";
        String czasowo = "";
        String kolorowo = "";
        String backgroundowo = "";
        for(int i=0;i<txt.length();i++)
        {
            if(Character.compare(txt.charAt(i),'X')==0)
                xx++;
            else if(xx==1)
                tytulowo+=txt.charAt(i);
            else if(xx==2)
                czasowo+=txt.charAt(i);
            else if(xx==3)
                kolorowo+=txt.charAt(i);
            else if(xx==4)
                backgroundowo+=txt.charAt(i);
            else if(Character.compare(txt.charAt(i),'Y')==0)
            {
                xx=0;
                Theme nowe = new Theme(Integer.parseInt(kolorowo),Integer.parseInt(czasowo),Integer.parseInt(backgroundowo),tytulowo);
                listatheme.add(nowe);
                Toast.makeText(this, "DODANO", Toast.LENGTH_SHORT).show();
            }




        }


    }

}
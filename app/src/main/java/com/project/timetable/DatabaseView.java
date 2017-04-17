package com.project.timetable;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by hp on 10/12/2016.
 */
public class DatabaseView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.dataview);

        TextView tv8=(TextView)findViewById(R.id.tvc8);
        TextView tv9=(TextView)findViewById(R.id.tvc9);
        TextView tv10=(TextView)findViewById(R.id.tvc10);
        TextView tv11=(TextView)findViewById(R.id.tvc11);
        TextView tv12=(TextView)findViewById(R.id.tvc12);
        TextView tv2=(TextView)findViewById(R.id.tvc2);
        TextView tv3=(TextView)findViewById(R.id.tvc3);
        TextView tv4=(TextView)findViewById(R.id.tvc4);

        TimeTableData info=new TimeTableData(this);
        Bundle bun=getIntent().getExtras();
        String day=bun.getString("key");
        info.open(day);
        String data=info.getData(day);
        info.close();
        setTitle(day);

        String ex="";
        int i=0,c=0;
        while(c<8){
            if(data.charAt(i)=='\n'){
                switch (c){
                    case 0:
                        tv8.setText(ex);
                        break;
                    case 1:
                        tv9.setText(ex);
                        break;
                    case 2:
                        tv10.setText(ex);
                        break;
                    case 3:
                        tv11.setText(ex);
                        break;
                    case 4:
                        tv12.setText(ex);
                        break;
                    case 5:
                        tv2.setText(ex);
                        break;
                    case 6:
                        tv3.setText(ex);
                        break;
                    case 7:
                        tv4.setText(ex);
                }
                ex="";
                c++;
                i++;
            }
            else{
                ex=ex+data.charAt(i);
                i++;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setTitle("Timetable");
    }
}

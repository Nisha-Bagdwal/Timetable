package com.project.timetable;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    TabHost th;
    InputStream dbInput=null;
    Button bSave;
    EditText c8,c9,c10,c11,c12,c2,c3,c4,r8,r9,r10,r11,r12,r2,r3,r4;
    Drawable draw;
    File data,sdc;
    ImageView ll;
    Bitmap bitmap;
    Spinner etDay;
    String day;
    int xxx,count,nnt;
    String[] paths={"---Select---","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private static final int SELECT_IMAGE = 1;
    private static final int SELECT_FILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setVariables();
        bSave.setOnClickListener(this);

        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,paths);
        etDay=(Spinner)findViewById(R.id.etday);
        etDay.setAdapter(adapter1);
        etDay.setOnItemSelectedListener(this);

        th.setup();
        TabHost.TabSpec spec=th.newTabSpec("tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Home");
        th.addTab(spec);

        spec=th.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Edit TimeTable");
        th.addTab(spec);

        getThumbnail("myBackground.jpg");

        sdc = new File(Environment.getExternalStorageDirectory(),"TimeTableApp");
        data = Environment.getDataDirectory();
        if(!sdc.exists()){
            sdc.mkdirs();
        }
    }

    private void getThumbnail(String s) {
        Bitmap thumbnail=null;
        try{
            File filePath = this.getFileStreamPath(s);
            if(filePath.exists()) {
                FileInputStream fis = new FileInputStream(filePath);
                thumbnail = BitmapFactory.decodeStream(fis);
                ll.setImageBitmap(thumbnail);
            }else{
                draw=getResources().getDrawable(R.drawable.back1);
                ll.setImageDrawable(draw);
            }
        }catch(java.io.FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    private void setVariables() {
        th=(TabHost)findViewById(R.id.tabHost);
        ll=(ImageView)findViewById(R.id.imageBackground);

        c8=(EditText)findViewById(R.id.etCourse8_9);
        c9=(EditText)findViewById(R.id.etCourse9_10);
        c10=(EditText)findViewById(R.id.etCourse10_11);
        c11=(EditText)findViewById(R.id.etCourse11_12);
        c12=(EditText)findViewById(R.id.etCourse12_01);
        c2=(EditText)findViewById(R.id.etCourse2_3);
        c3=(EditText)findViewById(R.id.etCourse3_4);
        c4=(EditText)findViewById(R.id.etCourse4_5);

        r8=(EditText)findViewById(R.id.etClassroom8_9);
        r9=(EditText)findViewById(R.id.etClassroom9_10);
        r10=(EditText)findViewById(R.id.etClassroom10_11);
        r11=(EditText)findViewById(R.id.etClassroom11_12);
        r12=(EditText)findViewById(R.id.etClassroom12_01);
        r2=(EditText)findViewById(R.id.etClassroom2_3);
        r3=(EditText)findViewById(R.id.etClassroom3_4);
        r4=(EditText)findViewById(R.id.etClassroom4_5);

        bSave=(Button)findViewById(R.id.bSave);
        bSave.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View arg0, MotionEvent m) {
                // TODO Auto-generated method stub
                if(m.getAction()==MotionEvent.ACTION_DOWN){
                    bSave.setBackgroundResource(R.color.colorPrimaryDark);
                    bSave.invalidate();
                }
                if(m.getAction()== MotionEvent.ACTION_UP){
                    bSave.setBackgroundResource(R.color.colorPrimary);
                    bSave.invalidate();
                }
                return false;
            }

        });
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.itemAbout:
                Intent iab=new Intent(MainActivity.this,AboutUs.class);
                startActivity(iab);
                break;
            case R.id.mShare:
                count=1;
                xxx=0;
                exportDatabase("TimeTable");
                if(xxx==0){
                    File dir=new File(Environment.getExternalStorageDirectory(),"TimeTableApp");
                    File fileWithinDir=new File(dir,"TimeTable");
                    if(fileWithinDir.exists()){
                        Intent in=new Intent(Intent.ACTION_SEND);
                        in.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileWithinDir));
                        in.setType("*/*");
                        startActivity(Intent.createChooser(in,"Share Using.."));
                    }
                }
                break;
            case R.id.mImport:
                Intent chooser1=new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath().toString());
                chooser1.addCategory(Intent.CATEGORY_OPENABLE);
                chooser1.setDataAndType(uri,"*/*");
                startActivityForResult(chooser1,SELECT_FILE);
                break;
            case R.id.itemExit:
                finish();
                break;
            case R.id.itemChange:
                Intent chooser2=new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri2=Uri.parse(Environment.getExternalStorageDirectory().getPath().toString());
                chooser2.addCategory(Intent.CATEGORY_OPENABLE);
                chooser2.setDataAndType(uri2,"image/*");
                startActivityForResult(Intent.createChooser(chooser2,"Select Image From.."),SELECT_IMAGE);
                break;
            case R.id.itemBackup:
                count=0;
                nnt=0;
                exportDatabase("TimeTable");
                if(nnt==0){
                    Toast.makeText(MainActivity.this,"Backup created in TimeTableApp folder",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.itemDelete:
                TimeTableData ttd=new TimeTableData(this);
                ttd.open("Monday");
                ttd.deleteTable("Monday");
                ttd.close();
                ttd.open("Tuesday");
                ttd.deleteTable("Tuesday");
                ttd.close();
                ttd.open("Wednesday");
                ttd.deleteTable("Wednesday");
                ttd.close();
                ttd.open("Thursday");
                ttd.deleteTable("Thursday");
                ttd.close();
                ttd.open("Friday");
                ttd.deleteTable("Friday");
                ttd.close();
                ttd.open("Saturday");
                ttd.deleteTable("Saturday");
                ttd.close();

                Toast.makeText(MainActivity.this,"TimeTable Deleted",Toast.LENGTH_LONG).show();
                break;
            case R.id.itemShare:
                count=1;
                xxx=0;
                exportDatabase("TimeTable");
                if(xxx==0){
                    File dir=new File(Environment.getExternalStorageDirectory(),"TimeTableApp");
                    File fileWithinDir=new File(dir,"TimeTable");
                    if(fileWithinDir.exists()){
                        Intent in=new Intent(Intent.ACTION_SEND);
                        in.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileWithinDir));
                        in.setType("*/*");
                        startActivity(Intent.createChooser(in,"Share Using.."));
                    }
                }
                break;
            case R.id.itemImport:
                Intent chooser3=new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri3=Uri.parse(Environment.getExternalStorageDirectory().getPath().toString());
                chooser3.addCategory(Intent.CATEGORY_OPENABLE);
                chooser3.setDataAndType(uri3,"*/*");
                startActivityForResult(Intent.createChooser(chooser3,"Select file from.."),SELECT_FILE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void exportDatabase(String dbName) {
        try{
            if(sdc.canWrite()){
                String currentDBpath="//data//"+getPackageName()+"//databases//"+dbName;
                String backupDBpath="TimeTable";

                File currentDB=new File(data,currentDBpath);
                File backupDB=new File(sdc,backupDBpath);
                if(currentDB.exists()){
                    FileChannel src=new FileInputStream(currentDB).getChannel();
                    FileChannel dst=new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src,0,src.size());
                    src.close();
                    dst.close();
                }else{
                    if(count==1) {
                        xxx = 2;
                        Dialog d = new Dialog(this);
                        d.setTitle("Nothing to Share!!");
                        TextView tv = new TextView(this);
                        tv.setText("First create a Timetable");
                        tv.setPadding(10, 10, 10, 10);
                        d.setContentView(tv);
                        d.show();
                    }else if(count==0){
                        nnt=1;
                        Dialog d = new Dialog(this);
                        d.setTitle("Nothing to Backup!!");
                        TextView tv = new TextView(this);
                        tv.setText("First create a Timetable");
                        tv.setPadding(10, 10, 10, 10);
                        d.setContentView(tv);
                        d.show();
                    }
                }
            }else{
                nnt=1;
                if(count==1)
                    Toast.makeText(MainActivity.this, "Cannot Share", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "SD card unavailable", Toast.LENGTH_LONG).show();
            }
        }catch (java.io.FileNotFoundException ex){
            ex.printStackTrace();
        }catch(java.io.IOException ex){
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String br;
        Bundle bun=new Bundle();
        Intent inn=new Intent(MainActivity.this,DatabaseView.class);
        switch(id){
            case R.id.nav_mon:
                br="Monday";
                bun.putString("key",br);
                inn.putExtras(bun);
                startActivity(inn);
                break;
            case R.id.nav_tue:
                br="Tuesday";
                bun.putString("key",br);
                inn.putExtras(bun);
                startActivity(inn);
                break;
            case R.id.nav_wed:
                br="Wednesday";
                bun.putString("key",br);
                inn.putExtras(bun);
                startActivity(inn);
                break;
            case R.id.nav_thu:
                br="Thursday";
                bun.putString("key",br);
                inn.putExtras(bun);
                startActivity(inn);
                break;
            case R.id.nav_fri:
                br="Friday";
                bun.putString("key",br);
                inn.putExtras(bun);
                startActivity(inn);
                break;
            case R.id.nav_sat:
                br="Saturday";
                bun.putString("key",br);
                inn.putExtras(bun);
                startActivity(inn);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int pos=etDay.getSelectedItemPosition();
        switch (pos){
            case 1:
                day="Monday";
                break;
            case 2:
                day="Tuesday";
                break;
            case 3:
                day="Wednesday";
                break;
            case 4:
                day="Thursday";
                break;
            case 5:
                day="Friday";
                break;
            case 6:
                day="Saturday";
                break;
            default:
                day="xyz";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        String br;
        Bundle bun=new Bundle();
        Intent inn=new Intent(MainActivity.this,DatabaseView.class);
        switch (v.getId()) {
            case R.id.fab:
                Calendar cl=Calendar.getInstance();
                int dayx=cl.get(Calendar.DAY_OF_WEEK);
                //Toast.makeText(MainActivity.this,"Not working "+dayx,Toast.LENGTH_LONG).show();

                switch (dayx){
                    case Calendar.SUNDAY:
                        Dialog d=new Dialog(this);
                        d.setTitle("Yippee!!");
                        d.setContentView(R.layout.sunday);
                        d.show();
                        break;
                    case Calendar.MONDAY:
                        br="Monday";
                        bun.putString("key",br);
                        inn.putExtras(bun);
                        startActivity(inn);
                        break;
                    case Calendar.TUESDAY:
                        br="Tuesday";
                        bun.putString("key",br);
                        inn.putExtras(bun);
                        startActivity(inn);
                        break;
                    case Calendar.WEDNESDAY:
                        br="Wednesday";
                        bun.putString("key",br);
                        inn.putExtras(bun);
                        startActivity(inn);
                        break;
                    case Calendar.THURSDAY:
                        br="Thursday";
                        bun.putString("key",br);
                        inn.putExtras(bun);
                        startActivity(inn);
                        break;
                    case Calendar.FRIDAY:
                        br="Friday";
                        bun.putString("key",br);
                        inn.putExtras(bun);
                        startActivity(inn);
                        break;
                    case Calendar.SATURDAY:
                        br="Saturday";
                        bun.putString("key",br);
                        inn.putExtras(bun);
                        startActivity(inn);
                        break;
                }
                break;
            case R.id.bSave:
                TimeTableData ttd = new TimeTableData(this);
                if (day.equals("xyz")) {
                    Dialog d = new Dialog(this);
                    d.setTitle("Yikes!!!");
                    TextView tv = new TextView(this);
                    tv.setPadding(60, 40, 60, 40);
                    tv.setText("Select a day to edit!!");
                    tv.setTextSize(20);
                    d.setContentView(tv);
                    d.show();
                } else {
                    ttd.open(day);
                    String ec8 = c8.getText().toString();
                    String er8 = r8.getText().toString();
                    if (!(ec8.isEmpty()) || !(er8.isEmpty()))
                        ttd.updateEntry("08:00-09:00", ec8, er8, day);

                    String ec9 = c9.getText().toString();
                    String er9 = r9.getText().toString();
                    if (!(ec9.isEmpty()) || !(er9.isEmpty()))
                        ttd.updateEntry("09:00-10:00", ec9, er9, day);
                    ttd.close();
                    ttd.open(day);
                    String ec10 = c10.getText().toString();
                    String er10 = r10.getText().toString();
                    if (!(ec10.isEmpty()) || !(er10.isEmpty()))
                        ttd.updateEntry("10:00-11:00", ec10, er10, day);

                    String ec11 = c11.getText().toString();
                    String er11 = r11.getText().toString();
                    if (!(ec11.isEmpty()) || !(er11.isEmpty()))
                        ttd.updateEntry("11:00-12:00", ec11, er11, day);

                    String ec12 = c12.getText().toString();
                    String er12 = r12.getText().toString();
                    if (!(ec12.isEmpty()) || !(er12.isEmpty()))
                        ttd.updateEntry("12:00-01:00", ec12, er12, day);

                    String ec2 = c2.getText().toString();
                    String er2 = r2.getText().toString();
                    if (!(ec2.isEmpty()) || !(er2.isEmpty()))
                        ttd.updateEntry("02:00-03:00", ec2, er2, day);

                    String ec3 = c3.getText().toString();
                    String er3 = r3.getText().toString();
                    if (!(ec3.isEmpty()) || !(er3.isEmpty()))
                        ttd.updateEntry("03:00-04:00", ec3, er3, day);

                    String ec4 = c4.getText().toString();
                    String er4 = r4.getText().toString();
                    if (!(ec4.isEmpty()) || !(er4.isEmpty()))
                        ttd.updateEntry("04:00-05:00", ec4, er4, day);
                    ttd.close();
                    Toast.makeText(this, "Timetable Modified", Toast.LENGTH_SHORT).show();
                    View view = this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                if (!day.equals("xyz")) {
                    c8.setText(null);
                    r8.setText(null);
                    c9.setText(null);
                    r9.setText(null);
                    c10.setText(null);
                    r10.setText(null);
                    c11.setText(null);
                    r11.setText(null);
                    c12.setText(null);
                    r12.setText(null);
                    c2.setText(null);
                    r2.setText(null);
                    c3.setText(null);
                    r3.setText(null);
                    c4.setText(null);
                    r4.setText(null);
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode){
                case SELECT_IMAGE:
                    if(resultCode==RESULT_OK){
                        try{
                            if(bitmap!=null){
                                bitmap.recycle();
                            }
                            InputStream stream=getContentResolver().openInputStream(data.getData());
                            bitmap=BitmapFactory.decodeStream(stream);
                            stream.close();
                            saveImage(bitmap);
                            getThumbnail("myBackground.jpg");
                            Toast.makeText(MainActivity.this,"Background Changed",Toast.LENGTH_LONG).show();
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }else
                        Toast.makeText(this, "Unable to load image",Toast.LENGTH_LONG).show();
                    break;
                case SELECT_FILE:
                    if(resultCode==RESULT_OK){
                        try {
                            dbInput=getContentResolver().openInputStream(data.getData());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        checkDB();
                    }else
                        Toast.makeText(this, "Unable to import Timetable",Toast.LENGTH_LONG).show();
            }
        }

    }

    private void checkDB() {
        try {
            String packageName = this.getPackageName();
            String destPath = "/data/data/" + packageName + "/databases/";
            String fullPath = "/data/data/" + packageName + "/databases/" + "TimeTable";

            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
            }
            this.copyDB(fullPath);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void copyDB(String fullPath) {
        try{
            FileChannel src=((FileInputStream) dbInput).getChannel();
            FileChannel dst=new FileOutputStream(fullPath).getChannel();
            dst.transferFrom(src,0,src.size());
            src.close();
            dst.close();
            Toast.makeText(MainActivity.this, "Timetable Imported", Toast.LENGTH_SHORT).show();
        }catch (IOException ex){
            Toast.makeText(MainActivity.this, "Timetable cannot be imported", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    private void saveImage(Bitmap bitmap) {
        try{
            FileOutputStream fos=this.openFileOutput("myBackground.jpg", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

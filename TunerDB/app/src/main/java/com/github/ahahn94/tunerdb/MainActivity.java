package com.github.ahahn94.tunerdb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class MainActivity extends AppCompatActivity {

    public static final int RED=0;
    public static final int GREEN=1;
    public static final int BLUE=2;
    public static final int WHITE=3;
    public static final int YELLOW=4;
    public static final int MAGENTA=5;
    public static final int E=0;
    public static final int A=1;
    public static final int D=2;
    public static final int G=3;
    public static final int B=4;
    public static final int e=5;


    FrameLayout background;
    Button buttonSave, buttonSearch, buttonViewDB, buttonResetDB;
    TextView textviewDB1;
    EditText editTextEntry;
    RadioButton rbRed, rbGreen, rbBlue, rbWhite, rbYellow, rbMagenta;
    RadioButton rbE, rbA, rbD, rbG, rbB, rbe;




    int bank=-1;
    int cell=-1;
    String[][] array=new String[6][6];

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        File file = new File(getFilesDir() + "/db.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            resetDB();
        }

        readDB(array);

        buttonSave=(Button) findViewById(R.id.buttonSave);
        buttonSearch=(Button) findViewById(R.id.buttonSearch);
        buttonViewDB=(Button) findViewById(R.id.buttonViewDB);
        buttonResetDB=(Button) findViewById(R.id.buttonResetDB);
        textviewDB1=(TextView) findViewById(R.id.textviewDB1);
        editTextEntry=(EditText) findViewById(R.id.edittextEntry);

        rbRed=(RadioButton) findViewById(R.id.radioButtonRed);
        rbGreen=(RadioButton) findViewById(R.id.radioButtonGreen);
        rbBlue=(RadioButton) findViewById(R.id.radioButtonBlue);
        rbWhite=(RadioButton) findViewById(R.id.radioButtonWhite);
        rbYellow=(RadioButton) findViewById(R.id.radioButtonYellow);
        rbMagenta=(RadioButton) findViewById(R.id.radioButtonMagenta);

        rbE=(RadioButton) findViewById(R.id.radioButtonE);
        rbA=(RadioButton) findViewById(R.id.radioButtonA);
        rbD=(RadioButton) findViewById(R.id.radioButtonD);
        rbG=(RadioButton) findViewById(R.id.radioButtonG);
        rbB=(RadioButton) findViewById(R.id.radioButtonB);
        rbe=(RadioButton) findViewById(R.id.radioButtone);

        rbRed.setOnClickListener(rbRedListener);
        rbGreen.setOnClickListener(rbGreenListener);
        rbBlue.setOnClickListener(rbBlueListener);
        rbWhite.setOnClickListener(rbWhiteListener);
        rbYellow.setOnClickListener(rbYellowListener);
        rbMagenta.setOnClickListener(rbMagentaListener);

        rbE.setOnClickListener(rbEListener);
        rbA.setOnClickListener(rbAListener);
        rbD.setOnClickListener(rbDListener);
        rbG.setOnClickListener(rbGListener);
        rbB.setOnClickListener(rbBListener);
        rbe.setOnClickListener(rbeListener);

        buttonSave.setOnClickListener(buttonSaveListener);
        buttonSearch.setOnClickListener(buttonSearchListener);
        buttonViewDB.setOnClickListener(buttonViewDBListener);

    }

    View.OnClickListener buttonResetDBListener = new View.OnClickListener(){
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            resetDB();
        }
    };

    View.OnClickListener buttonSaveListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            if(cell>-1&&bank>-1){
                array[bank][cell]=editTextEntry.getText().toString();
            }
            saveArray(array);
        }
    };

    View.OnClickListener buttonSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            if(cell>-1&&bank>-1){
                editTextEntry.setText(array[bank][cell], TextView.BufferType.EDITABLE);
            }
        }
    };

    View.OnClickListener buttonViewDBListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            String out1="", out2="", color, entry;
            for (int i=0; i<6; i++){
                switch(i){
                    case 0:
                        color="Red";
                        break;
                    case 1:
                        color="Green";
                        break;
                    case 2:
                        color="Blue";
                        break;
                    case 3:
                        color="White";
                        break;
                    case 4:
                        color="Yellow";
                        break;
                    case 5:
                        color="Magenta";
                        break;
                    default:
                        color="error";
                }
                if(i<3){
                    out1=out1+"\n"+color+"\n";}
                else{out1=out1+"\n"+color+"\n";}
                for(int j=0; j<6; j++){
                    switch (j){
                        case 0:
                            entry="E";
                            break;
                        case 1:
                            entry="A";
                            break;
                        case 2:
                            entry="D";
                            break;
                        case 3:
                            entry="G";
                            break;
                        case 4:
                            entry="B";
                            break;
                        case 5:
                            entry="e";
                            break;
                        default:
                            entry="error";
                    }
                    if(i<3){
                        out1=out1+entry+" "+array[i][j]+"\n";}
                    else{out1=out1+entry+" "+array[i][j]+"\n";}
                }
            }
            textviewDB1.setText(out1);
            System.out.printf(out1);
            System.out.printf(out2);
        }
    };


    View.OnClickListener rbRedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bank=0;
        }
    };

    View.OnClickListener rbGreenListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bank=1;
        }
    };

    View.OnClickListener rbBlueListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bank=2;
        }
    };

    View.OnClickListener rbWhiteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bank=3;
        }
    };

    View.OnClickListener rbYellowListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bank=4;
        }
    };

    View.OnClickListener rbMagentaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bank=5;
        }
    };

    View.OnClickListener rbEListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cell=0;
        }
    };

    View.OnClickListener rbAListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cell=1;
        }
    };

    View.OnClickListener rbDListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cell=2;
        }
    };

    View.OnClickListener rbGListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cell=3;
        }
    };

    View.OnClickListener rbBListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cell=4;
        }
    };

    View.OnClickListener rbeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cell=5;
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resetDB(){


        String[][]array=new String[6][6];
        //{{ RedBank
        array[RED][E]="E Standard";
        array[RED][A]="DADGAD";
        array[RED][D]="Whole Step";
        array[RED][G]="Drop D";
        array[RED][B]="E Flat";
        array[RED][e]="Double Drop D";
        //}}

        //{{ GreenBank
        array[GREEN][E]="OPEN E";
        array[GREEN][A]="OPEN A";
        array[GREEN][D]="OPEN D";
        array[GREEN][G]="OPEN G";
        array[GREEN][B]="Dobro";
        array[GREEN][e]="All 4ths";
        //}}

        //{{ BlueBank
        array[BLUE][E]="E Standard";
        array[BLUE][A]="E Standard";
        array[BLUE][D]="E Standard";
        array[BLUE][G]="E Standard";
        array[BLUE][B]="E Standard";
        array[BLUE][e]="E Standard";
        //}}

        //{{ WhiteBank
        array[WHITE][E]="C Tuning";
        array[WHITE][A]="Low C";
        array[WHITE][D]="C Sharp";
        array[WHITE][G]="B Tuning";
        array[WHITE][B]="Dropped C";
        array[WHITE][e]="Dropped B";
        //}}

        //{{ YellowBank
        array[YELLOW][E]="Open C";
        array[YELLOW][A]="Open C6";
        array[YELLOW][D]="Open B";
        array[YELLOW][G]="Double Drop C Sharp";
        array[YELLOW][B]="Double Drop C";
        array[YELLOW][e]="Double Drop B";
        //}}

        //{{ MagentaBank
        array[MAGENTA][E]="E Standard";
        array[MAGENTA][A]="E Standard";
        array[MAGENTA][D]="E Standard";
        array[MAGENTA][G]="E Standard";
        array[MAGENTA][B]="E Standard";
        array[MAGENTA][e]="E Standard";
        //}}


        try {
            BufferedWriter bf= new BufferedWriter(new FileWriter(getFilesDir() + "/db.txt"));
            for(int i=0; i<6; i++){
                for (int j=0; j<6; j++){
                    String out = i+" "+j+" "+array[i][j]+"\n";
                    bf.write(out);
                }
            }
            bf.close();
        } catch (Exception f) {
            f.printStackTrace();
        }
    }

    public void readDB(String[][] array){
        String line;
        try{
            BufferedReader br= new BufferedReader(new FileReader(getFilesDir() + "/db.txt"));
            while((line=br.readLine())!=null){
                int i=line.charAt(0)-48;
                int j=line.charAt(2)-48;
                String tuning=line.substring(4);
                array[i][j]=tuning;
            }
            br.close();
        }catch(Exception f){
            f.printStackTrace();
        }
    }

    public void saveArray(String[][] array){
        try {
            BufferedWriter bf= new BufferedWriter(new FileWriter(getFilesDir() + "/db.txt"));
            for(int i=0; i<6; i++){
                for (int j=0; j<6; j++){
                    String out = i+" "+j+" "+array[i][j]+"\n";
                    bf.write(out);
                }
            }
            bf.close();
        } catch (Exception f) {
            f.printStackTrace();
        }
    }



























}

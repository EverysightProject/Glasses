package com.example.goddard.cloudtest;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private String APK_TYPE = "Phone";
    private String TURN_TO_SEND = "Right";
    private DatabaseReference mDatabase;
    private EditText etDest;
    private EditText etRange;
    private TextView tvRange;
    private TextView tvDestGot;
    private Spinner spTurn;
    private Spinner spMode;
    private ImageView ivTurnGot;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etDest = (EditText)findViewById(R.id.editText);
        etRange = (EditText)findViewById(R.id.rangeTxt);
        tvRange = (TextView)findViewById(R.id.rangeGotTxt);
        tvDestGot = (TextView)findViewById(R.id.destGotTxt);
        spTurn = (Spinner)findViewById(R.id.turnSpinner);
        spMode = (Spinner)findViewById(R.id.spinner);
        ivTurnGot = (ImageView)findViewById(R.id.turnImg);
        setApkDetailes(APK_TYPE);
        initWidgets();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void setPhone() {
        mDatabase.child("dest from phone").setValue(etDest.getText().toString());
        mDatabase.child("range from phone").setValue(etRange.getText().toString());
        mDatabase.child("turn from phone").setValue(TURN_TO_SEND);


        mDatabase.child("dest from glass").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvDestGot.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error dest from glass",Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.child("range from glass").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvRange.setText(dataSnapshot.getValue().toString()+ " meters");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error range from glass",Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.child("turn from glass").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String turn =dataSnapshot.getValue().toString();
                if (turn.equals("Left") ){
                    ivTurnGot.setImageResource(R.mipmap.left_arrow);
                } else if (turn.equals("Right")){
                    ivTurnGot.setImageResource(R.mipmap.right_arrow);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error turn from glass",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setGlass(){
        mDatabase.child("dest from glass").setValue(etDest.getText().toString());
        mDatabase.child("range from glass").setValue(etRange.getText().toString());
        mDatabase.child("turn from glass").setValue(TURN_TO_SEND);


        mDatabase.child("dest from phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvDestGot.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error dest from phone",Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.child("range from phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvRange.setText(dataSnapshot.getValue().toString()+" meters");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error range from phone",Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.child("turn from phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String turn = dataSnapshot.getValue().toString();
                if (turn == "Left"){
                    ivTurnGot.setImageResource(R.mipmap.left_arrow);
                } else if (turn == "Right"){
                    ivTurnGot.setImageResource(R.mipmap.right_arrow);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error turn from phone",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setApkFor(String outputStream){
        switch (outputStream){
            case "phone":
                setPhone();
                break;
            case "glass":
                setGlass();
                break;
            case "Phone":
                setPhone();
                break;
            case "Glass":
                setGlass();
                break;
            default:
                return;
        }
    }

    private void setApkDetailes(String outputStream){
        TextView toTxt = (TextView)findViewById(R.id.textView);
        switch (outputStream){
            case "phone":
                toTxt.setText("send to Glass");
                break;
            case "Phone":
                toTxt.setText("send to Glass");

                break;
            case "glass":
                toTxt.setText("send to Phone");
                break;
            case "Glass":
                toTxt.setText("send to Phone");
                break;
            default:
                return;
        }
    }

    private void initWidgets(){

//        ##################### Init Spinner Mode###########################
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> modeAdapter = ArrayAdapter.createFromResource(this,
                R.array.app_modes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spMode.setAdapter(modeAdapter);

        spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        APK_TYPE = "Phone";
                        break;
                    case 1:
                        APK_TYPE = "Glass";
                        break;
                }
                setApkDetailes(APK_TYPE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
//        ##################### Init Spinner Turn###########################

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> turnsAdapter = ArrayAdapter.createFromResource(this,
                R.array.turnes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        turnsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spTurn.setAdapter(turnsAdapter);

        spTurn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        TURN_TO_SEND = "Right";
                        break;
                    case 1:
                        TURN_TO_SEND = "Left";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });


        Button btn = (Button) findViewById(R.id.sendBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "send complete", Toast.LENGTH_SHORT);
                toast.show();
//                // Write a message to the database
                mDatabase = FirebaseDatabase.getInstance().getReference();
                // set "glass" or "phone" for the right apk form
                setApkFor(APK_TYPE);

            }
        });
    }


}

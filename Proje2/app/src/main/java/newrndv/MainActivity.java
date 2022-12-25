package newrndv;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.view.Menu;
import android.view.MenuInflater;
import android.net.Uri;
import android.widget.VideoView;

import com.newrndv.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.TextView;
import android.graphics.Color;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText txt1, txt2, txt3, txt4,txt5;
    private Button btn2;
    private VideoView videoView;
    FirebaseDatabase firebaseDatabase;
    private TextView ap;

    private Retrofit retrofit;

    private TimeApi timeApi;

    private String baseUrl="http://worldtimeapi.org/api/timezone/";
    private Call<TimeTurkey> timeTurkeyCall;
    private TimeTurkey timeTurkey;
    private void init(){
        ap = (TextView) findViewById(R.id.textView);
    }


    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;


    // creating a variable for
    // our object class
    RndvCreate employeeInfo;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itemm:
                Intent ıntentt=new Intent(MainActivity.this,MainActivity.class);
                startActivity(ıntentt);
                return true;
            case R.id.item1:
                Intent ıntent=new Intent(MainActivity.this, ServisVale.class);
                startActivity(ıntent);
                return true;
            case R.id.item2:
                Intent ıntent2=new Intent(MainActivity.this, YedekParca.class);
                startActivity(ıntent2);
                return true;
            case R.id.item3:
                Intent ıntent3=new Intent(MainActivity.this, BakimUrun.class);
                startActivity(ıntent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRetrofitSettings();
        txt1 = findViewById(R.id.editTextTextPersonName5); //adsoyad
        txt2 = findViewById(R.id.editTextTextPersonName6); //aracbilgi
        txt3 = findViewById(R.id.editTextTextPersonName7); //plaka
        txt4 = findViewById(R.id.editTextTextPersonName8); //telefon
        txt5 = findViewById(R.id.editTextDate);

        init();

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("EmployeeInfo");

        // initializing our object
        // class variable.
        employeeInfo = new RndvCreate();



        btn2 = findViewById(R.id.button5);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String adSoyad = txt1.getText().toString();
                String aracBilg = txt2.getText().toString();
                String telefon = txt4.getText().toString();
                String tarih = txt5.getText().toString();
                String plaka = txt3.getText().toString();

                // below line is for checking whether the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(adSoyad) && TextUtils.isEmpty(aracBilg) && TextUtils.isEmpty(plaka) && TextUtils.isEmpty(telefon) && TextUtils.isEmpty(tarih)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(adSoyad, aracBilg, plaka, telefon, tarih);
                }
            }
        });








        videoView= (VideoView) findViewById(R.id.videoView2);
        Uri adres = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.seat);

        videoView.setVideoURI(adres);
        videoView.start();






    }
    private void setRetrofitSettings(){
        retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        timeApi= retrofit.create(TimeApi.class);
        timeTurkeyCall= timeApi.getTime();
        timeTurkeyCall.enqueue(new Callback<TimeTurkey>() {
            @Override
            public void onResponse(Call<TimeTurkey> call, Response<TimeTurkey> response) {
                if(response.isSuccessful()){
                   timeTurkey= response.body();

                   if(timeTurkey != null)
                       ap.setText(timeTurkey.getDateTime().split("T")[0]);
                }
            }

            @Override
            public void onFailure(Call<TimeTurkey> call, Throwable t) {
                System.out.println((t.toString()));
            }
        });


    }
    private void addDatatoFirebase(String adSoyad, String aracBilg,String plaka, String telefon,String tarih) {
        // below 3 lines of code is used to set
        // data in our object class.
        employeeInfo.setAdSoyad(adSoyad);
        employeeInfo.setAracBilg(aracBilg);
        employeeInfo.setTelefon(telefon);
        employeeInfo.setPlaka(plaka);
        employeeInfo.setTarih(tarih);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(employeeInfo);

                // after adding this data we are showing toast message.
                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();

                TextView sonuc = (TextView) findViewById(R.id.textView2);

                sonuc.setText("Randevu Kaydınız Olusturuldu");
                sonuc.setTextColor(Color.parseColor("#0F9D58"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
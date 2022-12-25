package newrndv;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newrndv.R;

public class BakimUrun extends AppCompatActivity{
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
                Intent ıntent=new Intent(BakimUrun.this, MainActivity.class);
                startActivity(ıntent);
                return true;
            case R.id.item1:
                Intent ıntent1=new Intent(BakimUrun.this, ServisVale.class);
                startActivity(ıntent1);
                return true;
            case R.id.item2:
                Intent ıntent3=new Intent(BakimUrun.this, YedekParca.class);
                startActivity(ıntent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakimurun);





    }
}

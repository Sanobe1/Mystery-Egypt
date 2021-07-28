package com.misteryegypt.prize;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        YoYo.with(Techniques.Pulse)
                .duration(1500)
                .repeat(99)
                .playOn(findViewById(R.id.bugLeft));

        YoYo.with(Techniques.Pulse)
                .duration(1500)
                .repeat(99)
                .playOn(findViewById(R.id.bugRight));

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .repeat(0)
                .playOn(findViewById(R.id.textView));




    }

    public void onClick(View v) {
        Button button = findViewById(v.getId());

        switch (button.getTag().toString()) {

            case "start" : {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            }

            case "policy": {
                startActivity(new Intent(this, PolicyActivity.class));
                finish();
                break;
            }


            case "rules": {
                startActivity(new Intent(this, ActivityRules.class));
                finish();
                break;
            }

            case "exit": {
                finish();
                break;
            }

            default:
                break;
        }
    }
}


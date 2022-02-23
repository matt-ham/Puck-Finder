package handin.projects.assignment3_276;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import handin.projects.assignment3_276.databinding.ActivityMainBinding;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/*
class for startup screen activity with animations
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    TextView welcome;
    TextView subTitle;
    ImageView puckLeft;
    ImageView puckRight;
    ImageView puckCentre;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        welcome = findViewById(R.id.tvWelcomeText);
        subTitle = findViewById(R.id.tvSubTitle);
        puckLeft = findViewById(R.id.puckLeft);
        puckRight = findViewById(R.id.puckRight);
        puckCentre = findViewById(R.id.puckCentre);

        setUpMainMenuBtn();
        startAnimations();
    }

    public void setUpMainMenuBtn(){
        btn = findViewById(R.id.btnMainMenu);

        btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainMenuScreen.class);
            startActivity(intent);
            finish();
        });
    }

    private void startAnimations(){
        Animation fadeAnim = AnimationUtils.loadAnimation(this,R.anim.fade_text_anim);
        Animation slideAnimLeft = AnimationUtils.loadAnimation(this, R.anim.slide_left_anim);
        Animation slideAnimRight = AnimationUtils.loadAnimation(this, R.anim.slide_right_anim);
        subTitle.startAnimation(fadeAnim);
        welcome.startAnimation(fadeAnim);
        puckLeft.startAnimation(slideAnimLeft);
        puckRight.startAnimation(slideAnimRight);
        puckCentre.startAnimation(fadeAnim);
        btn.startAnimation(fadeAnim);
    }




}
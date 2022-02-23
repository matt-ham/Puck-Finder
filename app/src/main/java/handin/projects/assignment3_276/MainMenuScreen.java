package handin.projects.assignment3_276;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import handin.projects.assignment3_276.databinding.ActivityMainMenuScreenBinding;
/*
class for main menu screen that sets up menu buttons
 */
public class MainMenuScreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainMenuScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        setUpStartButton();
        setUpOptionsButton();
        setUpHelpButton();


    }

    public void setUpHelpButton(){
        Button helpButton = findViewById(R.id.btnHelp);
        helpButton.setOnClickListener(view -> {
            Intent intent = Help.makeIntent(MainMenuScreen.this);
            startActivity(intent);
        });


    }

    public void setUpStartButton(){
        Button startButton = findViewById(R.id.btnStart);
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameBoardUI.class);
            startActivity(intent);
        });
    }

    public void setUpOptionsButton(){
        Button optionsButton = findViewById(R.id.btnOptions);
        optionsButton.setOnClickListener(view -> {
            Intent intent = Options.makeIntent(MainMenuScreen.this);
            startActivity(intent);
        });
    }




}
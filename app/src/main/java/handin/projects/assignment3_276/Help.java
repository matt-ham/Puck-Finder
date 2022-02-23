package handin.projects.assignment3_276;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import handin.projects.assignment3_276.databinding.ActivityHelpBinding;
/*
class for help activity
 */
public class Help extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        hyperLinks();

    }
    public static Intent makeIntent(Context context){
        return new Intent(context, Help.class);
    }


    //  https://stackoverflow.com/questions/2734270/how-to-make-links-in-a-textview-clickable
    public void hyperLinks(){
        TextView tvAuthor = findViewById(R.id.tvAboutAuthor);
        tvAuthor.setMovementMethod(LinkMovementMethod.getInstance());

        TextView tvImageSources = findViewById(R.id.tvResourceLinks);
        tvImageSources.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
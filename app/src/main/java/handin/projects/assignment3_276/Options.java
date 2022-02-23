package handin.projects.assignment3_276;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import handin.projects.assignment3_276.databinding.ActivityOptionsBinding;
import handin.projects.assignment3_276.model.GameLogic;
/*
class for options menu that sets user's preferred options
 */
public class Options extends AppCompatActivity {

    private ActivityOptionsBinding binding;
    public static final String PUCK_PREFS = "Puck Prefs";
    public static final String PUCK_AMOUNT = "Amount of Pucks";
    public static final String GAME_SIZE_PREF_ROW = "Game Row Prefs";
    public static final String GAME_SIZE_PREF_COL = "Game Col Prefs";
    public static final String BOARD_DIMENSIONS_ROW = "Board Dimensions Row";
    public static final String BOARD_DIMENSIONS_COL = "Board Dimensions col";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        createPucks();
        createBoard();

    }



    private void createPucks(){
        RadioGroup rgPucks = findViewById(R.id.rgNumberPucks);

        int[] numPucksOptions = getResources().getIntArray(R.array.num_of_pucks);

        for (int i = 0; i <  numPucksOptions.length; i++) {
            final int numPucks = numPucksOptions[i];
            RadioButton buttonPucks = new RadioButton(this);
            buttonPucks.setText(numPucks + getString(R.string.pucks));
            buttonPucks.setTextColor(Color.WHITE);

            buttonPucks.setOnClickListener(view -> {
                saveNumPucks(numPucks);
            });

            rgPucks.addView(buttonPucks);

            // select default button
            if (numPucks == getNumPucks(this)) {
                buttonPucks.setChecked(true);
            }

        }
    }

    public void createBoard(){

        RadioGroup rgGameDimensions = findViewById(R.id.rgGameDimensions);

        int[] gameSizeRow = getResources().getIntArray(R.array.game_dimensions_row);
        int[] gameSizeCol = getResources().getIntArray(R.array.game_dimensions_col);

        for (int i = 0; i<gameSizeCol.length;i++){
            final int row = gameSizeRow[i];
            final int col = gameSizeCol[i];

            RadioButton buttonDimensions = new RadioButton(this);
            buttonDimensions.setText(row + " Rows by " + col + " Columns");
            buttonDimensions.setTextColor(Color.WHITE);

            buttonDimensions.setOnClickListener(view -> {
                saveGameDimensionsRow(row);
                saveGameDimensionsCol(col);
            });

            rgGameDimensions.addView(buttonDimensions);

            //default
            if (row == getGameRow(this) && col == getGameCol(this)) {
                buttonDimensions.setChecked(true);
            }
        }
    }

    private void saveNumPucks(int numPucks){
        SharedPreferences prefs = this.getSharedPreferences(PUCK_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PUCK_AMOUNT,numPucks);
        editor.apply();
    }

    static public int getNumPucks(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PUCK_PREFS, MODE_PRIVATE);
        int defaultPucks = context.getResources().getInteger(R.integer.default_num_pucks);
        return prefs.getInt(PUCK_AMOUNT, defaultPucks); // fix default
    }


    private void saveGameDimensionsRow(int row){
        SharedPreferences prefs = this.getSharedPreferences(GAME_SIZE_PREF_ROW, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BOARD_DIMENSIONS_ROW, row);
        editor.apply();
    }

    private void saveGameDimensionsCol(int col){
        SharedPreferences prefs = this.getSharedPreferences(GAME_SIZE_PREF_COL, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BOARD_DIMENSIONS_COL, col);
        editor.apply();
    }

    static public int getGameRow(Context context){
        SharedPreferences prefs = context.getSharedPreferences(GAME_SIZE_PREF_ROW, MODE_PRIVATE);
        int defaultDimensionsRow = context.getResources().getInteger(R.integer.default_game_dimensions_row);
        return prefs.getInt(BOARD_DIMENSIONS_ROW, defaultDimensionsRow);
    }

    static public int getGameCol(Context context){
        SharedPreferences prefs = context.getSharedPreferences(GAME_SIZE_PREF_COL, MODE_PRIVATE);
        int defaultDimensionsCol = context.getResources().getInteger(R.integer.default_game_dimensions_col);
        return prefs.getInt(BOARD_DIMENSIONS_COL, defaultDimensionsCol);
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, Options.class);
    }


}
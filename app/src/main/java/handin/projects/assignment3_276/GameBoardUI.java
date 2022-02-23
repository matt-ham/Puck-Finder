package handin.projects.assignment3_276;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import java.util.Timer;

import handin.projects.assignment3_276.databinding.ActivityGameBoardBinding;
import handin.projects.assignment3_276.model.GameLogic;


/*
Game UI class that creates the entire UI and applies GameLogic
 */
public class GameBoardUI extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityGameBoardBinding binding;
    private GameLogic game;
    private  int num_rows;
    private int num_cols;
    Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        game = GameLogic.getInstance();

        getGameOptions();

        populateButtons();

        updatePuckScanTextCount();
         /// check me later
    }


    public static Intent makeIntent(Context context){
        return new Intent(context, Options.class);
    }

    private void populateButtons() {
        game.resetPuckFoundCount();
        game.resetScanCounter();

        TableLayout table = findViewById(R.id.tblButtons);
        num_rows = game.getGameRow();
        num_cols = game.getGameCol();
        buttons = new Button[num_rows][num_cols];
        game.randomizePuckLocation();

        for(int row = 0; row < num_rows; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int col = 0; col < num_cols; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.getBackground().setAlpha(95);
                button.setTextColor(Color.WHITE);

                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setPadding(0,0,0,0);

                button.setOnClickListener(view -> {
                    if(!game.buttonHasBeenChecked(FINAL_ROW, FINAL_COL)){
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int col, int row){
        Button button = buttons[row][col];
        lockButtonSizes();

        if (game.checkPuckLocation(row,col)) {
            // http://commons.wikimedia.org/wiki/Crystal_Clear
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hockey_puck);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            final MediaPlayer puckFoundSound = MediaPlayer.create(this, R.raw.unlock);
            puckFoundSound.start();

            updateAllButtons();
            checkForWinner();
        }
        // Change text on button:
      else {
           button.setTextColor(Color.RED);
           game.incrementScanCount();
           scanBoard(row,col);
           button.setText("" + game.scanForPucks(row, col));
        }
        updatePuckScanTextCount();
    }

    private void checkForWinner() {
        if(game.getNumPucks() == game.getPuckFoundCount()){
            FragmentManager manager = getSupportFragmentManager();
            WinnerDialogFragment dialog = new WinnerDialogFragment();
            dialog.show(manager, "WinnerDialogFragment");
        }
    }

    private void updateAllButtons(){ // to be used when updating counts in real time (easier)
        for(int i = 0; i < num_rows; i++){
            for(int x = 0; x < num_cols; x++){
                if(game.buttonHasBeenChecked(i, x)) {
                    Button button = buttons[i][x];
                    //lockButtonSizes(); // is this line necessary???

                    button.setText("" + game.scanForPucks(i,x));
                }
            }
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < num_rows; row++){
            for (int col = 0; col < num_cols; col ++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    public void updatePuckScanTextCount(){
        TextView numPuckFound = findViewById(R.id.tvFoundPucks);
        TextView scansUsed = findViewById(R.id.tvScansUsed);
        numPuckFound.setText("Found " + game.getPuckFoundCount() + " of " + game.getNumPucks() + " Pucks");
        scansUsed.setText("# of Scans Used: " + game.getScanCounter());
    }

    private void getGameOptions(){
        game.setNumPucks(Options.getNumPucks(this));
        game.setGameRow(Options.getGameRow(this));
        game.setGameCol(Options.getGameCol(this));
    }


    private void scanBoard(int row, int col){
        final MediaPlayer buzzer = MediaPlayer.create(this, R.raw.hockeybuzzer);
        buzzer.start();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_btn);

        for (int c = 0; c<num_cols; c++){
            buttons[row][c].startAnimation(animation);

        }

        for(int r = 0; r <num_rows; r++){
                buttons[r][col].startAnimation(animation);

        }
    }



}
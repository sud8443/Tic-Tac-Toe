package developersudhanshu.com.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int n = 4;
    LinearLayout mainLayout;
    LinearLayout[] rows;
    MyButton buttons[][];
    Button resetBtn;
    TextView p1Score;
    TextView p2Score;
    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;
    public final static int PLAYER1WINS = 1;
    public final static int PLAYER2WINS = 2;
    public final static int DRAW = 3;
    public final static int INCOMPLETE = 4;
    boolean player1Turn;
    int textSize = 60;
    boolean gameOver = false;
    ScoreKeeper scoreKeeper = new ScoreKeeper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBoard();
    }

    private void setUpBoard() {
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.linear_layout_activity_main);

        player1Turn = true;
        gameOver = false;
        buttons = new MyButton[n][n];
        mainLayout.removeAllViews();
        rows = new LinearLayout[n];
        for(int i = 0; i < n; i++){
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            params.setMargins(5,5,5,5);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                buttons[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(5,5,5,5);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setTextSize(textSize);
                rows[i].addView(buttons[i][j]);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.newGame){
            resetBoard();
        }else if(id == R.id.boardSize3){
            n = 3;
            setUpBoard();
        }else if(id == R.id.boardSize4){
            n = 4;
            setUpBoard();
        }else if(id == R.id.boardSize5){
            n = 5;
            setUpBoard();
        } else if (id == R.id.scoreboard) {
            showScoreboard();
        }
        return true;
    }

    private void resetBoard() {

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setPlayer(0);
            }
        }
        gameOver = false;
        player1Turn = true;
    }

    @Override
    public void onClick(View v) {
        MyButton b = (MyButton) v;

        if(gameOver){
            return;
        }

        if(b.getPlayer() != 0){
            Toast.makeText(this,"Invalid Move !", Toast.LENGTH_SHORT).show();
            return;
        }

        if(player1Turn){
            b.setPlayer(PLAYER1);
            b.setText("0");
        }else{
            b.setPlayer(PLAYER2);
            b.setText("X");
        }

        int status = gameStatus();
        player1Turn = !player1Turn;

        if(status == PLAYER1WINS || status == PLAYER2WINS){
            Toast.makeText(this, "Player " + status + " wins." +
                    "\nP1: " + scoreKeeper.getP1() + " P2: " + scoreKeeper.getP2(),
                    Toast.LENGTH_SHORT).show();
            gameOver = true;
            return;
        }else if (status == DRAW){
            Toast.makeText(this, " Draw", Toast.LENGTH_SHORT).show();
            gameOver = true;
            return;
        }

    }

    private int gameStatus() {

        // Rows
        for(int i = 0; i < n; i++){
            boolean flag = true;
            for(int j = 1; j < n; j++){
                if(buttons[i][0].getPlayer() == 0 || buttons[i][0].getPlayer() != buttons[i][j].getPlayer()){
                    flag = false;
                    break;
                }
            }
            if(flag){
                if(buttons[i][0].getPlayer() == PLAYER1){
                    scoreKeeper.setP1(scoreKeeper.getP1()+1);
                    return PLAYER1WINS;
                }else{
                    scoreKeeper.setP2(scoreKeeper.getP2()+1);
                    return  PLAYER2WINS;
                }
            }
        }

        // Columns
        for(int j = 0; j < n; j++){
            boolean flag = true;
            for(int i = 1; i < n; i++){
                if(buttons[0][j].getPlayer() == 0 || buttons[0][j].getPlayer() != buttons[i][j].getPlayer()){
                    flag = false;
                    break;
                }
            }
            if(flag){
                if(buttons[0][j].getPlayer() == PLAYER1){
                    scoreKeeper.setP1(scoreKeeper.getP1()+1);
                    return PLAYER1WINS;
                }else{
                    scoreKeeper.setP2(scoreKeeper.getP2()+1);
                    return  PLAYER2WINS;
                }
            }
        }

        boolean flag = true;
        for(int i = 1; i < n; i++){
            if(buttons[0][0].getPlayer() == 0 || buttons[0][0].getPlayer() != buttons[i][i].getPlayer()){
                flag = false;
                break;
            }
        }
        if(flag){
            if(buttons[0][0].getPlayer() == PLAYER1){
                scoreKeeper.setP1(scoreKeeper.getP1()+1);
                return PLAYER1WINS;
            }else{
                scoreKeeper.setP2(scoreKeeper.getP2()+1);
                return  PLAYER2WINS;
            }
        }

        flag = true;
        for(int i = n-1; i >= 0; i--){
            int col = n - 1 - i;
            if(buttons[i][col].getPlayer() == 0 || buttons[n-1][0].getPlayer() != buttons[i][col].getPlayer()){
                flag = false;
                break;
            }
        }
        if(flag){
            if(buttons[n-1][0].getPlayer() == PLAYER1){
                scoreKeeper.setP1(scoreKeeper.getP1()+1);
                return PLAYER1WINS;
            }else{
                scoreKeeper.setP2(scoreKeeper.getP2()+1);
                return  PLAYER2WINS;
            }
        }


        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(buttons[i][j].getPlayer() == 0){
                    return  INCOMPLETE;
                }
            }
        }

        return DRAW;
    }

    private void showScoreboard() {
        setContentView(R.layout.activity_scoreboard);
        p1Score = findViewById(R.id.textView6);
        p2Score = findViewById(R.id.textView7);
        displayScore();

        resetBtn = findViewById(R.id.button);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreKeeper.resetScore();
                displayScore();
            }
        });
    }

    private void displayScore() {
        p1Score.setText("\t\tPlayer 1: " + scoreKeeper.getP1());
        p2Score.setText("\t\tPlayer 2: " + scoreKeeper.getP2());
    }

}

package vinay.gaba.snappy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class LoginActivity extends ActionBarActivity {

    ActionProcessButton btnLogIn;
    MaterialEditText passMaterialEditText,usernameMaterialEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_SHOW_TITLE);
        usernameMaterialEditText = (MaterialEditText)findViewById(R.id.usernameEditText);
        passMaterialEditText = (MaterialEditText)findViewById(R.id.passwordEditText);
        btnLogIn = (ActionProcessButton)findViewById(R.id.btnLogIn);
        btnLogIn.setMode(ActionProcessButton.Mode.ENDLESS);


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                btnLogIn.setProgress(1);
                String username = usernameMaterialEditText.getText().toString();
                String password = passMaterialEditText.getText().toString();
                ParseUser.logInInBackground(username, password, new LogInCallback() {


                    @Override
                    public void done(ParseUser parseUser, com.parse.ParseException e) {
                        if (parseUser != null) {
                            // Hooray! The user is logged in.
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            // Login failed. Look at the ParseException to see what happened.
                            Crouton.makeText(LoginActivity.this, "Login attempt failed!", Style.ALERT).show();
                        }
                    }
                });


            }
        });


        usernameMaterialEditText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if(btnLogIn.getProgress() == -1){
                    btnLogIn.setProgress(0);
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        passMaterialEditText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if(btnLogIn.getProgress() == -1){
                    btnLogIn.setProgress(0);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }


    public void startSignUpActivity(View view){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}

package com.ghomebyrw.gworker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.clients.JobClient;

import java.net.PasswordAuthentication;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button logInButton;
    private JobClient jobClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        jobClient = new JobClient();
        etEmail = (EditText) findViewById(R.id.etEmailAddress);
        etPassword = (EditText) findViewById(R.id.etPassword);
        logInButton = (Button) findViewById(R.id.btnLogin);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (emailAddress.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.email_password_not_null),
                            Toast.LENGTH_SHORT).show();
                } else {
                    jobClient.logIn(emailAddress, password, new Callback<Boolean>() {
                        @Override
                        public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                            Intent intent = new Intent(LoginActivity.this, JobsActivity.class);
                            intent.putExtra("email", etEmail.getText());
                            intent.putExtra("password", etPassword.getText());
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(LoginActivity.this, getString(R.string.email_password_invalid),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
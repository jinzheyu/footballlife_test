package nanoFuntas.footballlife.loginAndRegister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class LoginAndRegister extends Activity {
	
	EditText mET_email = null; 
	EditText mET_password = null;
	Button mBtn_login = null;
	String strLoginResult = null;
	
	EditText mET_email_reg = null;
	EditText mET_pw_reg = null;
	EditText mET_pw_reg_retype = null;
	Button mBtn_register = null;
	
	final String TYPE_LOGIN = "LOGIN";
	final String TYPE_REGISTER = "REGISTER";
	
	//test
	TextView mTV_testMsg;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);

        initViews();              
        mBtn_login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str_email = 	mET_email.getText().toString();
				String str_password = mET_password.getText().toString();
				ArrayList<NameValuePair> postParameters = wrapPostParameters(TYPE_LOGIN, str_email, str_password);
								
				LoginAsyncTask mLoginAsyncTask = new LoginAsyncTask();			
				mLoginAsyncTask.execute(postParameters);
								
				try {
					strLoginResult = mLoginAsyncTask.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				if(strLoginResult.equalsIgnoreCase("1")){
					mTV_testMsg.setText(strLoginResult);
				} else if(strLoginResult.equalsIgnoreCase("0")){
					mTV_testMsg.setText(strLoginResult);
				} else{
					mTV_testMsg.setText(strLoginResult);
					//mTV_testMsg.setText("not equal to 1 or 0");
				}					
			}
		});
        
        mBtn_register.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		String str_email_reg = mET_email_reg.getText().toString();
        		String str_pw_reg = mET_pw_reg.getText().toString();
        		String str_pw_reg_retype = mET_pw_reg_retype.getText().toString();
				
        		if( !str_pw_reg.equals(str_pw_reg_retype) ){
        			mTV_testMsg.setText("Password entered not equal ^^");
        		} else{
        			ArrayList<NameValuePair> postParameters = wrapPostParameters(TYPE_REGISTER, str_email_reg, str_pw_reg);
    				
    				LoginAsyncTask mLoginAsyncTask = new LoginAsyncTask();			
    				mLoginAsyncTask.execute(postParameters);
    				
    				try {
    					strLoginResult = mLoginAsyncTask.get();
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (ExecutionException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			
    				if(strLoginResult.equalsIgnoreCase("1")){
    					mTV_testMsg.setText(strLoginResult);
    				} else if(strLoginResult.equalsIgnoreCase("0")){
    					mTV_testMsg.setText(strLoginResult);
    				} else{
    					mTV_testMsg.setText(strLoginResult);
    					//mTV_testMsg.setText("not equal to 1 or 0");
    				}
        		}			
			}
		});
        
    }
	
    private class LoginAsyncTask extends AsyncTask<ArrayList<NameValuePair>, Void, String>{

		@Override
		protected String doInBackground(ArrayList<NameValuePair>... params) {
			// TODO Auto-generated method stub
			HttpClientService mHttpClientService = new HttpClientService();
			return mHttpClientService.executeHttpPose(params[0]);
		}
    }
    
    private ArrayList<NameValuePair> wrapPostParameters(String type, String email, String pw){

    	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("Type", type));
    	postParameters.add(new BasicNameValuePair("Email", email));
		postParameters.add(new BasicNameValuePair("Password", pw));								
        Log.d("email", email);

    	return postParameters; 	
    }

    private void initViews(){
    	//initialize view for login
    	mET_email = (EditText)findViewById(R.id.et_email);
        mET_password = (EditText)findViewById(R.id.et_password);
        mBtn_login = (Button)findViewById(R.id.btn_login);       
        //initialize view for register
        mET_email_reg = (EditText)findViewById(R.id.et_email_reg);
        mET_pw_reg = (EditText)findViewById(R.id.et_pw_reg);
        mET_pw_reg_retype = (EditText)findViewById(R.id.et_pw_retype);
        mBtn_register = (Button)findViewById(R.id.btn_regiter);
        //test TextView
        mTV_testMsg = (TextView)findViewById(R.id.tv_test);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login_and_register, menu);
        return true;
    }
}

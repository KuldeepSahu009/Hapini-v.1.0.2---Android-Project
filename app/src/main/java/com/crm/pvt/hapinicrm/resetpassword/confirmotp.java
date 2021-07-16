package com.crm.pvt.hapinicrm.resetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crm.pvt.hapinicrm.R;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class confirmotp extends AppCompatActivity {
    private Button confirmotpfromforgotpassword;
    private OtpTextView otpTextView;
    private int receivedOtp;
    /**
     * for testing success and failure of OTP View take Fake OTP is 12345
     */
    private final static int tempOtp = 123456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmotp);
        otpTextView = findViewById(R.id.recievedOTP);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox

            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                receivedOtp = Integer.parseInt(otp);
                Toast.makeText(getApplicationContext(),"OTP is "+otp,Toast.LENGTH_LONG).show();
                if(receivedOtp == tempOtp)
                {
                    // if OTP is right
                    otpTextView.showSuccess();

                }
                else
                {
                    otpTextView.showError();
                }
            }
        });
        confirmotpfromforgotpassword=findViewById(R.id.confirmotpbtn);
        confirmotpfromforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String s=getotptext.getText().toString();
                //s contains otp

                //add the activity after verifying otp
                Intent intent=new Intent(confirmotp.this, newpassword.class);
                startActivity(intent);

            }
        });
    }
}
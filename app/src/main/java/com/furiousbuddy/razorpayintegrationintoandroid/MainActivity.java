package com.furiousbuddy.razorpayintegrationintoandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends Activity implements PaymentResultListener {

    String TAG="Payment Error";
    Button pay;
    EditText phone,email;
    String currency="INR";
    String amount="500";
    String description="Test Order";
    String name="Subrata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Checkout.preload(getApplicationContext());

        pay=(Button) findViewById(R.id.paybtn);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPayment();
            }
        });

    }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.ic_launcher_background);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", name);

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", description);

            options.put("currency", currency);

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email.getText().toString());
            preFill.put("contact", phone.getText().toString());

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        try {
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}

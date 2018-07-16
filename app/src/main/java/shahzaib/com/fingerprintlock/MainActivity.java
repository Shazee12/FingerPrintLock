package shahzaib.com.fingerprintlock;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtheading,txtfingerprint;
    ImageView imageView;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtheading = findViewById(R.id.txtHading);
        txtfingerprint = findViewById(R.id.textView3);
        imageView = findViewById(R.id.imageView);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){
              txtfingerprint.setText("FingerPrint scanner not available");

            }
            else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
              txtfingerprint.setText("Permission not granted");
            }
            else if (!keyguardManager.isKeyguardSecure()) {
                txtfingerprint.setText("Ad lock to your phone ");
            }
            else if(!fingerprintManager.hasEnrolledFingerprints()) {
                txtfingerprint.setText("Add atleast one fingerprint");
            }
            else {
                txtfingerprint.setText("Place your finger on scanner");

                FingerPrintHandler fingerPrintHandler = new FingerPrintHandler(this);
                fingerPrintHandler.startAuth(fingerprintManager,null);
            }


        }
    }
}

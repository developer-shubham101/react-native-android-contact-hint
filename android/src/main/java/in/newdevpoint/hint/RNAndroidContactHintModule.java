package in.newdevpoint.hint;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.HintRequest;

public class RNAndroidContactHintModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private static final int RC_HINT = 21;
    private static final String TAG = "AndroidContactHint";
    private final ReactApplicationContext reactContext;
    private Callback fetchContactCallback;
    private ResponseHelper responseHelper = new ResponseHelper();

    public RNAndroidContactHintModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "RNAndroidContactHint";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }

    @ReactMethod
    public void fetchContactNo(Callback fetchContactCallback) {
        this.fetchContactCallback = fetchContactCallback;
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
//            responseHelper.invokeError(callback, "can't find current Activity");
            return;
        }

        CredentialsOptions options = new CredentialsOptions.Builder()
                .forceEnableSaveDialog()
                .build();
        CredentialsClient mCredentialsClient = Credentials.getClient(getCurrentActivity(), options);


        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = mCredentialsClient.getHintPickerIntent(hintRequest);
        try {
            currentActivity.startIntentSenderForResult(intent.getIntentSender(), RC_HINT, null, 0, 0, 0, null);
        } catch (IntentSender.SendIntentException e) {
            Log.e("", "Could not start hint picker Intent", e);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultFinal(requestCode, resultCode, data);
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        onActivityResultFinal(requestCode, resultCode, data);
    }


    public void onActivityResultFinal(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_HINT) {
            responseHelper = new ResponseHelper();
            if (resultCode == AppCompatActivity.RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                System.out.println(credential);

                Log.d(TAG, "onActivityResult: " + credential.getId());

                this.responseHelper.putString("mobile", credential.getId());
                this.responseHelper.invokeResponse(this.fetchContactCallback);
//                this.fetchContactCallback.invoke(credential.getId());
            } else {
//                this.fetchContactCallback.invoke("Hint Read: NOT OK");

                this.responseHelper.invokeError(this.fetchContactCallback, "Hint Read: NOT OK");
                Log.e(TAG, "Hint Read: NOT OK");
//                Toast.makeText(this, "Hint Read Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onNewIntent(Intent intent) {

    }
}
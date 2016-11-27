package net.atos.mobiflow;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.topimagesystems.Common.*;
import com.topimagesystems.controllers.imageanalyze.CameraController;
import com.topimagesystems.controllers.imageanalyze.CameraManagerController.TISMobiFlowMessages;
import com.topimagesystems.controllers.imageanalyze.CameraTypes.*;
import com.topimagesystems.data.SessionResultParams;
import com.topimagesystems.data.TISLicenseParameters;
import com.topimagesystems.intent.CaptureIntent;
import com.topimagesystems.intent.CaptureIntent.*;
import com.topimagesystems.util.OcrValidationUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements TISMobiFlowMessages {

    final String COLOUR_FRONT = "COLOUR_FRONT";
    final String GREYSCALE_FRONT = "GREYSCALE_FRONT";
    final String JPEG_BW_FRONT = "JPEG_BW_FRONT";
    final String ORIGINAL_FRONT = "ORIGINAL_FRONT";
    final String TIFF_FRONT = "TIFF_FRONT";

    final TISLicenseParameters tisLicenseParameters = new TISLicenseParameters(
            "Atos",
            "01e9d6ae-a17e-4f32-b418-a309a309f41e",
            "peDToXFikcFVh8JnzJA9fpgOh6z6hYoSkAOePh7KMmFwOrkNMb8iHKtK4jRuy6Ip1vcOrKzmXK6MomYt+Ve1xA=="
            );

    private HashMap<String,byte[]> image_data = new HashMap<String,byte[]>();

    private EditText caseRef;
    private EditText custId;
    private EditText empId;

    private void resetImageData() {
        image_data = new HashMap<String,byte[]>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        CameraController.registerListener(this);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        caseRef = (EditText) findViewById(R.id.case_ref);
        custId  = (EditText) findViewById(R.id.cust_id);
        empId   = (EditText) findViewById(R.id.emp_id);

        final Button btn_submit = (Button) this.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doSubmit(v);
            }
        });

        final Button btn_capture = (Button) this.findViewById(R.id.btn_capture);
        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptureIntent ci = new CaptureIntent(MainActivity.this);
                CardParams input = (CaptureIntent.CardParams) ci.getCaptureParams(CaptureIntent.TISDocumentType.CARD);
                input.documnetType = CaptureIntent.TISDocumentType.CARD;
                input.showGuidelinesIndicators = true;
                input.scanFrontOnly = true;
                input.uxType = TISFlowUXType.STATIC;
                input.ocrType = OCRType.OFF;
                input.license = tisLicenseParameters;
                ci.captureDocument(input);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {

        SessionResultParams result = CaptureIntent.parseActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureIntent.MOBI_FLOW_REQUEST_CODE) {
            Log.e("onActivityResult", String.valueOf(resultCode));
            switch (resultCode) {
                case RESULT_OK:
                    resetImageData();
                    image_data.put(COLOUR_FRONT, SessionResultParams.colorFront);
                    image_data.put(GREYSCALE_FRONT, SessionResultParams.grayscaleFront);
                    image_data.put(JPEG_BW_FRONT, SessionResultParams.jpegBWFront);
                    image_data.put(ORIGINAL_FRONT, SessionResultParams.originalFront);
                    image_data.put(TIFF_FRONT, SessionResultParams.tiffFront);
                    updateImagePreview(image_data);
                    break;

                default:
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        CameraController.unregisterListener();
    }

    private void doSubmit(View v) {
        //final String cdcUrl = "http://mobiflow-photo-upload.apps.eu01.cf.canopy-cloud.com/jaxrs/uploadimage";
        final String cdcUrl = "http://csms-demo.atos.io/rome/uploadimage";

        final String CASE_REF = "case_ref";
        final String CUST_ID = "customer_id";
        final String EMP_ID = "employee_id";
        final String FILE = "file";

        try
        {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(cdcUrl);

            byte[] image = image_data.get(COLOUR_FRONT);//getTestPhotoData();

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            entityBuilder.addTextBody(CASE_REF, caseRef.getText().toString());
            entityBuilder.addTextBody(CUST_ID, custId.getText().toString());
            entityBuilder.addTextBody(EMP_ID, empId.getText().toString());
            entityBuilder.addBinaryBody(FILE, image, ContentType.create("image/jpg"), COLOUR_FRONT+".jpg");

            HttpEntity entity = entityBuilder.build();
            //post.addHeader("Content-Type","multipart/form-data");
            post.addHeader("Accept","application/json");
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            Log.v("result", result);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private byte[] getTestPhotoData() {
        byte[] data = new byte[0];

        InputStream is = null;
        try {
            is = getResources().openRawResource(
                    getResources().getIdentifier("cute_red_panda","raw", getPackageName())
                );
            data = IOUtils.toByteArray(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
            // Do something useful to the data
            return data;
        }
    }

    private void updateImagePreview(HashMap<String, byte[]> dataMap) {

        for ( String key : dataMap.keySet() ) {
            byte[] val = dataMap.get(key);
            if (val != null) System.out.println("***** key: "+key+" length: "+val.length+" *****");
        }

        ImageView preview = (ImageView) findViewById(R.id.img_preview);
        byte[] rawImage = dataMap.get(COLOUR_FRONT);
        Bitmap image = BitmapFactory.decodeByteArray(rawImage,0,rawImage.length);
        preview.setImageBitmap(image);
    }

    @Override
    public void onMobiFlowGeneralMessageReceived(TISFlowGeneralMessages message, Object[] extraData, Context context) {
        // get messages from the library.
        CaptureIntent.callbackReturnMessage returnMessage = CameraController.getManagerListener();
        Log.e("MobiFlowGeneralMessage","TISFlowGeneralMessages "+ message.name().toString());
        switch (message) {
            case BACK_PRESSED:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;

            case CAPTURE_BACK:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;

            case PAN_CARD_OCR_RESULT:
                String[] ocrData = (String[]) extraData;
                boolean panValidation = OcrValidationUtils.validationPanCard((String) extraData[1]);
                panValidation = true;
                if (panValidation) {
                    returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_OK);
                } else {
                    returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_FAILED);
                }
                break;
            case CHECK_OCR_RESULT:
                String[] ocrCheckData = (String[]) extraData;
                returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_OK);
                break;

            case ID_CARD_OCR_RESULT:
                returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_OK);
                break;

            case PASSPORT_OCR_RESULT:
                Object[] passportResult = extraData;
                boolean passportValidation = OcrValidationUtils.validatePassport((String) extraData[0], Integer.valueOf((String) (extraData[1])));
                HashMap<String, String> passportResultParsed = (HashMap<String, String>) extraData[4];
                if (passportValidation) {
                    returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_OK);
                } else {
                    returnMessage.onMessageReturn(TISFlowInputMessages.OCR_RESULT_FAILED);
                }
                break;


            case MULTI_CAPTURE:
                // get the image here!!! image data can be taken
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            default:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
        }
    }

    @Override
    public void onMobiFlowErrorMessageReceived(TISFlowErrorMessage error, Object[] objects, Context context) {
        // get Error messages from the library.
        Log.e("MobiFlowErrorMessage","TISFlowErrorMessage"+ error.name().toString());
        CaptureIntent.callbackReturnMessage returnMessage = CameraController.getManagerListener();
        switch (error) {
            case ERROR_MICR_READING_CHECK:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                //errorMessageReceived = true;
                break;
            case ERROR_IMAGE_CONTRAST:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                //errorMessageReceived = true;
                break;
            case ERROR_MICR_LENGHT: // for checks!
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                //errorMessageReceived = true;
                break;
            case ERROR_NO_VALID_BOUNDING_BOX: // didn't find any boundingbox
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_BLUR_DETECTED:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_IQA_DARKNESS:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_IQA_CORNER_DATA:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_IQA_EDGE_DATA:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_IQA_SKEW:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_IQA_NUM_SPOTS:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_IQA_HORIZONTAL_STREAK:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_IQA_PIGGY_BACK:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_IQA_CARBON_STRIP:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_MICR_INTERUPPTED:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_MICR_ON_BACK:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case UNSUPPORTED_CAMERA:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case UNSPORTTED_CPU:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            case ERROR_OCR_READING: // ocr general error.
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;
            default:
                returnMessage.onMessageReturn(TISFlowInputMessages.CONTINUE_MOBI_FLOW);
                break;

        }
        return;
    }

    @Override
    public void onMobiFlowUIEventMessageReceived(TISFlowUIMessages tisFlowUIMessages, ViewGroup viewGroup) {
        return;
    }

    @Override
    public void onFailed() {
        return;
    }
}

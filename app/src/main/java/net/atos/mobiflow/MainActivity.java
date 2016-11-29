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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.topimagesystems.Common.*;
import com.topimagesystems.controllers.imageanalyze.CameraController;
import com.topimagesystems.controllers.imageanalyze.CameraManagerController;
import com.topimagesystems.controllers.imageanalyze.CameraManagerController.TISMobiFlowMessages;
import com.topimagesystems.controllers.imageanalyze.CameraTypes.*;
import com.topimagesystems.data.SessionResultParams;
import com.topimagesystems.data.TISLicenseParameters;
import com.topimagesystems.intent.CaptureIntent;
import com.topimagesystems.intent.CaptureIntent.*;
import com.topimagesystems.util.OcrValidationUtils;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements TISMobiFlowMessages {

    /* Define String constants for image types */
    final String COLOUR_FRONT = "COLOUR_FRONT";
    final String GREYSCALE_FRONT = "GREYSCALE_FRONT";
    final String JPEG_BW_FRONT = "JPEG_BW_FRONT";
    final String ORIGINAL_FRONT = "ORIGINAL_FRONT";
    final String TIFF_FRONT = "TIFF_FRONT";

    /* Define String constants for document types */
    final String CARD = "CARD";
    final String PASSPORT = "PASSPORT";
    final String FULL_PAGE = "FULL_PAGE";
    final String PAYMENT = "PAYMENT";
    final String CHEQUE = "CHEQUE";

    /* MobiFlow License Details */
    /*
    final TISLicenseParameters tisLicenseParameters = new TISLicenseParameters(
        "Atos",
        "01e9d6ae-a17e-4f32-b418-a309a309f41e",
        "peDToXFikcFVh8JnzJA9fpgOh6z6hYoSkAOePh7KMmFwOrkNMb8iHKtK4jRuy6Ip1vcOrKzmXK6MomYt+Ve1xA=="
    );
    */
    final TISLicenseParameters tisLicenseParameters = new TISLicenseParameters(
        "Atos",
        "de12c436-c0f1-43f9-bbc2-a644810d09e3",
        "Np/POefAOkid1uy+yxfK4ywMSThUs+tVZnMy51DntaB6z5moFh7hPEghUCWTP05yOCe+S0IFIZ9XPwqFIjXt7Q=="
    );

    /* Data structure for captured images */
    private HashMap<String,byte[]> image_data = new HashMap<String,byte[]>();

    /* Variables for input fields */
    private Spinner  docType;
    private EditText caseRef;
    private EditText custId;
    private EditText empId;

    private String docTypeString = CARD;

    /* Routine to flush any image data */
    private void resetImageData() {
        image_data = new HashMap<String,byte[]>();
    }

    // Methods for handling the ActionBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public void onRefreshAction(MenuItem mi) {
        resetImageData();
        docType.setId(0);
        caseRef.getText().clear();
        custId.getText().clear();
        empId.getText().clear();
        updateImagePreview(new HashMap<String, byte[]>());
    }

    public void onSettingsAction(MenuItem mi) {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    // END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        docType = (Spinner)  findViewById(R.id.doc_type);
        caseRef = (EditText) findViewById(R.id.case_ref);
        custId  = (EditText) findViewById(R.id.cust_id);
        empId   = (EditText) findViewById(R.id.emp_id);

        // Add events to UI controls
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
            CameraController.registerListener(MainActivity.this);
            CaptureIntent ci = new CaptureIntent(MainActivity.this);
            ci.captureDocument(getCaptureParams(ci));
            }
        });

        docType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0: //CARD
                        docTypeString = CARD;
                        break;
                    case 1: //PASSPORT
                        docTypeString = PASSPORT;
                        break;
                    case 2: //FULL PAGE
                        docTypeString = FULL_PAGE;
                        break;
                    case 3: //PAYMENT
                        docTypeString = PAYMENT;
                        break;
                    case 4: //CHEQUE
                        docTypeString = CHEQUE;
                        break;
                    default:
                        docTypeString = CARD;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                docTypeString = CARD;
            }
        });

        // END OF onCreate()
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
        if(requestCode == CaptureIntent.MOBI_FLOW_REQUEST_CODE) {
            Log.e("onActivityResult", String.valueOf(resultCode));
            switch (resultCode) {
                case RESULT_OK:
                    resetImageData();
                    SessionResultParams result = CaptureIntent.parseActivityResult(requestCode, resultCode, data);
                    image_data.put(COLOUR_FRONT, result.colorFront);
                    //image_data.put(GREYSCALE_FRONT, SessionResultParams.grayscaleFront);
                    //image_data.put(JPEG_BW_FRONT, SessionResultParams.jpegBWFront);
                    //image_data.put(ORIGINAL_FRONT, SessionResultParams.originalFront);
                    //image_data.put(TIFF_FRONT, SessionResultParams.tiffFront);
                    updateImagePreview(image_data);
                    break;

                case RESULT_CANCELED:
                    break;

                case CameraManagerController.RESULT_CAMERA_PERMISSION_ACSSES_DENIED:
                    Toast.makeText(getApplicationContext(), "Camera Permission Access Denied!", Toast.LENGTH_LONG).show();
                    break;

                case CameraManagerController.RESULT_CANCELED_FROM_ALERT:
                    break;

                case CameraManagerController.RESULT_CLOSE_SESSION:
                    break;

                case CameraManagerController.RESULT_LIBRARY_ERROR:
                    String libraryErrMsg = "MobiFlow Library Error: " + data.getStringExtra(CaptureIntent.MOBIFLOW_ERROR_DETAILS);
                    Toast.makeText(getApplicationContext(), libraryErrMsg, Toast.LENGTH_LONG).show();
                    break;

                case CameraManagerController.RESULT_LICENSE_INVALID:
                    String licenseErrMsg = "MobiFlow License Error: " + data.getStringExtra(CaptureIntent.MOBIFLOW_ERROR_DETAILS);
                    Toast.makeText(getApplicationContext(), licenseErrMsg, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        CameraController.unregisterListener();
    }

    private baseCaptureParams getCaptureParams(CaptureIntent ci) {

        // Establish a default
        baseCaptureParams input = ci.getCaptureParams(TISDocumentType.CARD);

        // Use the UI Spinner to change the Document Type for scanning
        switch (docType.getSelectedItemPosition()) {
            case 0: // CARD
                input = ci.getCaptureParams(TISDocumentType.CARD);
                input.documnetType = TISDocumentType.CARD;
                break;
            case 1: // PASSPORT
                input = ci.getCaptureParams(TISDocumentType.CARD);
                input.documnetType = TISDocumentType.CARD;
                break;
            case 2: // FULL PAGE
                input = ci.getCaptureParams(TISDocumentType.FULL_PAGE);
                input.documnetType = TISDocumentType.FULL_PAGE;
                break;
            case 3: // PAYMENT
                input = ci.getCaptureParams(TISDocumentType.PAYMENT);
                input.documnetType = TISDocumentType.PAYMENT;
                break;
            case 4: // CHEQUE
                input = ci.getCaptureParams(TISDocumentType.CHECK);
                input.documnetType = TISDocumentType.CHECK;
                break;
            default:
                input = ci.getCaptureParams(TISDocumentType.CARD);
                input.documnetType = TISDocumentType.CARD;
                break;
        }

        // Set other defaults
        CameraManagerController.levlerType = LevelerType.ONE_UNIT;
        input.showGuidelinesIndicators = true;
        input.scanFrontOnly = true;
        input.uxType = TISFlowUXType.STATIC;
        input.ocrType = OCRType.OFF;
        input.useMaxResolution = true;
        input.outputBinarizedImage=false;
        input.outputGrayscaleImage=false;
        input.outputOriginalImage=false;
        input.license = tisLicenseParameters;

        return input;
    }

    private void doSubmit(View v) {

        final String DATA_URL = ((MyApplication) getApplication()).getDataUrl();

        final String CASE_REF = "case_ref";
        final String CUST_ID = "customer_id";
        final String EMP_ID = "employee_id";
        final String FILE = "file";

        try
        {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(DATA_URL);

            byte[] image = image_data.get(COLOUR_FRONT);//getTestPhotoData();

            byte[] testImg = Arrays.copyOfRange(image,0,2);
            String hexStr = Utils.bytesToHex(testImg);

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            entityBuilder.addTextBody(CASE_REF, caseRef.getText().toString());
            entityBuilder.addTextBody(CUST_ID, custId.getText().toString());
            entityBuilder.addTextBody(EMP_ID, empId.getText().toString());
            entityBuilder.addBinaryBody(FILE, image, ContentType.create("image/jpeg"), docTypeString+".jpg");

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
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
        byte[] rawImage = new byte[0];
        if (dataMap != null && dataMap.size() > 0 ) rawImage = dataMap.get(COLOUR_FRONT);
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
        if (error == null) {
            //create an 'uknown' error
            error = TISFlowErrorMessage.ERROR_GENERAL_FAIL;
        }
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

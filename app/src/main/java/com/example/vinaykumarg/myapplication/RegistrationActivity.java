package com.example.vinaykumarg.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 1222;
    private static final int PICK_IMAGE = 2;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private DatePickerEditText datePickerEditText;
    private DatePickerEditText from_dp_EditText;
    private DatePickerEditText to_dp_EditText;
    private DatePickerEditText today_dp_EditText;
    private String mCurrentPhotoPath;
    private final String TAG = this.getClass().getSimpleName();
    private AlertDialog dialog;
    private ImageView mPassportImageView;
    private ImageView mSignatureImageView;
    private View view;
    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;
    private String mSignatureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        view = (ScrollView) findViewById(R.id.activity_registration);
        Spinner religion_spinner = (Spinner) findViewById(R.id.religionspinner);
        Spinner gender_spinner = (Spinner) findViewById(R.id.genderspinner);
        Spinner marital_status_spinner = (Spinner) findViewById(R.id.maritalspinner);
        Spinner examlevel_spinner = (Spinner) findViewById(R.id.examlevelspinner);
        Spinner passing_year_spinner = (Spinner) findViewById(R.id.passyearspinner);
        Spinner board_spinner = (Spinner) findViewById(R.id.boardspinner);
        Spinner cast_spinner = (Spinner) findViewById(R.id.castspinner);
        Spinner posts_spinner = (Spinner) findViewById(R.id.postspinner);
        Spinner office_spinner = (Spinner) findViewById(R.id.offcspinner);
        Spinner payscale_spinner = (Spinner) findViewById(R.id.scalespinner);
        Spinner duties_spinner = (Spinner) findViewById(R.id.dutyspinner);
        Spinner post_name_spinner = (Spinner) findViewById(R.id.postnamespinner);
        Spinner organisation_spinner = (Spinner) findViewById(R.id.orga_name_spinner);
        Spinner duration_spinner = (Spinner) findViewById(R.id.durationspinner);
        Spinner jd_spinner = (Spinner) findViewById(R.id.jdspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.locations_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religion_spinner.setAdapter(adapter);
        gender_spinner.setAdapter(adapter);
        marital_status_spinner.setAdapter(adapter);
        examlevel_spinner.setAdapter(adapter);
        passing_year_spinner.setAdapter(adapter);
        board_spinner.setAdapter(adapter);
        cast_spinner.setAdapter(adapter);
        posts_spinner.setAdapter(adapter);
        office_spinner.setAdapter(adapter);
        payscale_spinner.setAdapter(adapter);
        duties_spinner.setAdapter(adapter);
        post_name_spinner.setAdapter(adapter);
        organisation_spinner.setAdapter(adapter);
        duration_spinner.setAdapter(adapter);
        jd_spinner.setAdapter(adapter);
        datePickerEditText = (DatePickerEditText) findViewById(R.id.datePickerEditText);
        from_dp_EditText = (DatePickerEditText) findViewById(R.id.fromdateET);
        to_dp_EditText = (DatePickerEditText) findViewById(R.id.todateET);
        today_dp_EditText = (DatePickerEditText) findViewById(R.id.dateET);
        datePickerEditText.setManager(getSupportFragmentManager());
        from_dp_EditText.setManager(getSupportFragmentManager());
        to_dp_EditText.setManager(getSupportFragmentManager());
        today_dp_EditText.setManager(getSupportFragmentManager());
        mPassportImageView = (ImageView) findViewById(R.id.passportphoto);
        mSignatureImageView = (ImageView) findViewById(R.id.signature);
        mPassportImageView.setOnClickListener(this);
        mSignatureImageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passportphoto:
                showDialog();
                break;
            case R.id.signature:
                showSignatureDialog();
                break;
        }
    }

    private void showSignatureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.signhere));
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.signaturedialog, null);
        mSignaturePad = (SignaturePad) view.findViewById(R.id.signature_pad);
        mClearButton = (Button) view.findViewById(R.id.clear_button);
        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                if (addJpgSignatureToGallery(signatureBitmap)) {
                    Toast.makeText(RegistrationActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                }
                setSignature();

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void setSignature() {
        Glide
                .with(this)
                .load(mSignatureUrl)
                .centerCrop()
                .into(mSignatureImageView);
        dialog.dismiss();
    }


    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            mSignatureUrl = photo.getAbsolutePath();
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        RegistrationActivity.this.sendBroadcast(mediaScanIntent);
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title));
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.profilepicdialog, null);
        TextView CameraButton = (TextView) view.findViewById(R.id.camera) ;
        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()){
                    Log.d(TAG,"true");
                    dispatchTakePictureIntent();
                } else{
                    requestPermission();
                }
                dismissDialog();

            }
        });
        TextView GalleryButton = (TextView) view.findViewById(R.id.gallery);
        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchImageFromGallery();
                dismissDialog();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void dismissDialog() {
        dialog.dismiss();
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

    }
    private void dispatchImageFromGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE&& resultCode== Activity.RESULT_OK) {
            Uri uri = data.getData();
            Glide
                    .with(this)
                    .load(uri)
                    .centerCrop()
                    .into(mPassportImageView);
        }
        else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Glide
                    .with(this)
                    .load(mCurrentPhotoPath)
                    .centerCrop()
                    .into(mPassportImageView);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (writeStorageAccepted && cameraAccepted) {
                        Snackbar.make(view, "Permission Granted, Now you can memory and camera.", Snackbar.LENGTH_LONG).show();
                        dispatchTakePictureIntent();
                    } else {

                        Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d(TAG, "image created");
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d(TAG, "dispatchTakePictureIntent");
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}

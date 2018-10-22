package com.grp17.dalassist.Assist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grp17.dalassist.BuildConfig;
import com.grp17.dalassist.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/*  Referred Android Documentation and following cites
  * https://www.howtobuildsoftware.com/index.php/how-do/vzQ/android-image-compression-compress-image-like-whats-app-and-facebook-using-android
   * https://www.androidpit.com/forum/627162/change-image-size-when-uploaded-to-server-from-android-device
   * https://stackoverflow.com/questions/43487288/android-how-to-convert-the-compressed-images-to-string-with-minimum-size
   * */


public class uploadimage extends AppCompatActivity {

    private static final int GALLERY_PICTURE = 1;
    private static final int CAMERA_REQUEST = 0;
    private static final String TAG = "Upload picture";
    private static final int REQUEST_WRITE_STORAGE = 1;
    File mImageFile;
    ImageView uploadImage;
    EditText description;
    Button upload;
    StorageReference mstorageReference;
    FirebaseUser firebaseuser;
    Uri selectedImage = null;
    Uri uri, thumbnailpic = null;
    Uri tempURI;
    String imageName;
    Uri download_uri;
    String uriSting;
    String picturePath;
    Bitmap thumbnail;
    Uri mediumpic = null;
    Context context;
    ProgressDialog progressDialog;
    File file, root;
    String user_id;
    double progress1, progress2, progress3, progress;
    String thumbpic, picture, thumbmed;
    DatabaseReference databaseReference, mdatabaseReference;
    private File output = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimage);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, REQUEST_WRITE_STORAGE);

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Assists");
        mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Assists");
        mstorageReference = FirebaseStorage.getInstance().getReference();


        user_id = firebaseuser.getUid();

        uploadImage = findViewById(R.id.imageView2);

        description = findViewById(R.id.image_description);
        upload = findViewById(R.id.button_upload);


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fn_Choose_Image(savedInstanceState);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImage != null) {
                    Log.e(TAG, selectedImage.toString());

                    uploadFile();

                    Log.e(TAG, selectedImage.toString());
                } else {
                    Toast.makeText(uploadimage.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void fn_Choose_Image(Bundle savedInstanceState) {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(uploadimage.this);
        myAlertDialog.setTitle("Upload Pictures ");
        myAlertDialog.setMessage("Select picture?");
        myAlertDialog.setPositiveButton("Gallery", (arg0, arg1) -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_PICTURE);
        });
//
        myAlertDialog.setNegativeButton("Camera", (arg0, arg1) -> {

            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH) + 1;
            int year = c.get(Calendar.YEAR);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minutes = c.get(Calendar.MINUTE);
            int seconds = c.get(Calendar.SECOND);
            int milliSeconds = c.get(Calendar.MILLISECOND);


            //Can cause error in uploading
            String temp = day + "-" + month + "-" + year + "-" + hour + ":" + minutes + ":" + seconds + ":" + milliSeconds + ".png";


            mImageFile = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + temp);



            tempURI = FileProvider.getUriForFile(uploadimage.this, BuildConfig.APPLICATION_ID + ".provider",
                    mImageFile);

            Log.e("tempURI: ", tempURI.toString());

            Toast.makeText(uploadimage.this, tempURI.toString(), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, tempURI);



//            myAlertDialog.setPositiveButton("Gallery", (arg0, arg1) -> {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, GALLERY_PICTURE);

            startActivityForResult(i, CAMERA_REQUEST);
        });

        myAlertDialog.show();
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = this.getContentResolver().query(contentUri, proj, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, "onActivityResult: "+data );

        if (requestCode == GALLERY_PICTURE && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            Log.e(TAG, "onActivityResult: " + selectedImage);
            String[] filePath = {MediaStore.Images.Media.DATA};
            @SuppressLint("Recycle")
            Cursor c = uploadimage.this.getContentResolver().query(selectedImage, filePath, null, null, null);
            assert c != null;
            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);
            picturePath = c.getString(columnIndex);

            uploadImage.setImageURI(data.getData());

            String file = compressImage(String.valueOf(data.getData()));


            thumbnailpic = Uri.fromFile(new File(file));

            mediumpic = Uri.fromFile(new File(file));

            Log.e(TAG, "Uploaded on image view");
            Log.e(TAG, selectedImage.toString());

        }
        try {
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

                selectedImage = Uri.fromFile(mImageFile);

                Log.e(TAG, "onActivityResult: selectedImage camera" + selectedImage);
                String file = compressImage(String.valueOf(data.getData()));
                thumbnailpic = Uri.fromFile(new File(file));

                mediumpic = Uri.fromFile(new File(file));


                Log.d("ImagePath", "Image saved to path : " + mImageFile.getAbsolutePath());

                Toast.makeText(uploadimage.this, "Path: " + mImageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                uploadImage.setImageURI(tempURI);
                tempURI = null;

            }
        }catch (Exception e){
            Log.e(TAG, "onActivityResult: "+e.getMessage() );
        }

    }


    private void uploadFile() {
        //if there is a file to upload
        if (selectedImage != null) {
            //displaying a progress dialog while upload is going on
            progressDialog = new ProgressDialog(uploadimage.this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);


            StorageReference riversRef = mstorageReference.child("Photos")
                    .child(firebaseuser.getUid()).child("User Photo")
                    .child(selectedImage.getLastPathSegment());

            riversRef.child("picture").putFile(selectedImage)
                    .addOnSuccessListener(taskSnapshot -> {
                        //if the upload is successfull
                        //hiding the progress dialog

                        download_uri = taskSnapshot.getDownloadUrl();
                        user_id = firebaseuser.getUid();
                        picture = String.valueOf(download_uri);


                        riversRef.child("thumbnail_medium").putFile(mediumpic).addOnSuccessListener(taskSnapshot2 -> {
                            thumbmed = String.valueOf(taskSnapshot2.getDownloadUrl());
                            riversRef.child("thumbnail").putFile(thumbnailpic).addOnSuccessListener(taskSnapshot1 -> {

                                progressDialog.dismiss();


                                thumbpic = String.valueOf(taskSnapshot1.getDownloadUrl());


                                Calendar c = Calendar.getInstance();
                                int day = c.get(Calendar.DAY_OF_MONTH);
                                int month = c.get(Calendar.MONTH) + 1;
                                int year = c.get(Calendar.YEAR);
                                int hour = c.get(Calendar.HOUR_OF_DAY);
                                int minutes = c.get(Calendar.MINUTE);
                                int seconds = c.get(Calendar.SECOND);
                                int milliSeconds = c.get(Calendar.MILLISECOND);


                                //Can cause error in uploading
                                imageName = day + "-" + month + "-" + year + "-" + hour + ":" + minutes + ":" + seconds + ":" + milliSeconds + "";
                                Log.e("Camera", imageName);


                                DatabaseReference currentuser_db = databaseReference.child("User Pictures");
                                currentuser_db.child(imageName).orderByPriority();
                                DatabaseReference currentuser = currentuser_db.child(imageName);
                                currentuser.child("pic").setValue(picture);
                                currentuser.child("picName").setValue(imageName);
                                currentuser.child("userName").setValue(firebaseuser.getDisplayName());
                                currentuser.child("user_id").setValue(user_id);
                                currentuser.child("thumbnail_pic").setValue(thumbpic);
                                currentuser.child("medium").setValue(thumbmed);
                                currentuser.child("description").setValue(description.getText().toString());


                                DatabaseReference user_db = mdatabaseReference.child("users").child(user_id).child("User Pictures");
                                user_db.child(imageName).orderByPriority();
                                DatabaseReference user = user_db.child(imageName);
                                user.child("pic").setValue(picture);
                                user.child("picName").setValue(imageName);
                                user.child("userName").setValue(firebaseuser.getDisplayName());
                                user.child("user_id").setValue(user_id);
                                user.child("medium").setValue(thumbmed);
                                user.child("thumbnail_pic").setValue(thumbpic);
                                user.child("description").setValue(description.getText().toString());


                                //and displaying a success toast
                                Toast.makeText(uploadimage.this, "File Uploaded ", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(uploadimage.this, Assists.class));


                            }).addOnProgressListener(taskSnapshot1 -> {
                                //calculating progress percentage
                                progress3 = (100.0 * taskSnapshot1.getBytesTransferred()) / taskSnapshot1.getTotalByteCount();
                                Log.e("Process3", String.valueOf(progress3));

                                progress = (progress1 + progress2 + progress3) / 3;

                                Log.e("Process", String.valueOf(progress));

                                //displaying percentage in progress dialog
                                progressDialog.setMessage("Uploaded " +  "...");

                            });

                        }).addOnProgressListener(taskSnapshot2 -> {
                            //calculating progress percentage
                            progress2 = (100.0 * taskSnapshot2.getBytesTransferred()) / taskSnapshot2.getTotalByteCount();
                            Log.e("Process2", String.valueOf(progress2));
                        });
                    })
                    .addOnFailureListener(exception -> {
                        //if the upload is not successful
                        //hiding the progress dialog
                        progressDialog.dismiss();

                        //and displaying error message
                        Toast.makeText(uploadimage.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        //calculating progress percentage
                        progress1 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Log.e("Process1", String.valueOf(progress1));

//                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress1) + "%...");
                    });

        }
        //if there is not any file
        else {
            //you can display an error toast
            Toast.makeText(uploadimage.this, "File Upload Failed", Toast.LENGTH_SHORT).show();
        }
    }

    //Compression code

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();


        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);


//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.e("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.e("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.e("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.e("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("name ======= ", filename);
        return filename;

    }

    public String getFilename() {
        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        file = new File(root.toString() + "/Moments");

        if (!file.exists()) {
            file.mkdirs();
        }
        uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        Log.e("name location ", uriSting);
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        @SuppressLint("Recycle")
        Cursor cursor = uploadimage.this.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        Log.e("SIze ", String.valueOf(inSampleSize));
        return inSampleSize;

    }
}


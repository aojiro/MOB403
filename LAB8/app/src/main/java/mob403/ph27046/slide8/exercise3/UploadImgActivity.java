package mob403.ph27046.slide8.exercise3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import mob403.ph27046.slide8.R;

public class UploadImgActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 234;

    //ImageView
    private ImageView imageView;
    //a Uri object to store file path
    private Uri filePath;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        imageView = findViewById(R.id.imageView);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            StorageReference riversRef =
                    mStorageRef.child("images/pic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot
                                                      taskSnapshot) {
                            //if the upload is successfully
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            //and displaying a success toast TRANG 62 2
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            //and displaying error message
                            Toast.makeText(getApplicationContext(),
                                    exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new
                                                   OnProgressListener<UploadTask.TaskSnapshot>() {
                                                       @Override
                                                       public void onProgress(UploadTask.TaskSnapshot
                                                                                      taskSnapshot) {
                                                           //calculating progress percentage
                                                           double progress = (100.0 *
                                                                   taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                           //displaying percentage in progress dialog
                                                           progressDialog.setMessage("Uploaded " + ((int)
                                                                   progress) + "%...");
                                                       }
                                                   });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap =
                        MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void upload(View view) {
        uploadFile();
    }

    public void choose(View view) {
        showFileChooser();
    }
}
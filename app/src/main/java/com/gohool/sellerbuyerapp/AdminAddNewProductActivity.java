package com.gohool.sellerbuyerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public
class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName, Description, Price , Pname,saveCurrentDate,savecurrentTime;
    private Button AddNewProductButton;
    private EditText InputProductName,InputProductDescription,InputProductPrice;
    private ImageView InputProductImage ;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String ProductRandomKey, downloadImageUrl;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_admin_add_new_product );


        CategoryName = getIntent ().getExtras ().get ( "category" ).toString ();
        ProductImageRef = FirebaseStorage.getInstance ().getReference ().child ( "Product Images" );
        ProductsRef = FirebaseDatabase.getInstance ().getReference ().child ( "Products" );

        AddNewProductButton =(Button)findViewById ( R.id.add_new_product );
        InputProductImage=(ImageView)findViewById ( R.id.select_product_image );
        InputProductName=(EditText)findViewById ( R.id.product_name );
        InputProductDescription=(EditText)findViewById ( R.id.product_description );
        InputProductPrice=(EditText)findViewById ( R.id.product_price );
        loadingBar= new ProgressDialog ( this );

        //Toast.makeText ( this,CategoryName,Toast.LENGTH_SHORT ).show ();
        InputProductImage.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {

                OpenGallery();
            }
        } );

        AddNewProductButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                ValidateProductData();
            }
        } );

    }

    private
    void ValidateProductData() {

        Description = InputProductDescription.getText ().toString ();
        Price = InputProductPrice.getText ().toString ();
        Pname = InputProductName.getText ().toString ();


        if (ImageUri == null)
        {
            Toast.makeText ( this,"Product image is mandatory...", Toast.LENGTH_SHORT ).show ();
        }
        else if (TextUtils.isEmpty ( Description ))
        {
            Toast.makeText ( this,"Please write product description",Toast.LENGTH_SHORT ).show ();
        }
        else if (TextUtils.isEmpty ( Price ))
        {
            Toast.makeText ( this,"Please write product price",Toast.LENGTH_SHORT ).show ();
        }
        else if (TextUtils.isEmpty ( Pname ))
        {
            Toast.makeText ( this,"Please write product name",Toast.LENGTH_SHORT ).show ();
        }
        else
        {
            StoreProductInformation();
        }
    }



    private
    void OpenGallery() {

        Intent galleryIntent = new Intent (  );
        galleryIntent.setAction ( Intent.ACTION_GET_CONTENT );
        galleryIntent.setType ( "image/*" );
        startActivityForResult ( galleryIntent, GalleryPick );


    }

    @Override
    protected
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );

        if (requestCode==GalleryPick && requestCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData ();
            InputProductImage.setImageURI ( ImageUri );
        }

    }

    private
    void StoreProductInformation() {

        loadingBar.setTitle ( "Adding new Product" );
        loadingBar.setMessage ( "Please wait while we are adding the new product" );
        loadingBar.setCanceledOnTouchOutside ( false );
        loadingBar.show ();

        Calendar calender = Calendar.getInstance ();

        SimpleDateFormat currentDate = new SimpleDateFormat ( "MM,dd,yyyy" );
        saveCurrentDate = currentDate.format ( calender.getTime () );

        SimpleDateFormat currentTime = new SimpleDateFormat ( "HH:mm:ss" );
        savecurrentTime = currentTime.format ( calender.getTime () );

        ProductRandomKey = saveCurrentDate + savecurrentTime;

        final StorageReference filePath = ProductImageRef.child ( ImageUri.getLastPathSegment () + ProductRandomKey );

        final UploadTask uploadTask = filePath.putFile ( ImageUri );

        uploadTask.addOnFailureListener ( new OnFailureListener () {
            @Override
            public
            void onFailure(@NonNull Exception e) {
                String message = e.toString ();
                Toast.makeText ( AdminAddNewProductActivity.this,"Error: "+ message ,Toast.LENGTH_SHORT ).show ();
                loadingBar.dismiss ();
            }
        } ).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public
            void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Toast.makeText ( AdminAddNewProductActivity.this,"Image uploaded Successfully...",Toast.LENGTH_SHORT ).show ();

                Task<Uri> uriTask = uploadTask.continueWithTask ( new Continuation<UploadTask.TaskSnapshot, Task<Uri>> () {
                    @Override
                    public
                    Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful ())
                        {
                            throw  task.getException ();
                        }
                        downloadImageUrl = filePath.getDownloadUrl ().toString ();
                        return filePath.getDownloadUrl ();
                    }
                } ).addOnCompleteListener ( new OnCompleteListener<Uri> () {
                    @Override
                    public
                    void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful ()) {

                            downloadImageUrl = task.getResult ().toString ();

                            Toast.makeText ( AdminAddNewProductActivity.this, " got Product image Uri Successfully...", Toast.LENGTH_SHORT ).show ();

                            SaveProductInfoDatabse();

                        }

                    }
                } );

            }
        } );

    }

    private
    void SaveProductInfoDatabse() {
        HashMap<String, Object> productMap = new HashMap<> (  );
        productMap.put ( "pid",ProductRandomKey );
        productMap.put ( "date",saveCurrentDate );
        productMap.put ( "time",savecurrentTime );
        productMap.put ( "description",Description );
        productMap.put ( "image",downloadImageUrl );
        productMap.put ( "category",CategoryName );
        productMap.put ( "price",Price );
        productMap.put ( "pname",Pname );

        ProductsRef.child ( ProductRandomKey ).updateChildren ( productMap ).addOnCompleteListener ( new OnCompleteListener<Void> () {
            @Override
            public
            void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful ())
                {
                    Intent intent = new Intent ( AdminAddNewProductActivity.this,AdminCategoryActivity.class );
                    startActivity ( intent );


                    loadingBar.dismiss ();
                    Toast.makeText ( AdminAddNewProductActivity.this,"Product is added successfully ",Toast.LENGTH_SHORT ).show ();
                }
                else
                {
                    loadingBar.dismiss ();
                    String message = task.getException ().toString ();
                    Toast.makeText ( AdminAddNewProductActivity.this,"Error: " + message ,Toast.LENGTH_SHORT ).show ();

                }
            }
        } );
    }
}

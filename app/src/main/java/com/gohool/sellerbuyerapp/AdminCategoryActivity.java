package com.gohool.sellerbuyerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public
class AdminCategoryActivity extends AppCompatActivity {

    private
    ImageView tshirts, sportsTshirts,femaleDresses, sweaters;
    private ImageView glasses,hatscaps,walletsBagPurse,shoes;
    private ImageView headPhonesHandsfree,Laptops,Watches,mobilePhones;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_admin_category );


        tshirts=(ImageView )findViewById ( R.id.t_shirts );
        sportsTshirts=(ImageView )findViewById ( R.id.sports_t_shirts );
        femaleDresses=(ImageView )findViewById ( R.id.female_dress );
        sweaters=(ImageView )findViewById ( R.id.sweaters );

        hatscaps=(ImageView )findViewById ( R.id.hats_caps );
        shoes=(ImageView )findViewById ( R.id.shoe_sandals );
        glasses=(ImageView )findViewById ( R.id.glasses );
        walletsBagPurse=(ImageView )findViewById ( R.id.Bags_wallets_purse );

        headPhonesHandsfree=(ImageView )findViewById ( R.id.headphones_earphones );
        Laptops=(ImageView )findViewById ( R.id.laptops_mini_laptops );
        Watches=(ImageView )findViewById ( R.id.watches );
        mobilePhones=(ImageView )findViewById ( R.id.mobile_phones  );

        tshirts.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","tshirts" );
                startActivity ( intent );
            }
        } );

        sportsTshirts.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","sportsTshirts" );
                startActivity ( intent );
            }
        } );

        femaleDresses.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","femaleDresses" );
                startActivity ( intent );
            }
        } );

        sweaters.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","sweaters" );
                startActivity ( intent );
            }
        } );

        glasses.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","glasses" );
                startActivity ( intent );
            }
        } );

        hatscaps.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","hatscaps" );
                startActivity ( intent );
            }
        } );

        walletsBagPurse.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","walletsBagPurse" );
                startActivity ( intent );
            }
        } );

        shoes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","shoes" );
                startActivity ( intent );
            }
        } );

        mobilePhones.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","mobilePhones" );
                startActivity ( intent );
            }
        } );

        headPhonesHandsfree.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","headPhonesHandsfree" );
                startActivity ( intent );
            }
        } );

        Watches.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","Watches" );
                startActivity ( intent );
            }
        } );

        Laptops.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent ( AdminCategoryActivity.this,AdminAddNewProductActivity.class );
                intent.putExtra ( "category","Laptops" );
                startActivity ( intent );
            }
        } );

    }
}

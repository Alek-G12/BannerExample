package com.example.g12.bannerexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.native_ad_unit_id))
                .forCustomTemplateAd(getString(R.string.native_template_id), new NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener() {
                    @Override
                    public void onCustomTemplateAdLoaded(NativeCustomTemplateAd nativeCustomTemplateAd) {
                        //Display and record impression
                        ViewGroup adView = findViewById(R.id.adView);
                        displayCustomTemplateAd(adView, nativeCustomTemplateAd);
                    }
                }, null).build();

        adLoader.loadAd(new PublisherAdRequest.Builder().build());
    }

    private void displayCustomTemplateAd(ViewGroup parent, final NativeCustomTemplateAd ad){
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View adView = layoutInflater.inflate(R.layout.custom_template_ad, parent);

        //Show custom template
        TextView headline = findViewById(R.id.headline);
        TextView caption = findViewById(R.id.caption);
        ImageView mainImage = findViewById(R.id.mainImage);
        headline.setText(ad.getText("Headline"));
        caption.setText(ad.getText("Caption"));
        mainImage.setImageDrawable(ad.getImage("MainImage").getDrawable());

        //Record Impression
        ad.recordImpression();

        //Handle clicks on image
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.performClick("MainImage");
            }
        });
    }
}

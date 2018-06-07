package com.example.micha.usabilty;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.jsoup.parser.*;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import org.json.simple.parser.JSONParser;
import org.jsoup.nodes.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import java.io.IOException;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;



/*SPEECH:
https://www.androidhive.info/2014/07/android-speech-to-text-tutorial/
 *Parse: https://stackoverflow.com/questions/40162503/java-jsoup-google-image-search-result-parsing
 *  Sources:
 * Flag German:
 * https://pixabay.com/de/flagge-deutschland-flagge-1060305/
 *
 *Flag British:
 * https://www.publicdomainpictures.net/en/view-image.php?image=136202&picture=british-flag
 *
 * Flag Spanish:
 *https://wallpapercave.com/spanish-flag-wallpaper
 *
 * Flag France:
 * https://wall.alphacoders.com/by_sub_category.php?id=210400&name=Flag+Of+France+Wallpapers
 *
 * Error:
 * https://blog.sqlauthority.com/i/a/errorstop.png
 *
 * */

public class MainActivity extends Activity implements View.OnTouchListener {
    ImageButton FlagButton1;
    ImageButton FlagButton2;
    ImageButton FlagButton3;
    ImageButton FlagButton4;


    int buttonColor = Color.DKGRAY;
    int buttonColorPressed = Color.GRAY;


    final int semiTransparentGrey = Color.argb(155, 185, 185, 185);


    private final int REQ_CODE_SPEECH_INPUT = 100;
    //eventuell 2d array mit optionalen URLS, dann einfach weiterhüpfen falls lade fehler

    List<String> ImageURLS = new ArrayList<String>(Arrays.asList(
            "",
            "",
            "",
            "",
            "",
            "",
            ""));

    ImageView BigView;
    ImageView small1;
    ImageView small2;
    ImageView small3;
    ImageView small4;
    ImageView small5;
    ImageView small6;
    String language = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
      //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
      //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);


        ImageButton FlagButton1 = (ImageButton) findViewById(R.id.image_flag_1);
        FlagButton1.setOnTouchListener(this);
        this.FlagButton1 = FlagButton1;

        ImageButton FlagButton2 = (ImageButton) findViewById(R.id.image_flag_2);
        FlagButton2.setOnTouchListener(this);
        this.FlagButton2 = FlagButton2;

        ImageButton FlagButton3 = (ImageButton) findViewById(R.id.image_flag_3);
        FlagButton3.setOnTouchListener(this);
        this.FlagButton3 = FlagButton3;

        ImageButton FlagButton4 = (ImageButton) findViewById(R.id.image_flag_4);
        FlagButton4.setOnTouchListener(this);
        this.FlagButton4 = FlagButton4;

        //   FlagButton1.setImageResource(R.drawable.flag_ger);
        // FlagButton2.setImageResource(R.drawable.flag_bri);
        //     FlagButton3.setImageResource(R.drawable.flag_fra);
        //    FlagButton4.setImageResource(R.drawable.flag_spa);


        FlagButton1.setBackgroundColor(buttonColor);
        FlagButton2.setBackgroundColor(buttonColor);
        FlagButton3.setBackgroundColor(buttonColor);
        FlagButton4.setBackgroundColor(buttonColor);

        BigView = (ImageView) findViewById(R.id.image_big);
        small1 = (ImageView) findViewById(R.id.image_small_1);
        small2 = (ImageView) findViewById(R.id.image_small_2);
        small3 = (ImageView) findViewById(R.id.image_small_3);
        small4 = (ImageView) findViewById(R.id.image_small_4);
        small5 = (ImageView) findViewById(R.id.image_small_5);
        small6 = (ImageView) findViewById(R.id.image_small_6);
        resetImageViews();
        //Picasso.with(this).load("https://www.mehrcontainerfuerdeutschland.de/wp-content/uploads/sites/9/2017/02/Weniger-Schiffe-unter-deutscher-Flagge_Symbolfoto-c-pixabay.com_.jpg").into(FlagButton1);

        //Picasso.get().load("https://www.mehrcontainerfuerdeutschland.de/wp-content/uploads/sites/9/2017/02/Weniger-Schiffe-unter-deutscher-Flagge_Symbolfoto-c-pixabay.com_.jpg").into(FlagButton1);
    }

    public void showImages() {
        if (ImageURLS.get(0) != "") {
            Picasso.get()
                    .load(ImageURLS.get(0))

                    .error(R.drawable.errorstop)
                    .into(BigView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Image LOAD Error", e.getMessage());

                        }
                    });
        }
        if (ImageURLS.get(1) != "") {
            Picasso.get()
                    .load(ImageURLS.get(1))

                    .error(R.drawable.flag_bri)
                    .into(small1, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Image LOAD Error", e.getMessage());

                        }
                    });
        }
        if (ImageURLS.get(2) != "") {
            Picasso.get()
                    .load(ImageURLS.get(2))

                    .error(R.drawable.flag_bri)
                    .into(small2, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Image LOAD Error", e.getMessage());

                        }
                    });
        }
        if (ImageURLS.get(3) != "") {
            Picasso.get()
                    .load(ImageURLS.get(3))

                    .error(R.drawable.flag_bri)
                    .into(small3, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Image LOAD Error", e.getMessage());

                        }
                    });
        }
        if (ImageURLS.get(4) != "") {
            Picasso.get()
                    .load(ImageURLS.get(4))

                    .error(R.drawable.flag_bri)
                    .into(small4, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Image LOAD Error", e.getMessage());

                        }
                    });
        }
        if (ImageURLS.get(5) != "") {
            Picasso.get()
                    .load(ImageURLS.get(5))

                    .error(R.drawable.flag_bri)
                    .into(small5, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Image LOAD Error", e.getMessage());

                        }
                    });
        }
        if (ImageURLS.get(6) != "") {
            Picasso.get()
                    .load(ImageURLS.get(6))

                    .error(R.drawable.flag_bri)
                    .into(small6, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Image LOAD Error", e.getMessage());

                        }
                    });
        }
    }

    public void addImageURL(String newURL) {
        ImageURLS.add(0, newURL);
        ImageURLS.remove(ImageURLS.size() - 1);
        showImages();
    }

    public void searchGoodURL(List<String> resultUrls) {
        String imageURL = "";
        for (String imageUrl : resultUrls) {
            if (imageUrl.endsWith(".jpg")) {
                imageURL = imageUrl;
                break;
            }
            Log.d("Image URL", imageUrl);

        }

        addImageURL(imageURL);
    }

    public void getImageURL(String SearchTerm) {
        new RetrieveFeedTask().execute(SearchTerm);


    }

    public void resetURLList() {
        ImageURLS = new ArrayList<String>(Arrays.asList(
                "",
                "",
                "",
                "",
                "",
                "",
                ""));
    }

    public void resetImageViews() {
        BigView.setImageResource(android.R.color.transparent);
        small1.setImageResource(android.R.color.transparent);
        small2.setImageResource(android.R.color.transparent);
        small3.setImageResource(android.R.color.transparent);
        small4.setImageResource(android.R.color.transparent);
        small5.setImageResource(android.R.color.transparent);
        small6.setImageResource(android.R.color.transparent);
    }

    public void startRegognition(String lang) {
        if (lang == "GER") {
            language = "de_DE";
        }
        if (lang == "ENG") {
            language = "en_US";
        }
        if (lang == "FRA") {
            language = "fr_FR";
        }
        if (lang == "ESP") {
            language = "es_ES";
        }
        promptSpeechInput();

    }

    private void promptSpeechInput() {


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language);
        intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, language);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, language);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, language);
        intent.putExtra(RecognizerIntent.EXTRA_RESULTS, language);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("SPEECH REGOGNICED ", result.get(0));

                    String[] arr = result.get(0).split(" ");

                    for (String ss : arr) {
                        getImageURL(ss);
                    }

                }
                break;
            }

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                resetURLList();
                resetImageViews();
                switch (v.getId()) {
                    case R.id.image_flag_1: {
                        Toast.makeText(getApplicationContext(), "Deutsch", Toast.LENGTH_SHORT).show();
                     //   FlagButton1.setBackgroundColor(buttonColorPressed);

                        FlagButton1.setColorFilter(semiTransparentGrey, PorterDuff.Mode.SRC_ATOP);


                        startRegognition("GER");


                        break;
                    }
                    case R.id.image_flag_2: {
                        Toast.makeText(getApplicationContext(), "English", Toast.LENGTH_SHORT).show();
                      //  FlagButton2.setBackgroundColor(buttonColorPressed);
                        FlagButton2.setColorFilter(semiTransparentGrey, PorterDuff.Mode.SRC_ATOP);
                        startRegognition("ENG");
                        break;
                    }
                    case R.id.image_flag_3: {
                        Toast.makeText(getApplicationContext(), "Français", Toast.LENGTH_SHORT).show();
                    //    FlagButton3.setBackgroundColor(buttonColorPressed);
                        FlagButton3.setColorFilter(semiTransparentGrey, PorterDuff.Mode.SRC_ATOP);
                        startRegognition("FRA");
                        break;
                    }
                    case R.id.image_flag_4: {
                        Toast.makeText(getApplicationContext(), "Español", Toast.LENGTH_SHORT).show();
                    //    FlagButton4.setBackgroundColor(buttonColorPressed);
                        FlagButton4.setColorFilter(semiTransparentGrey, PorterDuff.Mode.SRC_ATOP);
                        startRegognition("ESP");
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.image_flag_1: {

                        FlagButton1.setColorFilter(null);
                        break;
                    }
                    case R.id.image_flag_2: {
                        FlagButton2.setColorFilter(null);
                        break;
                    }
                    case R.id.image_flag_3: {
                        FlagButton3.setColorFilter(null);
                        break;
                    }
                    case R.id.image_flag_4: {
                        FlagButton4.setColorFilter(null);
                        break;
                    }
                }
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }


    public void gerFlagClicked(boolean is) {

    }

    public void engFlagClicked(boolean is) {

    }

    public void fraFlagClicked(boolean is) {

    }

    public void espFlagClicked(boolean is) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, List<String>> {

        private Exception exception;

        protected List<String> doInBackground(String... searchTerms) {
            try {
                String searchTerm = searchTerms[0];
                List<String> resultUrls = new ArrayList<String>();
                // can only grab first 100 results
                String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
                String url = "https://www.google.com/search?num=1&site=imghp&tbm=isch&source=hp&q=" + searchTerm + "&gws_rd=cr";


                try {
                    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.google.com/").get();

                    Elements elements = doc.select("div.rg_meta");

                    JSONObject jsonObject;
                    for (Element element : elements) {
                        if (element.childNodeSize() > 0) {
                            jsonObject = (JSONObject) new JSONParser().parse(element.childNode(0).toString());
                            resultUrls.add((String) jsonObject.get("ou"));
                        }
                    }

                    Log.d("URLS", "number of results: " + resultUrls.size());

                    //  for (String imageUrl : resultUrls) {
                    //    Log.d("Image LOAD Error", imageUrl);

                    //  }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
                return resultUrls;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(List<String> resultUrls) {
            searchGoodURL(resultUrls);
        }
    }

}

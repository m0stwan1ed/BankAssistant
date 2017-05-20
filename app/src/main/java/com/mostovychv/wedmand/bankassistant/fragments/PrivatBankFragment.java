package com.mostovychv.wedmand.bankassistant.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mostovychv.wedmand.bankassistant.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;


public class PrivatBankFragment extends android.app.Fragment
{
    class PBCurrency
    {
        public String ccy;
        public String base_ccy;
        public String buy;
        public String sale;
    }

    TextView textViewCurrency1, textViewCurrency2, textViewCurrency3, textViewCurrency4;
    TextView textViewCurrency1Buy, textViewCurrency2Buy, textViewCurrency3Buy, textViewCurrency4Buy;
    TextView textViewCurrency1Sell, textViewCurrency2Sell, textViewCurrency3Sell, textViewCurrency4Sell;

    TextView textViewCardsCurrency1, textViewCardsCurrency2, textViewCardsCurrency3, textViewCardsCurrency4;
    TextView textViewCardsCurrency1Buy, textViewCardsCurrency2Buy, textViewCardsCurrency3Buy, textViewCardsCurrency4Buy;
    TextView textViewCardsCurrency1Sell, textViewCardsCurrency2Sell, textViewCardsCurrency3Sell, textViewCardsCurrency4Sell;

    ArrayList<PBCurrency> pbCurrency = new ArrayList<>();
    ArrayList<PBCurrency> pbCurrencyCards = new ArrayList<>();

    class PobierzDane extends AsyncTask<Void, Void, Void>
    {
        private String url;
        private String url2;
        private String answer = "";
        private String answer2 = "";
        private Gson gson = new Gson();		// GSON library object

        PobierzDane(String url, String url2)
        {
            this.url = url;
            this.url2 = url2;
        }

        protected void onPreExecute()
        {

        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                URL u1 = new URL(url);
                URLConnection conn = u1.openConnection();
                BufferedInputStream in = new BufferedInputStream(conn.getInputStream());


                byte[] contents = new byte[1024];
                int bytesRead;

                while((bytesRead = in.read(contents)) != -1)
                {
                    answer += new String(contents, 0, bytesRead);
                }

                URL u2 = new URL(url2);
                URLConnection conn2 = u1.openConnection();
                BufferedInputStream in2 = new BufferedInputStream(conn2.getInputStream());

                while((bytesRead = in2.read(contents)) != -1)
                {
                    answer2 += new String(contents, 0, bytesRead);
                }

            }

            catch (IOException e) {
                // TODO: handle exception
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            pbCurrency = new ArrayList<>(Arrays.asList(gson.fromJson(answer, PBCurrency[].class)));

            textViewCurrency1.setText(pbCurrency.get(0).base_ccy + " - " +pbCurrency.get(0).ccy);
            textViewCurrency2.setText(pbCurrency.get(1).base_ccy + " - " +pbCurrency.get(1).ccy);
            textViewCurrency3.setText(pbCurrency.get(2).base_ccy + " - " +pbCurrency.get(2).ccy);
            textViewCurrency4.setText(pbCurrency.get(3).base_ccy + " - " +pbCurrency.get(3).ccy);
            textViewCurrency1Buy.setText(pbCurrency.get(0).buy);
            textViewCurrency2Buy.setText(pbCurrency.get(1).buy);
            textViewCurrency3Buy.setText(pbCurrency.get(2).buy);
            textViewCurrency4Buy.setText(pbCurrency.get(3).buy);
            textViewCurrency1Sell.setText(pbCurrency.get(0).sale);
            textViewCurrency2Sell.setText(pbCurrency.get(1).sale);
            textViewCurrency3Sell.setText(pbCurrency.get(2).sale);
            textViewCurrency4Sell.setText(pbCurrency.get(3).sale);

            pbCurrencyCards = new ArrayList<>(Arrays.asList(gson.fromJson(answer2, PBCurrency[].class)));

            textViewCardsCurrency1.setText(pbCurrencyCards.get(0).base_ccy + " - " +pbCurrencyCards.get(0).ccy);
            textViewCardsCurrency2.setText(pbCurrencyCards.get(1).base_ccy + " - " +pbCurrencyCards.get(1).ccy);
            textViewCardsCurrency3.setText(pbCurrencyCards.get(2).base_ccy + " - " +pbCurrencyCards.get(2).ccy);
            textViewCardsCurrency4.setText(pbCurrencyCards.get(3).base_ccy + " - " +pbCurrencyCards.get(3).ccy);
            textViewCardsCurrency1Buy.setText(pbCurrencyCards.get(0).buy);
            textViewCardsCurrency2Buy.setText(pbCurrencyCards.get(1).buy);
            textViewCardsCurrency3Buy.setText(pbCurrencyCards.get(2).buy);
            textViewCardsCurrency4Buy.setText(pbCurrencyCards.get(3).buy);
            textViewCardsCurrency1Sell.setText(pbCurrencyCards.get(0).sale);
            textViewCardsCurrency2Sell.setText(pbCurrencyCards.get(1).sale);
            textViewCardsCurrency3Sell.setText(pbCurrencyCards.get(2).sale);
            textViewCardsCurrency4Sell.setText(pbCurrencyCards.get(3).sale);

            super.onPostExecute(result);

        }
    }

    public PrivatBankFragment()
    {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ConstraintLayout cl = (ConstraintLayout)inflater.inflate(R.layout.fragment_privat_bank, container, false);

        textViewCurrency1 = (TextView)cl.findViewById(R.id.textViewCurrency1);
        textViewCurrency2 = (TextView)cl.findViewById(R.id.textViewCurrency2);
        textViewCurrency3 = (TextView)cl.findViewById(R.id.textViewCurrency3);
        textViewCurrency4 = (TextView)cl.findViewById(R.id.textViewCurrency4);
        textViewCurrency1Buy = (TextView)cl.findViewById(R.id.textViewCurrency1Buy);
        textViewCurrency2Buy = (TextView)cl.findViewById(R.id.textViewCurrency2Buy);
        textViewCurrency3Buy = (TextView)cl.findViewById(R.id.textViewCurrency3Buy);
        textViewCurrency4Buy = (TextView)cl.findViewById(R.id.textViewCurrency4Buy);
        textViewCurrency1Sell = (TextView)cl.findViewById(R.id.textViewCurrency1Sell);
        textViewCurrency2Sell = (TextView)cl.findViewById(R.id.textViewCurrency2Sell);
        textViewCurrency3Sell = (TextView)cl.findViewById(R.id.textViewCurrency3Sell);
        textViewCurrency4Sell = (TextView)cl.findViewById(R.id.textViewCurrency4Sell);

        textViewCardsCurrency1 = (TextView)cl.findViewById(R.id.textViewCardsCurrency1);
        textViewCardsCurrency2 = (TextView)cl.findViewById(R.id.textViewCardsCurrency2);
        textViewCardsCurrency3 = (TextView)cl.findViewById(R.id.textViewCardsCurrency3);
        textViewCardsCurrency4 = (TextView)cl.findViewById(R.id.textViewCardsCurrency4);
        textViewCardsCurrency1Buy = (TextView)cl.findViewById(R.id.textViewCardsCurrency1Buy);
        textViewCardsCurrency2Buy = (TextView)cl.findViewById(R.id.textViewCardsCurrency2Buy);
        textViewCardsCurrency3Buy = (TextView)cl.findViewById(R.id.textViewCardsCurrency3Buy);
        textViewCardsCurrency4Buy = (TextView)cl.findViewById(R.id.textViewCardsCurrency4Buy);
        textViewCardsCurrency1Sell = (TextView)cl.findViewById(R.id.textViewCardsCurrency1Sell);
        textViewCardsCurrency2Sell = (TextView)cl.findViewById(R.id.textViewCardsCurrency2Sell);
        textViewCardsCurrency3Sell = (TextView)cl.findViewById(R.id.textViewCardsCurrency3Sell);
        textViewCardsCurrency4Sell = (TextView)cl.findViewById(R.id.textViewCardsCurrency4Sell);

        PobierzDane serwer = new PobierzDane("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11", "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11");
        serwer.execute();
        return cl;
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}

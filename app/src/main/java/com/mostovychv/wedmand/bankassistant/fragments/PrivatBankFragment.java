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

    ArrayList<PBCurrency> pbCurrency = new ArrayList<>();

    class PobierzDane extends AsyncTask<Void, Void, Void>
    {
        private String url;//Link do danych
        private String answer = "";//Odpowiedź serwera
        private Gson gson = new Gson();		// GSON library object

        PobierzDane(String url)
        {
            this.url = url;
        }//Konstruktor klasy

        protected void onPreExecute()//Metoda klasy AsyncTask, która wykonuje się przed uruchamieniem wątku
        {

        }

        @Override
        protected Void doInBackground(Void... params)//Metoda klasyAsyncTask, która wykonuje się w osobnym wątku
        {
            try
            {
                URL u1 = new URL(url);//Zapisujemy link w postaci URL
                URLConnection conn = u1.openConnection();//Tworzymy nowe połączenie
                BufferedInputStream in = new BufferedInputStream(conn.getInputStream());//Otrzymujemy odpowiedź od serwera

                //------Ten kod pozwala skonwertować BufferedInputStream w String
                byte[] contents = new byte[1024];
                int bytesRead;

                while((bytesRead = in.read(contents)) != -1)
                {
                    answer += new String(contents, 0, bytesRead);
                }
                //------
            }

            catch (IOException e) {
                // TODO: handle exception
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            System.out.println(answer);
            pbCurrency = new ArrayList<>(Arrays.asList(gson.fromJson(answer, PBCurrency[].class)));
            System.out.println(pbCurrency.size());
            for(int i = 0; i< pbCurrency.size(); i++)
            {
                System.out.println(pbCurrency.get(i).base_ccy + " " + pbCurrency.get(i).ccy + " " + pbCurrency.get(i).buy + " " + pbCurrency.get(i).sale);
            }
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

        PobierzDane serwer = new PobierzDane("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11");
        serwer.execute();
        return cl;

    }


    @Override
    public void onDetach()
    {
        super.onDetach();
    }
}

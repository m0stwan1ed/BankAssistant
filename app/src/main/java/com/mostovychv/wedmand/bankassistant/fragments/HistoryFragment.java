package com.mostovychv.wedmand.bankassistant.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mostovychv.wedmand.bankassistant.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HistoryFragment extends android.app.Fragment implements View.OnClickListener
{
    public class ExchangeRate
    {
        public String baseCurrency;
        public String currency;
        public String saleRateNB;
        public String purchaseRateNB;
        public String saleRate;
        public String purchaseRate;
    }

    public class HistoricalData
    {
        public String date;
        public String bank;
        public int baseCurrency;
        public String baseCurrencyLit;
        public List<ExchangeRate> exchangeRate;
    }

    TextView textViewCurrency1, textViewCurrency1Buy, textViewCurrency1Sell;
    Switch switchPB;

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);

    TextView textViewSelectDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    class PobierzDane extends AsyncTask<Void, Void, Void>
    {
        private String url;
        private String answer = "";
        private Gson gson = new Gson();		// GSON library object

        PobierzDane(String url)
        {
            this.url = url;
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
            HistoricalData data = new Gson().fromJson(answer, HistoricalData.class);
            System.out.println(data.exchangeRate.size());
            if(data.exchangeRate.size() == 0)
            {

            }
            else {
                if (switchPB.isChecked()) {
                    String currency1 = "";
                    for (int i = 0; i < data.exchangeRate.size(); i++) {
                        if (data.exchangeRate.get(i).purchaseRateNB != null) {
                            currency1 = currency1 + data.exchangeRate.get(i).baseCurrency + " - " + data.exchangeRate.get(i).currency + "\n";
                        }
                    }
                    textViewCurrency1.setText(currency1);

                    String currency1Buy = "";
                    for (int i = 0; i < data.exchangeRate.size(); i++) {
                        if (data.exchangeRate.get(i).purchaseRateNB != null) {
                            currency1Buy = currency1Buy + data.exchangeRate.get(i).purchaseRateNB + "\n";
                        }
                    }
                    textViewCurrency1Buy.setText(currency1Buy);

                    String currency1Sell = "";

                    for (int i = 0; i < data.exchangeRate.size(); i++) {
                        if (data.exchangeRate.get(i).purchaseRateNB != null) {
                            currency1Sell = currency1Sell + data.exchangeRate.get(i).saleRateNB + "\n";
                        }
                    }
                    textViewCurrency1Sell.setText(currency1Sell);

                } else {
                    String currency1 = "";
                    for (int i = 0; i < data.exchangeRate.size(); i++) {
                        if (data.exchangeRate.get(i).purchaseRate != null) {
                            currency1 = currency1 + data.exchangeRate.get(i).baseCurrency + " - " + data.exchangeRate.get(i).currency + "\n";
                        }
                    }
                    textViewCurrency1.setText(currency1);

                    String currency1Buy = "";
                    for (int i = 0; i < data.exchangeRate.size(); i++) {
                        if (data.exchangeRate.get(i).purchaseRate != null) {
                            currency1Buy = currency1Buy + data.exchangeRate.get(i).purchaseRate + "\n";
                        }
                    }
                    textViewCurrency1Buy.setText(currency1Buy);

                    String currency1Sell = "";

                    for (int i = 0; i < data.exchangeRate.size(); i++) {
                        if (data.exchangeRate.get(i).purchaseRate != null) {
                            currency1Sell = currency1Sell + data.exchangeRate.get(i).saleRate + "\n";
                        }
                    }
                    textViewCurrency1Sell.setText(currency1Sell);
                }
            }
            super.onPostExecute(result);
        }
    }

    public HistoryFragment()
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
        ConstraintLayout cl = (ConstraintLayout)inflater.inflate(R.layout.fragment_history, container, false);

        textViewSelectDate = (TextView)cl.findViewById(R.id.textViewSelectDate);

        textViewCurrency1 = (TextView)cl.findViewById(R.id.textViewCurrency1);
        textViewCurrency1Buy = (TextView)cl.findViewById(R.id.textViewCurrency1Buy);
        textViewCurrency1Sell = (TextView)cl.findViewById(R.id.textViewCurrency1Sell);

        switchPB = (Switch)cl.findViewById(R.id.switchPB);

        textViewSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(new Date().getTime()-60*60*24);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                textViewSelectDate.setText(date);
                String url = "https://api.privatbank.ua/p24api/exchange_rates?json&date="+day+"."+month+"."+year;
                PobierzDane serwer = new PobierzDane(url);
                serwer.execute();
            }
        };

        return cl;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.textViewSelectDate:
            {
                break;
            }
            default:
            {
                break;
            }
        }
    }
}

package com.panda.harcszyfry;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.Hashtable;
import android.widget.Toast;

import java.util.PriorityQueue;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    Spinner sylWyborSzyfru;
    LinearLayout sylDodSzyfr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();


        switch(position){
            case 0:
                SylabowyFragment Sfragment = new SylabowyFragment();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, Sfragment)
                        .commit();
                break;
            case 1:
                WstecznyFragment Wfragment = new WstecznyFragment();

                fragmentManager.beginTransaction()
                .replace(R.id.container, Wfragment)
                .commit();
                break;
            case 2:
                CezarFragment Cfragment = new CezarFragment();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, Cfragment)
                        .commit();
                break;
            case 3:
                MorseFragment Mfragment = new MorseFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Mfragment)
                        .commit();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    public static class WstecznyFragment extends Fragment {

        LinearLayout hiddenWynik;
        EditText input;
        Button enButton;
        TextView wynik;
        View mV;

        public WstecznyFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_wsteczny, container, false);

            setUp(view);

            return view;
        }

        public void setUp(View v)
        {
            hiddenWynik = (LinearLayout) v.findViewById(R.id.wsteczHiddenWynik);
            input = (EditText) v.findViewById(R.id.wsteczInput);
            enButton = (Button) v.findViewById(R.id.wsteczButt);
            wynik = (TextView) v.findViewById(R.id.wsteczWynikText);
            mV=v;

            enButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dencrypt();
                }
            });

        }

        public void dencrypt()
        {
            String wprowadzone = input.getText().toString().toLowerCase();
            String odszyfr = "";
            if(wprowadzone.length()==0)
                return;

            for(int i=wprowadzone.length()-1;i>=0;i--)
            {
                odszyfr+=wprowadzone.charAt(i);
            }

            wynik.setText(odszyfr);
            hiddenWynik.setVisibility(View.VISIBLE);

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    this.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.
                    newPlainText("Odszyfrowany tekst", odszyfr);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Skopiowano do schowka", Toast.LENGTH_SHORT).show();
        }



    }

    public static class SylabowyFragment extends Fragment {

        Spinner sylWyborSzyfru;
        LinearLayout sylDodSzyfr;
        Button szyfrujButt;
        LinearLayout layWynik;
        TextView sylWynik;
        EditText sylInput;
        EditText sylDiff;


        public SylabowyFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);


            setUp(view);

            return view;
        }

        public void setUp(View v)
        {
            sylWyborSzyfru = (Spinner) v.findViewById(R.id.sylabowySpiner);
            sylDodSzyfr = (LinearLayout) v.findViewById(R.id.hiddenLayoutSzyfrSelect);
            szyfrujButt = (Button) v.findViewById(R.id.sylabowySzyfruj);
            layWynik = (LinearLayout) v.findViewById(R.id.hiddenWynikLayout);
            sylWynik = (TextView) v.findViewById(R.id.sylabowyWynik);
            sylInput = (EditText) v.findViewById(R.id.sylabowyInput);
            sylDiff = (EditText) v.findViewById(R.id.sylabowyInny);

            szyfrujButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dencrypt();
                }
            });


            sylWyborSzyfru.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if(sylWyborSzyfru.getSelectedItem().toString().matches("Inny"))
                        sylDodSzyfr.setVisibility(View.VISIBLE);
                    else
                        sylDodSzyfr.setVisibility(View.GONE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView)
                {};
            });
        }

        public void dencrypt()
        {
            Hashtable<Character, Character> table = new Hashtable<Character, Character>();

            String klucz;

            if(sylWyborSzyfru.getSelectedItem().toString().matches("Inny"))
                klucz=sylDiff.getText().toString();
            else
                klucz=sylWyborSzyfru.getSelectedItem().toString();

            if(klucz.length()%2!=0)
                return;

            String wprowadzone = sylInput.getText().toString().toLowerCase();
            String sKlucz = klucz.toLowerCase();

            for(int i=0; i<sKlucz.length();i+=2)
            {
                table.put(sKlucz.charAt(i), sKlucz.charAt(i+1));
                table.put(sKlucz.charAt(i+1), sKlucz.charAt(i));
            }

            String deszyfr = "";

            for(int i=0; i<wprowadzone.length();i++)
            {
                Character c = table.get(wprowadzone.charAt(i));
                if(c==null)
                    deszyfr+=wprowadzone.charAt(i);
                else
                    deszyfr+=c;
            }

            sylWynik.setText(deszyfr);
            layWynik.setVisibility(View.VISIBLE);

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    this.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.
                    newPlainText("Odszyfrowany tekst", deszyfr);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Skopiowano do schowka", Toast.LENGTH_SHORT).show();
        }
    }

    public static class CezarFragment extends Fragment {

        String ppz = "aąbcćdeęfghijklłmnńoóprsśtuwyzźż";
        String pbpz = "abcdefghijklmnoprstuwyz";
        String ang = "abcdefghijklmnopqrstuvwxyz";

        EditText input;
        Spinner alfabetSpinner;
        LinearLayout alfabetSelectLayout;
        EditText customAlfabet;
        Spinner przesuSpinner;
        LinearLayout przesuCustomLayout;
        EditText przesuCustom;
        LinearLayout wynikLayout;
        TextView wynik;
        Button szyfrujButt;
        Button odszyfrujButt;


        public CezarFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_cezar, container, false);

            setUp(view);

            return view;
        }

        public void setUp(View v)
        {
            input = (EditText)v.findViewById(R.id.cezarInput);
            alfabetSpinner = (Spinner)v.findViewById(R.id.cezarAlfabetSpinner);
            alfabetSelectLayout = (LinearLayout)v.findViewById(R.id.cezarHiddenAlfSelect);
            alfabetSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(alfabetSpinner.getSelectedItem().toString().matches("Inny"))
                        alfabetSelectLayout.setVisibility(View.VISIBLE);
                    else
                        alfabetSelectLayout.setVisibility(View.GONE);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
            customAlfabet = (EditText)v.findViewById(R.id.cezarAlfabetCustom);
            przesuSpinner = (Spinner)v.findViewById(R.id.cezarPrzesSpinner);
            przesuCustomLayout = (LinearLayout)v.findViewById(R.id.cezarHiddenPrzesSelect);
            przesuSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(przesuSpinner.getSelectedItem().toString().matches("Inne"))
                        przesuCustomLayout.setVisibility(View.VISIBLE);
                    else
                        przesuCustomLayout.setVisibility(View.GONE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
            przesuCustom = (EditText)v.findViewById(R.id.cezarPrzesCustom);
            wynikLayout = (LinearLayout)v.findViewById(R.id.cezarHiddenWynikLayout);
            wynik = (TextView)v.findViewById(R.id.cezarWynikText);
            szyfrujButt = (Button)v.findViewById(R.id.cezarButtSzyfruj);
            szyfrujButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dencrypt(true);
                }
            });
            odszyfrujButt = (Button)v.findViewById(R.id.cezarButtOdszyfr);
            odszyfrujButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dencrypt(false);
                }
            });
        }

        public void dencrypt(boolean encrypt)
        {
            String wprowadzone = input.getText().toString().toLowerCase();

            String alfabet;
            if(alfabetSpinner.getSelectedItem().toString().matches("Polski - bez polskich znaków"))
                alfabet = pbpz;
            else if(alfabetSpinner.getSelectedItem().toString().matches("Polski - polskie znaki"))
                alfabet = ppz;
            else if(alfabetSpinner.getSelectedItem().toString().matches("Angielski"))
                alfabet=ang;
            else
                alfabet = customAlfabet.getText().toString();

            int[] infoLiczbowo = new int[wprowadzone.length()];

            for(int i=0;i<wprowadzone.length();i++)
            {
                if(wprowadzone.charAt(i)==' ')
                {
                    infoLiczbowo[i]=-1;
                    continue;
                }

                for(int j=0; j<alfabet.length(); j++)
                {
                    if(alfabet.charAt(j)==wprowadzone.charAt(i))
                    {
                        infoLiczbowo[i]=j;
                        break;
                    }

                    if(j==alfabet.length()-1)
                        infoLiczbowo[i]=-2;
                }
            }

            int iklucz;
            if(przesuSpinner.getSelectedItem().toString().matches("Inne"))
                iklucz = Integer.parseInt(przesuCustom.getText().toString());
            else
                iklucz = Integer.parseInt(przesuSpinner.getSelectedItem().toString());

            int[] infoLiczbowoZaszyfr = new int[wprowadzone.length()];

            for(int i=0;i<infoLiczbowo.length;i++)
            {
                if(infoLiczbowo[i]<0)
                {
                    infoLiczbowoZaszyfr[i] = infoLiczbowo[i];
                    continue;
                }

                int var = infoLiczbowo[i];
                if(encrypt)
                    var += iklucz;
                else
                    var -= iklucz;

                if(var<0)
                    var = (var+alfabet.length()+1);
                else if(var>alfabet.length())
                    var = (var-alfabet.length());
                infoLiczbowoZaszyfr[i] = var;
            }

            String odszyfr = "";

            for(int i=0;i<wprowadzone.length();i++)
            {
                if(infoLiczbowoZaszyfr[i]==-1)
                {
                    odszyfr+=" ";
                    continue;
                }
                else if(infoLiczbowoZaszyfr[i]==-2)
                {
                    odszyfr+="?";
                    continue;
                }
                for(int j=0; j<alfabet.length(); j++)
                {
                    if(j==infoLiczbowoZaszyfr[i])
                    {
                        odszyfr+=alfabet.charAt(j);
                        break;
                    }
                }
            }


            wynik.setText(odszyfr);
            wynikLayout.setVisibility(View.VISIBLE);

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    this.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.
                    newPlainText("Odszyfrowany tekst", odszyfr);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Skopiowano do schowka", Toast.LENGTH_SHORT).show();
        }
    }

    public static class MorseFragment extends Fragment {

        EditText input;
        Button buttSzyfruj;
        Button buttOdszyfruj;
        Button buttPomoc;
        LinearLayout wynikLayout;
        TextView wynik;
        Hashtable<String, String> code;
        Hashtable<String, String> decode;


        public MorseFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_morse, container, false);

            setUp(view);

            return view;
        }

        public void setUp(View v)
        {
            input = (EditText)v.findViewById(R.id.morseInput);
            buttOdszyfruj = (Button)v.findViewById(R.id.morseButtOdszyfruj);
            buttOdszyfruj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dencrypt(false);
                }
            });
            buttSzyfruj = (Button)v.findViewById(R.id.morseButtSzyfruj);
            buttSzyfruj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dencrypt(true);
                }
            });
            buttPomoc = (Button)v.findViewById(R.id.morseButtPomoc);
            buttPomoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "/ = koniec litery \n // = koniec wyrazu" , Toast.LENGTH_LONG).show();
                }
            });
            wynikLayout = (LinearLayout)v.findViewById(R.id.morseWynikLayout);
            wynik = (TextView)v.findViewById(R.id.morseWynik);

            String all = "abcdefghijklmnopqrstuvwxyząćęłóśźż";
            code = new Hashtable<String, String>();
            code.put("a", ".-");
            code.put("b", "-...");
            code.put("c", "-.-.");
            code.put("d", "-..");
            code.put("e", ".");
            code.put("f", "..-.");
            code.put("g", "--.");
            code.put("h", "....");
            code.put("i", "..");
            code.put("j", ".---");
            code.put("k", "-.-");
            code.put("l", ".-..");
            code.put("m", "--");
            code.put("n", "-.");
            code.put("o", "---");
            code.put("p", ".--.");
            code.put("q","--.-");
            code.put("r", ".-.");
            code.put("s", "...");
            code.put("t", "-");
            code.put("u", "..-");
            code.put("v", "...-");
            code.put("w", ".--");
            code.put("x", "-..-");
            code.put("y", "-.--");
            code.put("z", "--..");
            code.put("ą", ".-.-");
            code.put("ć", "-.-..");
            code.put("ę", "..-..");
            code.put("ł", ".-..-");
            code.put("ó", "---.");
            code.put("ś", "...-...");
            code.put("ż", "--..-.");
            code.put("ź", "--..-");

            decode = new Hashtable<String,String>();
            for(int i=0; i<all.length();i++)
            {
                /*if(!code.containsKey(all.charAt(i)))
                {
                    Toast.makeText(getActivity(), "Brakuje: "+all.charAt(i), Toast.LENGTH_SHORT).show();
                    continue;
                }*/
                decode.put(code.get(Character.toString(all.charAt(i))), Character.toString(all.charAt(i)));
            }
            code.put(" ", "/");
        }

        public void dencrypt(boolean encrypt)
        {
            Hashtable<String, String> cryptor;
            if(encrypt)
                cryptor=code;
            else
                cryptor=decode;

            String wprowadzone = input.getText().toString().toLowerCase();
            String Swynik = "";

            String partial = "";

            for(int i=0;i<wprowadzone.length();i++)
            {
                if(!encrypt) {
                    if (wprowadzone.charAt(i) == '/') {
                        if (!cryptor.containsKey(partial)) {
                            Swynik += "?";
                        } else {
                            Swynik += cryptor.get(partial);
                        }

                        partial = "";

                        if (i != (wprowadzone.length() - 1)) {
                            if (wprowadzone.charAt(i + 1) == '/') {
                                i += 1;
                                Swynik += " ";
                            }
                        }
                    }
                    else
                        partial+=wprowadzone.charAt(i);
                }
                else
                {
                    if (!cryptor.containsKey(Character.toString(wprowadzone.charAt(i)))) {
                        Swynik += "?";
                    } else {
                        Swynik += cryptor.get(Character.toString(wprowadzone.charAt(i)));
                    }

                    if(Swynik.charAt(Swynik.length()-1)!='/')
                        Swynik+="/";
                }

            }

            wynik.setText(Swynik);
            wynikLayout.setVisibility(View.VISIBLE);

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    this.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.
                    newPlainText("Odszyfrowany tekst", Swynik);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Skopiowano do schowka", Toast.LENGTH_SHORT).show();
        }
    }
}

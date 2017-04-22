package com.example.win.racecounter.activities;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.LayoutParams;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win.racecounter.adapters.ProtocolAdapter;
import com.example.win.racecounter.adapters.RacerListViewFinishAdapter;
import com.example.win.racecounter.api.WebClient;
import com.example.win.racecounter.handlers.DialogAddRacer;
import com.example.win.racecounter.R;
import com.example.win.racecounter.handlers.DialogChangeRacer;
import com.example.win.racecounter.handlers.DialogClearStartList;
import com.example.win.racecounter.handlers.DialogRestartRace;
import com.example.win.racecounter.handlers.IdComparator;
import com.example.win.racecounter.handlers.RacersHandler;
import com.example.win.racecounter.adapters.RacerListViewAdapter;
import com.example.win.racecounter.adapters.RacerGridViewAdapter;
import com.example.win.racecounter.handlers.FilesHandler;
import com.example.win.racecounter.handlers.ReadFileTask;
import com.example.win.racecounter.handlers.SaveFileTask;
import com.example.win.racecounter.handlers.TRC;
import com.example.win.racecounter.models.Racer;
import com.example.win.racecounter.models.RacerProtocol;

import java.util.ArrayList;
import java.util.Collections;

import static android.os.SystemClock.elapsedRealtime;

public class MainActivity extends AppCompatActivity {

    public static RacerListViewAdapter startListAdapter;
    public static RacerGridViewAdapter racersAdapterGv;
    public static ProtocolAdapter protocolAdapter;
    public static RacerListViewFinishAdapter racersAdapterFinishlv;
    private static RacersHandler racersHandler;

    public static final int INITIAL_CAPACITY = 100;
    public static final int INITIAL_LAPS_COUNT = 100;
    public static ArrayList<Racer> arrayOfRacers = new ArrayList<>(INITIAL_CAPACITY);
    public static ArrayList<Racer> arrayOfRacersSorted = new ArrayList<>(INITIAL_CAPACITY);
    public static ArrayList<RacerProtocol> arrayOfRacersProtocol = new ArrayList<>(INITIAL_CAPACITY * INITIAL_LAPS_COUNT);

    //Time of start in ticks
    public static long tickStart;
    private static long tickNow;
    private static long timeDifference;
    public static boolean raceWasStarted = false;

    private static Chronometer chronometer;
    private static long timeChStart;

    public static View tableView;
    private static Context tableContext;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        chronometer = (Chronometer) findViewById(R.id.cChronometer);


/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Log.i("MAIN_ACTIVITY", "OnCreate method");

    }

    @Override
    public void onResume(){
        super.onResume();
        chronometer.setBase(timeChStart);
        if (raceWasStarted){
            chronometer.start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
/*        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/

        switch (item.getItemId()) {
            case R.id.action_add_racer:
                DialogAddRacer dialog = new DialogAddRacer();
                dialog.show(getSupportFragmentManager(), "add_racer_dialog");
                return true;
            case R.id.action_load_start_list:
                if (!raceWasStarted) {
                    String fileName = getString(R.string.file_start_list_open);
                    new ReadFileTask(MainActivity.this).execute(fileName);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.toast_race_was_already_started, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_save_start_list:
                if (!arrayOfRacers.isEmpty()) {
                    String fileNameSave = getString(R.string.file_start_list_save);
                    new SaveFileTask(MainActivity.this).execute(fileNameSave, arrayOfRacers, false);
                }
                return true;
            case R.id.action_save_results:
                String fileNameSaveResults = getString(R.string.file_results_save);
                new SaveFileTask(MainActivity.this).execute(fileNameSaveResults, arrayOfRacersSorted, true);

                return true;
            case R.id.action_clear_start_list:
                if (!raceWasStarted) {
                    DialogClearStartList dialogClearStartList = new DialogClearStartList();
                    dialogClearStartList.show(getSupportFragmentManager(), "clear_start_list_dialog");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.toast_race_was_already_started, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_clear_race:
                DialogRestartRace dialogRestartRace = new DialogRestartRace();
                dialogRestartRace.show(getSupportFragmentManager(), "restart_race_dialog");
                return true;
            case R.id.action_start_race:
                if (!raceWasStarted && startRace()){
                    if (tickStart == 0){
                        tickStart = elapsedRealtime();
                        startChronometer();
                    } else {
                        chronometer.setVisibility(View.VISIBLE);
                    }

                    Vibrator v = (Vibrator) getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(50);
                    Log.v("MAIN_ACTIVITY", "MainActivity tickStart STARTED");
                    raceWasStarted = true;

                    Collections.sort(arrayOfRacers, new IdComparator());
                    notifyAllAdapters();

                    Log.v("MAIN_ACTIVITY", "arrayOfRacers sorted by ID");
                    Toast.makeText(getApplicationContext(), R.string.toast_race_was_started, Toast.LENGTH_LONG).show();
                }
                else {
                    if (raceWasStarted){
                        Toast.makeText(getApplicationContext(), R.string.toast_race_was_already_started, Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.toast_add_racer, Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            case R.id.action_stop_race:
                Vibrator v = (Vibrator) getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);
                chronometer.setVisibility(View.INVISIBLE);
                raceWasStarted = false;
                racersAdapterGv.notifyDataSetChanged();

                return true;
            case R.id.action_send_data_toserver:
                String fileName = getString(R.string.file_results_save);
                WebClient webClient = new WebClient(findViewById(R.id.container));
                webClient.setData2Send(FilesHandler.readJsonFileToString(fileName));
                webClient.post();
/*            case R.id.action_settings:
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private static void startChronometer() {
        timeChStart = elapsedRealtime();
        chronometer.setBase(timeChStart);
        chronometer.setVisibility(View.VISIBLE);
        chronometer.start();
    }

    private static void stopChronometer() {
        chronometer.stop();
        chronometer.setVisibility(View.INVISIBLE);
        chronometer.setBase(elapsedRealtime());
    }

    public static void notifyAllAdapters() {
        startListAdapter.notifyDataSetChanged();
        racersAdapterGv.notifyDataSetChanged();
        protocolAdapter.notifyDataSetChanged();
        //check if user already switched to Results tab (Data center) and instance of table is created
        if (tableContext!= null){
            arrayOfRacersSorted.clear();
            arrayOfRacersSorted = new ArrayList<>(arrayOfRacers);
            RacersHandler.PopulateRacersToTable(tableContext, tableView, arrayOfRacersSorted);
            Log.v("MAIN_ACTIVITY", "Notify all adapters -> populate racers func");
        }
    }

    public static void restartRace() {
        raceWasStarted = false;
        RacersHandler.restartRace();

        stopChronometer();
        //tickStart = elapsedRealtime();
        Log.v("MAIN_ACTIVITY", "MainActivity restartRace() method");
        MainActivity.notifyAllAdapters();
        //Toast.makeText(getContext(), "Restarting of race", Toast.LENGTH_SHORT).show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            /*View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;*/

            View rootView = null;
            int position = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (position){
                case 1: //fragment_start_list
                    Log.d("MAIN_ACTIVITY", "Case 1");

                    rootView = inflater.inflate(R.layout.framgent_start_list, container, false);

                    ListView lvRegistration = (ListView) rootView.findViewById(R.id.lvRegistration);
                    loadList(lvRegistration, getContext());

                    lvRegistration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DialogChangeRacer dialog = new DialogChangeRacer();
                            dialog.setPostition(position);
                            dialog.show(getFragmentManager(), "change_racer_dialog");

                        }
                    });

                    /*if (startRace()){
                        //btStartRace.setEnabled(false);
                        raceWasStarted = true;
                    }*/

                    break;
                case 2: //fragment_buttons
                    Log.d("MAIN_ACTIVITY", "Case 2");

                    rootView = inflater.inflate(R.layout.fragment_buttons, container, false);

                    GridView gvButtons = (GridView) rootView.findViewById(R.id.gvButtons);
                    racersAdapterGv = new RacerGridViewAdapter(getContext(), arrayOfRacers);
                    gvButtons.setAdapter(racersAdapterGv);

                    ListView lvProtocol = (ListView) rootView.findViewById(R.id.lvProtocol);
                    protocolAdapter = new ProtocolAdapter(getContext(), arrayOfRacersProtocol, getActivity());
                    lvProtocol.setAdapter(protocolAdapter);

                    break;
                case 3: //fragment_fininsh_list
                    Log.d("MAIN_ACTIVITY", "Case 3");

                    rootView = inflater.inflate(R.layout.racer_finish_table, container, false);
                    tableView = rootView;
                    tableContext = getContext();

                    arrayOfRacersSorted = new ArrayList<>(arrayOfRacers);
                    RacersHandler.PopulateRacersToTable(getContext(), rootView, arrayOfRacersSorted);

                    break;
                case 4:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);

                    TextView textView2 = (TextView) rootView.findViewById(R.id.section_label);
                    textView2.setText("Case 4");
                    break;
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tabStartList);
                case 1:
                    return getString(R.string.tabRaceField);
                case 2:
                    return getString(R.string.tabDataCenter);
            }
            return null;
        }
    }

    private static void loadList(ListView listView, Context context) {
        //arrayOfRacers = racersHandler.generateRacers();
        startListAdapter = new RacerListViewAdapter(context, arrayOfRacers);
        listView.setAdapter(startListAdapter);
    }

    private static boolean startRace(){
        //if (arrayOfRacers!= null) {
        if (arrayOfRacers.size() > 0) {
            Log.v("MAIN_ACTIVITY", "MainActivity check if Race was started");
            return true;
        } else {
            return false;
        }
    }

}

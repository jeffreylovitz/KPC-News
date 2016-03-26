package kpc.katonahpresbyterian;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

/*
 * Initialize is the main class of this app, which is loaded on launch.
 * It extends FragmentActivity to create and oversee the two tabs of this app, Settings and Task,
 * and implements the SensorEventListener interface to ensure control over the accelerometer.
 */
public class Initialize extends FragmentActivity {

    private Context context;
    public List<String> fragments = new Vector<String>();
    public static AppSectionsPagerAdapter adapter;
    public static ViewPager pager;

    //Fragment sermon = new Sermons();
    //Sermons sermonFragment = new Sermons();

    /*
     * onCreate() is the only function that MUST be overwritten for a FragmentActivity.
     * For Initialize, onCreate() renders the UI, initializes the two wakelocks,
     * registers a sensor listener for the accelerometer, and starts the first
     * asynctask - hash table generation.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setContentView(R.layout.activity_initialize);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new AppSectionsPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
        */

        this.context = this;

        setContentView(R.layout.pager_init);

        fragments.add(Sermons.class.getName());
        fragments.add(Welcome.class.getName());
        fragments.add(PresNotesHistory.class.getName());

        pager = (ViewPager)findViewById( R.id.pager );
        adapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);

        /*
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new AppSectionsPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
        */
    }


    /*
     * The following overriding functions release both wakelocks when the OS steps in to change
     * phases of the app's lifecycle.
     * Both releases can cause exceptions if the wakelock exists, but has not been acquired,
     * but these exceptions require no handling.
     */
    @Override
    public void onPause(){
        try {
        } catch (Throwable e){
        }
        super.onPause();
    }
    @Override
    public void onStop(){
        try {
        } catch (Throwable e){
        }
        super.onStop();
    }

    @Override
    public void onDestroy(){
        try {
        } catch (Throwable e){
        }
        super.onDestroy();
    }

    /*
     * An object of this class is instantiated during the onCreate() call of the primary app
     * object, Initialize.  It is responsible for managing the UI, which is comprised of 2 fragments.
     */
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public List<String> fragmentList;

        public AppSectionsPagerAdapter(FragmentManager manager) {
            super(manager);
            fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            //return MyFragment.newInstance();
            return Fragment.instantiate(context, fragmentList.get(position));


        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Sermons";
            }
            else if (position == 1) {
                return "Katonah Presbyterian Church";
            }
            else {
                return "PresNotes";
            }
        }

        @Override
        public int getCount() {
            // return CONTENT.length;
            return 3;
        }

        /*
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        */
    }
        /*


        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = Fragment.instantiate(context, Sermons.class.getName());
                    break;
                case 1:
                    fragment = Fragment.instantiate(context, Welcome.class.getName());
                    break;
                case 2:
                    fragment = Fragment.instantiate(context, PresNotes.class.getName());
                    break;
            }
            return fragment;

        }
        */

        /*
          * The following two functions are necessary overrides for FragmentPagerAdapter classes,
          * serving self-evident purposes.
          */
        /*
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Sermons";
            }
            else if (position == 1) {
                return "Katonah Presbyterian Church";
            }
        else {
                return "PresNotes";
            }
        }
    }
        */


    /*
     * The Settings object is a UI element (Fragment) responsible for all user input, progress
     * tracking, and battery management.  These functions, however, are largely fulfilled by
     * button handlers and updates to the object's TextViews.
     */
    public static class Sermons extends Fragment {

        private Context context;

        //@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            context = getActivity().getApplicationContext();

            View rootView = inflater.inflate(R.layout.sermons, container, false);

            ((TextView) rootView.findViewById(R.id.opentab)).setText("Sermons tab open.");

            return rootView;
        }
    }


    public static class Welcome extends Fragment {

        private Context context;

        //@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            context = getActivity().getApplicationContext();

            View rootView = inflater.inflate(R.layout.welcome, container, false);

            ((TextView) rootView.findViewById(R.id.opentab)).setText("Welcome tab open.");

            return rootView;
        }
    }


    public void startSermon(View v){
        Context context = getApplicationContext();
        CharSequence text = "Latest sermon requested";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        startActivity(new Intent(getApplicationContext(), MP3Player.class));

    }

    public void startPresNotes(View v){

        /*
        Context context = getApplicationContext();
        CharSequence text = "Latest PresNotes requested";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        */


        pager.setCurrentItem(1);

        ReadPresNotes presNotesFragment = new ReadPresNotes();
        Bundle args = new Bundle();
        //args.putInt(ReadSermon.ARG_POSITION, position);
        presNotesFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    // Replace whatever is in the fragment_container view with this fragment,
    // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.front_screen, presNotesFragment, "New front screen");
        //transaction.replace(((ViewGroup)(getView().getParent())).getId(), presNotesFragment);

        transaction.addToBackStack(null);

    // Commit the transaction
        transaction.commit();

        /*
        setContentView(R.layout.activity_initialize);
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.front_screen, new ReadSermon(), "read_presnotes");
        ft.commit();
    */
    }

    public static class ReadPresNotes extends Fragment {
        private Context context;

        //@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            context = getActivity().getApplicationContext();

            View rootView = inflater.inflate(R.layout.read_presnotes, container, false);

            String pdfLink = "http://www.katonahpresbyterian.org/images/stories/Newsletter/2016/2016.01%20PN.pdf";

            WebView wv = (WebView) rootView.findViewById(R.id.webPage);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setDomStorageEnabled(true);
            wv.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfLink);

            //setContentView(wv);

            return rootView;
        }
    }

}

    /*
    public void parseSermonPage(View v) {
        int pageEnd = 0;
        while(pageEnd != 1) {
            try {
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder().parse(new InputSource(new StringReader(html)));

                XPathExpression xpath = XPathFactory.newInstance()
                        .newXPath().compile("//td[text()=\"Description\"]/following-sibling::td[2]");

                String result = (String) xpath.evaluate(doc, XPathConstants.STRING);
            } catch (Throwable e) {
                toastException("parsing the sermons page.");
                pageEnd = 1;
            }
        }
    }

    public void toastException(String source) {
        Context context = getApplicationContext();
        CharSequence text = "An error occurred when " + source;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
    */


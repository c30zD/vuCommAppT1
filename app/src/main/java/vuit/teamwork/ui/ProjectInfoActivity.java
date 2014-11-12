package vuit.teamwork.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import vuit.teamwork.R;

/**
 * Activitat per veure la informaci&oacute; d'un projecte concret.
 *
 * @author c30zD
 */
public class ProjectInfoActivity extends ActionBarActivity {

    private static final String[] DUMMY_LIST_ENTRIES1 = {"Hello", "Anything", "Chao"};
    private static final String[] DUMMY_LIST_ENTRIES2 = {"Fortuna", "Imperatrix", "Mundi"};
    private static final String[] DUMMY_LIST_ENTRIES3 = {"Homer", "Marge", "Bart", "Lisa", "Maggie"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);

        // TODO Get description, people, messages, etc.

        ListView mRecentActivity = (ListView) findViewById(R.id.lstRecentActivity);
        TextView lblDescription = (TextView) findViewById(R.id.lblProjectDescription);
        TextView lblID = (TextView) findViewById(R.id.lblProjectID);

        ListView lstPeople = (ListView) findViewById(R.id.lstPeopleWorking);

        ListView lstFiles = (ListView) findViewById(R.id.lstProjectFiles);

        ListView lstMessages = (ListView) findViewById(R.id.lstProjectMessages);

        // TODO Obtener info. proyecto y mostrarla

        lstPeople.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                DUMMY_LIST_ENTRIES3));

        lstMessages.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                DUMMY_LIST_ENTRIES1));

        lstFiles.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                DUMMY_LIST_ENTRIES2));

        lstPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(ProjectInfoActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        // TODO Lo que pasa al seleccionar un archivo

        lstMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(ProjectInfoActivity.this, MessageList.class);
                startActivity(intent);
            }
        });

        mRecentActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Determinar si se ha seleccionado un mensaje, archivo, etc. y actuar en consecuencia
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

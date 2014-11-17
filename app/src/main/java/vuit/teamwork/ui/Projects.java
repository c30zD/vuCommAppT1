package vuit.teamwork.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import vuit.teamwork.R;
import vuit.teamwork.utils.ProjectListBinder;

/**
 * Activitat que mostra tots els projectes.
 *
 * @author c30zD
 */
public class Projects extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView lstProjects = (ListView) findViewById(R.id.lstMultiuse);
        EditText txtSearch = (EditText) findViewById(R.id.txtSearch);

        // TODO Give data collection to ProjectListBinder
        ProjectListBinder listBinder = new ProjectListBinder(this);
        lstProjects.setAdapter(listBinder);

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Implement search
                return false;
            }
        });
        lstProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                // TODO Determinar proyecto y pasarlo a ProjectInfoActivity para que obtenga sus datos
                i = new Intent(Projects.this, ProjectInfoActivity.class);
                /* TODO send data to the intent to display on ProjectInfoActivity
                i.putExtra("PROJECT_NAME", projName);
                i.putExtra("PROJECT_DESCRIPTION", projectDescriptionReference); // Reference to fetch from DB
                i.putExtra("PROJECT_ID", projectId);        // Perhaps the only thing I need for the DB
                */
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_projects, menu);
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

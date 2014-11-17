package vuit.teamwork.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import vuit.teamwork.R;

/**
 * Activitat que mostra el perfil de l'usuari.
 *
 * @author c30zD
 */
public class ProfileActivity extends ActionBarActivity {

    private static final String[] DUMMY_LIST_ENTRIES1 = {"Hello", "Anything", "Chao"};
    private static final String[] DUMMY_LIST_ENTRIES2 = {"Fortuna", "Imperatrix", "Mundi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        TextView lblName = (TextView) findViewById(R.id.lblName);
        TextView lblInfo = (TextView) findViewById(R.id.lblInfo);
        ImageView contactImage = (ImageView) findViewById(R.id.imgContact);
        Button btnViewAllMessages = (Button) findViewById(R.id.btnTop);
        Button btnViewAllProjects = (Button) findViewById(R.id.btnBottom);
        ListView lstMessages = (ListView) findViewById(R.id.lstTop);
        ListView lstProjects = (ListView) findViewById(R.id.lstBottom);

        // TODO Obtener info. perfil
        lstMessages.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DUMMY_LIST_ENTRIES1));
        lstProjects.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DUMMY_LIST_ENTRIES2));

        btnViewAllMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MessageList.class);
                startActivity(i);
            }
        });

        btnViewAllProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, Projects.class);
                startActivity(i);
            }
        });

        lstMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                // TODO Determinar el mensaje que ha sido seleccionado (id)
                // TODO Implementar la funcionalidad para cargar un mensaje en MessageActivity
                i = new Intent(ProfileActivity.this, MessageActivity.class);
                i.putExtra(MessageActivity.MESSAGE_BOARD, "room@server.com");
                startActivity(i);
            }
        });

        lstProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                // TODO Determinar el mensaje que ha sido seleccionado (id)
                // TODO Implementar la funcionalidad para cargar un proyecto en ProjectInfoActivity
                i = new Intent(ProfileActivity.this, ProjectInfoActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

package vuit.teamwork.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import vuit.teamwork.R;

/**
 * Activitat que mostra tots els contactes.
 *
 * @author c30zD
 */
public class ContactsActivity extends ActionBarActivity {

    private static final String[] DUMMY_LIST_ENTRIES3 = {"Homer", "Marge", "Bart", "Lisa", "Maggie"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView lstContacts = (ListView) findViewById(R.id.lstMultiuse);
        EditText txtSearch = (EditText) findViewById(R.id.txtSearch);

        // TODO Obtener los contactos de la empresa
        lstContacts.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                DUMMY_LIST_ENTRIES3));

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Implement search
                return false;
            }
        });
        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                // TODO Determinar contacto y pasarlo a ContactInfo para que obtenga sus datos
                i = new Intent(ContactsActivity.this, ContactInfoActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
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

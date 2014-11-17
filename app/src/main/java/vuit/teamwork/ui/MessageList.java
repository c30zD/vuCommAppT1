package vuit.teamwork.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import vuit.teamwork.R;
import vuit.teamwork.utils.MessageListBinder;

/**
 * Activitat que mostra tots els missatges.
 *
 * @author c30zD
 */
public class MessageList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView lstMessages = (ListView) findViewById(R.id.lstMultiuse);
        EditText searchTextBox = (EditText) findViewById(R.id.txtSearch);

        // TODO Obtener todos los mensajes
        MessageListBinder binder = new MessageListBinder(this);
        lstMessages.setAdapter(binder);

        lstMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Determinar el mensaje seleccionado para mostrarlo en la siguiente actividad
                Intent intent;
                intent = new Intent(MessageList.this, MessageActivity.class);
                intent.putExtra(MessageActivity.MESSAGE_BOARD, "room@server.com");
                startActivity(intent);
            }
        });

        // TODO Crear la funcionalitat del searchTextBox (addTextChangedListener ?)
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_messages, menu);
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

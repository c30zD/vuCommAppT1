package vuit.teamwork.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import vuit.teamwork.R;
import vuit.teamwork.messaging.MessengerService;

/**
 * Activitat que permet enviar missatges a altres usuaris.
 *
 * @author c30zD
 */
public class MessageActivity extends ActionBarActivity {

    public static final String MESSAGE_BOARD = "MUC - What message?";

    private String room;

    private PacketListener mMessageListener;

    private boolean mBound = false;
    private MessengerService msgService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MessengerService.Chat binder = (MessengerService.Chat) service;
            msgService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // Called when the connection with the service disconnects unexpectedly
            mBound = false;
        }
    };

    EditText mMessages;
    EditText mNewMessage;

    TextView lblUsers;
    TextView lblTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_screen);

        // TODO Populate messages from DB and get info on people chatting and tags
        int numberOfUsers = 1;
        int numberOfTags = 1;
        String dummyTag = "#Proyecto1";

        room = getIntent().getExtras().getString(MESSAGE_BOARD);

        mMessages = (EditText) findViewById(R.id.txtMessages);
        mNewMessage = (EditText) findViewById(R.id.txtMyMessage);

        lblUsers = (TextView) findViewById(R.id.lblUsers);
        lblTags = (TextView) findViewById(R.id.lblTags);
        String users = String.format(getResources().getString(R.string.number_of_participants), numberOfUsers);
        String tags = String.format(getResources().getString(R.string.number_of_tags), dummyTag, numberOfTags);
        lblUsers.setText(users);
        lblTags.setText(tags);

        mNewMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    // TODO Maybe handled should be true and I should manage errors elsewhere
                    handled = sendMessage(v.getText().toString());
                }
                return handled;

            }
        });

        mMessageListener = new PacketListener() {
            @Override
            public void processPacket(Packet packet) throws SmackException.NotConnectedException {
                Message msg = (Message) packet;
                String messageBody = msg.getBody();
                if (messageBody != null) {
                    msgService.updateDatabaseWithMessage(msg);
                    appendMessage(messageBody, msg.getFrom());
                }
            }
        };
    }

    private boolean sendMessage(String text) {
        boolean success = false;
        if (mBound) {
            try {
                msgService.sendMessage(text);
                // TODO Query this user's name
                appendMessage(text, "me:");
                success = true;
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    private void appendMessage(String message, String from) {
        mMessages.getText().append('\n');
        mMessages.getText().append(from);
        mMessages.getText().append(message);
    }

    private void setInformationFormat(int users, int tags) {
        // Por la BD: que no se quede bloqueado al sincronizar
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    msgService.joinRoom(room);
                    msgService.getRoom(room).addMessageListener(mMessageListener);
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                } catch (XMPPException.XMPPErrorException e) {
                    e.printStackTrace();
                } catch (SmackException.NoResponseException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            msgService.getRoom(room).removeMessageListener(mMessageListener);
            msgService.outOfChat(room);
            unbindService(mConnection);
            mBound = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_screen, menu);
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

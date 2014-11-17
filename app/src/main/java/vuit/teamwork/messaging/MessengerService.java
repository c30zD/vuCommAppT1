package vuit.teamwork.messaging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

import vuit.teamwork.R;
import vuit.teamwork.ui.MessageActivity;

/**
 * Servei que s'encarrega de la comunicaci&oacute; amb el servidor XMPP
 *
 * @author c30zD
 */
public class MessengerService extends Service {

    public static final int NEW_MESSAGE_NOTIFICATION = 1;

    private final IBinder mBinder = new Chat();

    private XMPPConnection connection;

    private String mRoom;

    private Hashtable<String, MultiUserChat> mucReference;

    private InvitationListener mInvitationListener;

    private PacketListener mMessageListener;

    public MessengerService() {
        connection = null;
        SmackAndroid smackAndroid = SmackAndroid.init(getApplicationContext());
        /* SmackAndroid.init(ctx);
         * AndroidConnectionConfiguration mAndroidConnectionConfiguration
         *         = new AndroidConnectionConfiguration(host, port);
         *
         * or:
         *  Class.forName(org.jivesoftware.smackx.muc.MultiUserChat.class.getName(),
         *  true, ctx.getClassLoader());
         */
        mucReference = new Hashtable<String, MultiUserChat>();
        mRoom = null;

        // Will listen on the connection
        mInvitationListener = new InvitationListener() {
            @Override
            public void invitationReceived(XMPPConnection conn, String room, String inviter, String reason, String password, Message message) {
                // TODO Signal activities of incoming invitation to join a conversation
                // TODO Make sure the user isn't already invited
            }
        };

        // Will listen on a per MUC basis
        mMessageListener = new PacketListener() {
            @Override
            public void processPacket(Packet packet) throws SmackException.NotConnectedException {
                Message msg = (Message) packet;
                if (msg.getBody() != null) {
                    String id = msg.getThread();
                    if (id != null) {
                        // TODO What if there's more than one message?
                        showNewMessageNotification(id);
                        String from = msg.getFrom();
                        // TODO join the room that sent the message: query DB by thread ID
                        updateDatabaseWithMessage(msg);
                    }
                }
            }
        };
    }

    private void showNewMessageNotification(String threadId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getResources().getText(R.string.new_message_arrived))
                .setContentText("Tap to see");
        Intent resultIntent = new Intent(MessengerService.this, MessageActivity.class);
        resultIntent.putExtra(MessageActivity.MESSAGE_BOARD, threadId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MessengerService.this);
        stackBuilder.addParentStack(MessageActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // ID NEW_MESSAGE_NOTIFICATION allows you to update the notification later on.
        mNotificationManager.notify(NEW_MESSAGE_NOTIFICATION, builder.build());
    }

    @Override
    public void onCreate() {
        ConnectionConfiguration configuration;
        configuration = null;       // TODO Get the configuration
        connect(configuration);
        String[] rooms = {"Dummy", "Room1"};    // TODO Query rooms from DB
        for (String r : rooms) {
            try {
                createRoom(r);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            } catch (XMPPException.XMPPErrorException e) {
                e.printStackTrace();
            } catch (SmackException.NoResponseException e) {
                e.printStackTrace();
            }
        }
    }

    public void createConnection(String serviceName) {
        connection = new XMPPTCPConnection(serviceName);
        try {
            connection.connect();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    public void setConnection(XMPPConnection connection) {
        this.connection = connection;
    }

    // TODO Must notify whether the connection was successful or not

    public void connect(final ConnectionConfiguration configuration) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                XMPPConnection conn = new XMPPTCPConnection(configuration);
                try {
                    conn.connect();
                    setConnection(conn);
                    MultiUserChat.addInvitationListener(conn, mInvitationListener);
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Crea una sala de xat multiusuari ({@link org.jivesoftware.smackx.muc.MultiUserChat}).
     *
     * @param room La sala a qu&egrave; correspon el xat
     * @throws SmackException.NotConnectedException
     * @throws XMPPException.XMPPErrorException
     * @throws SmackException.NoResponseException
     */
    private void createRoom(String room) throws SmackException.NotConnectedException,
            XMPPException.XMPPErrorException, SmackException.NoResponseException {
        MultiUserChat muc = new MultiUserChat(connection, room);
        muc.addMessageListener(mMessageListener);
        muc.join(getUserID());
        mucReference.put(room, muc);
        mRoom = room;
    }

    public void joinRoom(String room) throws SmackException.NotConnectedException,
            XMPPException.XMPPErrorException, SmackException.NoResponseException {
        if (mucReference.containsKey(room)) {
            // If it exists, this allows for new messages to be handled by an activity
            // This way, notifications will not be necessary
            mucReference.get(room).removeMessageListener(mMessageListener);
        }
    }

    public void outOfChat(String currentRoom) {
        // TODO if (!mRoom.equals(currentRoom))
        if (mucReference.containsKey(currentRoom)) {
            mucReference.get(currentRoom).addMessageListener(mMessageListener);
        }
    }

    /**
     * Torna la refer&egrave;ncia al xat multiusuari
     * ({@link org.jivesoftware.smackx.muc.MultiUserChat}) que correspon
     * a la sala indicada.
     *
     * @param room La sala a qu&egrave; correspon el xat
     * @return Refer&egrave;ncia al {@link org.jivesoftware.smackx.muc.MultiUserChat} al qual
     * correspon la sala
     */
    public MultiUserChat getRoom(String room) {
        return mucReference.get(room);
    }

    public void sendMessage(String message) throws XMPPException, SmackException.NotConnectedException {
        MultiUserChat muc = mucReference.get(mRoom);
        Message msg = muc.createMessage();
        msg.setThread(mRoom);
        msg.setBody(message);
        muc.sendMessage(msg);
    }

    private Iterator<String> getRegisteredRooms() {
        Iterator<String> iter = null;
        // TODO Get collection.iterator from calling the DB.
        return iter;
    }

    public void updateDatabaseWithMessage(Message msg) {
        // TODO Update DB
    }

    /**
     * Obt&eacute; el nom d'usuari utilitzat als xats.
     *
     * @return {@link java.lang.String} que representa el nom d'usuari utilitzat als xats.
     */
    public String getUserID() {
        // TODO Ask DB for user nickname to join rooms (must be unique)
        return null;
    }

    public XMPPConnection getConnection() {
        return connection;
    }

    // TODO Conocer las credenciales del usuario

    /**
     * Retorna informaci&oacute; sobre els contactes.
     *
     * @return Un {@link org.jivesoftware.smack.Roster} o {@code null} si la connexi&oacute;
     * no ha estat inicialitzada
     */
    public Roster getRoster() {
        Roster contacts = null;
        if (connection != null) {
            contacts = connection.getRoster();
        }
        return contacts;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class Chat extends Binder {

        public MessengerService getService() {
            return MessengerService.this;
        }
    }
}

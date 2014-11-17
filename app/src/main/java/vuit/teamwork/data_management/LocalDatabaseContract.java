package vuit.teamwork.data_management;

import android.database.Cursor;
import android.provider.BaseColumns;

/**
 *
 * @author c30zD
 */
public class LocalDatabaseContract {

    public static final String SQL_CREATE_TABLE_CONTACT =
            "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
            ContactEntry.COLUMN_NAME_ENTRY_ID + " TEXT PRIMARY KEY, " +
            ContactEntry.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
            ContactEntry.COLUMN_NAME_DEPARTMENT + " TEXT NOT NULL, " +
            ContactEntry.COLUMN_NAME_POSITION + " TEXT NOT NULL, " +
            ContactEntry.COLUMN_NAME_PHOTO_REFERENCE + "TEXT);";

    public static final String SQL_CREATE_TABLE_PROJECT =
            "CREATE TABLE " + ProjectEntry.TABLE_NAME + " (" +
            ProjectEntry.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY" +
            ProjectEntry.COLUMN_NAME_PROJECT_NAME + " TEXT NOT NULL);";

    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "Contacto";
        public static final String COLUMN_NAME_ENTRY_ID = "dni";
        public static final String COLUMN_NAME_NAME = "nombre";
        public static final String COLUMN_NAME_DEPARTMENT = "departamento";
        public static final String COLUMN_NAME_POSITION = "puesto";
        public static final String COLUMN_NAME_PHOTO_REFERENCE = "foto";
    }

    public static abstract class ProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "Proyecto";
        public static final String COLUMN_NAME_ENTRY_ID = "id_proyecto";
        public static final String COLUMN_NAME_PROJECT_NAME = "nombre_proyecto";
        public static final String COLUMN_NAME_PROJECT_HAS_ENDED = "finalizado";
    }

    public static abstract class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "Mensaje";
        public static final String COLUMN_NAME_ENTRY_ID_1 = "id_usr";
        public static final String COLUMN_NAME_ENTRY_ID_2 = "id_conv";
        public static final String COLUMN_NAME_ENTRY_ID_3 = "fecha";
        public static final String COLUMN_NAME_MESSAGE_CONTENT = "contenido";
    }

    public static abstract class ConversationEntry implements BaseColumns {
        // TODO Checl if it's really necessary
        public static final String TABLE_NAME = "Conversacion";
        public static final String COLUMN_NAME_ENTRY_ID = "id_conv";
    }

    public static abstract class TagEntry implements BaseColumns {
        public static final String TABLE_NAME = "Tag";
        public static final String COLUMN_NAME_ENTRY_ID = "id_tag";
        public static final String COLUMN_NAME_VALUE = "deriv";     // Tag derivado de otro
    }

    public static abstract class FileEntry implements BaseColumns {
        public static final String TABLE_NAME = "Archivo";
        public static final String COLUMN_NAME_ENTRY_ID = "id_archivo";
        public static final String COLUMN_NAME_FILENAME = "nombre";
        public static final String COLUMN_NAME_CONTENT = "contenido";
        public static final String COLUMN_NAME_SIZE = "tam";
        //public static final String COLUMN_NAME_BAK = "bak";
    }

    public static abstract class MessageMarkedWithTagEntry implements BaseColumns {
        public static final String TABLE_NAME = "Marcado_Con";
        public static final String COLUMN_NAME_ENTRY_ID_CONVERSATION = "id_conv";
        public static final String COLUMN_NAME_ENTRY_ID_TAG = "id_tag";
    }

    public static abstract class UserWorksOnProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "Trabaja_En";
        public static final String COLUMN_NAME_ENTRY_ID_USER = "id_usr";
        public static final String COLUMN_NAME_ENTRY_ID_PROJECT = "id_proy";
    }

    public static abstract class FileContainedInProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "Contenido_En";
        public static final String COLUMN_NAME_ENTRY_ID_PROJECT = "id_proy";
        public static final String COLUMN_NAME_ENTRY_ID_FILE = "id_arch";
    }

    public static abstract class TagRelationToProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "Rel_Pro";
        public static final String COLUMN_NAME_ENTRY_ID_PROJECT = "id_proy";
        public static final String COLUMN_NAME_ENTRY_ID_TAG = "id_tag";
    }

    public static abstract class TagRelationToFileEntry implements BaseColumns {
        public static final String TABLE_NAME = "Rel_Arc";
        public static final String COLUMN_NAME_ENTRY_ID_FILE = "id_arch";
        public static final String COLUMN_NAME_ENTRY_ID_TAG = "id_tag";
    }
}

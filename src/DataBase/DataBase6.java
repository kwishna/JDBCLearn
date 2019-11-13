package DataBase;

import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.sql.*;
import java.util.Arrays;

public class DataBase6 {

    /**
     * New Way Of Creating An Object Using Reflection API. Since, <code>Class c = String.class; String s = c.newInstance("Hello"); Is @Deprecated </code>
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private void createAnObjectUsingReflection() throws Exception {

        Class s = Class.forName("java.lang.String");
        Constructor cons = s.getConstructor(s);
        String ss = (String) cons.newInstance("Hello");
        System.out.println(ss);
    }

    /**
     * Sqlite Query Execution Using {@link SQLiteDataSource}
     * @param path Complete Path Of The DB File
     */
    private void sqliteDataSource(String path, String query) {

        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + path);

        try (Connection conct = ds.getConnection();
             Statement state = conct.createStatement();
//           ResultSet set = state.executeQuery("SELECT * FROM playlists")){
             ResultSet set = state.executeQuery(query)) {
            while (set.next()) {

                System.out.println(set.getObject(2));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creating Sqlite Database.
     * @param dbName Database Name Which Is To Be Created.
     */
    private void createDB(String dbName) throws IOException {

        if(!dbName.endsWith(".db")) {

            dbName = dbName + ".db";
        }

        final String newDb = dbName;

        boolean isCreate = true;
        // Checking If DB Already Exists!
        File f = new File(System.getProperty("user.dir")+"/Resources/sqliteDB/");
        String[] files = f.list();
        long i = Arrays.asList(files).stream().filter( a -> a.equalsIgnoreCase(newDb)).count();

        if(i > 0){
            System.out.println("DB Already Exists!\n"+f.getAbsolutePath()+"\\"+newDb);
            isCreate = false;
        }

        if(isCreate) {

            try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + "./Resources/sqliteDB/"+dbName)
            ) {

                System.out.println("DB Is Created At Location : " + System.getProperty("user.dir") + "/Resources/sqliteDB/" + dbName);
            } catch (Exception e) {

                System.out.println(e.getMessage());
            }

        }
    }

    public static void main(String[] args) throws Exception {

        DataBase6 d = new DataBase6();
        d.createDB("baba");
    }
}
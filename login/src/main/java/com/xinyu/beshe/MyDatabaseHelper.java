package com.xinyu.beshe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mcontext;
    private static final String DB_NAME = "person.db";
    private static final String TABLE_NAME = "person";
    //中间有逗号，结尾没有逗号
    public static final String CREATE_DATABASE =
            "create table person ("
                    + "_id integer primary key,"
                    + "nickname varchar(40) not null, "
                    + "phone_number varchar(40) not null,"
                    + "password varchar(40) not null)";

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long insert(User user) {
        ContentValues contentValues = new ContentValues();
        //主键自增的话，这里不用insert，id
        contentValues.put("nickname", user.getNickname());
        contentValues.put("phone_number", user.getPhone_number());
        contentValues.put("password", user.getPassword());

        SQLiteDatabase db = getWritableDatabase();
        return db.insert("person", null, contentValues);
    }


    public void update(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", user.getId());
        contentValues.put("nickname", user.getNickname());
        contentValues.put("phone_number", user.getPhone_number());
        contentValues.put("password", user.getPassword());

        SQLiteDatabase db = getWritableDatabase();
        //String s = Integer.toString(i);
        db.update("person", contentValues, "id=?", new String[]{Integer.toString(user.getId())});
    }

    //Cursor cursor = db.query("person",new String[]{"password"},
    // "password = ?",new String []{username},null,null,null);


    public Cursor queryByphoneNumberANDpassword(String phoneNumber,String password) {

        SQLiteDatabase db = getReadableDatabase();

        return db.query("person",new String[]{"password","nickname"},
                "phone_number = ?",new String []{phoneNumber},null,null,null);

    }



    public int deleteItem(User user) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("person", "id=?", new String[]{Integer.toString(user.getId())});
    }

//    public Cursor countByMonth() {
//
//        //String[] columns = new  String[] { "sum(money) AS summoney,date" };
//
//        String[] columns =new  String[] { "sum(money) AS summoney,substr(date,1,7)AS date" };
//
//        //select substr(日期,1,7),sum(金额) from table group by substr(日期,1,7)
//        //date.substring(1,7);
//        SQLiteDatabase db = getReadableDatabase();
//        return db.query(TABLE_NAME, columns, null, null,
//                "substr(date,1,7)", null, null);
//    }

    public Cursor queryAll() {
        SQLiteDatabase db = getReadableDatabase();

        /*
        * TABLE_NAME,表名
        * columns,列名
        * selection,WHERE  selection = ”City=?";
        * selectionArgs,selectionArgs = {"Beijing"};
                 也就是说selectionArgs中的字符串就是对应selection中的问号所代表的变量。实际上就是让selection中的过滤条件City可以动态的赋值，而不是写死在程序当中。
        * groupBy,分组
        * having,String having对应SQL语句HAVING后面的字符串
        * orderBy,id DESC,排序
        *
        * */
        return db.query(TABLE_NAME, null, null,
                null, null, null, "_id DESC");

    }

    public int deleteAllItem() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }


}




 /*

    例如你输入一个值：first。
    varchar(10)则只保存五个字符。
    CHAR（10）依然会保存10个字符

 @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "("
                + "_id integer primary key, "
                + ID + " varchar, "
                + TITLE + " varchar, "
                + MONEY + " integer, "
                + DATE + " varchar"
                + ")");
    }


    */



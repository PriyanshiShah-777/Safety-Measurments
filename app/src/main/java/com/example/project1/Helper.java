package com.example.project1;

import android.widget.EditText;

public class Helper{
     public EditText message;

     public Helper(EditText editText){
         this.message = editText;
     }
    public EditText getEdittext(){
        return message;
    }
    public void setEdittext(EditText editText){
        this.message = editText;
    }


}
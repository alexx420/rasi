/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.util;

import java.util.Calendar;

/**
 *
 * @author rafael.esquivel
 */
public class Tool {

    public static java.sql.Date toSqlDate(java.util.Date utilDate) {
        if (utilDate == null) {
            return null;
        } else {
            java.util.Calendar cal = Calendar.getInstance();
            cal.setTime(utilDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return new java.sql.Date(cal.getTime().getTime()); // your sql date
        }
    }

}

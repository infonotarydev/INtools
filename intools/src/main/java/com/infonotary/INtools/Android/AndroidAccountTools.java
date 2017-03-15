package com.infonotary.INtools.Android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Patterns;

import com.infonotary.INtools.IO.IOMisc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("ResourceType")
public class AndroidAccountTools {

    /**
     * Gets the first account on the device.
     * Requires {@link android.Manifest.permission#GET_ACCOUNTS}
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static String getAccount(Context context) {
        String name = "";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        if (accounts.length > 0) {
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    name = account.name;
                    return name;                                                                    //to get the first found account, not the last
                }
            }
        }
        return name;
    }

    /**
     * Gets all accounts on the device.
     * Requires {@link android.Manifest.permission#GET_ACCOUNTS}
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static List<String> getAllAccounts(Context context) {
        List<String> emails = new ArrayList<>();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        if (accounts.length > 0) {
            for (Account account : accounts) {
                String name = account.name;
                if (emailPattern.matcher(name).matches()) {
                    emails.add(name);
                }
            }
        }
        return IOMisc.removeDuplicates(emails);
    }
}
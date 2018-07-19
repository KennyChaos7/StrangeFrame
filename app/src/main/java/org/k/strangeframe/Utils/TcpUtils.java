/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * E-mail: kennychaos7@gmail.com
 */

package org.k.strangeframe.Utils;

/**
 * Created by Kenny on 18-7-14.
 */
public class TcpUtils {
    private static TcpUtils sTcpUtils = null;

    public TcpUtils getInstance()
    {
        synchronized (TcpUtils.class) {
            if (sTcpUtils == null)
                sTcpUtils = new TcpUtils();
        }   return sTcpUtils;
    }

}

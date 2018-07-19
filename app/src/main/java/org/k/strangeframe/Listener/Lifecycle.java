/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * E-mail: kennychaos7@gmail.com
 */

package org.k.strangeframe.Listener;

import android.support.annotation.NonNull;

/**
 * Created by Kenny on 18-7-19.
 */
public abstract class Lifecycle {

    public abstract void addObserver(@NonNull LifecycleObserver observer);

    public abstract void removeObserver(@NonNull LifecycleObserver observer);

    public abstract State getCurrentState();

    public enum Event
    {
        ON_CREATE,
        ON_START,
        ON_RESUME,
        ON_PAUSE,
        ON_STOP,
        ON_DESTROY,
        ON_ANY
    }

    public enum State
    {
        DESTROYED,
        INITIALIZED,
        CREATED,
        STARTED,
        RESUMED
    }
}
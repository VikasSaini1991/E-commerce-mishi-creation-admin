package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.crinoidtechnologies.mishicreationadmin.R;


public  class CustomLoaderBar extends android.app.Dialog{



    public CustomLoaderBar() {
        super(null);
    }
    public CustomLoaderBar(@NonNull Context context) {
        super(context);
    }

    /**
     * Creates a dialog window that uses a custom dialog style.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     * <p>
     * The supplied {@code theme} is applied on top of the context's theme. See
     * <a href="{@docRoot}guide/topics/resources/available-resources.html#stylesandthemes">
     * Style and Theme Resources</a> for more information about defining and
     * using styles.
     *
     * @param context    the context in which the dialog should run
     * @param themeResId a style resource describing the theme to use for the
     *                   window, or {@code 0} to use the default dialog theme
     */
    public CustomLoaderBar(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomLoaderBar(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_loader_bar);
        if(getWindow() != null)
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}

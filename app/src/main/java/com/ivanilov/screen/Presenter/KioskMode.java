package com.ivanilov.screen.Presenter;

import android.view.View;

public class KioskMode {

    public void hide(final View view){

        //"hides" back, home and return button on screen.
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN);

        view.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                    View.SYSTEM_UI_FLAG_FULLSCREEN);
                        } }
                });

    }
}

package ca.mickealn.expbottles;

import ca.mickealn.expbottles.events.ExpBottlesListener;
import ca.mickealn.expbottles.util.ExpBottlesConfigurationContext;

import com.amshulman.mbapi.MbapiPlugin;

public final class ExpBottles extends MbapiPlugin {

    public void onEnable() {
        registerEventHandler(new ExpBottlesListener(new ExpBottlesConfigurationContext(this)));

        super.onEnable();
    }
}

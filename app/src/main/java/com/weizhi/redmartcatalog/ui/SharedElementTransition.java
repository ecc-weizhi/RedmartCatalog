package com.weizhi.redmartcatalog.ui;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SharedElementTransition extends TransitionSet {
    public SharedElementTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}
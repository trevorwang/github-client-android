package mingsin.github.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by trevorwang on 17/12/2016.
 */
class DashboardFragment : BaseFragment() {
    override fun onInject() {
        activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(activity)
    }
}
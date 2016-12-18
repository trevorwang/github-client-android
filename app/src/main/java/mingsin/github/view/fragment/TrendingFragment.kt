package mingsin.github.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import mingsin.github.R
import mingsin.github.data.GithubApiService
import mingsin.github.databinding.FragmentTrendingBinding
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by trevorwang on 17/12/2016.
 */
class TrendingFragment : BaseFragment() {
    @Inject lateinit var api: GithubApiService
    override fun onInject() {
        activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTrendingBinding>(inflater, R.layout.fragment_trending, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        api.contributors("square", "retrofit").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d("get contributions %s", it)
                }) {
                    Logger.e(it, "")
                }
    }
}
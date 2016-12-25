package mingsin.github.di

import dagger.Component
import mingsin.github.view.activity.MainActivity
import mingsin.github.view.fragment.DashboardFragment
import mingsin.github.view.fragment.TrendingFragment
import mingsin.github.view.fragment.UserFragment

/**
 * Created by trevorwang on 10/12/2016.
 */
@ForActivity
@Component(modules = arrayOf(ActivityModule::class), dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: TrendingFragment)
    fun inject(fragment: DashboardFragment)
    fun inject(fragment: UserFragment)
}
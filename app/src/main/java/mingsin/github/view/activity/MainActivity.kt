package mingsin.github.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.SparseArray
import android.view.MenuItem
import mingsin.github.R
import mingsin.github.databinding.ActivityMainBinding
import mingsin.github.view.fragment.BaseFragment
import mingsin.github.view.fragment.DashboardFragment
import mingsin.github.view.fragment.TrendingFragment
import mingsin.github.view.fragment.UserFragment

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawer: DrawerLayout
    val fragmentList: SparseArray<BaseFragment> = SparseArray()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.appBar.toolbar)

        drawer = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, binding.appBar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)

        selectDrawerMenuItem(R.id.nav_dashboard)
    }

    fun switchTo(fragment: Fragment?) {
        if (fragment == null) {
            return
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(fragment.tag).commit()
    }

    override fun onInject() {
        activityComponent.inject(this)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectDrawerMenuItem(item.itemId)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun selectDrawerMenuItem(itemId: Int) {
        var fragment = fragmentList.get(itemId)
        if (fragment == null) {
            when (itemId) {
                R.id.nav_dashboard -> {
                    fragment = DashboardFragment()
                }
                R.id.nav_trending -> {
                    fragment = TrendingFragment()
                }
                R.id.nav_user -> {
                    fragment = UserFragment()
                }
            }
        }
        switchTo(fragment)
        fragmentList.put(itemId, fragment)
    }
}

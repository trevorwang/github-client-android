package mingsin.github.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import mingsin.github.R
import mingsin.github.databinding.ActivityMainBinding
import mingsin.github.view.fragment.DashboardFragment
import mingsin.github.view.fragment.TrendingFragment

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawer: DrawerLayout
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

        val fragment = TrendingFragment()
        switchTo(fragment)

    }

    fun switchTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
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
        when (item.itemId) {
            R.id.nav_camera -> {
                val fragment = DashboardFragment()
                switchTo(fragment)
            }

            R.id.nav_gallery -> {
                switchTo(TrendingFragment())
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}

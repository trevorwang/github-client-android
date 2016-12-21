package mingsin.github.view.fragment

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import mingsin.github.LanguageUtility
import mingsin.github.R
import mingsin.github.data.GithubApiService
import mingsin.github.databinding.FragmentTrendingBinding
import mingsin.github.databinding.ItemTrendingBinding
import mingsin.github.model.Repository
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by trevorwang on 17/12/2016.
 */
class TrendingFragment : BaseFragment() {
    @Inject lateinit var api: GithubApiService
    @Inject lateinit var lanUtils: LanguageUtility
    lateinit var adapter: TrendingAdapter
    override fun onInject() {
        activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTrendingBinding>(inflater, R.layout.fragment_trending, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.getBinding<FragmentTrendingBinding>(view)
        binding.rvRepos.layoutManager = LinearLayoutManager(context)
        adapter = TrendingAdapter(context, lanUtils)
        binding.rvRepos.adapter = adapter
        binding.rvRepos.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscriptions.add(api.trending("created:>2016-12-17").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.v("get repos %s", it)
                    adapter.repos = it.items
                }) {
                    Logger.e(it, "")
                })
    }

    class ItemHolder<out T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)

    class TrendingAdapter(val context: Context, val languageUtility: LanguageUtility) : RecyclerView.Adapter<ItemHolder<ItemTrendingBinding>>() {
        var repos: List<Repository> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder<ItemTrendingBinding> {
            val inflater = LayoutInflater.from(context)
            val binding = DataBindingUtil.inflate<ItemTrendingBinding>(inflater, R.layout.item_trending, parent, false)
            return ItemHolder(binding)
        }

        override fun onBindViewHolder(holder: ItemHolder<ItemTrendingBinding>?, position: Int) {
            val repo = repos[position]
            holder?.binding?.repo = repo
            holder?.binding?.lanUtility = languageUtility
            holder?.binding?.root?.setOnClickListener { v ->
                openProjectPage(repo)
            }
        }

        private fun openProjectPage(repo: Repository) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(repo.htmlUrl)
            context.startActivity(intent)
        }

        override fun getItemCount(): Int {
            return repos.count()
        }
    }

}
package mingsin.github.view.fragment

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.net.Uri
import android.os.Bundle
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
import mingsin.github.databinding.ItemRepoBinding
import mingsin.github.databinding.RecyclerviewFooterBinding
import mingsin.github.model.Repository
import mingsin.github.view.InfiniteScrollListener
import mingsin.github.view.ItemHolder
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
    private lateinit var binding: FragmentTrendingBinding
    override fun onInject() {
        activityComponent.inject(this)
    }

    override fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentTrendingBinding>(inflater, R.layout.fragment_trending, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRepos.layoutManager = LinearLayoutManager(context)
        adapter = TrendingAdapter(context, lanUtils)
        binding.rvRepos.adapter = adapter
        binding.rvRepos.addOnScrollListener(object : InfiniteScrollListener(10) {
            override fun loadMore(page: Int) {
                Logger.v("loadMore.......page : %d", page)
                loadData(page)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(page: Int = 1) {
        subscriptions.add(api.trending("created:>2016-12-17", page = page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.v("get repos %s", it)
                    adapter.repos += it.items
                    hideLoadingView()
                    dataInited = true
                }) {
                    Logger.e(it, "")
                    hideLoadingView()
                })
    }


    class TrendingAdapter(val context: Context, val languageUtility: LanguageUtility) : RecyclerView.Adapter<ItemHolder<ViewDataBinding>>() {
        val ITEM_TYPE_FOOTER = 0x100
        var repos: List<Repository> = ArrayList()
            set(value) {
                val oldSize = field.size
                field = value
                if (oldSize < field.size) {
                    notifyItemRangeChanged(oldSize, itemCount)
                } else {
                    notifyDataSetChanged()
                }
            }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder<ViewDataBinding> {
            val inflater = LayoutInflater.from(context)
            if (viewType == ITEM_TYPE_FOOTER) {
                val footerBinding = DataBindingUtil.inflate<RecyclerviewFooterBinding>(inflater, R.layout.recyclerview_footer, parent, false)
                return ItemHolder(footerBinding)
            }

            val binding = DataBindingUtil.inflate<ItemRepoBinding>(inflater, R.layout.item_repo, parent, false)
            return ItemHolder<ItemRepoBinding>(binding)
        }


        override fun onBindViewHolder(holder: ItemHolder<ViewDataBinding>?, position: Int) {
            val binding = holder?.binding
            when (binding) {
                is ItemRepoBinding -> {
                    val repo = repos[position]
                    binding.repo = repo
                    binding.lanUtility = languageUtility
                    binding.root.setOnClickListener { v ->
                        openProjectPage(repo)
                    }
                }
                is RecyclerviewFooterBinding -> {

                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (repos.size == position) {
                return ITEM_TYPE_FOOTER
            }
            return super.getItemViewType(position)
        }

        private fun openProjectPage(repo: Repository) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(repo.htmlUrl)
            context.startActivity(intent)
        }

        override fun getItemCount(): Int {
            if (repos.isEmpty()) {
                return 0
            }
            return repos.count() + 1
        }
    }

}
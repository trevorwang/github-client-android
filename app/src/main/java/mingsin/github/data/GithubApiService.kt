package mingsin.github.data

import mingsin.github.model.Contributor
import mingsin.github.model.RepositoryResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable


/**
 * Created by trevorwang on 08/12/2016.
 */
interface GithubApiService {

    @GET("repos/{owner}/{repo}/contributors")
    fun contributors(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<List<Contributor>>

    @GET("search/repositories")
    fun trending(@Query("q") q: String,
                 @Query("sorts") sorts: String = "star",
                 @Query("order") order: String = " desc",
                 @Query("since") since: String? = null): Observable<RepositoryResult>

}
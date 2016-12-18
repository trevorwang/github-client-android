package mingsin.github.data

import mingsin.github.model.Contributor
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


/**
 * Created by trevorwang on 08/12/2016.
 */
interface GithubApiService {

    @GET("/repos/{owner}/{repo}/contributors")
    fun contributors(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<List<Contributor>>

}
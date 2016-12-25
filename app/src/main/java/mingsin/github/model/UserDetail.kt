package mingsin.github.model

import java.util.*

/**
 * Created by trevorwang on 25/12/2016.
 */
data class UserDetail(var user: User,
                      var name: String,
                      var compony: String,
                      var blog: String,
                      var location: String,
                      var email: String,
                      var hireable: String,
                      var bio: String,
                      var publicRepos: Int,
                      var publicGists: Int,
                      var followers: Int,
                      var folloowing: Int,
                      var createdAt: Date,
                      var updateedAt: Date
)
package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession
import java.util.*

class TripService {

    fun getTripsByUser(user: User): List<Trip> {
        val loggedUser = UserSession.loggedUser ?: throw UserNotLoggedInException()

        val isFriend = user.friends.contains(loggedUser)

        return if (isFriend) TripDAO.findTripsByUser(user) else emptyList()
    }

}

package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession
import java.util.*

class TripService {

    fun getTripsByUser(user: User): List<Trip> {
        var tripList: List<Trip> = ArrayList<Trip>()

        val loggedUser = UserSession.loggedUser ?: throw UserNotLoggedInException()

        val isFriend = user.friends.contains(loggedUser)

        if (isFriend) {
            tripList = TripDAO.findTripsByUser(user)
        }
        return tripList
    }

}

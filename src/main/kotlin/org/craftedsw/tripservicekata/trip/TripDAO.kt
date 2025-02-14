package org.craftedsw.tripservicekata.trip

import org.craftedsw.tripservicekata.exception.CollaboratorCallException
import org.craftedsw.tripservicekata.user.User

object TripDAO {

    fun findTripsByUser(user: User): List<Trip> {
        throw CollaboratorCallException("TripDAO should not be invoked on an unit test.")
    }

}

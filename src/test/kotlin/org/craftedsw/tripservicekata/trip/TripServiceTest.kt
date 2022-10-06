package org.craftedsw.tripservicekata.trip

import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.assertj.core.api.Condition
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException
import org.craftedsw.tripservicekata.user.User
import org.craftedsw.tripservicekata.user.UserSession
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TripServiceTest {
    private lateinit var user: User
    private lateinit var loggedUser: User

    @BeforeEach
    fun setUp() {
        user = spyk()
        loggedUser = User()
        mockkObject(UserSession)
    }

    @Test
    fun `getTripsByUser throws UserNotLoggedInException if user is not logged in`() {
        every { UserSession.loggedUser } returns null

        val service = TripService()

        val thrown = catchThrowable { service.getTripsByUser(user) }

        assertThat(thrown).isInstanceOf(UserNotLoggedInException::class.java)
    }

    @Test
    fun `getTripsByUser returns empty list if user is not friend of logged user`() {
        every { UserSession.loggedUser } returns loggedUser

        val service = TripService()

        assertThat(UserSession.loggedUser!!.friends).doesNotContain(user)
        assertThat(service.getTripsByUser(user)).isEmpty()
    }

    @Test
    fun `getTripsByUser returns list of user's trip if user is friend of logged user`() {
        val tripsList = listOf(Trip(), Trip())
        every { UserSession.loggedUser } returns loggedUser
        every { user.friends } returns listOf(User(), loggedUser)
        mockkObject(TripDAO)
        every { TripDAO.findTripsByUser(user) } returns tripsList

        val service = TripService()

        assertThat(user.friends).contains(UserSession.loggedUser)
        assertThat(service.getTripsByUser(user)).containsExactlyInAnyOrderElementsOf(tripsList)
    }
}

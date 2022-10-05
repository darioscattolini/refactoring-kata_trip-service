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
    lateinit var user: User
    lateinit var userSession: UserSession

    @BeforeEach
    fun setUp() {
        user = User()
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
        every { UserSession.loggedUser } returns User()

        val service = TripService()

        assertThat(UserSession.loggedUser!!.friends).doesNotContain(user)
        assertThat(service.getTripsByUser(user)).isEmpty()
    }
}
